package com.pritchmash.mixin.entity;

import com.pritchmash.entity.interfaces.IEntity;
import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin {

	@Inject(method = "knockBack", at = @At("HEAD"), cancellable = true)
	private void knockback(Entity entity, int i, double d, double d1, CallbackInfo ci) {
		if (entity instanceof MobPikmin) ci.cancel();
	}
}
