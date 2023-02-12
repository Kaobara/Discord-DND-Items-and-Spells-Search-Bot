package com.gradle.discord4jDiscordBot;

import com.gradle.staticScrapeService.ContentTable;
import com.gradle.staticScrapeService.Entity;
import com.gradle.staticScrapeService.Item;
import com.gradle.staticScrapeService.Spell;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;
import java.util.ArrayList;

public final class DnDEmbedBuilder {
    private static final DnDEmbedBuilder instance = new DnDEmbedBuilder();

    private DnDEmbedBuilder() {}

    public static EmbedCreateSpec spellEmbed(Spell spell, Message message) {
        spell.buildDescriptionSections();
        ArrayList<String> descriptionSections = spell.getDescSections();
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder()
            .color(Color.GREEN)
            .title(spell.getName())
            .url(spell.getURL())
            .author(message.getAuthor().get().getUsername(), "", message.getAuthor().get().getAvatarUrl())
            .addField("Source: ", spell.getSource(), false)
            .addField("", spell.getLevelSchool() + " _(" + spell.getSpellList() + ")_", false)
            .addField("", spell.getMetadata(), false);

            for (String descriptionSection : descriptionSections) {
                builder = builder.addField("", descriptionSection, false);
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

        for (String descriptionSection : descriptionSections) {
            builder = builder.addField("", descriptionSection, false);
        }
        return builder.build();
    }

    public static EmbedCreateSpec tableEmbed(String tableContents) {
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder()
                .color(Color.GREEN)
                .description(tableContents)
                .timestamp(Instant.now())
                .footer("Table", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png");

        return builder.build();
    }

    public static ArrayList<EmbedCreateSpec> entityAndTableEmbeds(Entity entity, Message message) {
        ArrayList<EmbedCreateSpec> embeds = new ArrayList<>();

        if(entity instanceof Spell) {
            embeds.add(spellEmbed((Spell)entity, message));
        }
        if(entity instanceof Item) {
            embeds.add(itemEmbed((Item)entity, message));
        }
        for(ContentTable contentTable : entity.getTables()){
            embeds.add(tableEmbed(contentTable.getFullTable()));
        }
        return embeds;
    }
}
