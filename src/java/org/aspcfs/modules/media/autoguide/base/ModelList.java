//Copyright 2002 Dark Horse Ventures

package com.darkhorseventures.autoguide.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.sql.*;
import com.darkhorseventures.utils.DatabaseUtils;

public class ModelList extends ArrayList {

  public ModelList() { }

  public void select(Connection db) throws SQLException {
    buildList(db);
  }
  
  public void buildList(Connection db) throws SQLException {
    PreparedStatement pst = null;
    ResultSet rs = null;
    int items = -1;
    
    StringBuffer sql = new StringBuffer(); 
    sql.append(  
      "SELECT model.model_id, model.make_id AS model_make_id, model.model_name, " +
      "model.entered, model.enteredby, " + 
      "model.modified, model.modifiedby, " +
      "make.make_id, make.make_name, " +
      "make.entered AS make_entered, make.enteredby AS make_enteredby, " +
      "make.modified AS make_modified, make.modifiedby AS make_modifiedby " +
      "FROM autoguide_model model LEFT JOIN autoguide_make make ON model.make_id = make.make_id ");
    sql.append("WHERE model.model_id > -1 ");
    createFilter(sql);
    pst = db.prepareStatement(sql.toString());
    items = prepareFilter(pst);
    rs = pst.executeQuery();
    while (rs.next()) {
      Model thisModel = new Model(rs);
      this.add(thisModel);
      thisModel.setMake(new Make(rs));
    }
    rs.close();
    pst.close();
    
    /* Iterator modelList = this.iterator();
    while (modelList.hasNext()) {
      Model thisModel = (Model)modelList.next();
      thisModel.buildResources(db);
    } */
  }
  
  private void createFilter(StringBuffer sqlFilter) {
    if (sqlFilter == null) {
      sqlFilter = new StringBuffer();
    }
  }
  
  private int prepareFilter(PreparedStatement pst) throws SQLException {
    int i = 0;
    return i;
  }
}

