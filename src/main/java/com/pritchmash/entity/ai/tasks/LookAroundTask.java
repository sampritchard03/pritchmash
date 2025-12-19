package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;

public class LookAroundTask extends Task {

	public float randomYawVelocity;

	public LookAroundTask(MobTaskdoer mob) {
		super(mob);
	}

	@Override
	protected void onStart() {

	}

	@Override
	protected Task onTick() {
		if (this.random.nextFloat() < 0.09F) {
			this.randomYawVelocity = (this.random.nextFloat() - 0.5F) * 20.0F;
		}

		this.mob.yRot += this.randomYawVelocity;
		this.mob.xRot = 0.0F;

		if (this.randomYawVelocity > 1.0F) this.randomYawVelocity *= 0.92F;
		else this.randomYawVelocity = 0;
		return null;
	}

	@Override
	protected void onStop(Task interruptTask) {

	}

	@Override
	protected boolean isEqual(Task other) {
		return other instanceof LookAroundTask;
	}
}
