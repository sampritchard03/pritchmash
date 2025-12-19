package com.pritchmash.mixin.entity;

import com.pritchmash.entity.ai.IEntity;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicDoor;
import net.minecraft.core.block.BlockLogicTrapDoor;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.MobCreeper;
import net.minecraft.core.world.WorldSource;
import net.minecraft.core.world.pathfinder.Node;
import net.minecraft.core.world.pathfinder.PathFinder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PathFinder.class)
public abstract class PathFinderMixin {

	@Shadow
	@Final
	private WorldSource worldSource;

	@Inject(method = "isFree", at = @At("HEAD"), cancellable = true)
	private void isFree(Entity entity, int x, int y, int z, Node pathpoint, CallbackInfoReturnable<Integer> cir) {
		for(int x1 = x; x1 < x + pathpoint.x; ++x1) {
			for(int y1 = y; y1 < y + pathpoint.y; ++y1) {
				for(int z1 = z; z1 < z + pathpoint.z; ++z1) {
					int blockId = this.worldSource.getBlockId(x1, y1, z1);
					if (blockId > 0) {
						if (Block.hasLogicClass(Blocks.blocksList[blockId], BlockLogicDoor.class)) {
							int blockMetadata = this.worldSource.getBlockMetadata(x1, y1, z1);
							if (!BlockLogicDoor.isOpen(blockMetadata)) {
								cir.setReturnValue(0);
								cir.cancel();
								return;
							}
						} else {
							if (entity instanceof MobCreeper) {
								int blockMetadata = this.worldSource.getBlockMetadata(x1, y1, z1);
								if (Block.hasLogicClass(Blocks.blocksList[blockId], BlockLogicTrapDoor.class)) {
									boolean isTopClosedTrapdoor = !BlockLogicTrapDoor.isTrapdoorOpen(blockMetadata) && BlockLogicTrapDoor.isUpperHalf(blockMetadata);
									if (isTopClosedTrapdoor) {
										cir.setReturnValue(1);
										cir.cancel();
										return;
									}
								}
							}

							Material material = Blocks.blocksList[blockId].getMaterial();
							if (material.blocksMotion()) {
								cir.setReturnValue(0);
								cir.cancel();
								return;
							}

							if (material == Material.water) {
								if (((IEntity)entity)._getShouldSwim()) cir.setReturnValue(-1);
								else cir.setReturnValue(1);
								cir.cancel();
								return;
							}

							if (material == Material.lava) {
								cir.setReturnValue(-2);
								cir.cancel();
								return;
							}
						}
					}
				}
			}
		}

		cir.setReturnValue(1);
		cir.cancel();
	}
}
