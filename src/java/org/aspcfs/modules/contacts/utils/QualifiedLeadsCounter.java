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

package org.aspcfs.modules.contacts.utils;

/**
 *  A generic class GenericQualifiedLeadsCounter.
 *
 * @author Aliaksei.Yarotski
 * @created Oct 11, 2006
 */

import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class QualifiedLeadsCounter {

  private String QUERY =
      "select ac.user_id, ac.manager_id, co.count_of_conversion,  co.conversion_date" +
          " FROM access ac," +
          " (select  count('x') as count_of_conversion, " +
          "  ## as conversion_date," +
          "  owner  " +
          "  from contact " +
          " where conversion_date > ?" +
          " group by owner, ##) co" +
          " where owner = user_id";

  private HashMap rawData;
  private HashMap result;


  private QualifiedLeadsCounter(Connection db) throws SQLException {
    rawData = new HashMap();
    result = new HashMap();

    Calendar startDate = Calendar.getInstance();
    startDate.add(Calendar.WEEK_OF_MONTH, -8);
    String truncSQL = DatabaseUtils.getTruncDateDialect("conversion_date", DatabaseUtils.DAY, DatabaseUtils.getType(db));
    QUERY = QUERY.replaceAll("##", truncSQL);
    QUERY = QUERY.replaceAll(" access ", " " + DatabaseUtils.addQuotes(db, "access") + " ");
    PreparedStatement ps = db.prepareStatement(QUERY);
    ps.setDate(1, new Date(startDate.getTimeInMillis()));
    int rowNum = populateRawData(ps.executeQuery());
    ps.close();
    buildHierarchy(rowNum, -1, null);
  }

  private double buildHierarchy(int size, int parentItemId, Date conversionDate) throws SQLException {
    QualifiedLeadsCount qlc;
    double count = 0;
    double cCount = 0;
    Integer key;
    ArrayList itemList = null;
    for (int i = 0; i <= size; i++) {
      key = new Integer(i);
      if (rawData.containsKey(key)) {
        qlc = (QualifiedLeadsCount) rawData.get(key);
        if (qlc.getManagerId() == parentItemId && (qlc.getConversionDate().equals(conversionDate) || conversionDate == null)) {
          rawData.remove(key);
          cCount = this.buildHierarchy(size, qlc.getUserId(), qlc.getConversionDate());
          qlc.setCountOfConversion(qlc.getCountOfConversion() + cCount);
          key = new Integer(qlc.getUserId());
          if (qlc.getCountOfConversion() > 0) {
            if (result.containsKey(key)) {
              itemList = (ArrayList) result.get(key);
            } else {
              itemList = new ArrayList();
            }
            itemList.add(qlc);
            result.put(key, itemList);
          }
          count = count + qlc.getCountOfConversion();
        }
      }
    }
    return count;
  }


  private int populateRawData(ResultSet rs) throws SQLException {
    int rowNum = 0;
    rawData = new HashMap();
    QualifiedLeadsCount qualifiedLeadsCounter;
    while (rs.next()) {
      qualifiedLeadsCounter = new QualifiedLeadsCount();
      qualifiedLeadsCounter.setUserId(rs.getInt("user_id"));
      qualifiedLeadsCounter.setManagerId(DatabaseUtils.getInt(rs, "manager_id", -1));
      qualifiedLeadsCounter.setConversionDate(rs.getDate("conversion_date"));
      qualifiedLeadsCounter.setCountOfConversion(rs.getDouble("count_of_conversion"));
      rawData.put(new Integer(rowNum++), qualifiedLeadsCounter);
    }
    rs.close();
    return rowNum;
  }

  public List getQualifiedLeadsCounter(int userId) {
    Integer id = new Integer(userId);
    if (result.containsKey(id))
      return (List) result.get(id);
    return new ArrayList();

  }

  public static QualifiedLeadsCounter getQualifiedLeadsCounter(Connection db) throws SQLException {
    return new QualifiedLeadsCounter(db);
  }
}
