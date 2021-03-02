package com.github.MrAn0nym.mixin;

import com.github.MrAn0nym.NexusClient;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(PlayerListHud.class)
public class PlayerListHUDMixin {
    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
    private int injected(MatrixStack matrices, Text text, float x, float y, int color) {
        if (color != -1862270977) {
            AtomicInteger colorNew = new AtomicInteger(color);
            NexusClient.roles.forEach((s, strings) -> {
                if (strings.contains(text.getString().toLowerCase().replace("■",""))) {
                    colorNew.set(Integer.parseInt(s.substring(0, 8)));
                }
            });
            color = colorNew.get();
        }
        return color;
    }
}
