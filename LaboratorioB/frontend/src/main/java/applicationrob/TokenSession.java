package applicationrob;
public class TokenSession {
    private static int userId = -1;

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int id) {
        userId = id;
    }

    public static void checkTkSession() {
        if (userId == -1) {
            return;
        }
    }
    
}
