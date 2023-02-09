package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateMono;
import discord4j.discordjson.json.gateway.MessageCreate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.gradle.staticScrapeService.*;

import java.util.ArrayList;

public class simpleBot {
    public static void main(String[] args) {
        GatewayDiscordClient client = DiscordClientBuilder.create("TOKEN HERE")
                .build()
                .login()
                .block();

        client.getEventDispatcher().on(ReadyEvent.class)
                .subscribe(event -> {
                    User self = event.getSelf();
                    botUsername = self.getUsername();
                    System.out.println(String.format("Logged in as %s#%s", self.getUsername(), self.getDiscriminator()));
                    System.out.println(self);
                });

        client.getEventDispatcher().on(MessageCreateEvent.class)
                .map(event -> event.getMessage())
                .filter(message -> message.getContent().startsWith("!" + botUsername ))
                .flatMap(simpleBot::botCommands)
                .subscribe();

        client.onDisconnect().block();
    }

    private static SpellSearch spellSearch = new SpellSearch();
    private static ItemSearch itemSearch = new ItemSearch();
    private static String botUsername;
    private static DnDEmbedBuilder embedBuilder = new DnDEmbedBuilder();
    private static String currentBotCommand;

    // List of all bot commands that can be done
    private static Flux<?> botCommands(Message message) {
        if(message.getContent().contains("!" + botUsername + " cast ")) {
            currentBotCommand = "!" + botUsername + " cast ";
            return spellSearchUp(message);
        } else if(message.getContent().contains("!" + botUsername + " find ")) {
            currentBotCommand = "!" + botUsername + " find ";
            return Flux.from(itemSearchUp(message));
        }else {
            return Flux.empty();
        }
    }

    private static Flux<Object> spellSearchUp(Message message) {
        // Name of Spell taken from message
        String spellName = message.getContent().replace(currentBotCommand, "");

        // Search spell from its name
        Spell spell = (Spell) spellSearch.searchEntityInfo(spellName);

        // If empty, spell not found from list
        if(spell.isEmpty()) {
            return message.getChannel()
                    .flatMapMany(channel -> channel.createMessage("Spell not found. Please check if you spelled it correctly"));
//                        channel.createMessage("Spell not found. Please check if you spelled it correctly"));
        }

        // Embed has a character limit of 1024. If the description is too long, just give the URL of spell to channel
//        if(spell.getDescription().length() < 1023) {

        ArrayList<EmbedCreateSpec> embeds= new ArrayList<>();
//        EmbedCreateSpec embed1 = embedBuilder.spellEmbed(spell, message);
//        EmbedCreateSpec embed2 = embedBuilder.continuedDescEmbed(spell.getDescription(), message);
//        EmbedCreateSpec embed3 = embedBuilder.continuedDescEmbed(spell.getDescription(), message);
//        embeds.add(.createMessage(embed1));
//        embeds.add(channel.createMessage(embed2));
//        embeds.add(channel.createMessage(embed3));
        EmbedCreateSpec embed = embedBuilder.spellEmbed(spell, message);
            return message.getChannel()
                    .flatMapMany(channel -> {
                        ArrayList<MessageCreateMono> messageCreateMonos= new ArrayList<>();
                        EmbedCreateSpec embed1 = embedBuilder.spellEmbed(spell, message);
                        EmbedCreateSpec embed2 = embedBuilder.continuedDescEmbed(spell.getDescription(), message);
                        EmbedCreateSpec embed3 = embedBuilder.continuedDescEmbed(spell.getDescription(), message);
                        messageCreateMonos.add(channel.createMessage(embed1));
                        messageCreateMonos.add(channel.createMessage(embed2));
                        messageCreateMonos.add(channel.createMessage(embed3));

                        return Flux.fromIterable(messageCreateMonos);
//                        return Flux.from(channel.createMessage(embed1));
                    }).flatMap(death -> Flux.from(death));
//                    .flatMapMany(channel -> {
//                        return Flux.fromIterable(embeds);
//                        return Flux.from(channel.createMessage(embed));
//                    });

//                    thingy.subscribe();
//
//                    .flatMapMany(channel -> channel.createMessage("Description of Spell is too long\nGo to: " + spell.getURL()));
//        }

        // Create the embed of spell and return it
//        EmbedCreateSpec embed = embedBuilder.spellEmbed(spell, message);
//
//        return message.getChannel()
//                .flatMapMany(channel -> channel.createMessage(embed));
    }

//    private static MessageCreateMono[] gm(MessageChannel channel) {
//        MessageCreateMono[] hm = new MessageCreateMono[3];
//        for(int i = 0; i<2; i++) {
//            hm[i] = channel.createMessage(String.valueOf(i));
//        }
//        return hm;
////        return Mono.when(damn -> hm.get(0), hm.get(1));
////        return channel.createMessage("AAAAAA");
//
//    }

//    private static Flux<Object> A

    private static Mono<Object> itemSearchUp(Message message) {
        // Name of Item taken from message
        String itemName = message.getContent().replace(currentBotCommand, "");

        // Search spell from its name
        Item item = (Item) itemSearch.searchEntityInfo(itemName);

        // If empty, spell not found from list
        if(item.isEmpty()) {
            return message.getChannel()
                    .flatMap(channel -> channel.createMessage("Item not found. Please check if you spelled it correctly"));
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = embedBuilder.itemEmbed(item, message);

        return message.getChannel()
                .flatMap(channel -> channel.createMessage(embed));
    }



}
