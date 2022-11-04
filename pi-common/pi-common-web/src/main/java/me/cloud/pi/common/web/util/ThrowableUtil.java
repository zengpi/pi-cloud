package me.cloud.pi.common.web.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具
 *
 * @author ZnPi
 * @date 2022-08-16
 */
public class ThrowableUtil {
    /**
     * 获取堆栈信息
     * @param throwable /
     * @return 堆栈信息
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter stringWriter = new StringWriter();
        try(PrintWriter printWriter = new PrintWriter(stringWriter)){
            throwable.printStackTrace(printWriter);
            return stringWriter.toString();
        }
    }
}
