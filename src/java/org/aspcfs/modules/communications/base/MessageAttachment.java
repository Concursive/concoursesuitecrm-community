/*
 *  Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.communications.base;

import com.darkhorseventures.framework.beans.GenericBean;
import com.zeroio.iteam.base.FileItem;
import org.aspcfs.utils.DatabaseUtils;

import java.sql.*;

/**
 * Represents an HTML message than can be emailed, faxed, or printed. Messages
 * are intended to be used with Campaigns.
 *
 * @author Zhenya
 * @version $Id: MessageAttachment.java 17961 2006-12-19 13:56:46Z zhenya.zhidok@corratech.com $
 * @created January 26, 2007
 */
public class MessageAttachment extends GenericBean {

  private int id = -1;
  private int linkModuleId = -1;
  private int linkItemId = -1;
  private int fileItemId = -1;
  private String fileName = "";
  private int size = 0;
  private double version = 0;
  private FileItem fileItem = null;
  private boolean buildFileItem = true;


  /**
   * Constructor for the MessageAttachment object
   */
  public MessageAttachment() {
  }


  /**
   * Constructor for the MessageAttachment object
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  public MessageAttachment(ResultSet rs) throws SQLException {
    buildRecord(rs);
  }


  /**
   * Constructor for the MessageAttachment object
   *
   * @param db                  Description of Parameter
   * @param messageAttachmentId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public MessageAttachment(Connection db, String messageAttachmentId) throws SQLException {
    queryRecord(db, Integer.parseInt(messageAttachmentId));
  }


  /**
   * Constructor for the MessageAttachment object
   *
   * @param db                  Description of Parameter
   * @param messageAttachmentId Description of Parameter
   * @throws SQLException Description of Exception
   */
  public MessageAttachment(Connection db, int messageAttachmentId) throws SQLException {
    queryRecord(db, messageAttachmentId);
  }

  /**
   * Gets the id attribute of the MessageAttachment object
   *
   * @return The id value
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id attribute of the MessageAttachment object
   *
   * @param tmp The new id value
   */
  public void setId(int tmp) {
    this.id = tmp;
  }


  /**
   * Sets the id attribute of the MessageAttachment object
   *
   * @param tmp The new id value
   */
  public void setId(String tmp) {
    this.setId(Integer.parseInt(tmp));
  }


  /**
   * Gets the linkModuleId attribute of the MessageAttachment object
   *
   * @return The linkModuleId value
   */
  public int getLinkModuleId() {
    return linkModuleId;
  }

  /**
   * Sets the linkModuleId attribute of the MessageAttachment object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(int tmp) {
    this.linkModuleId = tmp;
  }


  /**
   * Sets the linkModuleId attribute of the MessageAttachment object
   *
   * @param tmp The new linkModuleId value
   */
  public void setLinkModuleId(String tmp) {
    this.setLinkModuleId(Integer.parseInt(tmp));
  }

  /**
   * Gets the linkItemId attribute of the MessageAttachment object
   *
   * @return The linkItemId value
   */
  public int getLinkItemId() {
    return linkItemId;
  }

  /**
   * Sets the linkItemId attribute of the MessageAttachment object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(int tmp) {
    this.linkItemId = tmp;
  }

  /**
   * Sets the linkItemId attribute of the MessageAttachment object
   *
   * @param tmp The new linkItemId value
   */
  public void setLinkItemId(String tmp) {
    this.setLinkItemId(Integer.parseInt(tmp));
  }

  /**
   * Gets the fileItemId attribute of the MessageAttachment object
   *
   * @return The fileItemId value
   */
  public int getFileItemId() {
    return fileItemId;
  }

  /**
   * Sets the fileItemId attribute of the MessageAttachment object
   *
   * @param tmp The new fileItemId value
   */
  public void setFileItemId(int tmp) {
    this.fileItemId = tmp;
  }

