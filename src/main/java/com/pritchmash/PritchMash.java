package com.pritchmash;

import com.pritchmash.entity.PritchMashEntities;
import com.pritchmash.item.PritchMashItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class PritchMash implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "pritchmash";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		PritchMashItems.initializeItems();
		LOGGER.info("PritchMash initialized.");
	}

	@Override
	public void onRecipesReady() {}

	@Override
	public void initNamespaces() {}

	@Override
	public void beforeGameStart() {

		PritchMashEntities.init();
	}

	@Override
	public void afterGameStart() {}
}
