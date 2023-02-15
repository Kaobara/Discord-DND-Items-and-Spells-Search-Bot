package com.gradle.savedDataService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gradle.staticScrapeService.EntitySearch;
import com.gradle.staticScrapeService.ItemSearch;
import com.gradle.staticScrapeService.Spell;
import com.gradle.staticScrapeService.SpellSearch;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestMain {
    // This class is primarily used to test searches
    public static void main(String[] args) throws IOException {
//        Clock clock = new Clock();
//        clock.addSeconds(82043);
//        clock.addHr(1);
//        clock.addMinutes(12);
//        clock.addSeconds(38);
//        System.out.println("Seconds: " + clock.getSec());
//        System.out.println("Minutes: " + clock.getMin());
//        System.out.println("Hour: " + clock.getHr());
//        System.out.println(clock.stringGetCurrentTime());
//
//        File file = new File("DataStorage/channel.json");
//
//        Channel channel1 = new Channel(1);
//        Channel channel2 = new Channel(2);
//        Channel channel3 = new Channel(3);
//        channel2.getClock().addSeconds(19);
//        Channel[] channels = {channel1, channel2};

        JSONWriter jsonWriter = new JSONWriter();
        System.out.println(jsonWriter.getChannelTime(2L));
        System.out.println(jsonWriter.getChannelTime(1071815785107951697L));
        System.out.println(jsonWriter.getChannelTime(238947329845L));
        jsonWriter.editChannelTime(238947329845L, 1, 4, 213, "add");
        jsonWriter.editChannelTime(1071815785107951697L, 5, 3, 60, "set");
//        jsonWriter.getChannelTime(1071815785107951697L);
    }
}
