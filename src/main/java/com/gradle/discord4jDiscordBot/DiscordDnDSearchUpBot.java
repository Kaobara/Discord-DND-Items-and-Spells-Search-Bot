package com.gradle.discord4jDiscordBot;

import com.gradle.savedDataService.JSONTimeWriter;
import com.gradle.savedDataService.TimeController;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateMono;
import reactor.core.publisher.Flux;

import com.gradle.staticScrapeService.*;

import java.io.IOException;
import java.util.ArrayList;

public class DiscordDnDSearchUpBot {
    public static void main(String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create("TOKEN HERE")
                .build()
                .login()
                .block();

        assert client != null;
        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    botUsername = self.getUsername();
                    System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(MessageCreateEvent::getMessage)
                .filter(message -> message.getContent().startsWith("!" + botUsername ))
                .flatMap(DiscordDnDSearchUpBot::botCommands)
                .subscribe();

        client.onDisconnect().block();
    }

    private static final SpellSearch spellSearch = new SpellSearch();
    private static final ItemSearch itemSearch = new ItemSearch();
    private static String botUsername;
    private static String currentBotCommand;

    // List of all bot commands that can be done
    private static Flux<?> botCommands(Message message){
        if(message.getContent().contains("!" + botUsername + " cast ")) {
            System.out.println("This part works");
            currentBotCommand = "!" + botUsername + " cast ";
            System.out.println("Message ID: "+message.getChannelId().asLong());
            return spellSearchUp(message);
        } else if(message.getContent().contains("!" + botUsername + " find ")) {
            currentBotCommand = "!" + botUsername + " find ";
            return Flux.from(itemSearchUp(message));
        }
        else if(message.getContent().contains("!" + botUsername + " time ")) {
            System.out.println(message.getChannelId().asString());
            return channelTime(message);
        }

        else {
            return Flux.empty();
        }
    }

    private static Flux<Object> channelTime(Message message) {
        TimeController timeController = new TimeController();

        String finalCurrentTime;
        if(message.getContent().contains("currently")) {
            String currentTime = "";
            try {
                currentTime = timeController.getChannelTime(message.getChannelId().asLong());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finalCurrentTime = currentTime;
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("It is currently: " + finalCurrentTime));
        } else if(message.getContent().contains("set")) {
            String currentTime = "";
            try {
                currentTime = timeController.setChannelTime(message.getChannelId().asLong(), message.getContent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finalCurrentTime = currentTime;
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("Time set to: " + finalCurrentTime));
        } else if(message.getContent().contains("forward")) {
            String currentTime = "";
            try {
                currentTime = timeController.forwardChannelTime(message.getChannelId().asLong(), message.getContent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finalCurrentTime = currentTime;
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("Time forward to: " + finalCurrentTime));
        }

        return message.getChannel()
                .flatMapMany(channel -> channel.createMessage("Something went wrong!"));
//        return message.getChannelId().asString();
    }

//    private static String setEditCurrentTime(Message message) throws RuntimeException {
//        if(message.getContent().contains("%4"))
//    }

    private static Flux<Object> spellSearchUp(Message message) {
        // Name of Spell taken from message
        String spellName = message.getContent().replace(currentBotCommand, "");

        // Search spell from its name
        Spell spell = (Spell) spellSearch.searchEntityInfo(spellName);

        // If empty, spell not found from list
        if(spell.isEmpty()) {
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("Spell not found. Please check if you spelled it correctly"));
        }

        // Embed has a character limit of 1024. If the description is too long, just give the URL of spell to channel
        if(spell.isHasTable()) {
            return EntityDescTableMessages(spell, message);
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = DnDEmbedBuilder.spellEmbed(spell, message);

        return message.getChannel()
                .flatMapMany(channel -> channel.createMessage(embed));
    }

    private static Flux<Object> itemSearchUp(Message message) {
        // Name of Item taken from message
        String itemName = message.getContent().replace(currentBotCommand, "");

        // Search spell from its name
        Item item = (Item) itemSearch.searchEntityInfo(itemName);

        // If empty, spell not found from list
        if(item.isEmpty()) {
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("Item not found. Please check if you spelled it correctly"));
        }

        // If item has table, 
        if(item.isHasTable()) {
            return EntityDescTableMessages(item, message);
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = DnDEmbedBuilder.itemEmbed(item, message);

        return message.getChannel()
                .flatMapMany(channel -> channel.createMessage(embed));
    }

    private static Flux<Object> EntityDescTableMessages(Entity entity, Message message) {
        return message.getChannel()
                .flatMapMany(channel -> {
                    ArrayList<MessageCreateMono> messageCreateMonos= new ArrayList<>();
                    ArrayList<EmbedCreateSpec> embeds = DnDEmbedBuilder.entityAndTableEmbeds(entity, message);
                    System.out.println(embeds.size());
                    for(EmbedCreateSpec embed : embeds) {
                        messageCreateMonos.add(channel.createMessage(embed));
                    }
                    return Flux.fromIterable(messageCreateMonos);
                }).flatMap(Flux::from);
    }





}
