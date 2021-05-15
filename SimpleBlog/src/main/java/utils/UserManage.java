package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserManage {
    static String usernameFile = "D:/usernameFile.txt";

    public static ConcurrentMap<String, String[]> getUsers() {
        try {
            FileInputStream fis = new FileInputStream(usernameFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ConcurrentMap<String, String[]> ob = (ConcurrentMap<String, String[]>) ois.readObject();
            ois.close();
            fis.close();
            return ob;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ConcurrentHashMap<>();
    }

/**
 * 将users中的键值对通过流的形式保存至本地文件
 */
    public static boolean setUsers(ConcurrentMap<String, String[]> users) {
        try {
            FileOutputStream fos = new FileOutputStream(usernameFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
