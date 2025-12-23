package com.pritchmash.entity.darwinian;

import com.pritchmash.PritchMash;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.LightmapHelper;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.entity.MobRendererPig;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.model.ModelPig;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.Global;
import net.minecraft.core.entity.monster.MobGhast;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.models.entity.StaticEntityModel;

import turniplabs.halplibe.helper.ModelHelper;

public class MobRendererDarwinian extends MobRenderer<MobDarwinian> {

	public MobRendererDarwinian() {
		super(new ModelDarwinian(), 0.5F);
	}

	@Override
	protected void setupScale(MobDarwinian darwinian, float partialTick) {
		int age = Math.min(darwinian.time, 1200);
		float scale = age/1500.0F+0.2F;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	public void render(Tessellator tessellator, MobDarwinian entity, double x, double y, double z, float yaw, float partialTick) {
		((ModelDarwinian)this.mainModel).update(entity);
		this.shadowSize = entity.width();


		GL11.glPushMatrix();
		GL11.glDisable(2884);
		this.mainModel.onGround = this.getSwingProgress(entity, partialTick);
		if (this.armorModel != null) {
			this.armorModel.onGround = this.mainModel.onGround;
		}

		if (this.overlayModel != null) {
			this.overlayModel.onGround = this.mainModel.onGround;
		}

		this.mainModel.isRiding = entity.isPassenger();
		if (this.armorModel != null) {
			this.armorModel.isRiding = this.mainModel.isRiding;
		}

		if (this.overlayModel != null) {
			this.overlayModel.isRiding = this.mainModel.isRiding;
		}

		try {
			float bodyYaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTick;
			float headYaw = entity.yRotO + (entity.yRot - entity.yRotO) * partialTick;
			float headPitch = entity.xRotO + (entity.xRot - entity.xRotO) * partialTick;
			this.translateModel(entity, x, y, z);
			float limbSway = this.limbSway(entity, partialTick);
			this.setupRotations(entity, limbSway, bodyYaw, partialTick);
			float scale = 0.0625F;
			GL11.glEnable(32826);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.setupScale(entity, partialTick);
			GL11.glTranslatef(0.0F, -24.0F * scale - 0.0078125F, 0.0F);
			float walkSpeed = entity.walkAnimSpeedO + (entity.walkAnimSpeed - entity.walkAnimSpeedO) * partialTick;
			float walkProgress = entity.walkAnimPos - entity.walkAnimSpeed * (1.0F - partialTick);
			if (walkSpeed > 1.0F) {
				walkSpeed = 1.0F;
			}

			this.loadEntityTexture(entity);
			GL11.glEnable(3008);
			this.mainModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
			float r1 = (float)(entity.genetics.color >> 16 & 255) / 255.0F;
			float g1 = (float)(entity.genetics.color >> 8 & 255) / 255.0F;
			float b1 = (float)(entity.genetics.color & 255) / 255.0F;
			GL11.glColor4f(r1, g1, b1, 1.0F);
			this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
			if (this.overlayModel != null) {
				this.overlayModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
				this.bindTexture(this.overlayTexture);
				this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
			}

			if (this.armorModel != null) {
				this.armorModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
			}

			for(int renderPass = 0; renderPass < 4; ++renderPass) {
				if (this.prepareArmor(entity, renderPass, partialTick)) {
					this.armorModel.setLivingAnimations(entity, walkProgress, walkSpeed, partialTick);
					this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					GL11.glDisable(3042);
					GL11.glEnable(3008);
				}
			}

			this.renderAdditional(entity, partialTick);
			float brightness = entity.getBrightness(partialTick);
			if (Global.accessor.isFullbrightEnabled() || LightmapHelper.isLightmapEnabled()) {
				brightness = 1.0F;
			}

			int argb = this.getOverlayColor(entity, brightness, partialTick);
			if ((argb >> 24 & 255) > 0 || entity.hurtTime > 0 || entity.deathTime > 0) {
				GL11.glDisable(3553);
				GL11.glDisable(3008);
				GL11.glEnable(3042);
				GL11.glBlendFunc(770, 771);
				GL11.glDepthFunc(514);
				if (entity.hurtTime > 0 || entity.deathTime > 0) {
					GL11.glColor4f(0.0F, 0.0F, brightness, 0.4F);
					this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					if (this.overlayModel != null) {
						this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					}

					for(int k = 0; k < 4; ++k) {
						if (this.prepareArmor(entity, k, partialTick)) {
							GL11.glColor4f(brightness, 0.0F, 0.0F, 0.4F);
							this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
						}
					}
				}

				if ((argb >> 24 & 255) > 0) {
					float r = (float)(argb >> 16 & 255) / 255.0F;
					float g = (float)(argb >> 8 & 255) / 255.0F;
					float b = (float)(argb & 255) / 255.0F;
					float a = (float)(argb >> 24 & 255) / 255.0F;
					GL11.glColor4f(r, g, b, a);
					this.mainModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					if (this.overlayModel != null) {
						this.overlayModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
					}

					for(int l = 0; l < 4; ++l) {
						if (this.prepareArmor(entity, l, partialTick)) {
							GL11.glColor4f(r, g, b, a);
							this.armorModel.render(walkProgress, walkSpeed, limbSway, headYaw - bodyYaw, headPitch, scale);
						}
					}
				}

				GL11.glDepthFunc(515);
				GL11.glDisable(3042);
				GL11.glEnable(3008);
				GL11.glEnable(3553);
			}

			GL11.glDisable(32826);
		} catch (Exception exception) {
			PritchMash.LOGGER.error("Render exception in class '{}'!", this.getClass().getSimpleName(), exception);
		}

		GL11.glEnable(2884);
		GL11.glPopMatrix();
		this.renderSpecials(tessellator, entity, x, y, z);
	}
}
