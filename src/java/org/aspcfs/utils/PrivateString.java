/*
 *  Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Encrypts and decrypts data using a key
 *
 * @author matt rajkowski
 * @version $Id$
 * @created August 22, 2002
 */
public class PrivateString {

  private Key key = null;


  /**
   * Constructor for the PrivateString object
   */
  public PrivateString() {
  }


  /**
   * Constructor for the PrivateString object
   *
   * @param file Description of the Parameter
   */
  public PrivateString(String file) {
    key = PrivateString.generateEncodedKeyFile(file);
  }


  /**
   * Sets the key attribute of the PrivateString object
   *
   * @param tmp The new key value
   */
  public void setKey(Key tmp) {
    this.key = tmp;
  }

  public void setKeyFromHexEncoding(String hex) throws Exception {
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    DESKeySpec keySpec = new DESKeySpec(Hex.decodeHex(hex.toCharArray()));
    key = keyFactory.generateSecret(keySpec);
  }


  /**
   * Gets the key attribute of the PrivateString object
   *
   * @return The key value
   */
  public Key getKey() {
    return key;
  }

  public String getKeyAsHexEncoding() {
    return new String(Hex.encodeHex(key.getEncoded()));
  }


  /**
   * Description of the Method
   *
   * @param filename Description of the Parameter
   * @return Description of the Return Value
   */
  public static synchronized Key generateEncodedKeyFile(String filename) {
    try {
      File file = new File(filename);
      if (!file.exists()) {
        // Generate key
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(56, new SecureRandom());
        Key key = generator.generateKey();
        // Save key
        String hex = new String(Hex.encodeHex(key.getEncoded()));
        StringUtils.saveText(filename, hex);
      }
      return PrivateString.loadEncodedKey(filename);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
    return null;
  }

  public static Key loadEncodedKey(String keyFilename) throws IOException {
    File file = new File(keyFilename);
    return loadEncodedKey(file);
  }

  public static Key loadEncodedKey(File keyFilename) throws IOException {
    String hex = StringUtils.loadText(keyFilename);
    try {
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      DESKeySpec keySpec = new DESKeySpec(Hex.decodeHex(hex.toCharArray()));
      return keyFactory.generateSecret(keySpec);
    } catch (Exception e) {
      throw new IOException(e.getMessage());
    }
  }

  public static Key loadSerializedKey(String keyFilename) throws IOException {
    File file = new File(keyFilename);
    return loadSerializedKey(file);
  }

  /**
   * Description of the Method
   *
   * @param keyFile Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException
   */
  public static Key loadSerializedKey(File keyFile) throws IOException {
    try {
      ObjectInputStream in = new ObjectInputStream(
          new FileInputStream(keyFile));
      Key key = (Key) in.readObject();
      in.close();
      return key;
    } catch (Exception e) {
      System.out.println(
          "PrivateString-> Error loading key at: " + keyFile);
      throw new IOException(e.getMessage());
    }
  }


  /**
   * Description of the Method
   *
   * @param key      Description of the Parameter
   * @param inString Description of the Parameter
   * @return Description of the Return Value
   */
  public static String encrypt(Key key, String inString) {
    try {
      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] inputBytes = inString.getBytes("UTF8");
      byte[] outputBytes = cipher.doFinal(inputBytes);
      return new String(Base64.encodeBase64(outputBytes, true));
    } catch (Exception e) {
      return null;
    }
  }


  /**
   * Description of the Method
   *
   * @param key      Description of the Parameter
   * @param inString Description of the Parameter
   * @return Description of the Return Value
   */
  public static String decrypt(Key key, String inString) {
    try {
      //Security.addProvider(new com.sun.crypto.provider.SunJCE());

      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key);

      byte[] inputBytes = Base64.decodeBase64(inString.getBytes("UTF8"));
      byte[] outputBytes = cipher.doFinal(inputBytes);

      return new String(outputBytes, "UTF8");
    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }


  /**
   * Description of the Method
   *
   * @param keyFilename Description of the Parameter
   * @param inString    Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException
   */
  public static String encryptAsymmetric(String keyFilename, String inString) throws IOException {
    Key key = PrivateString.loadSerializedKey(keyFilename);
    return PrivateString.encryptAsymmetric(key, inString);
  }


  /**
   * Description of the Method
   *
   * @param keyFile  Description of the Parameter
   * @param inString Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException
   */
  public static String encryptAsymmetric(File keyFile, String inString) throws IOException {
    Key key = PrivateString.loadSerializedKey(keyFile);
    return PrivateString.encryptAsymmetric(key, inString);
  }


  /**
   * Encrypts a string using a public key, can only be decrypted using the
   * private key
   *
   * @param inString  Description of the Parameter
   * @param publicKey Description of the Parameter
   * @return Description of the Return Value
   */
  public static String encryptAsymmetric(Key publicKey, String inString) {
    try {
      Security.addProvider(
          new org.bouncycastle.jce.provider.BouncyCastleProvider());

      Cipher cipher = Cipher.getInstance("RSA/None/OAEPPadding", "BC");
      cipher.init(Cipher.ENCRYPT_MODE, publicKey);
      byte[] inputBytes = inString.getBytes("UTF8");
      byte[] outputBytes = cipher.doFinal(inputBytes);

      return new String(Base64.encodeBase64(outputBytes, true));
    } catch (Exception e) {
      return null;
    }
  }


  /**
   * Description of the Method
   *
   * @param keyFilename Description of the Parameter
   * @param inString    Description of the Parameter
   * @return Description of the Return Value
   * @throws java.io.IOException
   */
  public static String decryptAsymmetric(String keyFilename, String inString) throws IOException {
    Key key = PrivateString.loadSerializedKey(keyFilename);
    return PrivateString.decryptAsymmetric(key, inString);
  }


  /**
   * Decrypts a base64 encoded string, with a private key, that was encoded
   * using a public key
   *
   * @param inString   Description of the Parameter
   * @param privateKey Description of the Parameter
   * @return Description of the Return Value
   */
  public static String decryptAsymmetric(Key privateKey, String inString) {
    try {
      Security.addProvider(
          new org.bouncycastle.jce.provider.BouncyCastleProvider());

      Cipher cipher = Cipher.getInstance("RSA/None/OAEPPadding", "BC");
      cipher.init(Cipher.DECRYPT_MODE, privateKey);

      byte[] inputBytes = Base64.decodeBase64(inString.getBytes("UTF8"));
      byte[] outputBytes = cipher.doFinal(inputBytes);

      return new String(outputBytes, "UTF8");
    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }


  /**
   * Description of the Method
   *
   * @param args Description of the Parameter
   */
  public static void main(String args[]) {

    try {
      if (args.length == 0) {
        System.out.println("[encrypt or decrypt] [key] [string]");
      } else {
        String method = args[0];
        String keyFile = args[1];
        String text = args[2];

        File thisFile = new File(keyFile);
        Key key = null;
        if (!thisFile.exists()) {
          key = generateEncodedKeyFile(keyFile);
        } else {
          key = loadEncodedKey(keyFile);
        }
        if ("encrypt".equals(method)) {
          System.out.println(PrivateString.encrypt(key, text));
        } else {
          System.out.println(PrivateString.decrypt(key, text));
        }
      }

    } catch (Exception ex) {
      ex.printStackTrace(System.out);
    }
  }

}


