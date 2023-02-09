package com.gradle.discord4jDiscordBot;

import com.gradle.staticScrapeService.Entity;
import com.gradle.staticScrapeService.Item;
import com.gradle.staticScrapeService.Spell;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;
import java.util.ArrayList;

public class DnDEmbedBuilder {
    public static EmbedCreateSpec spellEmbed(Spell spell, Message message) {
        // Two types of spells: spells that can be upcasted and spells that cannot
        // TODO: find simpler way to add a new field, or some other else if
        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .title(spell.getName())
                .url(spell.getURL())
                .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
                .addField("Source: ", spell.getSource(), false)
                .addField("", spell.getLevelSchool() + " _(" + spell.getSpellList() + " )_", false)
                .addField("", spell.getMetadata(), false)
                .addField("", spell.getDescription(), false)
                .addField(spell.getUpcastTitle(), spell.getUpcast(), false)
                .timestamp(Instant.now())
                .footer("Spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png")
                .build();
        return embed;

    }

//    public static EmbedCreateSpec itemEmbed(Item item, Message message) {
//
//    }

    public static EmbedCreateSpec itemEmbed(Item item, Message message) {
        ArrayList<String> descriptionSections = item.getDescriptionSections();

        // Might have to figure out a better way to do this without duplication and hard coding
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder()
            .color(Color.GREEN)
            .title(item.getName())
            .url(item.getURL())
            .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
            .addField("Source: ", item.getSource(), false)
            .addField("", item.getTypeRarity(), false)
            .timestamp(Instant.now())
            .footer("Item", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png");

            for(int i=0; i<descriptionSections.size(); i++){
                builder = builder.addField("", descriptionSections.get(i), false);
            }
        return builder.build();
    }

    public static EmbedCreateSpec continuedDescEmbed(String continuedDescription, Message message) {
        EmbedCreateSpec embed = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .addField("", continuedDescription, false)
                .timestamp(Instant.now())
                .build();
        return embed;
    }

//    public static EmbedCreateSpec[] buildMultipleEmbeds(Entity entity, Message message) {
//        if(entity.getDescription().length() > 1023) {
//            entity.
//        }
//    }
}
