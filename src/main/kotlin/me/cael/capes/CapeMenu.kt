package me.cael.capes

import me.cael.capes.handler.PlayerHandler
import me.cael.capes.utils.composeToggleText
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig
import me.sargunvohra.mcmods.autoconfig1u.ConfigManager
import net.minecraft.client.gui.DrawableHelper
 import net.minecraft.client.gui.screen.ConfirmChatLinkScreen
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.ScreenTexts
import net.minecraft.client.gui.screen.options.GameOptionsScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.network.AbstractClientPlayerEntity
import net.minecraft.client.options.GameOptions
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Util
import java.math.BigInteger
import java.util.*

class CapeMenu(parent: Screen, gameOptions: GameOptions) : GameOptionsScreen(parent, gameOptions, TranslatableText("options.capes.title")) {

    override fun init() {
        val configManager = AutoConfig.getConfigHolder(CapeConfig::class.java) as ConfigManager
        val config = configManager.config
        addButton(ButtonWidget(width / 2 - 155, height / 6, 150, 20, config.clientCapeType.getText()) { buttonWidget: ButtonWidget ->
            config.clientCapeType = config.clientCapeType.cycle()
            configManager.save()
            buttonWidget.message = config.clientCapeType.getText()
            if (this.client?.player != null) PlayerHandler.onPlayerJoin(this.client!!.player as AbstractClientPlayerEntity)
        })
        addButton(ButtonWidget(width / 2 - 155 + 160, height / 6, 150, 20, glintMessage(config.glint)) { buttonWidget: ButtonWidget ->
            config.glint = !config.glint
            configManager.save()
            buttonWidget.message = glintMessage(config.glint)
        })
        addButton(ButtonWidget(width / 2 - 155, height / 6 + 24, 150, 20, CapeType.OPTIFINE.getToggleText(config.enableOptifine)) { buttonWidget: ButtonWidget ->
            config.enableOptifine = !config.enableOptifine
            configManager.save()
            buttonWidget.message = CapeType.OPTIFINE.getToggleText(config.enableOptifine)
        })
        addButton(ButtonWidget(width / 2 - 155 + 160, height / 6 + 24, 150, 20, CapeType.LABYMOD.getToggleText(config.enableLabyMod)) { buttonWidget: ButtonWidget ->
            config.enableLabyMod = !config.enableLabyMod
            configManager.save()
            buttonWidget.message = CapeType.LABYMOD.getToggleText(config.enableLabyMod)
        })
        addButton(ButtonWidget(width / 2 - 155, height / 6 + 2 * 24, 150, 20, CapeType.MINECRAFTCAPES.getToggleText(config.enableMinecraftCapesMod)) { buttonWidget: ButtonWidget ->
            config.enableMinecraftCapesMod = !config.enableMinecraftCapesMod
            configManager.save()
            buttonWidget.message = CapeType.MINECRAFTCAPES.getToggleText(config.enableMinecraftCapesMod)
        })
        addButton(ButtonWidget(width / 2 - 155 + 160, height / 6 + 2 * 24, 150, 20, CapeType.WYNNTILS.getToggleText(config.enableWynntils)) { buttonWidget: ButtonWidget ->
            config.enableWynntils = !config.enableWynntils
            configManager.save()
            buttonWidget.message = CapeType.WYNNTILS.getToggleText(config.enableWynntils)
        })
                addButton(ButtonWidget(width / 2 - 155, height / 6 + 3 * 24, 150, 20, CapeType.NETHERNET.getToggleText(config.enableNethernet)) { buttonWidget: ButtonWidget ->
            config.enableNethernet = !config.enableNethernet
            configManager.save()
            buttonWidget.message = CapeType.NETHERNET.getToggleText(config.enableNethernet)
        })
        addButton(ButtonWidget(width / 2 - 155 + 160, height / 6 + 3 * 24, 150, 20, CapeType.NETHERNETHD.getToggleText(config.enableNethernetHD)) { buttonWidget: ButtonWidget ->
            config.enableNethernetHD = !config.enableNethernetHD
            configManager.save()
            buttonWidget.message = CapeType.NETHERNETHD.getToggleText(config.enableNethernetHD)
        })
        addButton(ButtonWidget(width / 2 - 100, height / 6 + 6 * 24, 200, 20, TranslatableText("options.capes.optifineeditor")) { buttonWidget: ButtonWidget ->
            try {
                val r1 = Random()
                val r2 = Random(System.identityHashCode(Object()).toLong())
                val random1Bi = BigInteger(128, r1)
                val random2Bi = BigInteger(128, r2)
                val serverBi = random1Bi.xor(random2Bi)
                val serverId = serverBi.toString(16)
                client!!.sessionService.joinServer(client!!.session.profile, client!!.session.accessToken, serverId)
                val url = "https://optifine.net/capeChange?u=${client!!.session.uuid}&n=${client!!.session.username}&s=$serverId"
                client!!.openScreen(ConfirmChatLinkScreen({ bool: Boolean ->
                    if (bool) {
                        Util.getOperatingSystem().open(url)
                    }
                    client!!.openScreen(this)
                }, url, true))
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        })

        addButton(ButtonWidget(width / 2 - 100, height / 6 + 4 * 24, 200, 20, ScreenTexts.DONE) { buttonWidget: ButtonWidget ->
            client!!.openScreen(parent)
        })
    }

    private fun glintMessage(glint: Boolean) = composeToggleText(TranslatableText("options.capes.glint"), glint)

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        textRenderer.drawWithShadow(matrices, title.asString(), (width / 2 - textRenderer.getWidth(title.asString()) / 2).toFloat(), 20f, 16777215)
        super.render(matrices, mouseX, mouseY, delta)
    }
}
