package com.pritchmash;

import com.pritchmash.entity.pikmin.MobPikmin;
import net.minecraft.core.util.collection.NamespaceID;
import turniplabs.halplibe.helper.EntityHelper;

import static com.pritchmash.PritchMash.MOD_ID;

public class PritchMashEntities {
	public static boolean hasInit = false;

	public static void init() {
		if (!hasInit) {
			hasInit = true;
			initializeEntities();
		}

	}

	public static String entityKey(String string) {
		return MOD_ID + ".entity." + string;
	}

	public static void initializeEntities() {
		EntityHelper.createEntity(MobPikmin.class, NamespaceID.getPermanent(MOD_ID, "pikmin"), entityKey("pikmin"));

	}
}
