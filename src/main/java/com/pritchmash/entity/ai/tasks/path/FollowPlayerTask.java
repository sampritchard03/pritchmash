package com.pritchmash.entity.ai.tasks.path;

import com.pritchmash.entity.MobTaskrunner;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.interfaces.IFollower;
import net.minecraft.core.entity.Entity;

public class FollowPlayerTask<T extends MobTaskrunner> extends PathTask<T> {
	public FollowPlayerTask(T mob) {
		super(mob);
		this.moveSpeed = 15.0F;
	}

	@Override
	protected void onStart() {

	}

	@Override
	public Entity lookTarget() {
		if (this.mob instanceof IFollower) return ((IFollower)this.mob).leader();
		else return null;
	}

	@Override
	public Task onTick() {
		Entity target = this.lookTarget();
		if (target != null) {
			this.path = this.mob.world.getPathToEntity(this.mob, target, 20.0F);
		}
		return super.onTick();
	}

	@Override
	protected boolean isEqual(Task other) {
		return false;
	}
}
