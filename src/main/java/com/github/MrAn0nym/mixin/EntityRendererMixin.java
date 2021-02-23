package com.github.MrAn0nym.mixin;

import com.github.MrAn0nym.NexusClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Shadow
    @Final
    protected EntityRenderDispatcher dispatcher;

    @Inject(at = {@At("HEAD")}, method = {"renderLabelIfPresent(Lnet/minecraft/entity/Entity;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"}, cancellable = true)
    private void onRenderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {

        RenderLabelIfPresent(entity, text, matrices, vertexConsumers, light);
        ci.cancel();
    }

    protected void RenderLabelIfPresent(T entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        double d = this.dispatcher.getSquaredDistanceToCamera(entity);
        if (d <= 4096.0D) {
            boolean bl = !entity.isSneaky();
            float f = entity.getHeight() + 0.5F;
            int i = "deadmau5".equals(text.getString()) ? -10 : 0;
            matrices.push();
            matrices.translate(0.0D, (double) f, 0.0D);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getModel();
            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int) (g * 255.0F) << 24;
            TextRenderer textRenderer = this.getFontRenderer();
            float h = (float) (-textRenderer.getWidth((StringVisitable) text) / 2);
            textRenderer.draw(text, h, (float) i, 553648127, false, matrix4f, vertexConsumers, bl, j, light);
            if (bl) {

                //Change Color
                AtomicInteger color = new AtomicInteger(-1);
                if (entity instanceof PlayerEntity) {
                    NexusClient.roles.forEach((s, strings) -> {
                        if (strings.contains(text.getString().toLowerCase())) {
                            color.set(Integer.parseInt(s.substring(0, 8)));
                        }
                    });
                }

                textRenderer.draw((Text) text, h, (float) i, color.get(), false, matrix4f, vertexConsumers, false, 0, light);
            }

            matrices.pop();
        }
    }


    @Shadow
    public TextRenderer getFontRenderer() {
        return null;
    }
}