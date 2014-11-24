package br.com.reembolsofacil.entity.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class UtlCrypto {

	@Deprecated
	public static String encripta(String senha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(senha.getBytes("UTF-8"));
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(digest.digest());
		} catch (Exception ns) {
			return senha;
		}
	}

	public static String criptografaSenha(String senha) {
		String s=null;
		try{
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes("UTF-8")));
		s = hash.toString(16);
		if (s.length() % 2 != 0)
			s = "0" + s;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		return s;
	}
}
