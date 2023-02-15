package com.gradle.savedDataService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class JSONWriter {
    public String getChannelTime(Long channelID) throws IOException {
        File file = new File("DataStorage/channel.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode initialNode = objectMapper.readTree(file);

        JsonNode chosenNode = null;
        boolean hasChannel = false;
        for(JsonNode channelNode : initialNode) {
            if(channelNode.get("id").longValue() == channelID) {
                hasChannel = true;
                chosenNode = channelNode;
                System.out.println(channelNode);
            }
        }
        if(chosenNode != null){
            return chosenNode.get("id").toString();
        }

//        long channelIDS = 1071815785107951697L;

        Channel newChannel = new Channel(channelID);
        ArrayNode obj = initialNode.deepCopy();
        obj.addPOJO(newChannel);

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, obj);

        System.out.println(obj);



        return "AA";
    }
}
