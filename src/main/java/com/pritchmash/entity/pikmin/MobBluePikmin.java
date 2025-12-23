package com.pritchmash.entity.pikmin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

public class MobBluePikmin extends MobPikmin {
	public MobBluePikmin(World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "bluepikmin");
		waterImmune = true;
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		Material mat = this.world.getBlockMaterial(x, y, z);
		if (mat == Material.lava) return -100.0F;
		return 0.0F;
	}
}
