package com.darkhorseventures.utils;
import java.security.*;
import java.io.*;
/**
 *  Description of the Class
 *
 *@created    August 16, 2001
 *@version    $Id$
 */
public class PasswordHash {

  /**
   *  Default constructor.
   *
  */
  public PasswordHash () {
  }

  /**
   *  Takes a string and turns it into a one-way string hash
   *
   *@param  inString  Description of Parameter
   *@return           Description of the Returned Value
   *@since
   */
  public String encrypt(String inString) {
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
   *  Command line utility. When run as an application, this
   *  member uses the encrypt method to display the encrypted
   *  version of a line of input.
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
		}
		else {
			text = args [0];
      		System.out.println(hasher.encrypt(text));
		}

    } catch (Exception ex) {
      System.out.println(ex);
    }
  }

}


