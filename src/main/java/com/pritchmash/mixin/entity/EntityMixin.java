package com.pritchmash.mixin.entity;

import com.pritchmash.entity.interfaces.IEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntity {

	@Unique
	private boolean shouldPathOnSeafloor = false;

	@Override
	public void _setShouldPathOnSeafloor(boolean shouldPathOnSeafloor) {this.shouldPathOnSeafloor = shouldPathOnSeafloor;}

	@Override
	public boolean _getShouldPathOnSeafloor() {return this.shouldPathOnSeafloor;}
}
