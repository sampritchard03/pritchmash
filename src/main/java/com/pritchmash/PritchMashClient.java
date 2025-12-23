package com.pritchmash;

import com.pritchmash.particle.PritchMashParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.texture.stitcher.AtlasStitcher;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import turniplabs.halplibe.helper.TextureHelper;
import turniplabs.halplibe.util.ClientStartEntrypoint;

import static com.pritchmash.PritchMash.MOD_ID;

@Environment(EnvType.CLIENT)
public class PritchMashClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void beforeClientStart() {
		PritchMashParticles.init();

	}
	@Override
	public void afterClientStart() {

	}

	@Override
	public void onInitializeClient() {
		registerTextures();
	}


	public static void registerTextures() {
		for (final AtlasStitcher stitcher : TextureRegistry.stitcherMap.values()) {
			try {
				TextureHelper.initializeAllFiles(MOD_ID, stitcher, Integer.MAX_VALUE);
			} catch (Exception e) {
				PritchMash.LOGGER.error("Failed to initialize texture files!", e);
			}
		}
	}
}
