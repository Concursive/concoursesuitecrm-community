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

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.utils.DatabaseUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    January 15, 2003
 * @version    $Id: StateSelect.java 13207 2005-11-03 14:51:59 -0500 (Thu, 03
 *      Nov 2005) mrajkowski $
 */
public class StateSelect extends LinkedHashMap {
  SystemStatus systemStatus = null;
  public boolean buildAll = false;
  public String countries = null;
  public String jsEvent = null;
  public HashMap previousStates = new HashMap();


  /**
   *  Constructor for the StateSelect object
   */
  public StateSelect() { }


  /**
   *  Constructor for the StateSelect object
   *
   * @param  buildAll  Description of the Parameter
   */
  public StateSelect(boolean buildAll) {
    this.setBuildAll(buildAll);
    addStates();
  }


  /**
   *  Constructor for the StateSelect object
   *
   * @param  countries  Description of the Parameter
   */
  public StateSelect(String countries) {
    if (countries != null) {
      this.setCountries(countries);
    } else {
      this.setCountries("");
    }
    addStates();
  }


  /**
   *  Constructor for the StateSelect object
   *
   * @param  systemStatus  Description of the Parameter
   */
  public StateSelect(SystemStatus systemStatus) {
    this.setSystemStatus(systemStatus);
  }


  /**
   *  Constructor for the StateSelect object
   *
   * @param  systemStatus  Description of the Parameter
   * @param  buildAll      Description of the Parameter
   */
  public StateSelect(SystemStatus systemStatus, boolean buildAll) {
    this.setSystemStatus(systemStatus);
    this.setBuildAll(buildAll);
    addStates();
  }


  /**
   *  Constructor for the StateSelect object
   *
   * @param  systemStatus  Description of the Parameter
   * @param  countries     Description of the Parameter
   */
  public StateSelect(SystemStatus systemStatus, String countries) {
    this.setSystemStatus(systemStatus);
    if (countries != null) {
      this.setCountries(countries);
    } else {
      this.setCountries("");
    }
    addStates();
  }


  /**
   *  Gets the systemStatus attribute of the StateSelect object
   *
   * @return    The systemStatus value
   */
  public SystemStatus getSystemStatus() {
    return systemStatus;
  }


  /**
   *  Sets the systemStatus attribute of the StateSelect object
   *
   * @param  tmp  The new systemStatus value
   */
  public void setSystemStatus(SystemStatus tmp) {
    this.systemStatus = tmp;
  }


  /**
   *  Gets the buildAll attribute of the StateSelect object
   *
   * @return    The buildAll value
   */
  public boolean getBuildAll() {
    return buildAll;
  }


  /**
   *  Sets the buildAll attribute of the StateSelect object
   *
   * @param  tmp  The new buildAll value
   */
  public void setBuildAll(boolean tmp) {
    this.buildAll = tmp;
  }


  /**
   *  Sets the buildAll attribute of the StateSelect object
   *
   * @param  tmp  The new buildAll value
   */
  public void setBuildAll(String tmp) {
    this.buildAll = DatabaseUtils.parseBoolean(tmp);
  }


  /**
   *  Gets the countries attribute of the StateSelect object
   *
   * @return    The countries value
   */
  public String getCountries() {
    return countries;
  }


  /**
   *  Sets the countries attribute of the StateSelect object
   *
   * @param  tmp  The new countries value
   */
  public void setCountries(String tmp) {
    this.countries = tmp;
  }


  /**
   *  Adds a feature to the Country attribute of the StateSelect object
   *
   * @param  tmp  The feature to be added to the Country attribute
   */
  public void addCountry(String tmp) {
    countries = (new StringBuffer(countries + "," + tmp)).toString();
  }


  /**
   *  Gets the jsEvent attribute of the StateSelect object
   *
   * @return    The jsEvent value
   */
  public String getJsEvent() {
    return jsEvent;
  }


  /**
   *  Sets the jsEvent attribute of the StateSelect object
   *
   * @param  tmp  The new jsEvent value
   */
  public void setJsEvent(String tmp) {
    this.jsEvent = tmp;
  }


  /**
   *  Gets the previousStates attribute of the StateSelect object
   *
   * @return    The previousStates value
   */
  public HashMap getPreviousStates() {
    return previousStates;
  }


  /**
   *  Sets the previousStates attribute of the StateSelect object
   *
   * @param  tmp  The new previousStates value
   */
  public void setPreviousStates(HashMap tmp) {
    this.previousStates = tmp;
  }


