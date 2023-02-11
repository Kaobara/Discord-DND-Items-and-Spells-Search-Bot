package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.MessageCreateMono;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.gradle.staticScrapeService.*;

import java.util.ArrayList;

public class DiscordDnDSearchUpBot {
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
                .flatMap(DiscordDnDSearchUpBot::botCommands)
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
        }

        // Embed has a character limit of 1024. If the description is too long, just give the URL of spell to channel
        if(spell.isHasTable()) {
            return message.getChannel()
                    .flatMapMany(channel -> {
                        ArrayList<MessageCreateMono> messageCreateMonos= new ArrayList<>();
                        ArrayList<EmbedCreateSpec> embeds = embedBuilder.entityAndTableEmbeds(spell, message);
                        System.out.println(embeds.size());
                        for(EmbedCreateSpec embed : embeds) {
                            messageCreateMonos.add(channel.createMessage(embed));
                        }
                        return Flux.fromIterable(messageCreateMonos);
                    }).flatMap(messagesToSend -> Flux.from(messagesToSend));
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = embedBuilder.spellEmbed(spell, message);

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

        if(item.isHasTable()) {
            return message.getChannel()
                    .flatMapMany(channel -> {
                        ArrayList<MessageCreateMono> messageCreateMonos= new ArrayList<>();
                        ArrayList<EmbedCreateSpec> embeds = embedBuilder.entityAndTableEmbeds(item, message);
                        System.out.println(embeds.size());
                        for(EmbedCreateSpec embed : embeds) {
                            messageCreateMonos.add(channel.createMessage(embed));
                        }
                        return Flux.fromIterable(messageCreateMonos);
                    }).flatMap(messagesToSend -> Flux.from(messagesToSend));
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = embedBuilder.itemEmbed(item, message);

        return message.getChannel()
                .flatMapMany(channel -> channel.createMessage(embed));
    }



}
