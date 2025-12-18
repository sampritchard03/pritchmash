package com.pritchmash.entity.pikmin;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;
import org.useless.dragonfly.models.entity.BoneTransform;
import org.useless.dragonfly.models.entity.StaticEntityModel;
import org.useless.dragonfly.renderer.MobRenderer;

public class MobRendererPikmin extends MobRenderer<MobPikmin> {
	public MobRendererPikmin() {
		super(0.2F);
	}
	@Override
	protected @Nullable StaticEntityModel getAndSetupModelForLayer(@NonNull MobPikmin entity, float brightness, float partialTick, int layer) {
		StaticEntityModel model = this.getModel("main");
		this.setRotationAngles(model, entity, partialTick);
		return model;
	}

	public void setRotationAngles(StaticEntityModel model, MobPikmin entity, float partialTick) {
		BoneTransform head = model.getTransform("head");

		float bodyYaw = this.getBodyYaw(entity, partialTick);
		float headYaw = this.getHeadYaw(entity, partialTick) - bodyYaw;
		float headPitch = this.getHeadPitch(entity, partialTick);

		head.rotY = headYaw;
		head.rotX = headPitch;

		BoneTransform leg_left = model.getTransform("leg_left");
		BoneTransform leg_right = model.getTransform("leg_right");
		BoneTransform arm_left = model.getTransform("arm_left");
		BoneTransform arm_right = model.getTransform("arm_right");

		float limbSwing = this.getLimbSwing(entity, partialTick);
		float limbYaw = this.getLimbYaw(entity, partialTick);

		leg_left.rotX = (float)Math.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		leg_right.rotX = (float)Math.cos(limbSwing * 0.6662F + Math.PI) * 1.4F * limbYaw;
		arm_left.rotX = leg_right.rotX;
		arm_right.rotX = leg_left.rotX;

	}
}
