package com.seuusuario.vassouramod.client.renderer;

import com.seuusuario.vassouramod.entity.BroomEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BroomRenderer extends EntityRenderer<BroomEntity> {
    
    private final BroomModel model;

    public BroomRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new BroomModel(context.bakeLayer(BroomModel.LAYER_LOCATION));
    }

    @Override
    public void render(BroomEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        poseStack.translate(0.0D, 0.35D, 0.0D);

        // Renderiza o modelo corretamente
        this.model.renderToBuffer(poseStack, buffer.getBuffer(this.model.renderType(this.getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BroomEntity entity) {
        return new ResourceLocation("vassouramod", "textures/entity/vassouratexture.png");
    }
}