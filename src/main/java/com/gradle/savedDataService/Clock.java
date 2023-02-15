package com.gradle.savedDataService;

public class Clock {
    private int currentHour = 0;
    private int currentMinute = 0;
    private int currentSeconds = 0;

    public Clock() {
    }

    // Note: moved time conversion from the start of process (adding and setting) to end of processing (when time is called)
    // convert time only needs to be called at the end instead of constantly through the process
    public void addSeconds(int seconds) {
        currentSeconds += seconds;
//        convertTime();
    }

    public void addMinutes(int minutes) {
        currentMinute += minutes;
//        convertTime();
    }

    public void addHr(int hr) {
        currentHour += hr;
//        convertTime();
    }

    // TODO Make sec-min-hr into a single method to remove duplicates
    private void convertSecMin() {
        if(currentSeconds < 60) {
            return;
        }
        currentMinute += currentSeconds/60;
        currentSeconds = currentSeconds%60;
        return;
    }

    private void convertMinHr() {
        if(currentMinute < 60) {
            return;
        }
        currentHour += currentMinute/60;
        currentMinute = currentMinute%60;
        return;
    }

    private void convertHr() {
        if(currentHour < 24) {
            return;
        }
        currentHour = currentHour%24;
    }

    public void convertTime() {
        convertSecMin();
        convertMinHr();
        convertHr();
    }

    public int getSec() { convertTime(); return currentSeconds; }
    public int getMin() { convertTime(); return currentMinute; }
    public int getHr() { convertTime(); return currentHour; }

    public void setCurrentHour(int currentHour) {
        this.currentHour = currentHour;
    }

    public void setCurrentMinute(int currentMinute) {
        this.currentMinute = currentMinute;
    }

    public void setCurrentSeconds(int currentSeconds) {
        this.currentSeconds = currentSeconds;
    }

    public String getCurrentTime() {
        return String.format("%02d:%02d:%02d", currentHour, currentMinute,currentSeconds);
    }
}
