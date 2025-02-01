package snippets.crypto;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RsaMain {

  public static KeyPair generateKeyPair() {
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      System.err.println(e.getMessage());
      return null;
    }
    keyPairGenerator.initialize(2048);
    return keyPairGenerator.generateKeyPair();
  }

  public static byte[] encrypt(String message, KeyPair keyPair) {
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      System.err.println(e.getMessage());
      return null;
    } catch (NoSuchPaddingException e) {
      System.err.println(e.getMessage());
      return null;
    }
    try {
      cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
    } catch (InvalidKeyException e) {
      System.err.println(e.getMessage());
      return null;
    }
    try {
      return cipher.doFinal(message.getBytes());
    } catch (IllegalBlockSizeException e) {
      System.err.println(e.getMessage());
      return null;
    } catch (BadPaddingException e) {
      System.err.println(e.getMessage());
      return null;
    }
  }
  
  
  public String decrypt(byte[] ciperText, PrivateKey privateKey) {
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance("RSA");
    } catch (NoSuchAlgorithmException e) {
      System.err.println(e.getMessage());
      return null;
    } catch (NoSuchPaddingException e) {
      System.err.println(e.getMessage());
      return null;
    }
    try {
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
    } catch (InvalidKeyException e) {
      System.err.println(e.getMessage());
      return null;
    }
    try {
      return new String(cipher.doFinal(ciperText));
    } catch (IllegalBlockSizeException e) {
      System.err.println(e.getMessage());
      return null;
    } catch (BadPaddingException e) {
      System.err.println(e.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
    KeyPair keyPair = generateKeyPair();
    String message = "Hello, world!";
    byte[] encrypted = encrypt(message, keyPair);
    System.out.println(new String(encrypted));
  }

}
