package me.cael.capes

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import me.cael.capes.utils.composeToggleText
import net.minecraft.text.LiteralText

enum class CapeType(val stylized: String) {
    MINECRAFT("Minecraft"), OPTIFINE("OptiFine"), LABYMOD("LabyMod"), WYNNTILS("Wynntils"), MINECRAFTCAPES("MinecraftCapes"), NETHERNET("MCNethernet"), NETHERNETHD("MCNethernet HD");

    fun cycle() = when(this) {
        MINECRAFT -> OPTIFINE
        OPTIFINE -> LABYMOD
        LABYMOD -> WYNNTILS
        WYNNTILS -> MINECRAFTCAPES
        MINECRAFTCAPES -> NETHERNET
        NETHERNET -> NETHERNETHD
        NETHERNETHD -> MINECRAFT
    }

    fun getURL(player: PlayerEntity): String? {
        val config = AutoConfig.getConfigHolder(CapeConfig::class.java).config
        return when (this) {
            OPTIFINE -> if(config.enableOptifine) "http://s.optifine.net/capes/${player.entityName}.png" else null
            LABYMOD -> if(config.enableLabyMod) "https://www.labymod.net/page/php/getCapeTexture.php?uuid=${player.uuidAsString}" else null
            WYNNTILS -> if(config.enableWynntils) "https://athena.wynntils.com/user/getInfo" else null
            MINECRAFTCAPES -> if(config.enableMinecraftCapesMod) "https://minecraftcapes.net/profile/${player.uuidAsString.replace("-", "")}" else null
            NETHERNET -> if(config.enableNethernet) "https://static.nethernet.pl/cape/${player.entityName}.png" else null
            NETHERNETHD -> if(config.enableNethernetHD) "https://static.nethernet.pl/hdcape/${player.entityName}.png" else null
            MINECRAFT -> null
        }
    }

    fun getToggleText(enabled: Boolean): Text = composeToggleText(LiteralText(stylized), enabled)

    fun getText(): TranslatableText = TranslatableText("options.capes.capetype", stylized)

}
