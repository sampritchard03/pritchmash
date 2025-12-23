package com.pritchmash.particle;

import net.minecraft.client.entity.particle.ParticleNote;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.world.World;

public class ParticleExclamation extends ParticleNote {
	public ParticleExclamation(World world, double x, double y, double z, double xa, double ya, double za, int data) {
		super(world, x, y, z, xa, ya, za, data);
		this.tex = TextureRegistry.getTexture("pritchmash:particle/exclamation");
	}
}
