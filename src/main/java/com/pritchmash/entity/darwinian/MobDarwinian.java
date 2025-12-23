package com.pritchmash.entity.darwinian;

import com.mojang.nbt.tags.CompoundTag;
import com.pritchmash.entity.MobTaskrunner;
import com.pritchmash.entity.ai.tasks.Task;
import com.pritchmash.entity.ai.tasks.compound.IdleTask;
import com.pritchmash.entity.utils.DebugEntityUtils;
import net.minecraft.core.Global;
import net.minecraft.core.entity.SkinVariantList;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;

public class MobDarwinian extends MobTaskrunner {

	public boolean modelNeedsUpdate = false;
	public final Genetics genetics;

	public MobDarwinian(World world) {
		super(world);
		this.genetics = new Genetics(this);
		textureIdentifier = NamespaceID.getPermanent("pritchmash", "darwinian");
	}

	public void setSize(float width, float height) {
		super.setSize(width, height);
	}

	@Override
	public void livingTick() {
		this.genetics.tick();
		//DebugEntityUtils.test2(this);
	}

	@Override
	public String getTextureReference() {
		return (this.genetics.isBipedal ? "bi" : "quad") + "_0";
	}

	public float width() {
		if (genetics.isBipedal) {
			return 0.6F;
		} else {
			double a = this.genetics.bodyLength / 12.0;
			double b = (this.genetics.legLength - this.genetics.armLength) / 12.0;;
			return (float)Math.sqrt(a*a + b*b);
		}
	}

	public float height() {
		if (genetics.isBipedal) {
			return genetics.legLength/12.0F+genetics.bodyLength/12.0F+1.0F;
		} else {
			return Math.max(genetics.legLength/12.0F, genetics.armLength/12.0F)+0.5F;
		}
	}

	@Override
	public Task<? extends MobTaskrunner> createTask() {
		return new IdleTask<>(this);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("isBipedal", genetics.isBipedal);
		tag.putInt("bodyLength", genetics.bodyLength);
		tag.putInt("armLength", genetics.armLength);
		tag.putInt("legLength", genetics.legLength);
		tag.putInt("color", genetics.color);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.genetics.setIsBipedal(tag.getBoolean("isBipedal"));
		this.genetics.setBodyLength(tag.getInteger("bodyLength"));
		this.genetics.setArmLength(tag.getInteger("armLength"));
		this.genetics.setLegLength(tag.getInteger("legLength"));
		this.genetics.setColor(tag.getInteger("color"));
	}
}
