
package com.github.namenlosxy.mixin;

import com.github.namenlosxy.TrappedTools;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.namenlosxy.hexToColor.convertToColor;

@Mixin(PlayerListHud.class)
public class PlayerListHUDMixin {
    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreboardObjective;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I"))
    private int injected(MatrixStack matrices, Text text, float x, float y, int color) {
        if (color != -1862270977) {
            AtomicInteger colorNew = new AtomicInteger(color);
            TrappedTools.roles.forEach((s, strings) -> {
                if (strings.contains(text.getString().toLowerCase().replace("■",""))) {
                    colorNew.set(convertToColor(s.substring(0, 7)));
                }
            });
            color = colorNew.get();
        }
        return color;
    }
}