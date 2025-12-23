package com.pritchmash.item;

import com.pritchmash.entity.darwinian.MobDarwinian;
import com.pritchmash.entity.pikmin.MobBluePikmin;
import com.pritchmash.entity.pikmin.MobPikmin;
import com.pritchmash.entity.pikmin.MobRedPikmin;
import com.pritchmash.entity.pikmin.MobYellowPikmin;
import com.pritchmash.entity.projectile.ProjectilePikminVessel;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class ItemDevGlove extends Item {
	public final Map<Integer, Pair<String, Function<World, Mob>>> index = new HashMap<>();
	public int currentIndex = 0;

	public ItemDevGlove(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
		index.put(0, Pair.of("red pikmin", MobRedPikmin::new));
		index.put(1, Pair.of("yellow pikmin", MobYellowPikmin::new));
		index.put(2, Pair.of("blue pikmin", MobBluePikmin::new));
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		if (player.isSneaking()) {
			currentIndex = (currentIndex+1)%index.size();
			player.sendMessage("selected "+index.get(currentIndex).getLeft());
		} else {
			Mob mob = index.get(currentIndex).getRight().apply(world);
			mob.spawnInit();
			mob.moveTo(player.x, player.y, player.z, player.yRot, player.xRot);
			world.entityJoinedWorld(mob);
		}

		return itemstack;
	}
}
