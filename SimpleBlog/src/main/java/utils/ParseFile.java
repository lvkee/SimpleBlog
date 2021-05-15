package utils;

import spark.Request;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import java.io.*;
import java.util.Random;

import static spark.Spark.get;
import static spark.Spark.post;


public class ParseFile {
//    static String _pub_path = "G:/HttpSample/src/main/resources/public";

    static String _pub_path = "C:/Users/liuweiqi/IdeaProjects/HttpSample/HttpSample/src/main/resources/public";

    public static Boolean parse(Request req, String filepath, String name){
        req.attribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("/tmp"));
        // /temp 换成你自己，d:/tmp(必须存在)
        try (InputStream is = req.raw().getPart(
                name).getInputStream()) {


            System.out.println("path:"+filepath);
            //req.queryMap().
            OutputStream os = new FileOutputStream(
                    new File(filepath));
            byte[] buffer = new byte[10*1024];
            int l = 0;

            while((l=is.read(buffer))!=-1){
                os.write(buffer,0,l);
            }
            is.close();
            os.flush();
            os.close();
            return true;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return  false;

    }

    static Route show_ip = (req, res) -> {
        String ipStr = req.ip();    //获得IP
        return "{ok:0,ip:'" + ipStr + "'}";
    };

    static Route mGet = (req, res) -> {
        System.out.println(req.headers("ac"));
        String a = req.queryParams("a");
        String c = req.queryParams("c");
        System.out.println("a,c:" + a + "," + c);
//        return "{\"ok\":0}";
        return "test";
    };

    static Route mUpload = (req, res) -> {
        req.attribute("org.eclipse.jetty.multipartConfig",
                new MultipartConfigElement("D:/temp"));
        try (InputStream is = req.raw().getPart("file").getInputStream()) {

            Random random = new Random();
            random.setSeed(123045);
            String fpath = _pub_path + random.nextLong() + ".png";
            OutputStream os = new FileOutputStream(
                    fpath);
            byte[] buffer = new byte[10 * 1024];
            int l;

            while ((l = is.read(buffer)) != -1) {
                os.write(buffer, 0, l);
            }
            is.close();
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            InputStream is =
                    req.raw().getPart("name").getInputStream();
            ByteArrayOutputStream result =
                    new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int length; (length = is.read(buffer)) != -1; ) {
                result.write(buffer, 0, length);
            }
            System.out.println("name:" + result.toString());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "uploaded 1";
    };

    public static void init() {
        get("/m/get", mGet);
        get("/ip", show_ip);
        post("/m/upload", mUpload);
    }
}
