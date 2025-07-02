package wtf.gavinstrikes.monitor;

public class Main {
    public static void main(String[] args) {
        if (!System.getProperty("user.name").equals("root")) {
            System.err.println("this program requires root privileges");
            System.err.println("run with: sudo java -jar monitor.jar");
            System.exit(1);
        }
        
        monitor monitor = new monitor();
        monitor.start();
    }
} 