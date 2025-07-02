package wtf.gavinstrikes.monitor;

public class display {
    private static final String clear = "\033[2J\033[H";
    private static final String reset = "\033[0m";
    private static final String green = "\033[32m";
    private boolean firstrun = true;
    
    public void show(double wattage) {
        if (firstrun) {
            System.out.print(clear);
            System.out.println("system wattage monitor by gavinstrikes");
            System.out.println("=====================================");
            firstrun = false;
        }
        
        System.out.printf("\rcurrent power usage: %s%.2f%s watts | press ctrl+c to exit%s", 
                         green, wattage, reset, "                              \r");
        System.out.flush();
    }
} 