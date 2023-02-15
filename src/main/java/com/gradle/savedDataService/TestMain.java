package com.gradle.savedDataService;

import com.gradle.staticScrapeService.EntitySearch;
import com.gradle.staticScrapeService.ItemSearch;
import com.gradle.staticScrapeService.Spell;
import com.gradle.staticScrapeService.SpellSearch;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Date;

public class TestMain {
    // This class is primarily used to test searches
    public static void main(String[] args) throws IOException {
        Clock clock = new Clock();
        clock.addSeconds(82043);
        clock.addHr(1);
        clock.addMinutes(12);
        clock.addSeconds(38);
        System.out.println("Seconds: " + clock.getSec());
        System.out.println("Minutes: " + clock.getMin());
        System.out.println("Hour: " + clock.getHr());
//        System.out.printf("%02d:%02d:%02d", clock.getHr(), clock.getMin(),clock.getSec());
        System.out.println(clock.getCurrentTime());
    }
}