  /**
   *  Adds a feature to the States attribute of the StateSelect object
   */
  private void addStates() {
//United States
    LinkedHashMap states = new LinkedHashMap();
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("UNITED STATES") != -1 || buildAll) {
      addUnitedStates(states);
      this.put("UNITED STATES", states);
    }
//Canadian States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("CANADA") != -1 || buildAll) {
      states = new LinkedHashMap();
      addCanadianStates(states);
      this.put("CANADA", states);
    }
//Indian States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("INDIA") != -1 || buildAll) {
      states = new LinkedHashMap();
      addIndianStates(states);
      this.put("INDIA", states);
    }
//Venezuelan States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("VENEZUELA") != -1 || buildAll) {
      states = new LinkedHashMap();
      addVenezuelanStates(states);
      this.put("VENEZUELA", states);
    }
//Netherland States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("NETHERLANDS") != -1 || buildAll) {
      states = new LinkedHashMap();
      addNetherlandStates(states);
      this.put("NETHERLANDS", states);
    }
//Australian States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("AUSTRALIA") != -1 || buildAll) {
      states = new LinkedHashMap();
      addAustralianStates(states);
      this.put("AUSTRALIA", states);
    }
//Brazilian States
    if (CountrySelect.getCountryByAbbreviation(countries.trim().toUpperCase()).indexOf("BRAZIL") != -1 || buildAll) {
      states = new LinkedHashMap();
      addBrazilianStates(states);
      this.put("BRAZIL", states);
    }
  }


  /**
   *  Description of the Method
   *
   * @param  country  Description of the Parameter
   * @return          Description of the Return Value
   */
  public boolean hasCountry(String country) {
    if (country != null &&
        this.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) != null) {
      return true;
    }
    return false;
  }


  /**
   *  Gets the htmlSelect attribute of the StateSelect object
   *
   * @param  selectName  Description of the Parameter
   * @param  country     Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String country) {
    return getHtmlSelect(selectName, country, "");
  }


  /**
   *  Gets the htmlSelect attribute of the StateSelect object
   *
   * @param  selectName  Description of the Parameter
   * @param  country     Description of the Parameter
   * @param  defaultKey  Description of the Parameter
   * @return             The htmlSelect value
   */
  public String getHtmlSelect(String selectName, String country, String defaultKey) {
    HtmlSelect stateSelect = new HtmlSelect();
    stateSelect.setJsEvent(jsEvent);
    boolean hasPreviousStates = false;
    if (systemStatus != null) {
      stateSelect.addItem("-1", systemStatus.getLabel("calendar.none.4dashes"));
    } else {
      stateSelect.addItem("-1", "-- None --");
    }
    if (this.hasCountry(country)) {
      LinkedHashMap states = (LinkedHashMap) this.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase()));
      Iterator i = states.keySet().iterator();
      while (i.hasNext()) {
        String stateCode = (String) i.next();
        String stateName = (String) states.get(stateCode);
        stateSelect.addItem(stateCode, stateName);
        if (!hasPreviousStates && previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) != null && !"".equals((String)previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())))) {
          hasPreviousStates = stateCode.equals((String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())));
        }
      }
    }
    if (!hasPreviousStates && previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) != null && !"".equals((String)previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())))) {
      stateSelect.addItem((String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())), (String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) +" *");
    }
    stateSelect.setIdName(selectName);
    return stateSelect.getHtml(selectName, defaultKey);
  }


  /**
   *  Gets the htmlSelectObj attribute of the StateSelect object
   *
   * @param  country  Description of the Parameter
   * @return          The htmlSelectObj value
   */
  public HtmlSelect getHtmlSelectObj(String country) {
    boolean hasPreviousStates = false;
    HtmlSelect stateSelect = new HtmlSelect();
    stateSelect.setJsEvent(jsEvent);
    if (this.hasCountry(country)) {
      LinkedHashMap states = (LinkedHashMap) this.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase()));
      Iterator i = states.keySet().iterator();
      while (i.hasNext()) {
        String stateCode = (String) i.next();
        String stateName = (String) states.get(stateCode);
        stateSelect.addItem(stateCode, stateName);
        if (!hasPreviousStates && previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) != null && !"".equals((String)previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())))) {
          hasPreviousStates = stateCode.equals((String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())));
        }
      }
    }
    if (!hasPreviousStates && previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())) != null && !"".equals((String)previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())))) {
      stateSelect.addItem((String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())), (String) previousStates.get(CountrySelect.getCountryByAbbreviation(country.trim().toUpperCase())));
    }
    return stateSelect;
  }


  /**
   *  Adds a feature to the UnitedStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the UnitedStates attribute
   */
  public void addUnitedStates(LinkedHashMap states) {
    states.put("AL", "Alabama");
    states.put("AK", "Alaska");
    states.put("AS", "American Samoa");
    states.put("AZ", "Arizona");
    states.put("AR", "Arkansas");
    states.put("CA", "California");
    states.put("CO", "Colorado");
    states.put("CT", "Connecticut");
    states.put("DE", "Delaware");
    states.put("DC", "District of Columbia");
    states.put("FL", "Florida");
    states.put("GA", "Georgia");
    states.put("GU", "Guam");
    states.put("HI", "Hawaii");
    states.put("ID", "Idaho");
    states.put("IL", "Illinois");
    states.put("IN", "Indiana");
    states.put("IA", "Iowa");
    states.put("KS", "Kansas");
    states.put("KY", "Kentucky");
    states.put("LA", "Louisiana");
    states.put("ME", "Maine");
    states.put("MH", "Marshall Islands");
    states.put("MD", "Maryland");
    states.put("MA", "Massachusetts");
    states.put("MI", "Michigan");
    states.put("MN", "Minnesota");
    states.put("MS", "Mississippi");
    states.put("MO", "Missouri");
    states.put("MT", "Montana");
    states.put("NE", "Nebraska");
    states.put("NV", "Nevada");
    states.put("NH", "New Hampshire");
    states.put("NJ", "New Jersey");
    states.put("NM", "New Mexico");
    states.put("NY", "New York");
    states.put("NC", "North Carolina");
    states.put("ND", "North Dakota");
    states.put("MP", "Northern Mariana Islands");
    states.put("OH", "Ohio");
    states.put("OK", "Oklahoma");
    states.put("OR", "Oregon");
    states.put("PW", "Palau");
    states.put("PA", "Pennsylvania");
    states.put("PR", "Puerto Rico");
    states.put("RI", "Rhode Island");
    states.put("SC", "South Carolina");
    states.put("SD", "South Dakota");
    states.put("TN", "Tennessee");
    states.put("TX", "Texas");
    states.put("UT", "Utah");
    states.put("VT", "Vermont");
    states.put("VI", "Virgin Islands");
    states.put("VA", "Virginia");
    states.put("WA", "Washington");
    states.put("WV", "West Virginia");
    states.put("WI", "Wisconsin");
    states.put("WY", "Wyoming");
  }


  /**
   *  Adds a feature to the CanadianStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the CanadianStates attribute
   */
  public void addCanadianStates(LinkedHashMap states) {
    states.put("AB", "Alberta");
    states.put("BC", "British Columbia");
    states.put("FM", "Federated States of Micronesia");
    states.put("MB", "Manitoba");
    states.put("NB", "New Brunswick");
    states.put("NL", "Newfoundland");
    states.put("NT", "Northwest Territories");
    states.put("NS", "Nova Scotia");
    states.put("NU", "Nunavut");
    states.put("ON", "Ontario");
    states.put("PE", "Prince Edward Island");
    states.put("QC", "Quebec");
    states.put("SK", "Saskatchewan");
    states.put("YT", "Yukon");
  }


  /**
   *  Adds a feature to the IndianStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the IndianStates attribute
   */
  public void addIndianStates(LinkedHashMap states) {
    states.put("AN", "Andaman and Nicobar Islands");
    states.put("AP", "Andhra Pradesh");
    states.put("AR", "Arunachal Pradesh");
    states.put("AS", "Assam");
    states.put("BR", "Bihar");
    states.put("CH", "Chandigarh");
    states.put("CT", "Chhattisgarh");
    states.put("DN", "Dadra and Nagar Haveli");
    states.put("DD", "Daman and Diu");
    states.put("DL", "Delhi");
    states.put("GA", "Goa");
    states.put("GJ", "Gujarat");
    states.put("HR", "Haryana");
    states.put("HP", "Himachal Pradesh");
    states.put("JK", "Jammu and Kashmir");
    states.put("JH", "Jharkhand");
    states.put("KA", "Karnataka");
    states.put("KL", "Kerala");
    states.put("LD", "Lakshadweep");
    states.put("MP", "Madhya Pradesh");
    states.put("MH", "Maharashtra");
    states.put("MN", "Manipur");
    states.put("ML", "Meghalaya");
    states.put("MZ", "Mizoram");
    states.put("NL", "Nagaland");
    states.put("OR", "Orissa");
    states.put("PY", "Pondicherry");
    states.put("PB", "Punjab");
    states.put("RJ", "Rajasthan");
    states.put("SK", "Sikkim");
    states.put("TN", "Tamil Nadu");
    states.put("TR", "Tripura");
    states.put("UT", "Uttaranchal");
    states.put("UP", "Uttar Pradesh");
    states.put("WB", "West Bengal");
  }


  /**
   *  Adds a feature to the VenezuelanStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the VenezuelanStates attribute
   */
  public void addVenezuelanStates(LinkedHashMap states) {
    states.put("ANZ", "Anzoategui");
    states.put("MON", "Monagas");
    states.put("DEL", "Delta Amacuro");
    states.put("BOL", "Bolivar");
    states.put("AMZ", "Amazonas");
    states.put("SUC", "Sucre");
    states.put("NVE", "Nueva Esparta");
    states.put("GUA", "Guarico");
    states.put("ARA", "Aragua");
    states.put("CBO", "Carabobo");
    states.put("DFE", "Distrito Federal");
    states.put("MIR", "Miranda");
    states.put("MER", "Merida");
    states.put("LAR", "Lara");
    states.put("YAR", "Yaracuy");
    states.put("FAL", "Falcon");
    states.put("BAR", "Barinas");
    states.put("COJ", "Cojedes");
    states.put("APU", "Apure");
    states.put("TAC", "Tachira");
    states.put("TRU", "Trujillo");
    states.put("ZUL", "Zulia");
    states.put("VAR", "Vargas");
    states.put("POR", "Portuguesa");
  }


  /**
   *  Adds a feature to the NetherlandStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the NetherlandStates attribute
   */
  public void addNetherlandStates(LinkedHashMap states) {
    states.put("DR", "Drenthe");
    states.put("FL", "Flevoland");
    states.put("FR", "Friesland");
    states.put("GE", "Gelderland");
    states.put("GR", "Groningen");
    states.put("LI", "Limburg");
    states.put("NB", "Noord-Brabant");
    states.put("NH", "Noord-Holland");
    states.put("OV", "Overijssel");
    states.put("UT", "Utrecht");
    states.put("ZE", "Zeeland");
    states.put("ZH", "Zuid-Holland");
  }
  
  public void addAustralianStates(LinkedHashMap states) {
    states.put("CT", "Australian Capital Territory");
    states.put("NS", "New South Wales");
    states.put("NT", "Northern Territory");
    states.put("QL", "Queensland");
    states.put("SA", "South Australia");
    states.put("TS", "Tasmania");
    states.put("VI", "Victoria");
    states.put("WA", "Western Australia");
    states.put("AS", "Ashmore and Cartier Islands");
    states.put("CR", "Coral Sea Islands Territory");
  }
  /**
   *  Adds a feature to the BrazilianStates attribute of the StateSelect object
   *
   * @param  states  The feature to be added to the BrazilianStates attribute
   */
  public void addBrazilianStates(LinkedHashMap states) {
    states.put("AC","Acre");
    states.put("AL","Alagoas");
    states.put("AM","Amazonas");
    states.put("AP","Amap\u00e1");
    states.put("BA","Bahia");
    states.put("CE","Cear\u00e1");
    states.put("DF","Distrito Federal");
    states.put("ES","Esp\u00edrito Santo");
    states.put("GO","Goi\u00e1s");
    states.put("MA","Maranh\u00e3o");
    states.put("MG","Minas Gerais");
    states.put("MS","Mato Grosso do Sul");
    states.put("MT","Mato Grosso");
    states.put("PA","Par\u00e1");
    states.put("PB","Para\u00edba");
    states.put("PE","Pernambuco");
    states.put("PI","Piau\u00ed");
    states.put("PR","Paran\u00e1");
    states.put("RJ","Rio de Janeiro");
    states.put("RN","Rio Grande do Norte");
    states.put("RS","Rio Grande do Sul");
    states.put("RO","Rond\u00f4nia");
    states.put("RR","Roraima");
    states.put("SC","Santa Catarina");
    states.put("SP","S\u00e3o Paulo");
    states.put("SE","Sergipe");
    states.put("TO","Tocantins");
  }
}

