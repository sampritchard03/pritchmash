package com.pritchmash.entity;

import com.mojang.nbt.tags.CompoundTag;
import com.pritchmash.entity.ai.tasks.Task;
import net.minecraft.core.entity.MobPathfinder;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class MobTaskdoer extends MobPathfinder {

	public MobTaskdoer(World world) {
		super(world);
	}

	public abstract Task getTask();

	public float getBlockPathWeight(int x, int y, int z) {
		return 0.0F;
	}

	public void updateAI() {
		Task task = getTask();
		if (task != null) task.tick();
	}
}
