package com.pritchmash.item;

import com.pritchmash.entity.pikmin.MobPikmin;
import com.pritchmash.entity.projectile.ProjectilePikminVessel;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemBow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.List;

public class ItemGardeningGlove extends Item {
	public ItemGardeningGlove(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		List<MobPikmin> pikmins = world.getEntitiesWithinAABB(MobPikmin.class, player.bb.grow(4, 4, 4));

		if (!pikmins.isEmpty()) {
			MobPikmin closestPikmin = null;
			float closestDist = Float.POSITIVE_INFINITY;
			for (MobPikmin pikmin : pikmins) {
				if (pikmin.leaderName.equals(player.username) && pikmin.vehicle == null) {
					float dist = player.distanceTo(pikmin);
					if (dist < closestDist) {
						closestDist = dist;
						closestPikmin = pikmin;
					}
				}
			}
			if (closestPikmin != null) {
				Projectile projectile = new ProjectilePikminVessel(world, player);
				world.entityJoinedWorld(projectile);
				closestPikmin.startRiding(projectile);
			}
		}

		return itemstack;
	}
}