  /**
   * Sets the fileItemId attribute of the MessageAttachment object
   *
   * @param tmp The new lfileItemId value
   */
  public void setFileItemId(String tmp) {
    this.setFileItemId(Integer.parseInt(tmp));
  }

  /**
   * Gets the fileName attribute of the MessageAttachment object
   *
   * @return The fileName value
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Sets the FileName attribute of the MessageAttachment object
   *
   * @param tmp The new FileName value
   */
  public void setFileName(String tmp) {
    this.fileName = tmp;
  }

  /**
   * Gets the size attribute of the MessageAttachment object
   *
   * @return The size value
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the size attribute of the MessageAttachment object
   *
   * @param tmp The new size value
   */
  public void setSize(int tmp) {
    this.size = tmp;
  }

  /**
   * Gets the relativeSize attribute of the FileItem object
   *
   * @return The relativeSize value
   */
  public int getRelativeSize() {
    int newSize = (size / 1000);
    if (newSize == 0) {
      return 1;
    } else {
      return newSize;
    }
  }

  /**
   * Gets the version attribute of the MessageAttachment object
   *
   * @return The version value
   */
  public double getVersion() {
    return version;
  }

  /**
   * Sets the Version attribute of the MessageAttachment object
   *
   * @param tmp The new Version value
   */
  public void setVersion(double tmp) {
    this.version = tmp;
  }


  /**
   * Gets the fileItem attribute of the MessageAttachment object
   *
   * @return The fileItem value
   */
  public FileItem getFileItem() {
    return fileItem;
  }

  /**
   * Sets the fileItem attribute of the MessageAttachment object
   *
   * @param tmp The new fileItem value
   */
  public void setFileItem(FileItem tmp) {
    this.fileItem = tmp;
  }

  /**
   * Sets the buildFileItem attribute of the MessageAttachment object
   *
   * @param tmp The new buildFileItem value
   */
  public void setBuildFileItem(boolean tmp) {
    this.buildFileItem = tmp;
  }

  /**
   * Gets the fileExists attribute of the MessageAttachment object
   *
   * @return The fileExists value
   */
  public boolean getFileExists() {
    boolean result = false;
    if (fileItem != null && fileItemId > 0) {
      result = true;
    }
    return result;
  }

