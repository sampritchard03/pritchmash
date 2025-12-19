package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import com.pritchmash.entity.interfaces.IFollower;
import net.minecraft.core.entity.Entity;

public class FollowPlayerTask extends PathTask {
	public FollowPlayerTask(MobTaskdoer mob) {
		super(mob);
		this.moveSpeed = 12.0F;
	}

	@Override
	protected void onStart() {

	}

	@Override
	public Task onTick() {
		if (this.mob instanceof IFollower) {
			Entity target = ((IFollower)this.mob).followTarget();
			if (target != null) {
				this.path = this.mob.world.getPathToEntity(this.mob, target, 20.0F);
			}

			return super.onTick();
		}
		return null;
	}

	@Override
	protected void onStop(Task interruptTask) {
		this.path = null;
		this.mob.setMoveForward(0.0F);
	}

	@Override
	protected boolean isEqual(Task other) {
		return false;
	}
}
