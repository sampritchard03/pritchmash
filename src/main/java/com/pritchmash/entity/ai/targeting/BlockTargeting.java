package com.pritchmash.entity.ai.targeting;

import com.pritchmash.world.Blockpos;
import com.pritchmash.world.XZ;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.Sys;

public class BlockTargeting {

	public static XZ pickRandomXZ(Mob mob, int maxDist) {
		double dX = maxDist*Math.random();
		if (Math.random() > 0.5) dX = -dX;
		int x = (int)(mob.x + dX);
		double dZ = maxDist*Math.random();
		if (Math.random() > 0.5) dZ = -dZ;
		int z = (int)(mob.z + dZ);
		return new XZ(x, z);
	}

	public static @Nullable Blockpos getRandomWanderTarget(Mob mob, int maxDist, boolean avoidWater) {
		XZ b = pickRandomXZ(mob, maxDist);
		int y = (int)mob.y;

		if (mob.world == null) return null;
		int up = mob.world.getBlockId(b.x, y+1, b.z);
		int down = mob.world.getBlockId(b.x, y, b.z);

		int tries = 0;
		while (!(up == 0 && (down != 0 && down != 271)) && tries < 1000) {
			if (down == 271) {
				b = pickRandomXZ(mob, maxDist);
				y = (int)mob.y;
			} else {
				if (up != 0) {
					y += 1;
				} else {
					y -= 1;
				}
			}
			up = mob.world.getBlockId(b.x, y+1, b.z);
			down = mob.world.getBlockId(b.x, y, b.z);
			tries++;
		}

		if (tries == 1000) {
			System.out.println("Failed to find wander target.");
			return null;
		}

		System.out.println(Blocks.getBlock(down).getKey());
		return new Blockpos(b.x, y, b.z);
	}
}
