package com.pritchmash.entity;

import com.pritchmash.entity.ai.IEntity;
import com.pritchmash.entity.ai.IMobPathfinder;
import com.pritchmash.entity.ai.tasks.Task;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;

public abstract class MobTaskdoer extends MobPathfinder {

	public MobTaskdoer(World world) {
		super(world);
		this.setShouldWander(false);
	}

	public abstract Task makeTask();
	public Task task;

	public float getBlockPathWeight(int x, int y, int z) {
		return 0.0F;
	}

	public void updateAI() {
		this.task = makeTask();
		if (task != null) task.tick();
		super.updateAI();
	}

	public void setShouldSwim(boolean shouldSwim) {
		((IEntity)this)._setShouldSwim(shouldSwim);
	}

	public void setShouldWander(boolean shouldWander) {
		((IMobPathfinder)this)._setShouldWander(shouldWander);
	}

	public void setPath(Path path) {
		((IMobPathfinder)this)._setPath(path);
	}

	public Path getPath() {
		return ((IMobPathfinder)this)._getPath();
	}
}