  /**
   * Description of the Method
   *
   * @param db                  Description of the Parameter
   * @param messageAttachmentId Description of the Parameter
   * @throws SQLException Description of the Exception
   */
  public void queryRecord(Connection db, int messageAttachmentId) throws SQLException {
    if (messageAttachmentId == -1) {
      throw new SQLException("Invalid MessageAttachment ID.");
    }
    PreparedStatement pst = db.prepareStatement(
        "SELECT mfa.* " +
            "FROM message_file_attachment mfa" +
            "WHERE mfa.id = ? ");
    pst.setInt(1, messageAttachmentId);
    ResultSet rs = pst.executeQuery();
    if (rs.next()) {
      buildRecord(rs);
    }
    rs.close();
    pst.close();
    if (id == -1) {
      throw new SQLException("MessageAttachment not found.");
    }
    if (buildFileItem) {
      buildFileItems(db, false);
    }
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean insert(Connection db) throws SQLException {

    StringBuffer sql = new StringBuffer();
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      id = DatabaseUtils.getNextSeq(db, "message_file_attachment_attachment_id_seq");
      sql.append(
          "INSERT INTO message_file_attachment " +
              "(link_module_id, link_item_id, file_item_id, filename, " +
              (id > -1 ? "attachment_id, " : "") +
              DatabaseUtils.addQuotes(db, "size") + ", " +
              DatabaseUtils.addQuotes(db, "version") + ") ");
      sql.append("VALUES (?, ?, ?, ?, " + (id > -1 ? "?, " : "") + "?, ? )");
      int i = 0;
      PreparedStatement pst = db.prepareStatement(sql.toString());
      pst.setInt(++i, this.getLinkModuleId());
      pst.setInt(++i, this.getLinkItemId());
      if (this.getFileItemId() != -1) {
        pst.setInt(++i, this.getFileItemId());
      } else {
        pst.setNull(++i, java.sql.Types.INTEGER);
      }
      pst.setString(++i, this.getFileName());
      if (id > -1) {
        pst.setInt(++i, id);
      }
      pst.setInt(++i, this.getSize());
      pst.setDouble(++i, this.getVersion());
      pst.execute();
      pst.close();
      id = DatabaseUtils.getCurrVal(db, "message_file_attachment_attachment_id_seq", id);
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      e.printStackTrace();
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public int update(Connection db) throws SQLException {
    int resultCount = -1;
    boolean commit = false;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      resultCount = this.update(db, false);
      if (commit) {
        db.commit();
      }
    } catch (Exception e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.getMessage());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return resultCount;
  }


  /**
   * Description of the Method
   *
   * @param db Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  public boolean delete(Connection db) throws SQLException {
    boolean commit = true;
    try {
      commit = db.getAutoCommit();
      if (commit) {
        db.setAutoCommit(false);
      }
      PreparedStatement pst = db.prepareStatement(
          "DELETE FROM message_file_attachment " +
              "WHERE attachment_id  = ?");
      pst.setInt(1, this.getId());
      pst.execute();
      pst.close();
      if (commit) {
        db.commit();
      }
    } catch (SQLException e) {
      if (commit) {
        db.rollback();
      }
      throw new SQLException(e.toString());
    } finally {
      if (commit) {
        db.setAutoCommit(true);
      }
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param db       Description of Parameter
   * @param override Description of Parameter
   * @return Description of the Returned Value
   * @throws SQLException Description of Exception
   */
  protected int update(Connection db, boolean override) throws SQLException {
    int resultCount = 0;
    if (this.getId() == -1) {
      throw new SQLException("MessageAttachment ID was not specified");
    }
    PreparedStatement pst = null;
    StringBuffer sql = new StringBuffer();
    sql.append(
        "UPDATE message_file_attachment " +
            "SET link_module_id=?, link_item_id = ?, file_item_id = ?, filename = ?, " +
            "size = ?, version = ? ");
    sql.append("WHERE attachment_id = ? ");
    int i = 0;
    pst = db.prepareStatement(sql.toString());
    pst.setInt(++i, this.getLinkModuleId());
    pst.setInt(++i, this.getLinkItemId());
    if (this.getFileItemId() != -1) {
      pst.setInt(++i, this.getFileItemId());
    } else {
      pst.setNull(++i, java.sql.Types.INTEGER);
    }
    pst.setString(++i, this.getFileName());
    pst.setInt(++i, this.getSize());
    pst.setDouble(++i, this.getVersion());
    pst.setInt(++i, this.getId());
    resultCount = pst.executeUpdate();
    pst.close();
    return resultCount;
  }

  /**
   * Description of the Method
   *
   * @param rs Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildRecord(ResultSet rs) throws SQLException {
    this.setId(rs.getInt("attachment_id"));
    linkModuleId = rs.getInt("link_module_id");
    linkItemId = rs.getInt("link_item_id");
    fileItemId = rs.getInt("file_item_id");
    if (rs.wasNull()) {
      fileItemId = -1;
    }
    fileName = rs.getString("filename");
    size = rs.getInt("size");
    version = rs.getDouble("version");
  }

  /**
   * Description of the Method
   *
   * @param db        Description of Parameter
   * @param resetData Description of Parameter
   * @throws SQLException Description of Exception
   */
  protected void buildFileItems(Connection db, boolean resetData) throws SQLException {
    fileItem = new FileItem(db, fileItemId);
    if (resetData) {
      fileName = fileItem.getClientFilename();
      size = fileItem.getSize();
      version = fileItem.getVersion();
    }
  }

}

