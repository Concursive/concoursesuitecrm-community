/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.utils.web;

import java.util.*;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    January 15, 2003
 *@version    $Id$
 */
public class StateSelect extends HtmlSelect {

  /**
   *  Constructor for the StateSelect object
   */
  public StateSelect() {
    this.addItem(-1, "--None--");
    this.addItem("AL", "Alabama");
    this.addItem("AK", "Alaska");
    this.addItem("AB", "Alberta");
    this.addItem("AS", "American Samoa");
    this.addItem("AZ", "Arizona");
    this.addItem("AR", "Arkansas");
    this.addItem("BC", "British Columbia");
    this.addItem("CA", "California");
    this.addItem("CO", "Colorado");
    this.addItem("CT", "Connecticut");
    this.addItem("DE", "Delaware");
    this.addItem("DC", "District of Columbia");
    this.addItem("FM", "Federated States of Micronesia");
    this.addItem("FL", "Florida");
    this.addItem("GA", "Georgia");
    this.addItem("GU", "Guam");
    this.addItem("HI", "Hawaii");
    this.addItem("ID", "Idaho");
    this.addItem("IL", "Illinois");
    this.addItem("IN", "Indiana");
    this.addItem("IA", "Iowa");
    this.addItem("KS", "Kansas");
    this.addItem("KY", "Kentucky");
    this.addItem("LA", "Louisiana");
    this.addItem("ME", "Maine");
    this.addItem("MB", "Manitoba");
    this.addItem("MH", "Marshall Islands");
    this.addItem("MD", "Maryland");
    this.addItem("MA", "Massachusetts");
    this.addItem("MI", "Michigan");
    this.addItem("MN", "Minnesota");
    this.addItem("MS", "Mississippi");
    this.addItem("MO", "Missouri");
    this.addItem("MT", "Montana");
    this.addItem("NE", "Nebraska");
    this.addItem("NV", "Nevada");
    this.addItem("NF", "Newfoundland");
    this.addItem("NB", "New Brunswick");
    this.addItem("NH", "New Hampshire");
    this.addItem("NJ", "New Jersey");
    this.addItem("NM", "New Mexico");
    this.addItem("NY", "New York");
    this.addItem("NC", "North Carolina");
    this.addItem("ND", "North Dakota");
    this.addItem("MP", "Northern Mariana Islands");
    this.addItem("NT", "Northwest Territories");
    this.addItem("NS", "Nova Scotia");
    this.addItem("OH", "Ohio");
    this.addItem("OK", "Oklahoma");
    this.addItem("ON", "Ontario");
    this.addItem("OR", "Oregon");
    this.addItem("PW", "Palau");
    this.addItem("PA", "Pennsylvania");
    this.addItem("PE", "Prince Edward Island");
    this.addItem("PR", "Puerto Rico");
    this.addItem("QC", "Quebec");
    this.addItem("RI", "Rhode Island");
    this.addItem("SK", "Saskatchewan");
    this.addItem("SC", "South Carolina");
    this.addItem("SD", "South Dakota");
    this.addItem("TN", "Tennessee");
    this.addItem("TX", "Texas");
    this.addItem("UT", "Utah");
    this.addItem("VT", "Vermont");
    this.addItem("VI", "Virgin Islands");
    this.addItem("VA", "Virginia");
    this.addItem("WA", "Washington");
    this.addItem("WV", "West Virginia");
    this.addItem("WI", "Wisconsin");
    this.addItem("WY", "Wyoming");
  }

}

