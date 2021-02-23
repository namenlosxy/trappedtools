package com.github.MrAn0nym.mixin;

import com.github.MrAn0nym.NexusClient;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(at = {@At("HEAD")}, method = "reload")
    public void reload(CallbackInfo ci) {
        NexusClient.reloadroles();
    }


}
