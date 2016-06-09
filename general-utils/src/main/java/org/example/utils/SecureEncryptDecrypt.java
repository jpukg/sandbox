package org.example.utils;


import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class SecureEncryptDecrypt {

  private static final String SECRET = "secret1";

  public static void main(String[] args) throws UnsupportedEncodingException {
    
    byte[] encodeBase64 = Base64.encodeBase64(SECRET.getBytes());
    decrypt(encrypt(), encodeBase64);
  }

  private static String encrypt() {
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
    encryptor.setPassword(SECRET);
    String encryptedText = encryptor.encrypt("Hello World");
    System.out.println("Encrypted text is: " + encryptedText);
    return encryptedText;
  }

  private static void decrypt(String encryptedText, byte[] encodedBase64Password) throws UnsupportedEncodingException {
    StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

    // Receiving side
    byte[] data = Base64.decodeBase64(encodedBase64Password);
    String mySecretPassword = new String(data, "UTF-8");

    decryptor.setPassword(mySecretPassword);
    String decryptedText = decryptor.decrypt(encryptedText);
    System.out.println("Decrypted text is: " + decryptedText);
  }
}
