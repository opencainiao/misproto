package com.misproto.common.util;

import java.util.Random;

import org.mou.common.DateUtil;
import org.mou.common.security.EncryptMou;

public class RandomUtil {

	// 0-9 a-z A-Z
	public static String getRandomStr(int len) {
		Random random = new Random();
		/*
		 * 48-57 65-90 97-122
		 */
		byte[] bs = new byte[len];
		for (int i = 0; i < len; i++) {
			int num = random.nextInt(75) + 48;// 48-122
			if (num > 57 && num < 65) {
				num = num + 7;
			} else if (num > 90 && num < 97) {
				num = num + 6;
			}
			bs[i] = (byte) num;
		}
		return new String(bs);
	}

	public static String getRandomEncryptStr() {
		return EncryptMou.encrypt(RandomUtil.getRandomStr(10)
				+ DateUtil.getCurrentTimsmp());
	}

}
