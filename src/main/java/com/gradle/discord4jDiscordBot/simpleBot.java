package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import reactor.core.publisher.Mono;

public class simpleBot {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("TOKEN");

        Mono<Void> login = client .withGateway((GatewayDiscordClient gateway) -> Mono.empty());

        login.block();
    }
}
