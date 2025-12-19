package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import net.minecraft.core.world.pathfinder.Path;

import java.util.Random;

public class IdleTask extends Task {

	public boolean shouldWander = true;

	public final WanderTask wanderTask;
	public final LookAtPlayersTask lookAtPlayersTask;
	public final LookAroundTask lookAroundTask;

	public IdleTask(MobTaskdoer mob) {
		super(mob);
		this.wanderTask = new WanderTask(mob);
		this.lookAtPlayersTask = new LookAtPlayersTask(mob);
		this.lookAroundTask = new LookAroundTask(mob);
	}

	public boolean isLooking() {
		return lookAroundTask.randomYawVelocity > 0 || lookAtPlayersTask.currentTarget != null;
	}

	@Override
	protected void onStart() {

	}

	@Override
	public Task onTick() {
		if (
			this.shouldWander && (
				this.wanderTask.path != null ||
				!this.isLooking() && this.random.nextInt(80) == 0
			)
		) return this.wanderTask;
		else if (
			this.lookAtPlayersTask.currentTarget != null ||
			this.random.nextFloat() < 0.03F
		) return this.lookAtPlayersTask;
		else return this.lookAroundTask;
	}

	@Override
	protected void onStop(Task interruptTask) {
		this.wanderTask.path = null;
		this.lookAtPlayersTask.currentTarget = null;
		this.lookAroundTask.randomYawVelocity = 0;
		this.mob.setMoveForward(0.0F);
	}

	@Override
	protected boolean isEqual(Task other) {
		return other instanceof IdleTask;
	}
}
