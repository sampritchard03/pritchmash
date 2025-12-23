package com.pritchmash.entity.pikmin;

import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

public class MobYellowPikmin extends MobPikmin {
	public MobYellowPikmin(World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "yellowpikmin");
	}
}
