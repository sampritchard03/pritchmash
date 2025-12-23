package com.pritchmash.particle;

import net.minecraft.client.entity.particle.ParticleDispatcher;
import net.minecraft.client.entity.particle.ParticleLambda;

public class PritchMashParticles {
	public static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeParticles();
		}

	}

	public static void initializeParticles() {
		ParticleDispatcher.getInstance().addDispatch("exclamation", (ParticleLambda)(world, x, y, z, motionX, motionY, motionZ, data) -> new ParticleExclamation(world, x, y, z, motionX, motionY, motionX, data));
	}
}
