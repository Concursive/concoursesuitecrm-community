package org.aspcfs.utils;

import java.security.*;
import java.io.*;
import java.util.Random;

/**
 *  Utility to hash a String. Code from news group.
 *
 *@author     matt rajkowski
 *@created    August 16, 2001
 *@version    $Id: PasswordHash.java,v 1.1.1.1 2002/01/14 19:49:27 mrajkowski
 *      Exp $
 */
public class PasswordHash {
  private static Random random = new Random();


  /**
   *  Default constructor.
   */
  public PasswordHash() { }


  /**
   *  Takes a string and turns it into a one-way string hash
   *
   *@param  inString  Description of Parameter
   *@return           Description of the Returned Value
   *@since
   */
  public static String encrypt(String inString) {
    try {
      MessageDigest md;
      //MD5, SHA, SHA-1
      md = MessageDigest.getInstance("MD5");
      byte[] output = md.digest(inString.getBytes());
      StringBuffer sb = new StringBuffer(2 * output.length);
      for (int i = 0; i < output.length; ++i) {
        int k = output[i] & 0xFF;
        if (k < 0x10) {
          sb.append('0');
        }
        sb.append(Integer.toHexString(k));
      }
      return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
  }


  /**
   *  Command line utility. When run as an application, this member uses the
   *  encrypt method to display the encrypted version of a line of input.
   *
   *@param  args  Not used.
   */
  public static void main(String args[]) {
    PasswordHash hasher = new PasswordHash();
    try {
      String text;
      if (args.length == 0) {
        System.out.print("Password: ");
        BufferedReader in =
            new BufferedReader(new InputStreamReader(System.in));
        text = in.readLine();
        System.out.println("Hash: " + hasher.encrypt(text));
      } else {
        text = args[0];
        System.out.println(hasher.encrypt(text));
      }
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }


  /**
   *  Gets the randomString attribute of the PasswordHash class
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     The randomString value
   */
  public static String getRandomString(int lo, int hi) {
    int n = rand(lo, hi);
    byte b[] = new byte[n];
    for (int i = 0; i < n; i++) {
      b[i] = (byte) rand('a', 'z');
    }
    return new String(b);
  }


  /**
   *  Description of the Method
   *
   *@param  lo  Description of the Parameter
   *@param  hi  Description of the Parameter
   *@return     Description of the Return Value
   */
  public static int rand(int lo, int hi) {
    int n = hi - lo + 1;
    int i = random.nextInt() % n;
    if (i < 0) {
      i = -i;
    }
    return lo + i;
  }
}


