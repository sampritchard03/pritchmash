package com.pritchmash;

import com.pritchmash.entity.darwinian.MobDarwinian;
import com.pritchmash.entity.darwinian.MobRendererDarwinian;
import com.pritchmash.entity.pikmin.*;
import com.pritchmash.entity.pikmin.MobRendererPikmin;
import com.pritchmash.entity.projectile.ProjectilePikminVessel;
import com.pritchmash.entity.projectile.ProjectileRendererPikminVessel;
import com.pritchmash.item.PritchMashItems;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.TileEntityRenderDispatcher;
import net.minecraft.client.render.block.color.BlockColorDispatcher;
import net.minecraft.client.render.block.model.BlockModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.item.model.ItemModelStandard;
import turniplabs.halplibe.helper.ModelHelper;
import turniplabs.halplibe.util.ModelEntrypoint;

public class PritchMashModels implements ModelEntrypoint {
	@Override
	public void initBlockModels(BlockModelDispatcher dispatcher) {

	}

	@Override
	public void initItemModels(ItemModelDispatcher dispatcher) {
		dispatcher.addDispatch(new ItemModelStandard(PritchMashItems.GARDENING_GLOVE, null).setIcon("pritchmash:item/gardeningglove"));
		dispatcher.addDispatch(new ItemModelStandard(PritchMashItems.DEV_GLOVE, null).setIcon("pritchmash:item/devglove"));
		dispatcher.addDispatch(new ItemModelStandard(PritchMashItems.SUMMONER, null).setIcon("pritchmash:item/summoner"));
	}

	@Override
	public void initEntityModels(EntityRenderDispatcher dispatcher) {
		ModelHelper.setEntityModel(MobDarwinian.class, MobRendererDarwinian::new);

		ModelHelper.setEntityModel(MobRedPikmin.class, MobRendererPikmin::new);
		ModelHelper.setEntityModel(MobBluePikmin.class, MobRendererPikmin::new);
		ModelHelper.setEntityModel(MobYellowPikmin.class, MobRendererPikmin::new);

		ModelHelper.setEntityModel(ProjectilePikminVessel.class, ProjectileRendererPikminVessel::new);
	}

	@Override
	public void initTileEntityModels(TileEntityRenderDispatcher dispatcher) {

	}

	@Override
	public void initBlockColors(BlockColorDispatcher dispatcher) {

	}
}
