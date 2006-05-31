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

/**
 *  Description of the Class
 *
 * @author     matt rajkowski
 * @created    September 16, 2004
 * @version    $Id: CountrySelect.java 13207 2005-11-03 14:51:59 -0500 (Thu, 03
 *      Nov 2005) mrajkowski $
 */
public class CountrySelect extends HtmlSelect {

  /**
   *  Constructor for the CountrySelect object
   */
  public CountrySelect() {
    this.addItem(-1, "-- None --");
    this.setDefaultValue("UNITED STATES");
    addCountries();
  }


  /**
   *  Constructor for the CountrySelect object
   *
   * @param  thisSystem  Description of the Parameter
   */
  public CountrySelect(SystemStatus thisSystem) {
    this.addItem(-1, thisSystem.getLabel("calendar.none.4dashes"));
    this.setDefaultValue("UNITED STATES");
    addCountries();
  }


  /**
   *  Constructor for the CountrySelect object
   *
   * @param  emptyItem  Description of the Parameter
   */
  public CountrySelect(String emptyItem) {
    this.addItem(-1, emptyItem);
    this.setDefaultValue(-1);
    addCountries();
  }


  /**
   *  Adds a feature to the Countries attribute of the CountrySelect object
   */
  private void addCountries() {
    this.addItem("AFGHANISTAN", "AFGHANISTAN");
    this.addItem("ALBANIA", "ALBANIA");
    this.addItem("ALGERIA", "ALGERIA");
    this.addItem("ANDORRA", "ANDORRA");
    this.addItem("ANGOLA", "ANGOLA");
    this.addItem("ANGUILLA", "ANGUILLA");
    this.addItem("ANTIGUA AND BARBUDA", "ANTIGUA AND BARBUDA");
    this.addItem("ARGENTINA", "ARGENTINA");
    this.addItem("ARMENIA", "ARMENIA");
    this.addItem("ARUBA", "ARUBA");
    this.addItem("AUSTRALIA", "AUSTRALIA");
    this.addItem("AUSTRIA", "AUSTRIA");
    this.addItem("AZERBAIJAN", "AZERBAIJAN");
    this.addItem("AZORES", "AZORES");
    this.addItem("BAHAMAS", "BAHAMAS");
    this.addItem("BAHRAIN", "BAHRAIN");
    this.addItem("BANGLADESH", "BANGLADESH");
    this.addItem("BARBADOS", "BARBADOS");
    this.addItem("BELARUS", "BELARUS");
    this.addItem("BELGIUM", "BELGIUM");
    this.addItem("BELIZE", "BELIZE");
    this.addItem("BENIN", "BENIN");
    this.addItem("BERMUDA", "BERMUDA");
    this.addItem("BHUTAN", "BHUTAN");
    this.addItem("BOLIVIA", "BOLIVIA");
    this.addItem("BOSNIA HERCEGOVINA", "BOSNIA HERCEGOVINA");
    this.addItem("BOTSWANA", "BOTSWANA");
    this.addItem("BRAZIL", "BRAZIL");
    this.addItem("BRIT.VIRGIN IS.", "BRIT.VIRGIN IS.");
    this.addItem("BRUNEI", "BRUNEI");
    this.addItem("BULGARIA", "BULGARIA");
    this.addItem("BURKINA FASO", "BURKINA FASO");
    this.addItem("BURUNDI", "BURUNDI");
    this.addItem("CAMEROON", "CAMEROON");
    this.addItem("CANADA", "CANADA");
    this.addItem("CANARY ISLANDS", "CANARY ISLANDS");
    this.addItem("CAPE VERDE", "CAPE VERDE");
    this.addItem("CAROLINE ISLANDS", "CAROLINE ISLANDS");
    this.addItem("CAYMAN ISLANDS", "CAYMAN ISLANDS");
    this.addItem("CENTRAL AFRICAN REP", "CENTRAL AFRICAN REP");
    this.addItem("CHAD", "CHAD");
    this.addItem("CHILE", "CHILE");
    this.addItem("CHINA PEOPLES REP", "CHINA PEOPLES REP");
    this.addItem("COLOMBIA", "COLOMBIA");
    this.addItem("COMOROS ISLAND", "COMOROS ISLAND");
    this.addItem("COOK ISLANDS", "COOK ISLANDS");
    this.addItem("COSTA RICA", "COSTA RICA");
    this.addItem("COTE D'IVOIRE", "COTE D'IVOIRE");
    this.addItem("CROATIA", "CROATIA");
    this.addItem("CUBA", "CUBA");
    this.addItem("CYPRUS", "CYPRUS");
    this.addItem("CZECH REPUBLIC", "CZECH REPUBLIC");
    this.addItem("DENMARK", "DENMARK");
    this.addItem("DJIBOUTI", "DJIBOUTI");
    this.addItem("DOMINICA", "DOMINICA");
    this.addItem("DOMINICAN REPUBLIC", "DOMINICAN REPUBLIC");
    this.addItem("ECUADOR", "ECUADOR");
    this.addItem("EGYPT", "EGYPT");
    this.addItem("EL SALVADOR", "EL SALVADOR");
    this.addItem("EQUATORIAL GUINEA", "EQUATORIAL GUINEA");
    this.addItem("ERITREA", "ERITREA");
    this.addItem("ESTONIA", "ESTONIA");
    this.addItem("ETHIOPIA", "ETHIOPIA");
    this.addItem("FALKLAND ISLANDS", "FALKLAND ISLANDS");
    this.addItem("FAROE IS", "FAROE IS");
    this.addItem("FIJI", "FIJI");
    this.addItem("FINLAND", "FINLAND");
    this.addItem("FRANCE", "FRANCE");
    this.addItem("FRENCH GUIANA", "FRENCH GUIANA");
    this.addItem("FRENCH POLYNESIA", "FRENCH POLYNESIA");
    this.addItem("GABON", "GABON");
    this.addItem("GAMBIA", "GAMBIA");
    this.addItem("GEORGETOWN", "GEORGETOWN");
    this.addItem("GEORGIA", "GEORGIA");
    this.addItem("GERMANY", "GERMANY");
    this.addItem("GHANA", "GHANA");
    this.addItem("GIBRALTAR", "GIBRALTAR");
    this.addItem("GREECE", "GREECE");
    this.addItem("GREENLAND", "GREENLAND");
    this.addItem("GRENADA", "GRENADA");
    this.addItem("GUADELOUPE", "GUADELOUPE");
    this.addItem("GUATEMALA", "GUATEMALA");
    this.addItem("GUINEA", "GUINEA");
    this.addItem("GUINEA BISSAU", "GUINEA BISSAU");
    this.addItem("GUYANA", "GUYANA");
    this.addItem("HAITI", "HAITI");
    this.addItem("HONDURAS", "HONDURAS");
    this.addItem("HONG KONG", "HONG KONG");
    this.addItem("ICELAND", "ICELAND");
    this.addItem("INDIA", "INDIA");
    this.addItem("INDONESIA", "INDONESIA");
    this.addItem("IRAN", "IRAN");
    this.addItem("IRAQ", "IRAQ");
    this.addItem("IRELAND", "IRELAND");
    this.addItem("ISRAEL", "ISRAEL");
    this.addItem("ITALY", "ITALY");
    this.addItem("JAMAICA", "JAMAICA");
    this.addItem("JAPAN", "JAPAN");
    this.addItem("JORDAN", "JORDAN");
    this.addItem("KAMPUCHEA (CAMBODIA)", "KAMPUCHEA (CAMBODIA)");
    this.addItem("KAZAKHSTAN", "KAZAKHSTAN");
    this.addItem("KENYA", "KENYA");
    this.addItem("KUWAIT", "KUWAIT");
    this.addItem("KYRGYSTAN", "KYRGYSTAN");
    this.addItem("LAOS DEM REP OF", "LAOS DEM REP OF");
    this.addItem("LATVIA", "LATVIA");
    this.addItem("LEBANON", "LEBANON");
    this.addItem("LESOTHO", "LESOTHO");
    this.addItem("LIBERIA", "LIBERIA");
    this.addItem("LIBYA", "LIBYA");
    this.addItem("LIECHTENSTEIN", "LIECHTENSTEIN");
    this.addItem("LITHUANIA", "LITHUANIA");
    this.addItem("LUXEMBOURG", "LUXEMBOURG");
    this.addItem("MACAO", "MACAO");
    this.addItem("MADAGASCAR", "MADAGASCAR");
    this.addItem("MADEIRA ISLANDS", "MADEIRA ISLANDS");
    this.addItem("MALAWI", "MALAWI");
    this.addItem("MALAYSIA", "MALAYSIA");
    this.addItem("MALDIVES", "MALDIVES");
    this.addItem("MALI", "MALI");
    this.addItem("MALTA", "MALTA");
    this.addItem("MARSHALL ISLANDS", "MARSHALL ISLANDS");
    this.addItem("MARTINIQUE", "MARTINIQUE");
    this.addItem("MAURITANIA", "MAURITANIA");
    this.addItem("MAURITIUS", "MAURITIUS");
    this.addItem("MEXICO", "MEXICO");
    this.addItem("MIQUELON", "MIQUELON");
    this.addItem("MOLDOVA", "MOLDOVA");
    this.addItem("MONACO", "MONACO");
    this.addItem("MONGOLIA", "MONGOLIA");
    this.addItem("MONTSERRAT", "MONTSERRAT");
    this.addItem("MOROCCO", "MOROCCO");
    this.addItem("MOZAMBIQUE", "MOZAMBIQUE");
    this.addItem("MYANMAR (BURMA)", "MYANMAR (BURMA)");
    this.addItem("NAMIBIA", "NAMIBIA");
    this.addItem("NAURU", "NAURU");
    this.addItem("NEPAL", "NEPAL");
    this.addItem("NETHERLANDS", "NETHERLANDS");
    this.addItem("NETHERLANDS ANTILLES", "NETHERLANDS ANTILLES");
    this.addItem("NEW CALEDONIA", "NEW CALEDONIA");
    this.addItem("NEW ZEALAND", "NEW ZEALAND");
    this.addItem("NICARAGUA", "NICARAGUA");
    this.addItem("NIGER", "NIGER");
    this.addItem("NIGERIA", "NIGERIA");
    this.addItem("NORWAY", "NORWAY");
    this.addItem("OMAN SULTANATE", "OMAN SULTANATE");
    this.addItem("PAKISTAN", "PAKISTAN");
    this.addItem("PALAU", "PALAU");
    this.addItem("PANAMA", "PANAMA");
    this.addItem("PAPUA NEW GUINEA", "PAPUA NEW GUINEA");
    this.addItem("PARAGUAY", "PARAGUAY");
    this.addItem("PEO. REP. OF CONGO", "PEO. REP. OF CONGO");
    this.addItem("PERU", "PERU");
    this.addItem("PHILIPPINES", "PHILIPPINES");
    this.addItem("PITCAIRN ISLANDS", "PITCAIRN ISLANDS");
    this.addItem("POLAND", "POLAND");
    this.addItem("PORTUGAL", "PORTUGAL");
    this.addItem("QATAR", "QATAR");
    this.addItem("REP OF KOREA", "REP OF KOREA");
    this.addItem("REP. OF HUNGARY", "REP. OF HUNGARY");
    this.addItem("REP. OF KIRIBATI", "REP. OF KIRIBATI");
    this.addItem("REUNION IS.", "REUNION IS.");
    this.addItem("ROMANIA", "ROMANIA");
    this.addItem("RUSSIA", "RUSSIA");
    this.addItem("RWANDA", "RWANDA");
    this.addItem("ST HELENA", "ST HELENA");
    this.addItem("ST KITTS", "ST KITTS");
    this.addItem("ST LUCIA", "ST LUCIA");
    this.addItem("ST. VINCENT", "ST. VINCENT");
    this.addItem("SAO TOME ISLAND", "SAO TOME ISLAND");
    this.addItem("SAUDI ARABIA", "SAUDI ARABIA");
    this.addItem("SENEGAL", "SENEGAL");
    this.addItem("SERBIA", "SERBIA");
    this.addItem("SEYCHELLES", "SEYCHELLES");
    this.addItem("SIERRA LEONE", "SIERRA LEONE");
    this.addItem("SINGAPORE", "SINGAPORE");
    this.addItem("SLOVAKIA", "SLOVAKIA");
    this.addItem("SLOVENIA", "SLOVENIA");
    this.addItem("SOLOMON ISLANDS", "SOLOMON ISLANDS");
    this.addItem("SOMALIA", "SOMALIA");
    this.addItem("SOUTH AFRICA", "SOUTH AFRICA");
    this.addItem("SPAIN", "SPAIN");
    this.addItem("SRI LANKA", "SRI LANKA");
    this.addItem("SUDAN", "SUDAN");
    this.addItem("SURINAME", "SURINAME");
    this.addItem("SWAZILAND", "SWAZILAND");
    this.addItem("SWEDEN", "SWEDEN");
    this.addItem("SWITZERLAND", "SWITZERLAND");
    this.addItem("SYRIA", "SYRIA");
    this.addItem("TADIKSTAN", "TADIKSTAN");
    this.addItem("TAIWAN", "TAIWAN");
    this.addItem("TANZANIA UNION REP OF", "TANZANIA UNION REP OF");
    this.addItem("THAILAND", "THAILAND");
    this.addItem("TOGO", "TOGO");
    this.addItem("TONGA", "TONGA");
    this.addItem("TRINIDAD", "TRINIDAD");
    this.addItem("TRISTAN DA CUNHA", "TRISTAN DA CUNHA");
    this.addItem("TUNISIA", "TUNISIA");
    this.addItem("TURKEY", "TURKEY");
    this.addItem("TURKMENISTAN", "TURKMENISTAN");
    this.addItem("TURKS & CAICOS ISL", "TURKS & CAICOS ISL");
    this.addItem("TUVALU ISLAND", "TUVALU ISLAND");
    this.addItem("UGANDA", "UGANDA");
    this.addItem("UKRAINE", "UKRAINE");
    this.addItem("UNITED ARAB EMIRATES", "UNITED ARAB EMIRATES");
    this.addItem("UNITED KINGDOM", "UNITED KINGDOM");
    this.addItem("UNITED STATES", "UNITED STATES");
    this.addItem("URUGUAY", "URUGUAY");
    this.addItem("UZBEKISTAN", "UZBEKISTAN");
    this.addItem("VANUATU", "VANUATU");
    this.addItem("VATICAN CITY STATE", "VATICAN CITY STATE");
    this.addItem("VENEZUELA", "VENEZUELA");
    this.addItem("VIETNAM", "VIETNAM");
    this.addItem("WESTERN SAMOA", "WESTERN SAMOA");
    this.addItem("YEMEN", "YEMEN");
    this.addItem("ZAIRE", "ZAIRE");
    this.addItem("ZAMBIA", "ZAMBIA");
    this.addItem("ZIMBABWE", "ZIMBABWE");
  }


  /**
   *  Gets the countryByAbbreviation attribute of the CountrySelect object
   *
   * @param  country  Description of the Parameter
   * @return          The countryByAbbreviation value
   */
  public static String getCountryByAbbreviation(String country) {
    String result = country;
    if (country != null && (country.trim().toUpperCase().equals("USA") || country.trim().toUpperCase().equals("US"))) {
      result = new String("UNITED STATES");
    }
    return result;
  }
}

