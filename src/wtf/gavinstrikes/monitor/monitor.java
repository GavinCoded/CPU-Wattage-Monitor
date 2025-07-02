package wtf.gavinstrikes.monitor;

import java.util.concurrent.TimeUnit;

public class monitor {
    private final wattageReader reader;
    private final display display;
    private boolean running;

    public monitor() {
        this.reader = new wattageReader();
        this.display = new display();
        this.running = true;
    }

    public void start() {
        while (running) {
            double wattage = reader.getwattage();
            display.show(wattage);
            
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
} 