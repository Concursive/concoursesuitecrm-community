/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt.rajkowski@teamelements.com
 *  http://www.teamelements.com
 *  This source code cannot be modified, distributed or used without
 *  permission from Matt Rajkowski and Team Elements
 */
package org.aspcfs.modules.troubletickets.base;

import com.zeroio.iteam.base.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import java.sql.*;
import java.io.IOException;
import org.aspcfs.modules.troubletickets.base.Ticket;
import com.zeroio.utils.ContentUtils;

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
  public static void add(IndexWriter writer, Connection db) throws SQLException, IOException {
    int count = 0;
    PreparedStatement pst = db.prepareStatement(
        "SELECT ticketid, project_id, problem, solution, location, cause, modified " +
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
      // add the document
      TicketIndexer.add(writer, ticket, false);
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
    document.add(Field.Keyword("projectId", String.valueOf(ticket.getProjectId())));
    document.add(Field.Text("title", ticket.getProblem().length() > 150 ? ticket.getProblem().substring(0, 150) : ticket.getProblem()));
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

