/*
 *  Copyright 2000-2004 Matt Rajkowski
 *  matt@zeroio.com
 *  http://www.mavininteractive.com
 *  This class cannot be modified, distributed or used without
 *  permission from Matt Rajkowski
 */
package com.zeroio.iteam.base;

import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    May 27, 2004
 *@version    $Id$
 */
public class SearchResults extends ArrayList {

  /**
   *  Constructor for the SearchResults object
   *
   *@param  hits           Description of the Parameter
   *@exception  Exception  Description of the Exception
   */
  public SearchResults(Hits hits) throws Exception {
    for (int i = 0; i < hits.length(); i++) {
      Document doc = hits.doc(i);
      this.add(doc);
    }
  }

}

