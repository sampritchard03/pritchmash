package com.pritchmash.entity.pikmin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

public class MobRedPikmin extends MobPikmin {
	public MobRedPikmin(World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "redpikmin");
		fireImmune = true;
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		Material mat = this.world.getBlockMaterial(x, y, z);
		if (mat == Material.water) return -10.0F;
		return 0.0F;
	}
}
