import ui.Main;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Starting application from Launcher...");
        try {
            Main.main(args);
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}