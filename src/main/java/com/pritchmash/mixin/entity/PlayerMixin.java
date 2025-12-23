package com.pritchmash.mixin.entity;

import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Shadow
	public String username;

	@Inject(method = "push", at = @At("HEAD"), cancellable = true)
	private void push(Entity entity, CallbackInfo ci) {
		if (entity instanceof MobPikmin) {
			MobPikmin pikmin = (MobPikmin) entity;
			if (Objects.equals(this.username, pikmin.leaderName)) {
				ci.cancel();
			};
		}

	}

}
