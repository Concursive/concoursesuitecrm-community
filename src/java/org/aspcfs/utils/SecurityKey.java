package org.aspcfs.utils;

import java.security.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import com.sun.crypto.provider.*;
import sun.misc.*;
import org.aspcfs.utils.ObjectUtils;
import org.aspcfs.utils.PrivateString;
import org.aspcfs.utils.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.*;

/**
 *  Public and private key utilities
 *
 *@author     matt rajkowski
 *@created    June 8, 2004
 *@version    $Id$
 */
public class SecurityKey {

  /**
   *  Generates a random key pair
   *
   *@param  publicFilename                              Description of the
   *      Parameter
   *@param  privateFilename                             Description of the
   *      Parameter
   *@exception  java.security.NoSuchAlgorithmException  Description of the
   *      Exception
   *@exception  java.io.FileNotFoundException           Description of the
   *      Exception
   */
  public static void generateKeyPair(String publicFilename, String privateFilename) throws
      java.security.NoSuchAlgorithmException, java.io.FileNotFoundException,
      java.io.IOException, java.security.NoSuchProviderException {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGen.initialize(1024, new SecureRandom());
    KeyPair keyPair = keyPairGen.generateKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(publicFilename));
    out.writeObject(publicKey);
    out.close();

    out = new ObjectOutputStream(new FileOutputStream(privateFilename));
    out.writeObject(privateKey);
    out.close();
  }


  /**
   *  Simple password-based encryption of the key, for locking down a private
   *  key
   *
   *@param  unencodedFilename                               Description of the
   *      Parameter
   *@param  encodedFilename                                 Description of the
   *      Parameter
   *@param  password                                        Description of the
   *      Parameter
   *@exception  java.security.NoSuchAlgorithmException      Description of the
   *      Exception
   *@exception  java.io.FileNotFoundException               Description of the
   *      Exception
   *@exception  java.security.spec.InvalidKeySpecException  Description of the
   *      Exception
   *@exception  java.security.InvalidKeyException           Description of the
   *      Exception
   *@exception  java.lang.Exception                         Description of the
   *      Exception
   *@exception  javax.crypto.IllegalBlockSizeException      Description of the
   *      Exception
   */
  public static void encryptKey(String unencodedFilename, String encodedFilename, String password) throws
      java.security.NoSuchAlgorithmException, java.io.FileNotFoundException,
      java.security.spec.InvalidKeySpecException, java.security.InvalidKeyException,
      java.lang.Exception, javax.crypto.IllegalBlockSizeException {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    PBEKeySpec pbeKeySpec;
    PBEParameterSpec pbeParamSpec;
    SecretKeyFactory keyFac;

    // Salt
    byte[] salt = {
        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
        };

    // Iteration count
    int count = 20;

    // Create PBE parameter set
    pbeParamSpec = new PBEParameterSpec(salt, count);
    pbeKeySpec = new PBEKeySpec(password.toCharArray());
    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

    // Create PBE Cipher
    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
    // Initialize PBE Cipher with key and parameters
    pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

    // Load the key
    PrivateKey key = (PrivateKey) PrivateString.loadKey(unencodedFilename);

    // Convert to byteArray
    byte[] cleartext = ObjectUtils.toByteArray(key);

    // Encrypt the byteArray
    byte[] ciphertext = pbeCipher.doFinal(cleartext);

    // Convert byteArray to base64
    BASE64Encoder encoder = new BASE64Encoder();
    String base64 = encoder.encode(ciphertext);

    // Save the encoded text
    StringUtils.saveText(encodedFilename, base64);
  }


  /**
   *  Description of the Method
   *
   *@param  encodedFilename                                 Description of the
   *      Parameter
   *@param  password                                        Description of the
   *      Parameter
   *@param  base64                                          Description of the
   *      Parameter
   *@return                                                 Description of the
   *      Return Value
   *@exception  java.security.NoSuchAlgorithmException      Description of the
   *      Exception
   *@exception  java.security.spec.InvalidKeySpecException  Description of the
   *      Exception
   *@exception  java.security.InvalidKeyException           Description of the
   *      Exception
   *@exception  java.io.IOException                         Description of the
   *      Exception
   *@exception  java.lang.Exception                         Description of the
   *      Exception
   */
  public static String useEncodedKey(String encodedFilename, String password, String base64) throws
      java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException,
      java.security.InvalidKeyException, java.io.IOException, java.lang.Exception {
    if (encodedFilename == null || password == null || base64 == null) {
      return null;
    }
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    PBEKeySpec pbeKeySpec;
    PBEParameterSpec pbeParamSpec;
    SecretKeyFactory keyFac;

    // Salt
    byte[] salt = {
        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99
        };

    // Iteration count
    int count = 20;

    // Create PBE parameter set
    pbeParamSpec = new PBEParameterSpec(salt, count);
    pbeKeySpec = new PBEKeySpec(password.toCharArray());
    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
    SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

    // Create PBE Cipher
    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
    // Initialize PBE Cipher with key and parameters
    pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

    // Load the base64 text
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] ciphertext = decoder.decodeBuffer(StringUtils.loadText(encodedFilename));

    // Decrypt the byteArray
    byte[] cleartext = null;
    try {
      cleartext = pbeCipher.doFinal(ciphertext);
    } catch (Exception e) {
      throw new Exception("Incorrect password");
    }

    // Get the key
    Key key = (Key) ObjectUtils.toObject(cleartext);

    // Decrypt the text
    return PrivateString.decryptAsymmetric(key, base64);
  }
}

