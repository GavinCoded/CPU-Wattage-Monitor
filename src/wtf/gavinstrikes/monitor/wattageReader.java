package wtf.gavinstrikes.monitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class wattageReader {
    private static final String intelpath = "/sys/class/powercap/intel-rapl:0/energy_uj";
    private static final String k10temp = "/sys/class/hwmon";
    private static final String ryzen = "/sys/devices/pci0000:00/0000:00:18.3/hwmon";
    private final String powerpath;
    private long prevenergy;
    private long prevtime;
    private boolean initialized;

    public wattageReader() {
        if (Files.exists(Paths.get(intelpath))) {
            powerpath = intelpath;
        } else {
            powerpath = findamdpath();
        }
        
        if (powerpath == null) {
            System.err.println("no power monitoring interface found");
            System.exit(1);
        }
    }

    private String findamdpath() {
        try {
            if (Files.exists(Paths.get(ryzen))) {
                try (Stream<Path> paths = Files.walk(Paths.get(ryzen), 2)) {
                    return paths.filter(p -> p.toString().contains("power1_input"))
                              .findFirst()
                              .map(Path::toString)
                              .orElse(null);
                }
            }
            
            if (Files.exists(Paths.get(k10temp))) {
                try (Stream<Path> paths = Files.walk(Paths.get(k10temp), 2)) {
                    return paths.filter(p -> {
                        try {
                            Path namePath = p.getParent().resolve("name");
                            return Files.exists(namePath) && 
                                   Files.readString(namePath).contains("k10temp") &&
                                   p.toString().contains("power1_input");
                        } catch (IOException e) {
                            return false;
                        }
                    }).findFirst()
                      .map(Path::toString)
                      .orElse(null);
                }
            }
        } catch (IOException ignored) {}
        return null;
    }

    public double getwattage() {
        try {
            long currenergy = readvalue(powerpath);
            long currtime = System.currentTimeMillis() / 1000;

            if (!initialized) {
                prevenergy = currenergy;
                prevtime = currtime;
                initialized = true;
                return 0.0;
            }

            long deltaenergy = currenergy - prevenergy;
            long deltatime = currtime - prevtime;

            prevenergy = currenergy;
            prevtime = currtime;

            if (deltatime > 0) {
                return (double) deltaenergy / (deltatime * 1000000.0);
            }
        } catch (IOException e) {
            System.err.println("error reading power data");
            System.exit(1);
        }
        
        return 0.0;
    }

    private long readvalue(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line = reader.readLine();
            return line != null ? Long.parseLong(line.trim()) : 0;
        }
    }
} 
