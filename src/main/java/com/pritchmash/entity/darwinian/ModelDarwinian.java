package com.pritchmash.entity.darwinian;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.*;
import net.minecraft.core.util.collection.Pair;
import net.minecraft.core.util.helper.MathHelper;
import sun.reflect.generics.tree.Tree;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Environment(EnvType.CLIENT)
public class ModelDarwinian extends ModelBase {

	public Cube head;
	public Cube body;
	public Cube leg1;
	public Cube leg2;
	public Cube leg3;
	public Cube leg4;

	public Map<Integer, Integer> trackedSyncedData = new TreeMap<>();

	public ModelDarwinian() {
		trackedSyncedData.put(16, -1);
		trackedSyncedData.put(17, -1);
		trackedSyncedData.put(18, -1);
		trackedSyncedData.put(19, -1);
		trackedSyncedData.put(20, -1);
	}

	public void setupBiped(MobDarwinian darwinian) {
		int bodyLength = darwinian.genetics.bodyLength;
		int armLength = darwinian.genetics.armLength;
		int legLength = darwinian.genetics.legLength;
		float f = 0.0F;
		this.head = new Cube(0, 0);
		this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, f);
		this.head.setRotationPoint(0.0F,  (float)(24-bodyLength-legLength), 0.0F);
		this.body = new Cube(16, 16);
		this.body.addBox(-4.0F, 0.0F, -2.0F, 8, bodyLength, 4, f);
		this.body.setRotationPoint(0.0F,  (float)(24-bodyLength-legLength), 0.0F);


		this.leg1 = new Cube(40, 16);
		this.leg2 = new Cube(40, 16);
		this.leg2.mirror = true;
		if (armLength > 0) {
			this.leg1.addBox(-3.0F, -2.0F, -2.0F, 4, armLength, 4, f);
			this.leg2.addBox(-1.0F, -2.0F, -2.0F, 4, armLength, 4, f);
		} else {
			this.leg1.addBox(-3.0F, -2.0F, -2.0F, 0, 0, 0, f);
			this.leg2.addBox(-1.0F, -2.0F, -2.0F, 0, 0, 0, f);
		}
		this.leg1.setRotationPoint(-5.0F, (float)(26-bodyLength-legLength), 0.0F);
		this.leg2.setRotationPoint(5.0F, (float)(26-bodyLength-legLength), 0.0F);

		this.leg3 = new Cube(0, 16);
		this.leg4 = new Cube(0, 16);
		this.leg4.mirror = true;
		if (legLength > 0) {
			this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, legLength, 4, f);
			this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, legLength, 4, f);
		} else {
			this.leg3.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
			this.leg4.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
		}
		this.leg3.setRotationPoint(-2.0F, (float)(24-legLength), 0.0F);
		this.leg4.setRotationPoint(2.0F, (float)(24-legLength), 0.0F);
	}

	public void setupQuadruped(MobDarwinian darwinian) {
		int bodyLength = darwinian.genetics.bodyLength;
		int armLength = darwinian.genetics.armLength;
		int legLength = darwinian.genetics.legLength; // back legs
		float f = 0.0F;

		// === Geometry ===
		float groundY = 24.0F;

		// Z positions of leg attachment points
		float frontLegZ = -5.0F;
		float backLegZ  = bodyLength - 9.0F;

		float legSpan = backLegZ - frontLegZ;
		float heightDifference = legLength - armLength;

		// Body pitch angle so feet touch ground
		float bodyPitch = (float)Math.atan(heightDifference / legSpan);

		// === Head ===
		this.head = new Cube(0, 0);
		this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, f);
		this.head.setRotationPoint(0.0F, groundY - armLength - 6.0F, -6.0F);

		// === Body ===
		this.body = new Cube(28, 8);
		this.body.addBox(-5.0F, -legSpan+6.0F, -7.0F, 10, (int)Math.sqrt(bodyLength*bodyLength + heightDifference*heightDifference), 8, f);

		// Body Y is averaged between front and back leg heights
		float bodyY =
			groundY - ((armLength + legLength) * 0.5F);

		this.body.setRotationPoint(0.0F, bodyY, 2.0F);
		this.body.xRot = -(((float)Math.PI / 2.0F) - bodyPitch);

		// === Front Legs ===
		this.leg1 = new Cube(0, 16);
		this.leg2 = new Cube(0, 16);
		if (armLength > 0) {
			this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, armLength, 4, f);
			this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, armLength, 4, f);
		} else {
			this.leg1.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
			this.leg2.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
		}
		this.leg1.setRotationPoint(-3.0F, groundY - armLength, frontLegZ);
		this.leg2.setRotationPoint(3.0F, groundY - armLength, frontLegZ);

		// === Back Legs ===

		this.leg3 = new Cube(0, 16);
		this.leg4 = new Cube(0, 16);
		if (legLength > 0) {
			this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, legLength, 4, f);
			this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, legLength, 4, f);
		} else {
			this.leg3.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
			this.leg4.addBox(-2.0F, 0.0F, -2.0F, 0, 0, 0, f);
		}
		this.leg3.setRotationPoint(-3.0F, groundY - legLength, backLegZ);
		this.leg4.setRotationPoint(3.0F, groundY - legLength, backLegZ);
	}

	public void update(MobDarwinian darwinian) {
		boolean shouldSetup = false;
		for (Map.Entry<Integer, Integer> entry : trackedSyncedData.entrySet()) {
			int id = entry.getKey();
			int value = entry.getValue();
			int comparison = darwinian.getEntityData().getInt(id);
			if (comparison != value) {
				shouldSetup = true;
				trackedSyncedData.put(id, comparison);
			}
		}
		if (shouldSetup) {
			if (darwinian.genetics.isBipedal) this.setupBiped(darwinian);
			else this.setupQuadruped(darwinian);
		}
	}

	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		this.head.render(scale);
		this.body.render(scale);
		this.leg1.render(scale);
		this.leg2.render(scale);
		this.leg3.render(scale);
		this.leg4.render(scale);
	}

	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.head.xRot = headPitch / 57.29578F;
		this.head.yRot = headYaw / 57.29578F;
		this.leg1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		this.leg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbYaw;
		this.leg3.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbYaw;
		this.leg4.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
	}
}
