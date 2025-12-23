package com.pritchmash.entity;

import com.pritchmash.entity.darwinian.MobDarwinian;
import com.pritchmash.entity.pikmin.MobBluePikmin;
import com.pritchmash.entity.pikmin.MobPikmin;
import com.pritchmash.entity.pikmin.MobRedPikmin;
import com.pritchmash.entity.pikmin.MobYellowPikmin;
import com.pritchmash.entity.projectile.ProjectilePikminVessel;
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
		EntityHelper.createEntity(MobDarwinian.class, NamespaceID.getPermanent(MOD_ID, "darwinian"), entityKey("darwinian"));

		EntityHelper.createEntity(MobRedPikmin.class, NamespaceID.getPermanent(MOD_ID, "redpikmin"), entityKey("redpikmin"));
		EntityHelper.createEntity(MobBluePikmin.class, NamespaceID.getPermanent(MOD_ID, "bluepikmin"), entityKey("bluepikmin"));
		EntityHelper.createEntity(MobYellowPikmin.class, NamespaceID.getPermanent(MOD_ID, "yellowpikmin"), entityKey("yellowpikmin"));

		EntityHelper.createEntity(ProjectilePikminVessel.class, NamespaceID.getPermanent(MOD_ID, "projectilepikminvessel"), null);
	}
}
