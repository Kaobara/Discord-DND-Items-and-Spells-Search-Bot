package com.gradle.discord4jDiscordBot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import reactor.core.publisher.Mono;

public class simpleBot {
    public static void main(String[] args) {
        DiscordClient client = DiscordClient.create("OTM1NzgwODI4MzY0MDI5OTky.G96VpA.jsYhiQczCvBnzCSB45UNYqjjwgP8F6rxatZg80");

        Mono<Void> login = client .withGateway((GatewayDiscordClient gateway) -> Mono.empty());

        login.block();
    }
}
