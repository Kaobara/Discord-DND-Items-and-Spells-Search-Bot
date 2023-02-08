package com.gradle.discord4jDiscordBot;

import com.gradle.staticScrapeService.Spell;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;

import java.time.Instant;

public class DnDEmbedBuilder {
    public static EmbedCreateSpec spellEmbed(Spell spell, Message message) {
        // Two types of spells: spells that can be upcasted and spells that cannot
        // TODO: find simpler way to add a new field, or some other else if
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
                    .addField("", "_**Spell Lists. **" + spell.getSpellList() + "_", false)
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
                    .addField("", "_**Spell Lists. **" + spell.getSpellList() + "_", false)
                    .timestamp(Instant.now())
                    .footer("Spell", "https://cdn.discordapp.com/attachments/719088475533738044/935830321168011284/Droop_Laughing_Final.png")
                    .build();
            return embed;
        }
    }
}
