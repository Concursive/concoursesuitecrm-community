package org.aspcfs.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * Public and private key utilities
 *
 * @author matt rajkowski
 * @version $Id$
 * @created June 8, 2004
 */
public class SecurityKey {

  /**
   * Generates a random key pair
   *
   * @param publicFilename  Description of the
   *                        Parameter
   * @param privateFilename Description of the
   *                        Parameter
   * @throws java.security.NoSuchAlgorithmException
   *                                       Description of the
   *                                       Exception
   * @throws java.io.FileNotFoundException Description of the
   *                                       Exception
   */
  public static void generateKeyPair(String publicFilename, String privateFilename) throws
      java.security.NoSuchAlgorithmException, java.io.FileNotFoundException,
      java.io.IOException, java.security.NoSuchProviderException {
    Security.addProvider(
        new org.bouncycastle.jce.provider.BouncyCastleProvider());

    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
    keyPairGen.initialize(1024, new SecureRandom());
    KeyPair keyPair = keyPairGen.generateKeyPair();

    PublicKey publicKey = keyPair.getPublic();
    PrivateKey privateKey = keyPair.getPrivate();

    ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(publicFilename));
    out.writeObject(publicKey);
    out.close();

    out = new ObjectOutputStream(new FileOutputStream(privateFilename));
    out.writeObject(privateKey);
    out.close();
  }


  /**
   * Simple password-based encryption of the key, for locking down a private
   * key
   *
   * @param unencodedFilename Description of the
   *                          Parameter
   * @param encodedFilename   Description of the
   *                          Parameter
   * @param password          Description of the
   *                          Parameter
   * @throws java.security.NoSuchAlgorithmException
   *                                       Description of the
   *                                       Exception
   * @throws java.io.FileNotFoundException Description of the
   *                                       Exception
   * @throws java.security.spec.InvalidKeySpecException
   *                                       Description of the
   *                                       Exception
   * @throws java.security.InvalidKeyException
   *                                       Description of the
   *                                       Exception
   * @throws java.lang.Exception           Description of the
   *                                       Exception
   * @throws javax.crypto.IllegalBlockSizeException
   *                                       Description of the
   *                                       Exception
   */
  public static void encryptKey(String unencodedFilename, String encodedFilename, String password) throws
      java.security.NoSuchAlgorithmException, java.io.FileNotFoundException,
      java.security.spec.InvalidKeySpecException, java.security.InvalidKeyException,
      java.lang.Exception, javax.crypto.IllegalBlockSizeException {
    Security.addProvider(
        new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
    PrivateKey key = (PrivateKey) PrivateString.loadSerializedKey(unencodedFilename);

    // Convert to byteArray
    byte[] cleartext = ObjectUtils.toByteArray(key);

    // Encrypt the byteArray
    byte[] ciphertext = pbeCipher.doFinal(cleartext);

    // Convert byteArray to base64
    String base64 = new String(Base64.encodeBase64(ciphertext, true));

    // Save the encoded text
    StringUtils.saveText(encodedFilename, base64);
  }


  /**
   * Description of the Method
   *
   * @param encodedFilename Description of the
   *                        Parameter
   * @param password        Description of the
   *                        Parameter
   * @param base64          Description of the
   *                        Parameter
   * @return Description of the
   *         Return Value
   * @throws java.security.NoSuchAlgorithmException
   *                             Description of the
   *                             Exception
   * @throws java.security.spec.InvalidKeySpecException
   *                             Description of the
   *                             Exception
   * @throws java.security.InvalidKeyException
   *                             Description of the
   *                             Exception
   * @throws java.io.IOException Description of the
   *                             Exception
   * @throws java.lang.Exception Description of the
   *                             Exception
   */
  public static String useEncodedKey(String encodedFilename, String password, String base64) throws
      java.security.NoSuchAlgorithmException, java.security.spec.InvalidKeySpecException,
      java.security.InvalidKeyException, java.io.IOException, java.lang.Exception {
    if (encodedFilename == null || password == null || base64 == null) {
      return null;
    }
    Security.addProvider(
        new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
    String base64Text = StringUtils.loadText(encodedFilename);
    byte[] ciphertext = Base64.decodeBase64(base64Text.getBytes("UTF8"));

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

