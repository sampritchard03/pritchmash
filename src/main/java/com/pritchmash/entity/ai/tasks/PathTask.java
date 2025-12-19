package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.pathfinder.Path;

public abstract class PathTask extends Task{

	public Path path;
	public float moveSpeed = 0.7F;
	private boolean keepJumping;

	public PathTask(MobTaskdoer mob) {
		super(mob);
	}

	@Override
	public Task onTick() {
		if (this.mob.world != null) {
			int i = MathHelper.floor(this.mob.bb.minY + (double)0.5F);
			this.mob.xRot = 0.0F;
			this.mob.setMoveForward(0.0F);
			if (this.path != null && this.random.nextInt(100) != 0) {
				Vec3 coordsForNextPath = this.path.getPos(this.mob);
				double d = (double)(this.mob.bbWidth * 2.0F);


				while(coordsForNextPath != null && coordsForNextPath.distanceToSquared(this.mob.x, coordsForNextPath.y, this.mob.z) < d * d) {
					this.path.next();
					if (this.path.isDone()) {
						coordsForNextPath = null;
						this.path = null;
					} else {
						coordsForNextPath = this.path.getPos(this.mob);
					}
				}

				this.mob.stopJumping();
				if (coordsForNextPath != null) {
					double x1 = coordsForNextPath.x - this.mob.x;
					double z1 = coordsForNextPath.z - this.mob.z;
					double y1 = coordsForNextPath.y - (double)i;
					float f2 = (float)(Math.atan2(z1, x1) * (double)180.0F / Math.PI) - 90.0F;
					float f3;

					for(f3 = f2 - this.mob.yRot; f3 < -180.0F; f3 += 360.0F) {
					}
					this.mob.setMoveForward(this.moveSpeed);

					while(f3 >= 180.0F) {
						f3 -= 360.0F;
					}

					if (f3 > 30.0F) {
						f3 = 30.0F;
					}

					if (f3 < -30.0F) {
						f3 = -30.0F;
					}

					this.mob.yRot += f3;

					if (y1 > (double)0.0F || this.keepJumping) {
						this.keepJumping = this.mob.isInWater() && y1 > (double)-1.0F;
						this.mob.startJumping();
					}
				}

				if (this.mob.horizontalCollision) {
					this.mob.startJumping();
				}
			} else {
				this.path = null;
			}
		}
		return null;
	}
}
