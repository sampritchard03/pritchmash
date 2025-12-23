package com.pritchmash.entity.ai.tasks.attack;

import com.pritchmash.entity.MobTaskrunner;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.ai.tasks.path.PathTask;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.util.helper.DamageType;

public class MeeleeAttackTask<T extends MobTaskrunner> extends PathTask<T> {

	private final Entity target;
	public float reach;
	public int attackStrength;
	public int attackInterval;

	public MeeleeAttackTask(T mob, Entity target, float reach, int attackStrength, int attackInterval) {
		super(mob);
		this.target = target;
		this.reach = reach;
		this.attackStrength = attackStrength;
		this.attackInterval = attackInterval;
	}

	@Override
	protected void onStart() {

	}

	@Override
	public Entity lookTarget() {
		return target;
	}

	@Override
	public Task onTick() {
		this.path = this.mob.world.getPathToEntity(this.mob, target, 20.0F);
		if (this.target.distanceTo(this.mob) <= reach && time % this.attackInterval == 0) {
			this.target.hurt(this.mob, this.attackStrength, DamageType.COMBAT);
		}
		return super.onTick();
	}

	@Override
	public boolean isFinished() {
		return (this.target == null || !this.target.isAlive());
	}

	@Override
	protected boolean isEqual(Task other) {
		return false;
	}
}
