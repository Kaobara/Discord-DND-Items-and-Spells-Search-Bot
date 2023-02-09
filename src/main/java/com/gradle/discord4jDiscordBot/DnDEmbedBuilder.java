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
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder()
            .color(Color.GREEN)
            .title(spell.getName())
            .url(spell.getURL())
            .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
            .addField("Source: ", spell.getSource(), false)
            .addField("", spell.getLevelSchool() + " _(" + spell.getSpellList() + " )_", false)
            .addField("", spell.getMetadata(), false);

            if(!spell.hasLongDescription()) {builder.addField("", spell.getDescription(), false);
            } else {
                builder.addField("", spell.getDescSections().get(0), false);
            }

            if(spell.canUpcast()) { builder.addField("At Higher Levels", spell.getUpcast(), false); }

            builder.timestamp(Instant.now())
            .footer("Spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png");
        return builder.build();
    }

    public static EmbedCreateSpec itemEmbed(Item item, Message message) {
        item.buildDescriptionSections();
        ArrayList<String> descriptionSections = item.getDescSections();

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

    public static EmbedCreateSpec continuedDescEmbed(String continuedDescription) {
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder()
                .color(Color.GREEN);
//                for(int i=sectionIndex; i<sectionIndex+numFields; i++) {
                    builder.addField("", continuedDescription, false);
//                }

//                builder.timestamp(Instant.now());
        return builder.build();
    }

    public static ArrayList<EmbedCreateSpec> longSpellEmbed(Spell spell, Message message) {
        spell.buildDescriptionSections();
        ArrayList<String> descriptionSection = spell.getDescSections();
        ArrayList<EmbedCreateSpec> embeds = new ArrayList<>();
        EmbedCreateSpec spellEmbedTop = spellEmbed(spell, message);
        embeds.add(spellEmbedTop);
        System.out.println(descriptionSection.size());
        for(int i = 1; i < descriptionSection.size(); i++) {
            embeds.add(continuedDescEmbed(descriptionSection.get(i) ));
        }

        return embeds;
    }
}
