package utils;

import spark.Request;

import javax.servlet.MultipartConfigElement;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * The type Parse text.
 *
 * @author liuweiqi
 */
public class ParseText {
    /**
     * Parse string.
     *
     * @param req  the req
     * @param name the name
     * @return the string
     */
    public static String parse(Request req,String name){
        req.attribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("d:/tmp"));
        try{
            InputStream is =
                    req.raw().getPart(name).getInputStream();
            String s = is.toString();
            ByteArrayOutputStream result =
                    new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            is.close();
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "error";

    }
}
