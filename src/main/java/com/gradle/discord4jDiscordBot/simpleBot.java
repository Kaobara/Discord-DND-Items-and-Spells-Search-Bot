package com.gradle.discord4jDiscordBot;

public class simpleBot {
    public static void main(String[] args) {
        Mono.just("Hello World").subscribe(System.out::println);
// or
        Flux.just('H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd', '\n')
                .subscribe(System.out::print);
    }
}
