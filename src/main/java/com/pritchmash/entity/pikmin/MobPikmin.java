package com.pritchmash.entity.pikmin;

import com.pritchmash.entity.MobTaskdoer;
import com.pritchmash.entity.ai.tasks.PikminTask;
import com.pritchmash.entity.interfaces.IFollower;
import net.minecraft.core.block.material.MaterialColor;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public class MobPikmin extends MobTaskdoer implements IFollower {
	public State state;
	public String leaderName = "";

	public MobPikmin(World world) {
		super(world);
		this.task = new PikminTask(this);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "pikmin");
		setSize(0.5F, 1F);
		state = State.FOLLOWING;
		this.setShouldPathOnSeafloor(true);
	}

	@Override
	public boolean interact(Player player) {
		this.leaderName = player.username;
		return super.interact(player);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return this.world.getBlockMaterial(x, y, z).color == MaterialColor.water ? -10.0F : 0.0F;
	}

	@Override
	public @Nullable Entity followTarget() {
		return this.world.getPlayerEntityByName(this.leaderName);
	}

	public enum State {
		IDLE,
		FOLLOWING
	}
}
