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

        // Login to Discord and do nothing
//        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> Mono.empty());

        // Login to discord and print bot username and discriminator to console
//        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
//                gateway.on(ReadyEvent.class, event ->
//                        Mono.fromRunnable(() -> {
//                            final User self = event.getSelf();
//                            System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
//                        })));

        // Bot responds to "!ping" with "pong!"
//        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
//                gateway.on(MessageCreateEvent.class, event -> {
//                    Message message = event.getMessage();
//
//                    if(message.getContent().equalsIgnoreCase("!ping")) {
//                        return message.getChannel()
//                                .flatMap(channel -> channel.createMessage("pong!"));
//                    }
//
//                    return Mono.empty();
//
//                }));

        // Bot responds to message that contains "!Droop cast ", and sends the contents of that spell to the Discord channel
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();

                    if(message.getContent().contains("!Droop cast ")) {
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

//                        message.getAuthor().getAvatarUrl();
                        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                                .color(Color.GREEN)
                                .title(spell.getName())
                                .url(spell.getURL())
                                .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
                                .addField("Source: ", spell.getSource(), false)
//                                .addField("\u200B", "\u200B", false)
                                .addField("", spell.getLevelSchool(), false)
                                .addField("", "**Casting Time: **" + spell.getCastingTime(), false)
                                .addField("", "**Range: **" + spell.getRange(), false)
                                .addField("", "**Components: **" + spell.getComponents(), false)
                                .addField("", "**Duration: **" + spell.getDuration(), true)
                                .addField("", spell.getDescription(), false)
                                .timestamp(Instant.now())
                                .footer("spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png")
                                .build();

                        return message.getChannel()
                                .flatMap(channel -> channel.createMessage(embed));
                    }

                    return Mono.empty();

                }));

        login.block();
    }
}
