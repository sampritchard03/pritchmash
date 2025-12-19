package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.Sys;

public class LookAtPlayersTask extends Task {

	public Entity currentTarget;
	public int ticks;

	public LookAtPlayersTask(MobTaskdoer mob) {
		super(mob);
	}

	@Override
	protected void onStart() {

	}

	private float rotationLerp(float f, float f1, float f2) {
		float f3;
		for(f3 = f1 - f; f3 < -180.0F; f3 += 360.0F) {
		}

		while(f3 >= 180.0F) {
			f3 -= 360.0F;
		}

		if (f3 > f2) {
			f3 = f2;
		}

		if (f3 < -f2) {
			f3 = -f2;
		}

		return f + f3;
	}

	public void lookAt(Entity entity, float yRot, float xRot) {
		double x = entity.x - this.mob.x;
		double y = entity.z - this.mob.z;
		double y2;
		if (entity instanceof Mob) {
			Mob mob = (Mob)entity;
			y2 = mob.y + (double)mob.getHeadHeight() - (this.mob.y + (double)this.mob.getHeadHeight());
		} else {
			y2 = (entity.bb.minY + entity.bb.maxY) / (double)2.0F - (this.mob.y + (double)this.mob.getHeadHeight());
		}

		double d3 = (double) MathHelper.sqrt(x * x + y * y);
		float f2 = (float)(Math.atan2(y, x) * (double)180.0F / Math.PI) - 90.0F;
		float f3 = (float)(-(Math.atan2(-y2, d3) * (double)180.0F / Math.PI));
		this.mob.xRot = -this.rotationLerp(-this.mob.xRot, f3, xRot);
		this.mob.xRot = Math.min(xRot, Math.max(-xRot, this.mob.xRot));
		this.mob.yRot = this.rotationLerp(this.mob.yRot, f2, yRot);
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
			this.lookAt(this.currentTarget, 10.0F, 40);
			if (this.ticks-- <= 0 || this.currentTarget.removed || this.currentTarget.distanceToSqr(this.mob) > (double)(64.0F)) {
				this.currentTarget = null;
			}
		}
		return null;
	}

	@Override
	protected void onStop(Task interruptTask) {

	}

	@Override
	protected boolean isEqual(Task other) {
		return other instanceof LookAtPlayersTask;
	}
}
