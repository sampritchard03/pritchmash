package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.IMob;
import com.pritchmash.entity.MobTaskdoer;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.pathfinder.Path;

import java.util.Random;

public class WanderTask extends Task {

	private Path path;
	private final Random random;

	public WanderTask(MobTaskdoer mob) {
		super(mob);
		this.random = new Random();
	}

	@Override
	protected void onStart() {

	}

	protected void roamRandomPath() {
		if (this.mob.world != null) {
			boolean canMoveToPoint = false;
			int x = -1;
			int y = -1;
			int z = -1;
			float bestPathWeight = -99999.0F;

			for(int l = 0; l < 20; ++l) {
				int x1 = MathHelper.floor(this.mob.x + (double)this.random.nextInt(13) - (double)6.0F);
				int y1 = MathHelper.floor(this.mob.y + (double)this.random.nextInt(7) - (double)3.0F);
				int z1 = MathHelper.floor(this.mob.z + (double)this.random.nextInt(13) - (double)6.0F);
				float currentPathWeight = this.mob.getBlockPathWeight(x1, y1, z1);
				if (currentPathWeight > bestPathWeight) {
					bestPathWeight = currentPathWeight;
					x = x1;
					y = y1;
					z = z1;
					canMoveToPoint = true;
				}
			}

			if (canMoveToPoint) {
				this.path = this.mob.world.getEntityPathToXYZ(this.mob, x, y, z, 10.0F);
			}

		}
	}

	@Override
	protected Task onTick() {
		if (this.path == null && this.random.nextInt(80) == 0) {
			roamRandomPath();
		}

		int i = MathHelper.floor(this.mob.bb.minY + (double)0.5F);
		boolean inWater = this.mob.isInWater();
		boolean inLava = this.mob.isInLava();
		IMob iMob = (IMob)this.mob;
		iMob.setJumping(false);
		if (this.path != null && Math.random() > 0.001) {
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

			if (coordsForNextPath != null) {
				double x1 = coordsForNextPath.x - this.mob.x;
				double z1 = coordsForNextPath.z - this.mob.z;
				double y1 = coordsForNextPath.y - (double)i;
				float f2 = (float)(Math.atan2(z1, x1) * (double)180.0F / Math.PI) - 90.0F;
				float f3 = f2 - this.mob.yRot;

				iMob.setMoveForward(iMob.getMoveSpeed());

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

				if (y1 > (double)0.0F) {
					iMob.setJumping(true);
				}
			} else iMob.setMoveForward(0);

		} else {
			this.path = null;
		}

		if (this.mob.horizontalCollision && this.path == null) {
			iMob.setJumping(true);
		}

		if (Math.random() < 0.8F && (inWater || inLava)) {
			iMob.setJumping(true);
		}

		return null;
	}

	@Override
	protected void onStop(Task interruptTask) {

	}

	@Override
	protected boolean isEqual(Task other) {
		return false;
	}
}
