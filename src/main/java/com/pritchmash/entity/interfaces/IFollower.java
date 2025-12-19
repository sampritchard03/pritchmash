package com.pritchmash.entity.interfaces;

import net.minecraft.core.entity.Entity;
import org.jetbrains.annotations.Nullable;

public interface IFollower {
	@Nullable Entity followTarget();
}
