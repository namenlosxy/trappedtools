package com.github.namenlosxy.mixin;

import com.github.namenlosxy.TrappedTools;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.overlay.PlayerTabOverlayGui;
import net.minecraft.util.text.ITextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.namenlosxy.hexToColor.convertToColor;

@Mixin(PlayerTabOverlayGui.class)
public class PlayerListHUDMixin {
    private static final Logger LOGGER = LogManager.getLogger();
    @ModifyArg(method = "func_238523_a_(Lcom/mojang/blaze3d/matrix/MatrixStack;ILnet/minecraft/scoreboard/Scoreboard;Lnet/minecraft/scoreboard/ScoreObjective;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;func_243246_a(Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/util/text/ITextComponent;FFI)I"))
    private int injected(MatrixStack p_243246_1_, ITextComponent p_243246_2_, float p_243246_3_, float p_243246_4_, int p_243246_5_) {
        if (p_243246_5_ != -1862270977) {
            AtomicInteger colorNew = new AtomicInteger(p_243246_5_);
            TrappedTools.roles.forEach((s, strings) -> {
                if (strings.contains(p_243246_2_.getString().toLowerCase().replace("■", ""))) {
                    colorNew.set(convertToColor(s.substring(0, 7)));
                }
            });
            p_243246_5_ = colorNew.get();
        }
        return p_243246_5_;
    }
}