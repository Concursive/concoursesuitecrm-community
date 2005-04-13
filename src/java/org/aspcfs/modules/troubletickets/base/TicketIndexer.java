/*
 *  Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Team Elements LLC. Permission to use, copy, and modify this
 *  material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. TEAM
 *  ELEMENTS MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR ANY
 *  DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.troubletickets.base;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.iteam.base.Indexer;
import com.zeroio.utils.ContentUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.aspcfs.utils.DatabaseUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  Class for working with the Lucene search engine
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class TicketIndexer implements Indexer {

  /**
   *  Given a database and a Lucene writer, this method will add content to the
   *  searchable index
   *
   *@param  writer            Description of the Parameter
   *@param  db                Description of the Parameter
   *@exception  SQLException  Description of the Exception
   *@exception  IOException   Description of the Exception
   */
  public static void add(IndexWriter writer, Connection db, ActionContext context) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT ticketid, project_id, problem, solution, location, cause, modified, key_count " +
        "FROM ticket t, ticketlink_project l " +
        "WHERE t.ticketid = l.ticket_id ");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
      ++count;
      // read the record
      Ticket ticket = new Ticket();
      ticket.setId(rs.getInt("ticketid"));
      ticket.setProjectId(rs.getInt("project_id"));
      ticket.setProblem(rs.getString("problem"));
      ticket.setSolution(rs.getString("solution"));
      ticket.setLocation(rs.getString("location"));
      ticket.setCause(rs.getString("cause"));
      ticket.setModified(rs.getTimestamp("modified"));
      ticket.setProjectTicketCount(rs.getInt("key_count"));
      // add the document
      TicketIndexer.add(writer, ticket, false);
      DatabaseUtils.renewConnection(context, db);
    }
    rs.close();
    pst.close();
    System.out.println("TicketIndexer-> Finished: " + count);
  }


  /**
   *  Description of the Method
   *
   *@param  writer           Description of the Parameter
   *@param  ticket           Description of the Parameter
   *@param  modified         Description of the Parameter
   *@exception  IOException  Description of the Exception
   */
  public static void add(IndexWriter writer, Ticket ticket, boolean modified) throws IOException {
    // populate the document
    Document document = new Document();
    document.add(Field.Keyword("type", "ticket"));
    document.add(Field.Keyword("ticketId", String.valueOf(ticket.getId())));
    if (ticket.getProjectTicketCount() > 0) {
      document.add(Field.Keyword("projectTicketId", String.valueOf(ticket.getProjectTicketCount())));
    }
    document.add(Field.Keyword("projectId", String.valueOf(ticket.getProjectId())));
    document.add(Field.Text("title", "#" + ticket.getProjectTicketCount() + " " + (ticket.getProblem().length() > 150 ? ContentUtils.toText(ticket.getProblem().substring(0, 150)) : ContentUtils.toText(ticket.getProblem()))));
    document.add(Field.Text("contents",
        ContentUtils.toText(ticket.getProblem()) + " " +
        ContentUtils.toText(ticket.getSolution()) + " " +
        ContentUtils.toText(ticket.getLocation()) + " " +
        ContentUtils.toText(ticket.getCause())));
    if (modified) {
      document.add(Field.Keyword("modified", String.valueOf(System.currentTimeMillis())));
    } else {
      document.add(Field.Keyword("modified", String.valueOf(ticket.getModified().getTime())));
    }
    writer.addDocument(document);
    if (System.getProperty("DEBUG") != null && modified) {
      System.out.println("TicketIndexer-> Added: " + ticket.getId());
    }
  }


  /**
   *  Gets the searchTerm attribute of the TicketIndexer class
   *
   *@param  ticket  Description of the Parameter
   *@return         The searchTerm value
   */
  public static Term getSearchTerm(Ticket ticket) {
    Term searchTerm = new Term("ticketId", String.valueOf(ticket.getId()));
    return searchTerm;
  }


  /**
   *  Gets the deleteTerm attribute of the TicketIndexer class
   *
   *@param  ticket  Description of the Parameter
   *@return         The deleteTerm value
   */
  public static Term getDeleteTerm(Ticket ticket) {
    Term searchTerm = new Term("ticketId", String.valueOf(ticket.getId()));
    return searchTerm;
  }
}

