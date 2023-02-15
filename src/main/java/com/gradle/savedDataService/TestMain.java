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
        Clock clock = new Clock();
        clock.addSeconds(82043);
        clock.addHr(1);
        clock.addMinutes(12);
        clock.addSeconds(38);
        System.out.println("Seconds: " + clock.getSec());
        System.out.println("Minutes: " + clock.getMin());
        System.out.println("Hour: " + clock.getHr());
//        System.out.printf("%02d:%02d:%02d", clock.getHr(), clock.getMin(),clock.getSec());
        System.out.println(clock.stringGetCurrentTime());

//        {
//          "channelID": ,
//          "currentTime": , "00:00:00"
//
//        }

        File file = new File("DataStorage/channel.json");

        Channel channel1 = new Channel(1);
        Channel channel2 = new Channel(2);
        Channel channel3 = new Channel(3);
        channel2.getClock().addSeconds(19);
        Channel[] channels = {channel1, channel2};

        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(channel1));
//        objectMapper.writerWithDefaultPrettyPrinter().writeValue();
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, channels);

//        objectMapper.writeValue(file, channel2);
//        JsonNode node = objectMapper.valueToTree(channel1);
//        System.out.println(node);

//        Channel channel2 = objectMapper.readValue(file, Channel.class);
        JsonNode node = objectMapper.readTree(file);

        ObjectNode objNode= objectMapper.createObjectNode();
//        objNode.put();

        objNode.putPOJO("Channel3", channel3);
        ArrayNode obj = node.deepCopy();
        obj.addPOJO(channel3);
//        ((ObjectNode)node).putPOJO("Channel3", channel3);
//        System.out.println(obj);
        System.out.println(obj);




//        node.get(1);
//        JsonNode chosenNode;
//        for(JsonNode channelNodes : node) {
//            if(channelNodes.get("id").asInt() == 1) {
//                chosenNode = channelNodes;
//                System.out.println(channelNodes);
//            }
////            for(JsonNode thing : channelNodes) {
////                System.out.println(thing);
////            }
////            if(channelNodes.has("1"))
////            {
////            System.out.println(channelNodes.get("id"));
////
////            }
//        }


//        System.out.println(node.get);
//        JsonNode timeNode = node.get("clock");
//        System.out.println(timeNode);
//
//        Clock clock324 = objectMapper.treeToValue(timeNode, Clock.class);
////        long id = objectMapper.treeToValue(node.get("id"), long.class);
////        System.out.println(id);
//        System.out.println(clock324.stringGetCurrentTime());

//        Channel channel3 = objectMapper.treeToValue(node, Channel.class);

        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.getChannelTime(1071815785107951697L);
//        jsonWriter.getChannelTime(1071815785107951697);
    }
}
