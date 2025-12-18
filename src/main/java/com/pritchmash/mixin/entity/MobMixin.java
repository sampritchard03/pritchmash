package com.pritchmash.mixin.entity;

import com.pritchmash.entity.IMob;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class MobMixin implements IMob {

	@Shadow
	protected boolean isJumping;

	@Shadow
	protected float moveForward;

	@Shadow
	protected float moveSpeed;

	@Shadow
	protected float moveStrafing;

	@Override
	public boolean setJumping(boolean jumping) {
		this.isJumping = jumping;
		return jumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}

	@Override
	public float setMoveForward(float moveForward) {
		this.moveForward = moveForward;
		return moveForward;
	}

	@Override
	public float getMoveForward() {
		return this.moveForward;
	}

	@Override
	public float setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
		return moveSpeed;
	}

	@Override
	public float getMoveSpeed() {
		return this.moveSpeed;
	}

	@Override
	public float setMoveStrafing(float moveStrafing) {
		this.moveStrafing = moveStrafing;
		return moveStrafing;
	}

	@Override
	public float getMoveStrafing() {
		return this.moveStrafing;
	}
}
