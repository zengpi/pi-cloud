package me.cloud.pi.common.web.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author ZnPi
 * @date 2022-09-15
 */
public class FileUtil {
    public static void export(HttpServletResponse response, String fileName, String suffix, Runnable runnable) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + suffix);

        runnable.run();
    }
}