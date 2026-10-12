package com.seuusuario.vassouramod.entity;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.nbt.CompoundTag;

public class BroomEntity extends Entity {
    
    private static final double ACCELERATION = 0.05D;
    private static final double MAX_SPEED = 0.8D;
    private static final double FRICTION = 0.95D;

    public BroomEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return entity.isPushable() && !this.isPassengerOfSameVehicle(entity);
    }

    @Override
    public void tick() {
        super.tick();
        this.setNoGravity(true);
        
        Entity passenger = this.getControllingPassenger();
        if (passenger instanceof Player player) {
            this.setYRot(player.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(player.getXRot() * 0.5F);
            this.xRotO = this.getXRot();

            Vec3 movement = this.getDeltaMovement();
            double forward = player.zza; 
            double upward = 0.0D;

            // Olhar para cima (pitch negativo menor que -25) faz subir, Shift faz descer
            if (player.getXRot() < -25.0F) {
                upward += 0.2D;
            }
            if (player.isShiftKeyDown()) {
                upward -= 0.2D;
            }

            if (forward != 0.0D) {
                Vec3 lookAngle = this.getLookAngle();
                double newX = movement.x + lookAngle.x * ACCELERATION * forward;
                double newZ = movement.z + lookAngle.z * ACCELERATION * forward;
                
                double currentSpeed = Math.sqrt(newX * newX + newZ * newZ);
                if (currentSpeed > MAX_SPEED) {
                    newX = (newX / currentSpeed) * MAX_SPEED;
                    newZ = (newZ / currentSpeed) * MAX_SPEED;
                }
                
                this.setDeltaMovement(newX, upward != 0 ? upward : movement.y, newZ);
            } else {
                this.setDeltaMovement(movement.x * FRICTION, upward != 0 ? upward : movement.y * FRICTION, movement.z * FRICTION);
            }

            if (upward == 0.0D && forward == 0.0D) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(FRICTION, 0.8D, FRICTION));
            }
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.5D, 0.5D, 0.5D));
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        if (passenger instanceof LivingEntity livingEntity) {
            return livingEntity;
        }
        return null;
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction callback) {
        if (this.hasPassenger(passenger)) {
            double offsetX = 0.0D;
            double offsetY = 0.6D; 
            double offsetZ = 0.0D;
            
            Vec3 offset = new Vec3(offsetX, offsetY, offsetZ).yRot(-this.getYRot() * ((float)Math.PI / 180F));
            passenger.setPos(this.getX() + offset.x, this.getY() + offset.y, this.getZ() + offset.z);
            passenger.setYBodyRot(this.getYRot());
            passenger.setXRot(this.getXRot());
        }
    }
}