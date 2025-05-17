package appcode;

public class Session {
    private static String nama = "";
    private static String role = "";
    private static int user_id = 0;

    public static void setNama(String namaUser) {
        nama = namaUser;
    }

    public static String getNama() {
        return nama;
    }

    public static void setRole(String roleUser) {
        role = roleUser;
    }

    public static String getRole() {
        return role;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        Session.user_id = user_id;
    }

    // Optional: reset session saat logout
    public static void clear() {
        nama = null;
        role = null;
        user_id = 0;
    }
}
