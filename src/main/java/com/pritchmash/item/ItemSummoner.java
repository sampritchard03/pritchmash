package com.pritchmash.item;

import com.pritchmash.entity.darwinian.MobDarwinian;
import com.pritchmash.entity.pikmin.MobBluePikmin;
import com.pritchmash.entity.pikmin.MobPikmin;
import com.pritchmash.entity.pikmin.MobRedPikmin;
import com.pritchmash.entity.pikmin.MobYellowPikmin;
import com.pritchmash.entity.projectile.ProjectilePikminVessel;
import net.minecraft.client.render.model.ModelCow;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.Projectile;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public class ItemSummoner extends Item {
	public final Map<Integer, Pair<String, Consumer<MobDarwinian>>> index = new HashMap<>();
	public int currentIndex = 0;
	public final Random random;

	public ItemSummoner(String translationKey, String namespaceId, int id) {
		super(translationKey, namespaceId, id);
		this.random = new Random();
		index.put(0, Pair.of("random", (darwinian) -> {
			boolean isBipedal = this.random.nextInt(2) == 0;
			int bodyLength = this.random.nextInt(12)+14;
			int legLength = this.random.nextInt(16);
			int armLength = Math.max(0, legLength + this.random.nextInt(12)-6);
			int color = this.random.nextInt(16777215);

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
		index.put(1, Pair.of("gorilla", (darwinian) -> {
			boolean isBipedal = false;
			int bodyLength = 16;
			int legLength = 10;
			int armLength = 16;
			int color = 8411426;

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
		index.put(2, Pair.of("pig", (darwinian) -> {
			boolean isBipedal = false;
			int bodyLength = 16;
			int legLength = 6;
			int armLength = 6;
			int color = 16753116;

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
		index.put(3, Pair.of("cow", (darwinian) -> {
			boolean isBipedal = false;
			int bodyLength = 18;
			int legLength = 12;
			int armLength = 12;
			int color = 900000;

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
		index.put(4, Pair.of("steve", (darwinian) -> {
			boolean isBipedal = true;
			int bodyLength = 12;
			int legLength = 12;
			int armLength = 12;
			int color = this.random.nextInt(2) == 0 ? 16769185 : 5518336;

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
		index.put(5, Pair.of("sigma", (darwinian) -> {
			boolean isBipedal = false;
			int bodyLength = 12;
			int legLength = 0;
			int armLength = this.random.nextInt(12);
			int color = 16734810;

			darwinian.genetics.set(isBipedal, bodyLength, armLength, legLength, color);
		}));
	}

	@Override
	public ItemStack onUseItem(ItemStack itemstack, World world, Player player) {
		if (player.isSneaking()) {
			currentIndex = (currentIndex+1)%index.size();
			player.sendMessage("selected "+index.get(currentIndex).getLeft());
		} else {
			MobDarwinian mob = new MobDarwinian(world);
			mob.moveTo(player.x, player.y + (double)player.getHeadHeight(), player.z, player.yRot, player.xRot);
			world.entityJoinedWorld(mob);
			index.get(currentIndex).getRight().accept(mob);
		}

		return itemstack;
	}
}
