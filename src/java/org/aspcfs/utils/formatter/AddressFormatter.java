package org.aspcfs.utils.formatter;

import org.aspcfs.modules.base.Address;

/**
 *  Takes an address and formats the various fields to make the records
 *  consistent and presentable.
 *
 *@author     matt rajkowski
 *@created    March 5, 2003
 *@version    $Id: AddressFormatter.java,v 1.1.2.2 2003/03/06 17:50:06
 *      mrajkowski Exp $
 */
public class AddressFormatter {

  /**
   *  Constructor for the AddressFormatter object
   */
  public AddressFormatter() { }


  /**
   *  Description of the Method
   */
  public void config() {

  }


  /**
   *  Description of the Method
   *
   *@param  thisAddress  Description of the Parameter
   */
  public void format(Address thisAddress) {
    String country = thisAddress.getCountry();
    //Add a default country or else state cannot be retrieved
    if (country == null || country.trim().length() == 0) {
      country = "UNITED STATES";
    }
    //Format the country
    if (country != null) {
      country = country.toUpperCase().trim();
      if ("UNITED STATES OF AMERICA".equals(country) ||
          "USA".equals(country)) {
        country = "UNITED STATES";
      } else if ("UK".equals(country) ||
          "ENGLAND".equals(country)) {
        country = "UNITED KINGDOM";
      }
      thisAddress.setCountry(country);
    }
    //Add a 0 to the zip code when it was stripped by a number parser
    String zipCode = thisAddress.getZip();
    if (zipCode != null) {
      if ("UNITED STATES".equals(thisAddress.getCountry())) {
        if (zipCode.trim().length() == 4) {
          thisAddress.setZip("0" + zipCode.trim());
        } else if (zipCode.trim().length() == 8) {
          thisAddress.setZip("0" + zipCode.trim());
        }

        //Split zip code and PO Box No i.e 234569088 ==> 23456-9088
        if (zipCode.trim().length() == 9 && zipCode.indexOf("-") == -1) {
          thisAddress.setZip(zipCode.trim().substring(0, 5) + "-" + zipCode.substring(5));
        }
      }
    }
  }
}

