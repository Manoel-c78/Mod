package com.seuusuario.vassouramod.client;

import com.seuusuario.vassouramod.VassouraMod;
import com.seuusuario.vassouramod.client.renderer.BroomModel;
import com.seuusuario.vassouramod.client.renderer.BroomRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VassouraMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BroomModel.LAYER_LOCATION, BroomModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(VassouraMod.BROOM_ENTITY.get(), BroomRenderer::new);
    }
}