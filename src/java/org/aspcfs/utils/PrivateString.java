package org.aspcfs.utils;

import java.security.*;
import java.io.*;
import sun.misc.*;
import javax.crypto.*;
import com.sun.crypto.provider.*;

/**
 *  Encrypts and decrypts data using a key
 *
 *@author     matt rajkowski
 *@created    August 22, 2002
 *@version    $Id$
 */
public class PrivateString {

  private Key key = null;


  /**
   *  Constructor for the PrivateString object
   */
  public PrivateString() { }


  /**
   *  Constructor for the PrivateString object
   *
   *@param  file  Description of the Parameter
   */
  public PrivateString(String file) {
    key = PrivateString.generateKeyFile(file);
  }


  /**
   *  Sets the key attribute of the PrivateString object
   *
   *@param  tmp  The new key value
   */
  public void setKey(Key tmp) {
    this.key = tmp;
  }


  /**
   *  Gets the key attribute of the PrivateString object
   *
   *@return    The key value
   */
  public Key getKey() {
    return key;
  }


  /**
   *  Description of the Method
   *
   *@param  filename  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static Key generateKeyFile(String filename) {
    try {
      File file = new File(filename);
      if (!file.exists()) {
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(56, new SecureRandom());
        Key key = generator.generateKey();

        ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(filename));
        out.writeObject(key);
        out.close();

        return key;
      } else {
        return PrivateString.loadKey(filename);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }


  /**
   *  Description of the Method
   *
   *@param  keyFilename  Description of the Parameter
   *@return              Description of the Return Value
   */
  public static Key loadKey(String keyFilename) {
    try {
      ObjectInputStream in = new ObjectInputStream(
          new FileInputStream(keyFilename));
      Key key = (Key) in.readObject();
      in.close();
      return key;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  inString     Description of the Parameter
   *@param  keyFilename  Description of the Parameter
   *@return              Description of the Return Value
   */
  public static String encrypt(String keyFilename, String inString) {
    Key key = PrivateString.loadKey(keyFilename);
    return PrivateString.encrypt(key, inString);
  }


  /**
   *  Description of the Method
   *
   *@param  key       Description of the Parameter
   *@param  inString  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static String encrypt(Key key, String inString) {
    try {
      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, key);
      byte[] inputBytes = inString.getBytes("UTF8");
      byte[] outputBytes = cipher.doFinal(inputBytes);

      BASE64Encoder encoder = new BASE64Encoder();
      String base64 = encoder.encode(outputBytes);
      return (base64);
    } catch (Exception e) {
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  inString     Description of the Parameter
   *@param  keyFilename  Description of the Parameter
   *@return              Description of the Return Value
   */
  public static String decrypt(String keyFilename, String inString) {
    Key key = PrivateString.loadKey(keyFilename);
    return PrivateString.decrypt(key, inString);
  }


  /**
   *  Description of the Method
   *
   *@param  key       Description of the Parameter
   *@param  inString  Description of the Parameter
   *@return           Description of the Return Value
   */
  public static String decrypt(Key key, String inString) {
    try {
      Security.addProvider(new com.sun.crypto.provider.SunJCE());

      Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, key);

      BASE64Decoder decoder = new BASE64Decoder();

      byte[] inputBytes = decoder.decodeBuffer(inString);
      byte[] outputBytes = cipher.doFinal(inputBytes);

      String result = new String(outputBytes, "UTF8");
      return result;
    } catch (Exception e) {
      System.out.println(e.toString());
      return null;
    }
  }


  /**
   *  Description of the Method
   *
   *@param  args  Description of the Parameter
   */
  public static void main(String args[]) {

    try {
      if (args.length == 0) {
        System.out.println("[encrypt or decrypt] [key] [string]");
        System.exit(0);
      } else {
        String method = args[0];
        String key = args[1];
        String text = args[2];

        File thisFile = new File(key);
        if (!thisFile.exists()) {
          PrivateString.generateKeyFile(key);
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


