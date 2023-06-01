package com.atlas.legacy.legacyreviver.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractMinecartEntity.class)
public abstract class MinecartMixin extends Entity {

    public MinecartMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 0))
    public void redirectSpeed(AbstractMinecartEntity instance, Vec3d vec3d) {
        modSpeed(instance, vec3d);
    }
    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 1))
    public void redirectSpeed1(AbstractMinecartEntity instance, Vec3d vec3d) {
        modSpeed(instance, vec3d);
    }
    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 2))
    public void redirectSpeed2(AbstractMinecartEntity instance, Vec3d vec3d) {
        modSpeed(instance, vec3d);
    }
    @Redirect(method = "moveOnRail", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/vehicle/AbstractMinecartEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V", ordinal = 3))
    public void redirectSpeed3(AbstractMinecartEntity instance, Vec3d vec3d) {
        modSpeed(instance, vec3d);
    }
    @ModifyConstant(method = "moveOnRail", constant = @Constant(doubleValue = 0.1))
    public double modifyInheritedSpeed(double constant) {
        return constant;
    }
    @ModifyConstant(method = "moveOnRail", constant = @Constant(doubleValue = 0.06))
    public double modifyPoweredSpeed(double constant) {
        return constant / 2;
    }
    @ModifyConstant(method = "getMaxSpeed", constant = @Constant(doubleValue = 20.0))
    public double modifyMaxSpeed(double constant) {
        return constant / 2;
    }
    public void modSpeed(AbstractMinecartEntity entity, Vec3d vec3d) {
        entity.setVelocity(vec3d.multiply(2));
    }
}
