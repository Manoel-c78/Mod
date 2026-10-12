package com.seuusuario.vassouramod.item;

import com.seuusuario.vassouramod.VassouraMod;
import com.seuusuario.vassouramod.entity.BroomEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BroomItem extends Item {
    public BroomItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            BroomEntity broom = new BroomEntity(VassouraMod.BROOM_ENTITY.get(), level);
            Vec3 pos = player.position();
            broom.setPos(pos.x, pos.y + 0.5D, pos.z);
            level.addFreshEntity(broom);
            player.startRiding(broom);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}