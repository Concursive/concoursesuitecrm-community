package org.aspcfs.utils.web;

import java.util.*;
import java.sql.*;

/**
 *  Generates an HtmlSelect for time zones
 *
 *@author     matt rajkowski
 *@created    September 3, 2003
 *@version    $Id: HtmlSelectTimeZone.java,v 1.2 2003/09/03 16:59:45 mrajkowski
 *      Exp $
 */
public class HtmlSelectTimeZone {

  /**
   *  Gets the select attribute of the HtmlSelectTimeZone class
   *
   *@param  name          Description of the Parameter
   *@param  defaultValue  Description of the Parameter
   *@return               The select value
   */
  public static HtmlSelect getSelect(String name, String defaultValue) {
    HtmlSelect select = new HtmlSelect();
    select.setSelectName(name);
    select.setDefaultValue(defaultValue);
    populateSelect(select);
    return select;
  }


  /**
   *  DSome popular time zomes to choose from
   *
   *@param  select  Description of the Parameter
   */
  public static void populateSelect(HtmlSelect select) {
    select.addItem("Pacific/Samoa", "GMT-11 Somoa");
    select.addItem("US/Hawaii", "GMT-10 Hawaii");
    select.addItem("US/Alaska", "GMT-9 Alaska");
    select.addItem("US/Pacific", "GMT-8 Pacific US");
    select.addItem("US/Mountain", "GMT-7 Mountain US");
    select.addItem("US/Arizona", "GMT-7 Arizona");
    select.addItem("US/Central", "GMT-6 Central US");
    select.addItem("Canada/Saskatchewan", "GMT-6 Saskatchewan");
    select.addItem("America/Mexico_City", "GMT-6 Mexico City");
    //select.addItem("", "GMT-6 Central America");
    select.addItem("America/New_York", "GMT-5 Eastern US");
    select.addItem("US/East-Indiana", "GMT-5 Indiana (USA)");
    select.addItem("America/Bogota", "GMT-5 Bogota, Lima");
    select.addItem("Canada/Atlantic", "GMT-4 Atlantic");
    select.addItem("America/La_Paz", "GMT-4 La Paz");
    select.addItem("America/Santiago", "GMT-4 Santiago");
    select.addItem("Canada/Newfoundland", "GMT-3 Newfoundland");
    select.addItem("America/Maceio", "GMT-3 Brasilia");
    select.addItem("America/Buenos_Aires", "GMT-3 Buenos Aires");
    select.addItem("America/Godthab", "GMT-3 Greenland");
    select.addItem("Atlantic/South_Georgia", "GMT-2 Mid-Atlantic");
    select.addItem("Atlantic/Azores", "GMT-1 Azores");
    select.addItem("Atlantic/Cape_Verde", "GMT-1 Cape Verde");
    select.addItem("Europe/Dublin", "GMT Dublin");
    select.addItem("Europe/London", "GMT London");
    select.addItem("Africa/Casablanca", "GMT Casablanca");
    select.addItem("Europe/Prague", "GMT+1 Prague, Budapest");
    select.addItem("Europe/Warsaw", "GMT+1 Warsaw, Sofija");
    select.addItem("Europe/Madrid", "GMT+1 Paris, Madrid");
    select.addItem("Europe/Berlin", "GMT+1 Berlin, Rome");
    select.addItem("Africa/Libreville", "GMT+1 Western Africa");
    select.addItem("Africa/Gaborone", "GMT+2 Central Africa");
    select.addItem("Europe/Bucharest", "GMT+2 Bucharest");
    select.addItem("Africa/Cairo", "GMT+2 Cairo");
    select.addItem("Europe/Helsinki", "GMT+2 Helsinki, Riga");
    select.addItem("Europe/Athens", "GMT+2 Athens, Istanbul");
    select.addItem("Asia/Jerusalem", "GMT+2 Jerusalem");
    //select.addItem("", "GMT+2 Pretoria");
    select.addItem("Europe/Moscow", "GMT+3 Moscow");
    select.addItem("Asia/Kuwait", "GMT+3 Riyadh, Kuwait");
    select.addItem("Africa/Nairobi", "GMT+3 Nairobi");
    select.addItem("Asia/Baghdad", "GMT+3 Baghdad");
    select.addItem("Asia/Tehran", "GMT+3:30 Tehran");
    //select.addItem("", "GMT+4 Abu Dhabi");
    select.addItem("Asia/Baku", "GMT+4 Baku, Tbilisi");
    select.addItem("Asia/Kabul", "GMT+4:30 Kabul");
    select.addItem("Asia/Yekaterinburg", "GMT+5 Yekaterinburg");
    select.addItem("Asia/Karachi", "GMT+5 Karachi");
    select.addItem("Asia/Calcutta", "GMT+5:30 Calcutta");
    select.addItem("Asia/Katmandu", "GMT+5:45 Katmandu");
    select.addItem("Asia/Dhaka", "GMT+6 Astana, Dhaka");
    select.addItem("Asia/Colombo", "GMT+6 Colombo");
    select.addItem("Asia/Almaty", "GMT+6 Almaty");
    select.addItem("Asia/Rangoon", "GMT+6:30 Rangoon");
    select.addItem("Asia/Bangkok", "GMT+7 Bangkok, Hanoi");
    select.addItem("Asia/Krasnoyarsk", "GMT+7 Krasnoyarsk");
    select.addItem("Asia/Hong_Kong", "GMT+8 Hong Kong");
    select.addItem("Asia/Singapore", "GMT+8 Malaysia, Singapore");
    select.addItem("Asia/Taipei", "GMT+8 Taipei");
    select.addItem("Australia/Perth", "GMT+8 Perth");
    select.addItem("Asia/Ulaanbaatar", "GMT+8 Ulaanbataar");
    select.addItem("Asia/Seoul", "GMT+9 Seoul");
    select.addItem("Asia/Tokyo", "GMT+9 Tokyo, Osaka");
    select.addItem("Asia/Yakutsk", "GMT+9 Yakutsk");
    select.addItem("Australia/Darwin", "GMT+9:30 Darwin");
    select.addItem("Australia/Adelaide", "GMT+9:30 Adelaide");
    select.addItem("Australia/Sydney", "GMT+10 Sydney");
    select.addItem("Australia/Brisbane", "GMT+10 Brisbane");
    select.addItem("Australia/Hobart", "GMT+10 Hobart");
    select.addItem("Asia/Vladivostok", "GMT+10 Vladivostok");
    select.addItem("Pacific/Guam", "GMT+10 Guam");
    select.addItem("Asia/Magadan", "GMT+11 Magadan");
    select.addItem("Pacific/Fiji", "GMT+12 Fiji Islands");
    select.addItem("Pacific/Auckland", "GMT+12 New Zealand");
    select.addItem("Pacific/Tongatapu", "GMT+13 Tongatapu");
    select.addItem("Pacific/Kiritimati", "GMT+14 Kiritimati");
  }
}

