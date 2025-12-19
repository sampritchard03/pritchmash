package com.pritchmash.entity.ai.tasks;

import com.pritchmash.entity.MobTaskdoer;
import com.pritchmash.entity.ai.IMobPathfinder;
import com.pritchmash.entity.ai.targeting.BlockTargeting;
import com.pritchmash.world.Blockpos;
import net.minecraft.core.util.helper.MathHelper;

import java.util.Random;

public class WanderTask extends Task {

	private final Random random;

	public WanderTask(MobTaskdoer mob) {
		super(mob);
		this.random = new Random();
	}

	@Override
	protected void onStart() {

	}

	@Override
	protected Task onTick() {
		if (this.mob.getPath() == null && this.random.nextInt(10) == 0) {
			if (this.mob.world != null) {
				boolean canMoveToPoint = false;
				int x = -1;
				int y = -1;
				int z = -1;
				float bestPathWeight = -99999.0F;

				for(int l = 0; l < 10; ++l) {
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
					this.mob.setPath(this.mob.world.getEntityPathToXYZ(this.mob, x, y, z, 10.0F));
				}

			}
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
