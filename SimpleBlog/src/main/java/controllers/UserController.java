package controllers;

import utils.ParseText;
import utils.UserManage;
import spark.Route;

import java.util.concurrent.ConcurrentMap;

import static spark.Spark.post;

/**
 * The type User controller.
 *
 * @author liuweiqi
 */
public final class UserController {
    /**
     * The Users.
     */
    static ConcurrentMap<String, String[]> users;
//    static ConcurrentMap<String, String[]> users_page;

    static {
        users = UserManage.getUsers();
    }

    /**
     * 用户注册路由
     * 注册时应传递username,password,email
     */
    static Route user_register = (request, response) -> {
        String username = ParseText.parse(request, "username");
        String password = ParseText.parse(request, "password");
        String userEmail = ParseText.parse(request, "userEmail");
        users.put(username, new String[]{username, password, userEmail});
        if (UserManage.setUsers(users)) {
            return "success";
        } else {
            return "fail";
        }
    };

    /**
     * 用户登录路由
     */
    static Route user_login = (request, response) -> {
        String username = ParseText.parse(request, "username");
        String password = ParseText.parse(request, "password");
//        此处获得密码
//        0：username  1：passwd 2：email
        users = UserManage.getUsers();
//        检查Map集合中是否有username: map.containsKey()
        if (users.containsKey(username)) {
            String pwd = users.get(username)[1];
            if (username != null && pwd != null && pwd.equals(password)) {
//                request.session().attribute("uname", username);
//                创建username cookie 设置到期时间为1小时
                response.cookie("/", "username", username, 3600, true);
                return "login success";
            } else {
                return "login fail";
            }
        } else {
            return "login fail";
        }
    };


    //用户主页信息修改：包括性别和个人简介
    //getPart()只能传递表单中输入的内容
    //实现将表单中的性别和个人简介保存至本地文件
    //    static Route user_page = (request, response) -> {
    //        String username = request.cookie("username");
    //        String sex = ParseText.parse(request, "sex");
    //        String resume = ParseText.parse(request, "resume");
    //        System.out.println(username + "\n" + sex + "\n" + resume);
    //        users_page.put(username, new String[]{username, sex, resume});
    //        setUsers(users_page);
    //        return 0;
    //    };

    /**
     * Init.
     */
    public static void init() {
        post("/user/login", user_login);
        post("/user/register", user_register);
//        post("/user/page", user_page);
    }
}
