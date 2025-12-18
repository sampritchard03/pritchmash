package com.pritchmash.entity;

public interface IMob {
	boolean setJumping(boolean jumping);
	boolean isJumping();
	float setMoveForward(float moveForward);
	float getMoveForward();
	float setMoveSpeed(float moveSpeed);
	float getMoveSpeed();
	float setMoveStrafing(float moveStrafing);
	float getMoveStrafing();
}
