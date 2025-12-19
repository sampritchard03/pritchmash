package com.pritchmash.mixin.entity;

import com.pritchmash.entity.ai.IEntity;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mob.class)
public abstract class MobMixin {

	@Redirect(method = "updateAI", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/entity/Mob;isInWater()Z"))
	public boolean modifiedIsInWater2(Mob instance) {
		return instance.isInWater() && ((IEntity)instance)._getShouldSwim();
	}
}
