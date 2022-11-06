/*
 * Copyright 2022 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.cloud.pi.auth.extension;

import lombok.extern.slf4j.Slf4j;
import me.cloud.pi.auth.constant.SecurityConstants;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ZnPi
 * @date 2022-10-22
 */
@Slf4j
public class PiUserDetailsManager extends JdbcDaoImpl implements UserDetailsService {
    // @formatter:off
    public static final String USERS_BY_USERNAME_QUERY = "select username,password,enabled "
            + "from sys_user "
            + "where username = ? and deleted=0";
    // @formatter:on

    public static final String ROLES_BY_USERNAME_QUERY = "select r.id, r.role_code "
            + "from sys_role r "
            + "inner join sys_user_role ur on r.id = ur.role_id "
            + "inner join sys_user u on u.id = ur.user_id "
            + "where u.username = ?";

    public static final String PERMISSIONS_BY_USERNAME_QUERY = "select m.permission "
            + "from sys_menu m "
            + "inner join sys_role_menu rm on m.id = rm.menu_id "
            + "where m.permission is not null and rm.role_id in (?) ";

    public PiUserDetailsManager(JdbcTemplate jdbcTemplate) {
        super.setUsersByUsernameQuery(USERS_BY_USERNAME_QUERY);
        super.setJdbcTemplate(jdbcTemplate);
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails
     * objects. There should normally only be one matching user.
     */
    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        assert getJdbcTemplate() != null;
        return getJdbcTemplate().query(getUsersByUsernameQuery(), this::mapToUser, username);
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        assert getJdbcTemplate() != null;
        ArrayList<Long> roleIds = new ArrayList<>();
        List<SimpleGrantedAuthority> roles = getJdbcTemplate().query(
                ROLES_BY_USERNAME_QUERY,
                (rs, rowNum) -> {
                    roleIds.add(rs.getLong(1));
                    return new SimpleGrantedAuthority(SecurityConstants.ROLE + rs.getString(2));
                },
                username);
        Set<GrantedAuthority> dbAuthsSet = new HashSet<>(roles);
        if (roleIds.size() != 0) {
            List<SimpleGrantedAuthority> authorities = getJdbcTemplate().query(
                    PERMISSIONS_BY_USERNAME_QUERY,
                    (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString(1)),
                    roleIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
            dbAuthsSet.addAll(authorities);
        }

        return new ArrayList<>(dbAuthsSet);
    }

    private UserDetails mapToUser(ResultSet rs, int rowNum) throws SQLException {
        String userName = rs.getString(1);
        String password = rs.getString(2);
        boolean enabled = rs.getBoolean(3);
        boolean accLocked = false;
        boolean accExpired = false;
        boolean credsExpired = false;
        if (rs.getMetaData().getColumnCount() > 3) {
            // NOTE: acc_locked, acc_expired and creds_expired are also to be loaded
            accLocked = rs.getBoolean(4);
            accExpired = rs.getBoolean(5);
            credsExpired = rs.getBoolean(6);
        }
        return new User(userName, password, enabled, !accExpired, !credsExpired, !accLocked,
                AuthorityUtils.NO_AUTHORITIES);
    }
}