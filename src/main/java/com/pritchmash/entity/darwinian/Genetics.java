package com.pritchmash.entity.darwinian;

import net.minecraft.core.world.data.SynchedEntityData;

public class Genetics {

	public boolean needsUpdate = true;

	public boolean isBipedal;
	public int bodyLength;
	public int armLength;
	public int legLength;
	public int color = 9736743;

	public final MobDarwinian mob;
	public final SynchedEntityData data;

	public Genetics(MobDarwinian mob) {
		this.mob = mob;
		this.data = mob.getEntityData();

		this.data.define(16, 0, Integer.class); // Body Type
		this.data.define(17, 0, Integer.class); // Body Length
		this.data.define(18, 0, Integer.class); // Arm Length
		this.data.define(19, 0, Integer.class); // Leg Length
		this.data.define(20, 0, Integer.class); // Color
	}

	public void onUpdate() {
		this.mob.setSize(this.mob.width(), this.mob.height());
	}

	public void tick() {
		if (this.needsUpdate) {
			this.needsUpdate = false;
			this.onUpdate();
		}
	}

	public void set(boolean isBipedal, int bodyLength, int armLength, int legLength, int color) {
		this.setIsBipedal(isBipedal);
		this.setBodyLength(bodyLength);
		this.setArmLength(armLength);
		this.setLegLength(legLength);
		this.setColor(color);
	}

	public void setIsBipedal(boolean isBipedal) {
		this.data.set(16, isBipedal ? 0 : 1);
		this.isBipedal = isBipedal;
		this.needsUpdate = true;
	}

	public void setBodyLength(int bodyLength) {
		this.data.set(17, bodyLength);
		this.bodyLength = bodyLength;
		this.needsUpdate = true;
	}

	public void setArmLength(int armLength) {
		this.data.set(18, armLength);
		this.armLength = armLength;
		this.needsUpdate = true;
	}

	public void setLegLength(int legLength) {
		this.data.set(19, legLength);
		this.legLength = legLength;
		this.needsUpdate = true;
	}

	public void setColor(int color) {
		this.data.set(20, color);
		this.color = color;
		this.needsUpdate = true;
	}

}
