package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import com.gradle.staticScrapeService.*;

public class simpleBot {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("TOKEN");

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


        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(MessageCreateEvent.class, event -> {
                    Message message = event.getMessage();

                    if(message.getContent().contains("!spell ")) {
                        String spellName = message.getContent().replace("!spell ", "");

                        SpellSearch spellSearch = new SpellSearch();
                        Spell spell = spellSearch.searchSpellInfo(spellName);
                        String spellContent = spell.getContents();

                        return message.getChannel()
                                .flatMap(channel -> channel.createMessage(spellContent));
                    }

                    return Mono.empty();

                }));

        login.block();
    }
}
