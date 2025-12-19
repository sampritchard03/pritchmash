package com.pritchmash.entity.ai;

import net.minecraft.core.world.pathfinder.Path;

public interface IMobPathfinder {
	void _setShouldWander(boolean shouldWander);
	void _setPath(Path path);
	Path _getPath();
}
