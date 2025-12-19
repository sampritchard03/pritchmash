package com.pritchmash.mixin.entity;

import com.pritchmash.entity.ai.IEntity;
import com.pritchmash.entity.ai.IMobPathfinder;
import com.pritchmash.entity.ai.targeting.BlockTargeting;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobPathfinder.class)
public abstract class MobPathfinderMixin extends Mob implements IMobPathfinder {

	@Unique
	private boolean shouldWander = true;

	public MobPathfinderMixin(@Nullable World world) {
		super(world);
	}

	@Override
	public void _setShouldWander(boolean shouldWander) {this.shouldWander = shouldWander;}
	@Override
	public void _setPath(Path path) {this.pathToEntity = path;}
	@Override
	public Path _getPath() {return this.pathToEntity;}

	@Shadow
	@Nullable
	protected Path pathToEntity;

	@Shadow
	protected boolean hasAttacked;

	@Shadow
	protected abstract boolean isMovementCeased();

	@Shadow
	@Nullable
	protected Entity target;

	@Shadow
	protected abstract Entity findPlayerToAttack();

	@Shadow
	protected abstract void attackEntity(@NotNull Entity entity, float distance);

	@Shadow
	protected abstract void attackBlockedEntity(@NotNull Entity entity, float f);

	@Shadow
	@Nullable
	protected Entity closestFireflyEntity;

	@Shadow
	protected abstract void roamRandomPath();

	@Shadow
	public abstract boolean hasPath();

	@Inject(method = "roamRandomPath", at = @At("HEAD"), cancellable = true)
	protected void roamRandomPathInject(CallbackInfo ci) {
		if (!this.shouldWander) ci.cancel();
	}

	private boolean keepJumping;

	@Inject(method = "updateAI", at = @At(value = "HEAD"), cancellable = true)
	protected void updateAI(CallbackInfo ci) {
		if (this.world != null) {
			this.hasAttacked = this.isMovementCeased();
			float sightRadius = 16.0F;
			if (this.target == null) {
				this.target = this.findPlayerToAttack();
				if (this.target != null) {
					this.pathToEntity = this.world.getPathToEntity(this, this.target, sightRadius);
				}
			} else if (!this.target.isAlive()) {
				this.target = null;
			} else {
				float distanceToEntity = this.target.distanceTo(this);
				if (this.canEntityBeSeen(this.target)) {
					this.attackEntity(this.target, distanceToEntity);
				} else {
					this.attackBlockedEntity(this.target, distanceToEntity);
				}
			}

			if (this.hasAttacked || this.target == null || this.pathToEntity != null && this.random.nextInt(20) != 0) {
				if (!this.hasAttacked && this.closestFireflyEntity == null && (this.pathToEntity == null && this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0)) {
					this.roamRandomPath();
				}
			} else {
				this.pathToEntity = this.world.getPathToEntity(this, this.target, sightRadius);
			}

			int i = MathHelper.floor(this.bb.minY + (double)0.5F);
			boolean inWater = this.isInWater();
			boolean inLava = this.isInLava();
			this.xRot = 0.0F;
			if (this.pathToEntity != null && this.random.nextInt(100) != 0) {
				Vec3 coordsForNextPath = this.pathToEntity.getPos(this);
				double d = (double)(this.bbWidth * 2.0F);

				while(coordsForNextPath != null && coordsForNextPath.distanceToSquared(this.x, coordsForNextPath.y, this.z) < d * d) {
					this.pathToEntity.next();
					if (this.pathToEntity.isDone()) {
						this.closestFireflyEntity = null;
						coordsForNextPath = null;
						this.pathToEntity = null;
					} else {
						coordsForNextPath = this.pathToEntity.getPos(this);
					}
				}

				this.isJumping = false;
				if (coordsForNextPath != null) {
					d = coordsForNextPath.x - this.x;
					double z1 = coordsForNextPath.z - this.z;
					double y1 = coordsForNextPath.y - (double)i;
					float f2 = (float)(Math.atan2(z1, d) * (double)180.0F / Math.PI) - 90.0F;
					float f3 = f2 - this.yRot;

					for(this.moveForward = this.moveSpeed; f3 < -180.0F; f3 += 360.0F) {
					}

					while(f3 >= 180.0F) {
						f3 -= 360.0F;
					}

					if (f3 > 30.0F) {
						f3 = 30.0F;
					}

					if (f3 < -30.0F) {
						f3 = -30.0F;
					}

					this.yRot += f3;
					if (this.hasAttacked && this.target != null) {
						double d4 = this.target.x - this.x;
						double d5 = this.target.z - this.z;
						float f5 = this.yRot;
						this.yRot = (float)(Math.atan2(d5, d4) * (double)180.0F / Math.PI) - 90.0F;
						float f4 = (f5 - this.yRot + 90.0F) * (float)Math.PI / 180.0F;
						this.moveStrafing = -MathHelper.sin(f4) * this.moveForward * 1.0F;
						this.moveForward = MathHelper.cos(f4) * this.moveForward * 1.0F;
					}

					if (y1 > (double)0.0F || this.keepJumping) {
						this.keepJumping = this.isInWater() && y1 > (double)-1.0F;
						this.isJumping = true;
					}
				}

				if (this.target != null) {
					this.lookAt(this.target, 30.0F, 30.0F);
				}

				if (this.horizontalCollision && !this.hasPath()) {
					this.isJumping = true;
				}

				if ((((IEntity)this)._getShouldSwim()) && this.random.nextFloat() < 0.8F && (inWater || inLava)) {
					this.isJumping = true;
				}

			} else {
				super.updateAI();
				this.pathToEntity = null;
			}
		}
		ci.cancel();
	}
}
