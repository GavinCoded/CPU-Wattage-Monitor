# cpu wattage monitor

a simple cpu wattage monitor made in java.

---

## usage

you can build the project yourself or just grab the compiled jar here:  
**[download latest release](https://github.com/GavinCoded/CPU-Wattage-Monitor/releases/tag/WattageMonitor)**

### running the monitor
you'll need to run it with sudo:
```bash
sudo java -jar Monitor.jar
```

**system requirements:**
- linux systems (tested on fedora 42)
- root privileges required for hardware access

---

## why i made this

my system kept crashing, and i wanted to check if it was a power supply issue. turns out, it was. figured i'd make this public since i had it lying around and got bored.

## why java?

i was bored.

---

## details

this tool depends on:
- **intel rapl** (running average power limit)
- **amd's equivalent power interfaces**

### compatibility
- **tested on:** intel systems specifically
- **amd support:** no testing was done on amd systems

---

## license

mit license

---

## building from source

if you want to build the project yourself instead of using the pre-compiled jar, clone the repository and follow java build procedures.
