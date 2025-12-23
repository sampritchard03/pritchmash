package com.pritchmash.item;

import net.minecraft.core.item.Item;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.ItemBuilder;

import static com.pritchmash.PritchMash.MOD_ID;

public class PritchMashItems {
	static int itemID = 18550;

	public static Item GARDENING_GLOVE;
	public static Item DEV_GLOVE;
	public static Item SUMMONER;

	public static String itemKey(String value) {
		return MOD_ID+":item/"+value;
	}

	public static void initializeItems() {
		// Items
		GARDENING_GLOVE = new ItemBuilder(MOD_ID)
			.build(new ItemGardeningGlove("gardeningglove", itemKey("gardeningglove"), itemID++))
			.setMaxStackSize(1);
		DEV_GLOVE = new ItemBuilder(MOD_ID)
			.build(new ItemDevGlove("devglove", itemKey("devglove"), itemID++))
			.setMaxStackSize(1);
		SUMMONER = new ItemBuilder(MOD_ID)
			.build(new ItemSummoner("summoner", itemKey("summoner"), itemID++))
			.setMaxStackSize(1);
	}
}
