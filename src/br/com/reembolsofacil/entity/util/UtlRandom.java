package br.com.reembolsofacil.entity.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class UtlRandom {

	private static SecureRandom random = new SecureRandom();

	public static String randomString() {
		return new BigInteger(30, random).toString(32);
	}

}
