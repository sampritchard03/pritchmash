package com.pritchmash.mixin.entity;

import com.pritchmash.entity.ai.IEntity;
import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Mob.class)
public abstract class EntityMixin implements IEntity {

	@Unique
	private boolean shouldSwim = true;

	@Override
	public void _setShouldSwim(boolean shouldSwim) {this.shouldSwim = shouldSwim;}

	@Override
	public boolean _getShouldSwim() {return this.shouldSwim;}

}
