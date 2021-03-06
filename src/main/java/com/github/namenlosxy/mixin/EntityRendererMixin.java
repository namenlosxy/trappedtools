package com.github.namenlosxy.mixin;

import com.github.namenlosxy.TrappedTools;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.concurrent.atomic.AtomicInteger;

import static com.github.namenlosxy.hexToColor.convertToColor;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    private T entity;

    @ModifyVariable(method = "renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private T injected(T entity) {
        this.entity = entity;
        return entity;
    }


    @ModifyArgs(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/text/Text;FFIZLnet/minecraft/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I"))
    private void injected(Args args) {
        //Change Color
        AtomicInteger color = new AtomicInteger(args.get(3));
        Text text = args.get(0);
        if (entity instanceof PlayerEntity) {
            TrappedTools.roles.forEach((s, strings) -> {
                if (strings.contains(text.getString().toLowerCase().replace("■",""))) {
                    color.set(convertToColor(s.substring(0, 7)));
                }
            });
        }
        args.set(3, color.get());
    }
}