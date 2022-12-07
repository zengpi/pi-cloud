package me.cloud.pi.admin;

import me.cloud.pi.common.security.constant.SecurityConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ZnPi
 * @date 2022-12-06 23:01
 */
@SpringBootTest
public class PassTest {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void test() {
        String encode = passwordEncoder.encode("admin");
        System.out.println(encode);
    }
}
