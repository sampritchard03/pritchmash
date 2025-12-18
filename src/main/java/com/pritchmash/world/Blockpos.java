package com.pritchmash.world;

public class Blockpos extends XZ {

	public int y;

	public Blockpos(int x, int y, int z) {
		super(x, z);
		this.y = y;
	}

	public String toString() {
		return this.x+", "+this.y+", "+this.z;
	}
}
