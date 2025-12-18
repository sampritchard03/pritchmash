package com.pritchmash;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import turniplabs.halplibe.util.ClientStartEntrypoint;

@Environment(EnvType.CLIENT)
public class PritchMashClient implements ClientModInitializer, ClientStartEntrypoint {
	@Override
	public void beforeClientStart() {
		//SoundRepository.registerNamespace(MOD_ID);
	}
	@Override
	public void afterClientStart() {

	}

	@Override
	public void onInitializeClient() {

	}
}
