/*
 *  Copyright(c) 2005 Team Elements LLC (http://www.teamelements.com/) All
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
package com.zeroio.iteam.scheduler;

import com.zeroio.iteam.base.IndexEvent;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.StatefulJob;
import org.aspcfs.controller.ApplicationPrefs;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Vector;

/**
 * Description
 *
 * @author mrajkowski
 * @version $Id$
 * @created Jun 22, 2005
 */

public class IndexerJob implements StatefulJob {

  public void execute(JobExecutionContext context) throws JobExecutionException {
    SchedulerContext schedulerContext = null;
    try {
      if (System.getProperty("DEBUG") != null) {
        System.out.println("IndexerJob-> Indexing data...");
      }
      schedulerContext = context.getScheduler().getContext();
      ServletContext servletContext = (ServletContext) schedulerContext.get(
          "ServletContext");
      ApplicationPrefs prefs = (ApplicationPrefs) schedulerContext.get(
          "ApplicationPrefs");
      Vector eventList = (Vector) schedulerContext.get("IndexArray");
      while (eventList.size() > 0) {
        IndexEvent indexEvent = (IndexEvent) eventList.get(0);
        if (indexEvent.getAction() == IndexEvent.ADD) {
          indexAddItem(prefs, indexEvent);
        } else if (indexEvent.getAction() == IndexEvent.DELETE) {
          indexDeleteItem(prefs, indexEvent);
        }
        optimizeIndex(servletContext, prefs, indexEvent);
        eventList.remove(0);
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      throw new JobExecutionException(e.getMessage());
    }
  }

  private boolean indexAddItem(ApplicationPrefs prefs, IndexEvent event) throws IOException {
    // Delete the previous item from the index, by using a reader
    IndexReader reader = null;
    Directory index = null;
    try {
      index = getDirectory(prefs, event);
      reader = IndexReader.open(index);
      Class c = Class.forName(event.getItem().getClass().getName() + "Indexer");
      Class[] argTypes = new Class[]{event.getItem().getClass()};
      Method m = c.getDeclaredMethod("getSearchTerm", argTypes);
      Object o = m.invoke(null, new Object[]{event.getItem()});
      if (o != null) {
        reader.delete((Term) o);
      }
    } catch (Exception io) {
      if (System.getProperty("DEBUG") != null) {
        io.printStackTrace(System.out);
      }
      throw new IOException("Reader: " + io.getMessage());
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        reader = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }

    // Add the item to the index, optimize, and close
    IndexWriter writer = null;
    try {
      index = getDirectory(prefs, event, false);
      writer = new IndexWriter(index, new StandardAnalyzer(), false);
      Class c = Class.forName(event.getItem().getClass().getName() + "Indexer");
      Class[] argTypes = new Class[]{writer.getClass(), event.getItem().getClass(), boolean.class};
      Method m = c.getDeclaredMethod("add", argTypes);
      m.invoke(null, new Object[]{writer, event.getItem(), new Boolean(true)});
    } catch (Exception io) {
      throw new IOException("Writer: " + io.getMessage());
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        writer = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }
    return true;
  }

  private boolean indexDeleteItem(ApplicationPrefs prefs, IndexEvent event) throws IOException {
    // Delete the previous item from the index, by using a reader
    IndexReader reader = null;
    Directory index = null;
    try {
      index = getDirectory(prefs, event);
      reader = IndexReader.open(index);
      Class c = Class.forName(event.getItem().getClass().getName() + "Indexer");
      Class[] argTypes = new Class[]{event.getItem().getClass()};
      Method m = c.getDeclaredMethod("getDeleteTerm", argTypes);
      Object o = m.invoke(null, new Object[]{event.getItem()});
      int deleteCount = 0;
      if (o != null) {
        deleteCount = reader.delete((Term) o);
      }
      if (System.getProperty("DEBUG") != null) {
        System.out.println(
            "IndexerJob-> Deleted " + deleteCount + " terms, index: " + index);
      }
    } catch (Exception io) {
      throw new IOException(io.getMessage());
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
        reader = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }
    return true;
  }

  private boolean optimizeIndex(ServletContext context, ApplicationPrefs prefs, IndexEvent event) throws IOException {
    // Optimize the cache
    Directory index = null;
    IndexWriter writer = null;
    try {
      index = getDirectory(prefs, event, false);
      writer = new IndexWriter(index, new StandardAnalyzer(), false);
      writer.optimize();
      // Update the shared searcher
      IndexSearcher searcher = new IndexSearcher(index);
      context.setAttribute("indexSearcher", searcher);
    } catch (Exception io) {
      throw new IOException("Writer: " + io.getMessage());
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
        writer = null;
      } catch (Exception ie) {
      }
      try {
        if (index != null) {
          index.close();
        }
        index = null;
      } catch (Exception ie) {
      }
    }
    return true;
  }

  protected synchronized Directory getDirectory(ApplicationPrefs prefs, IndexEvent event) throws IOException {
    File path = new File(prefs.get("FILELIBRARY") + event.getDbName() + "index");
    boolean create = !path.exists();
    return getDirectory(prefs, event, create);
  }

  protected synchronized Directory getDirectory(ApplicationPrefs prefs, IndexEvent event, boolean create) throws IOException {
    Directory index = FSDirectory.getDirectory(
        prefs.get("FILELIBRARY") + event.getDbName() + "index", create);
    if (create) {
      IndexWriter writer = new IndexWriter(
          index, new StandardAnalyzer(), true);
      writer.optimize();
      writer.close();
    }
    return index;
  }
}
