package appcode;

public class Session {
    private static String nama = "";
    private static String role = "";

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

    // Optional: reset session saat logout
    public static void clear() {
        nama = null;
        role = null;
    }
}
