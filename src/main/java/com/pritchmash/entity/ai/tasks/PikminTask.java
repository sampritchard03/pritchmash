package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import com.pritchmash.entity.interfaces.IFollower;
import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.entity.Entity;

import java.util.Objects;

public class PikminTask extends Task{

	private final MobPikmin pikmin;

	public final FollowPlayerTask followPlayerTask;
	public final IdleTask idleTask;

	public PikminTask(MobTaskdoer mob) {
		super(mob);
		this.pikmin = (MobPikmin) mob;
		this.followPlayerTask = new FollowPlayerTask(mob);
		this.idleTask = new IdleTask(mob);
	}

	@Override
	protected void onStart() {

	}

	@Override
	protected Task onTick() {
		if (!Objects.equals(pikmin.leaderName, "")) {
			Entity target = pikmin.followTarget();
			if (target != null && target.distanceTo(this.mob) > 3.0F) return this.followPlayerTask;
			idleTask.shouldWander = false;
		} else idleTask.shouldWander = true;
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
