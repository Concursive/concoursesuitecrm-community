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
package com.zeroio.iteam.base;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;

import java.util.ArrayList;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created May 27, 2004
 */
public class SearchResults extends ArrayList {

  /**
   * Constructor for the SearchResults object
   *
   * @param hits Description of the Parameter
   * @throws Exception Description of the Exception
   */
  public SearchResults(Hits hits) throws Exception {
    for (int i = 0; i < hits.length(); i++) {
      Document doc = hits.doc(i);
      this.add(doc);
    }
  }

}

