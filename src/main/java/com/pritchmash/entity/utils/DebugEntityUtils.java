package com.pritchmash.entity.utils;

import com.pritchmash.entity.darwinian.MobDarwinian;

public class DebugEntityUtils {

	public static void test1(MobDarwinian darwinian) {
		int armLength = darwinian.genetics.armLength+1;
		if (armLength > 50) armLength = 4;
		darwinian.genetics.setArmLength(armLength);
	}

	public static void test2(MobDarwinian darwinian) {
		int bodyLength = darwinian.genetics.bodyLength+1;
		if (bodyLength > 50) bodyLength = 4;
		darwinian.genetics.setBodyLength(bodyLength);
	}
}
