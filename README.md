# Discord-DND-Rules-Bot
A Discord Bot to find Information on dnd rules from specific website without having to rely on DND Beyond. A Summerhack '23 project for CISSA

Video Pitch Link: https://youtu.be/buOJe2-_Wro

## How to Setup
As this is a Discord bot, the Discord application and a bot made from the Discord Developer Client is required

### Setting up the Discord Bot
In Discord's Developer Portal, you should be able to set up your bot. On the sidebar of the page of your Discord app is the "Bot" page which includes the name of your bot and a button to reset and generate a **Token** for your bot. This token is important, so make sure to save it somewhere.

![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/DiscordSetup1.png)

Scrolling down the same page, make sure to tick the "Message Content Intent" option

![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/DiscordSetup2.png)

Once done, open the project in your desired IDE, change the directory to src\main\groovy\java\com\gradle\discord4jDiscordBot, and open the DiscordDnDSearchUpBot.java class. 

Please replace the "TOKEN HERE" string with your bot's token on line 19 of the DiscordDnDSearchUpBot class. 
This program can then be run using Gradle!

![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/BotToken.png)

### Inviting your bot to a Discord Server
On the Discord Developer Portal, open the OAuth2 -> URL Generator page through the sidebar. Afterwards, tick "bot" for Scopes, and then "Send Messages" for Bot Permissions. The bottom of the page should automatically generate a URL, which you can open to invite your bot to any server you own or have the required permissions for.

![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/DiscordSetup3.png)

## Using the Discord Bot
You can watch the Video linked above to see what the bot can do. Essentially, the bot has 2 commands coded in: a command to search up a spell, and a command to search up a magic item belonging to the Dungeons and Dragons TTRPG.

The commands are:
  - !(InsertBotNameHere) cast (Spellname)
  - !(InsertBotNameHere) find (Itemnname)

![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/BotCommandCast.png)
![](https://github.com/Kaobara/Discord-DND-Items-and-Spells-Search-Bot/blob/main/media/BotCommandFind.png)
   
The bot will automatically search up the information and send it to your Discord channel
