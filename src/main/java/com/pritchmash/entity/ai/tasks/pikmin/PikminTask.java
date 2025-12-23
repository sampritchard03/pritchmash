package com.pritchmash.entity.ai.tasks.pikmin;

import com.pritchmash.entity.ai.tasks.path.FollowPlayerTask;
import com.pritchmash.entity.ai.tasks.compound.IdleTask;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.entity.Entity;

import java.util.Objects;

public class PikminTask<T extends MobPikmin> extends Task<T> {

	public final FollowPlayerTask<T> followPlayerTask;
	public final IdleTask<T> idleTask;

	public PikminTask(T mob) {
		super(mob);
		this.followPlayerTask = new FollowPlayerTask<>(mob);
		this.idleTask = new IdleTask<>(mob);
		this.idleTask.shouldSwim = false;
	}

	@Override
	protected void onStart() {

	}

	@Override
	protected Task onTick() {

		if (this.mob.vehicle != null) return null;

		if (this.mob.isInLava()) this.mob.startJumping();

		if (this.mob.landingTask != null) {
			if (this.mob.landingTask.isFinished()) this.mob.landingTask = null;
			return this.mob.landingTask;
		}

		idleTask.shouldWander = true;
		if (!Objects.equals(mob.leaderName, "")) {
			Entity target = mob.leader();
			if (target != null) {
				if (target.distanceTo(this.mob) > 3.0F) return this.followPlayerTask;
				else idleTask.shouldWander = false;
			}
		}
		return idleTask;
	}

	@Override
	protected void onStop(Task interruptTask) {

	}

	@Override
	protected boolean isEqual(Task other) {
		return false;
	}
}
