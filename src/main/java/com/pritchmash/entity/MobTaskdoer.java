package com.pritchmash.entity;

import com.pritchmash.entity.interfaces.IEntity;
import com.pritchmash.entity.ai.tasks.Task;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.world.World;

import java.util.function.Predicate;

public abstract class MobTaskdoer extends MobPathfinder {

	public Task task;

	public MobTaskdoer(World world) {
		super(world);
	}

	public float getBlockPathWeight(int x, int y, int z) {
		return 0.0F;
	}

	@Override
	public void updateAI() {
		if (task != null) task.tick();
	}

	/**
	 * Mobs that can't swim will get stuck in place on the seafloor if this is false. Default is false.
	 */
	public void setShouldPathOnSeafloor(boolean shouldPathOnSeafloor) {
		((IEntity)this)._setShouldPathOnSeafloor(shouldPathOnSeafloor);
	}

	public void setMoveForward(float moveForward) {
		this.moveForward = moveForward;
	}

	public void startJumping() {
		this.isJumping = true;
	}

	public void stopJumping() {
		this.isJumping = false;
	}
}
