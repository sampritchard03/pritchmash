package com.pritchmash.entity.pikmin;

import com.pritchmash.entity.MobTaskrunner;
import com.pritchmash.entity.ai.tasks.attack.MeeleeAttackTask;
import com.pritchmash.entity.ai.tasks.pikmin.PikminTask;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.interfaces.IFollower;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobPig;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class MobPikmin extends MobTaskrunner implements IFollower {
	public String leaderName = "";
	public boolean waterImmune = false;
	public Task<?> landingTask = null;

	public MobPikmin(World world) {
		super(world);
		setSize(0.5F, 1F);
		this.setShouldPathOnSeafloor(true);
	}

	@Override
	public Task<MobPikmin> createTask() {
		return new PikminTask<>(this);
	}

	public void livingTick() {
		if (!this.waterImmune && this.isInWater() && this.time % 30 == 0) this.hurt(null, 1, DamageType.GENERIC);
	}

	@Override
	public void push(Entity entity) {
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (Objects.equals(player.username, this.leaderName)) {
				return;
			};
		}
		super.push(entity);
	}

	public void call(Player player) {
		if (this.leaderName.equals("")) {
			this.leaderName = player.username;
			this.world.spawnParticle("exclamation", this.x, this.y+1, this.z, 0, 0, 0, this.random.nextInt(10));
		}
	}

	public Task landOnMob(Mob mob) {
		if (mob != leader()) return new MeeleeAttackTask<>(this, mob, 1.5F, 1, 7);
		return null;
	}

	public void onLanding(HitResult hitResult) {
		Entity closestEntity = null;
		double closestDist = 4;
		Vec3 p = hitResult.location;

		for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, AABB.getTemporaryBB(p.x-4, p.y-4, p.z-4, p.x+4, p.y+4, p.z+4))) {
			if (!(entity instanceof Mob)) continue;
			if (entity instanceof MobPikmin && ((MobPikmin) entity).leaderName.equals(this.leaderName)) continue;
			Vec3 p2 = Vec3.getTempVec3(entity.x, entity.y, entity.z);
			double dist = p.distanceTo(p2);
			if (dist < closestDist) {
				closestDist = dist;
				closestEntity = entity;
			}
		}
		if (closestEntity != null) landingTask = landOnMob((Mob)closestEntity);
	}

	@Override
	public boolean interact(Player player) {
		this.call(player);
		return super.interact(player);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		Material mat = this.world.getBlockMaterial(x, y, z);
		if (mat == Material.lava) return -100.0F;
		if (mat == Material.water) return -10.0F;
		return 0.0F;
	}

	@Override
	public int getMaxHealth() {
		return 8;
	}

	@Override
	public @Nullable Entity leader() {
		return this.world.getPlayerEntityByName(this.leaderName);
	}

	@Override
	public void causeFallDamage(float distance) {

	}

	@Override
	public boolean canBreatheUnderwater() {
		return waterImmune;
	}
}
