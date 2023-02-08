package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

import com.gradle.staticScrapeService.*;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

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
    private static String botUsername;
    private static DnDEmbedBuilder embedBuilder = new DnDEmbedBuilder();
    private static String currentBotCommand;

    // List of all bot commands that can be done
    private static Mono<Object> botCommands(Message message) {
        if(message.getContent().contains("!" + botUsername + " cast ")) {
            currentBotCommand = "!" + botUsername + " cast ";
            return spellSearchUp(message);
        } else {
            return Mono.empty();
        }
    }

    private static Mono<Object> spellSearchUp(Message message) {
        // Name of Spell taken from message
        String spellName = message.getContent().replace(currentBotCommand, "");

        // Search spell from its name
        Spell spell = spellSearch.searchSpellInfo(spellName);

        // If empty, spell not found from list
        if(spell.isEmpty) {
            return message.getChannel()
                    .flatMap(channel -> channel.createMessage("Spell not found. Please check if you spelled it correctly"));
        }

        // Embed has a character limit of 1024. If the description is too long, just give the URL of spell to channel
        if(spell.getDescription().length() > 1023) {
            return message.getChannel()
                    .flatMap(channel -> channel.createMessage("Description of Spell is too long\nGo to: " + spell.getURL()));
        }

        // Create the embed of spell and return it
        EmbedCreateSpec embed = embedBuilder.spellEmbed(spell, message);

        return message.getChannel()
                .flatMap(channel -> channel.createMessage(embed));
    }



}
