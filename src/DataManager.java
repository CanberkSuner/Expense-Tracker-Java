import java.io.*;

public class DataManager {

    private static String getFileName(String userName) {
        return userName.trim().replaceAll("\\s+", "_") + "_data.dat";
    }

    public static void saveUser(User user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFileName(user.getName())))) {
            oos.writeObject(user);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static User loadUser(String userName) {
        File file = new File(getFileName(userName));
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (User) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}