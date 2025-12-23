package com.pritchmash.entity.projectile;

import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.HitResult;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.lwjgl.Sys;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectilePikminVessel extends Projectile {
	public ProjectilePikminVessel(World world, Player player) {
		super(world, player);
	}

	@Override
	protected void initProjectile() {
		this.damage = 0;
		this.defaultGravity = 0.08F;
		this.defaultProjectileSpeed = 0.93F;
	}

	@Override
	public Entity ejectRider() {
		Entity entity = this.passenger;
		if (entity == null) {
			return null;
		} else {
			this.passenger = null;
			entity.vehicle = null;
			entity.moveTo(this.x, this.y+1, this.z, entity.yRot, entity.xRot);
			return entity;
		}
	}

	@Override
	public void tick() {
		this.gravity = this.defaultGravity;
		this.projectileSpeed = this.defaultProjectileSpeed;
		super.baseTick();
		++this.ticksInAir;
		HitResult movingobjectposition = this.getHitResult();
		Vec3 oldPosition = Vec3.getTempVec3(this.x, this.y, this.z);
		Vec3 newPosition = Vec3.getTempVec3(this.x + this.xd, this.y + this.yd, this.z + this.zd);
		if (movingobjectposition != null) {
			newPosition = Vec3.getTempVec3(movingobjectposition.location.x, movingobjectposition.location.y, movingobjectposition.location.z);
		}

		if (!this.world.isClientSide) {
			Entity entity = null;
			List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.bb.expand(this.xd, this.yd, this.zd).grow((double)1.0F, (double)1.0F, (double)1.0F))
				.stream().filter(e -> !e.equals(this.passenger)).collect(Collectors.toList());
			double d = (double)0.0F;

			for(Entity entity1 : list) {
				boolean canPick = true;
				if (entity1 instanceof MobPikmin && this.owner instanceof Player) {
					MobPikmin pikmin = (MobPikmin) entity1;
					Player player = (Player) this.owner;
					canPick = !pikmin.leaderName.equals(player.username);
				}
				if (entity1.isPickable() && (entity1 != this.owner || this.ticksInAir >= 5) && canPick) {
					float f4 = 0.3F;
					AABB axisalignedbb = entity1.bb.grow((double)f4, (double)f4, (double)f4);
					HitResult movingobjectposition1 = axisalignedbb.clip(oldPosition, newPosition);
					if (movingobjectposition1 != null) {
						double d1 = oldPosition.distanceTo(movingobjectposition1.location);
						if (d1 < d || d == (double)0.0F) {
							entity = entity1;
							d = d1;
						}
					}
				}
			}

			if (entity != null) {
				movingobjectposition = new HitResult(entity);
			}
		}

		MobPikmin pikmin = null;
		if (this.passenger instanceof MobPikmin) pikmin = (MobPikmin) this.passenger;

		if (movingobjectposition != null) {
			if (pikmin != null) pikmin.onLanding(movingobjectposition);
			this.remove();
		}

		if (pikmin != null) {
			Vec3 v = Vec3.getTempVec3(this.xd, this.yd, this.zd).normalize();
			pikmin.xRot = (float)(-Math.asin(v.y)*180.0/Math.PI);
			pikmin.yRot = (float)(Math.atan2(v.z, v.x)*180.0/Math.PI)-90.0F;
			pikmin.yBodyRot = pikmin.yRot;
		}

		this.afterTick();
	}
}
