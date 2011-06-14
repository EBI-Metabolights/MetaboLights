package uk.ac.ebi.bioinvindex.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;

/**
 * @author: Nataliya Sklyar (nsklyar@ebi.ac.uk) Date: Nov 19, 2009
 */
public final class StringEncryption {

	private static StringEncryption instance;

	private StringEncryption() {
	}

	public static synchronized StringEncryption getInstance() {
		if (instance == null) {
			return new StringEncryption();
		} else {
			return instance;
		}
	}

	public synchronized String encrypt(String plaintext) /*throws SystemUnavailableException*/ {

		try {
			MessageDigest md = MessageDigest.getInstance("SHA");

			md.update(plaintext.getBytes("UTF-8"));


			byte raw[] = md.digest();
			String hash = (new BASE64Encoder()).encode(raw);

			return hash; //step 6
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return plaintext;
	}

}
