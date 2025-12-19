package com.pritchmash.entity.pikmin;

import com.pritchmash.entity.MobTaskdoer;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.ai.tasks.WanderTask;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;

public class MobPikmin extends MobTaskdoer {
	public State state;

	public MobPikmin(World world) {
		super(world);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "pikmin");
		setSize(0.5F, 1F);
		state = State.FOLLOWING;
		this.setShouldSwim(false);
	}

	@Override
	public Task makeTask() {
		return new WanderTask(this);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return this.world.getBlockId(x, y, z) == 271 ? -10.0F : 0.0F;
	}

	public enum State {
		IDLE,
		FOLLOWING
	}
}
