import controllers.UserController;

import static spark.Spark.staticFiles;


/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        UserController.init();
    }
}
