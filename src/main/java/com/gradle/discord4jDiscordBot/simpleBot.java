package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

import com.gradle.staticScrapeService.*;

import java.awt.*;
import java.time.Instant;

public class simpleBot {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("Token");

        // Bot responds to message that contains "!Droop cast ", and sends the contents of that spell to the Discord channel
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();

                    // Spell Searching
                    if(message.getContent().contains("!Droop cast ")) { return spellSearchUp(message) ;
                    }

                    return Mono.empty();

                }));

        login.block();
    }

    private static Mono<Object> spellSearchUp(Message message) {
        String spellName = message.getContent().replace("!Droop cast ", "");

        SpellSearch spellSearch = new SpellSearch();
        Spell spell = spellSearch.searchSpellInfo(spellName);
        if(spell.isEmpty) {
            return message.getChannel()
                    .flatMap(channel -> channel.createMessage("Spell not found. Please check if you spelled it correctly"));
        }

        if(spell.getDescription().length() > 1023) {
            return message.getChannel()
                    .flatMap(channel -> channel.createMessage("Description of Spell is too long\nGo to: " + spell.getURL()));
        }

        EmbedCreateSpec embed = spellEmbed(spell, message);

        return message.getChannel()
                .flatMap(channel -> channel.createMessage(embed));

    }

    private static EmbedCreateSpec spellEmbed(Spell spell, Message message) {
        if(!spell.canUpcast()) {
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .color(Color.GREEN)
                    .title(spell.getName())
                    .url(spell.getURL())
                    .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
                    .addField("Source: ", spell.getSource(), false)
                    .addField("", spell.getLevelSchool(), false)
                    .addField("", spell.getMetadata(), false)
                    .addField("", spell.getDescription(), false)
                    .timestamp(Instant.now())
                    .footer("Spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png")
                    .build();
            return embed;
        } else {
            EmbedCreateSpec embed = EmbedCreateSpec.builder()
                    .color(Color.GREEN)
                    .title(spell.getName())
                    .url(spell.getURL())
                    .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
                    .addField("Source: ", spell.getSource(), false)
                    .addField("", spell.getLevelSchool(), false)
                    .addField("", spell.getMetadata(), false)
                    .addField("", spell.getDescription(), false)
                    .addField("At Higher Levels", spell.getUpcast(), false)
                    .timestamp(Instant.now())
                    .footer("Spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png")
                    .build();
            return embed;
        }
    }
}
