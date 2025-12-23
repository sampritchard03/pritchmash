package com.pritchmash.entity.ai.tasks.look;

import com.pritchmash.entity.MobTaskrunner;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.utils.LookUtils;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;

public class LookAtPlayersTask<T extends MobTaskrunner> extends Task<T> {

	public Entity currentTarget;
	public int ticks;

	public LookAtPlayersTask(T mob) {
		super(mob);
	}

	@Override
	protected void onStart() {

	}



	@Override
	protected Task onTick() {
		if (this.currentTarget == null) {
			Player entityplayer1 = this.mob.world.getClosestPlayerToEntity(this.mob, (double)8.0F);
			if (entityplayer1 != null) {
				this.currentTarget = entityplayer1;
				this.ticks = 10 + this.random.nextInt(20);
			}
		} else {
			LookUtils.lookAt(this.mob, this.currentTarget, 10.0F, 10.0F);
			if (this.ticks-- <= 0 || this.currentTarget.removed || this.currentTarget.distanceToSqr(this.mob) > (double)(64.0F)) {
				this.currentTarget = null;
			}
		}
		return null;
	}

	@Override
	protected void onStop(Task interruptTask) {
		this.currentTarget = null;
	}

	@Override
	protected boolean isEqual(Task other) {
		return other instanceof LookAtPlayersTask;
	}
}
