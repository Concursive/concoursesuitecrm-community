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
    //Add a 0 to the zip code when it was stripped by a number parser
    String zipCode = thisAddress.getZip();
    if (zipCode != null) {
      if (zipCode.trim().length() == 4 && "UNITED STATES".equals(thisAddress.getCountry())) {
        thisAddress.setZip("0" + zipCode.trim());
      }
    }
    //Format the country
    String country = thisAddress.getCountry();
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
  }
}

