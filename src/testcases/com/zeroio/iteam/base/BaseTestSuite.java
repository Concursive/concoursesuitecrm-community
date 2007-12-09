/*
 *  Copyright(c) 2007 Concursive Corporation (http://www.concursive.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Concursive Corporation. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. CONCURSIVE
 *  CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package com.zeroio.iteam.base;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Feb 6, 2007
 *
 */
public class BaseTestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.zeroio.iteam.base");
    //$JUnit-BEGIN$
    suite.addTestSuite(FileFolderListTest.class);
    suite.addTestSuite(FileItemListTest.class);
    suite.addTestSuite(FileItemVersionListTest.class);
    suite.addTestSuite(FileDownloadLogListTest.class);
    //$JUnit-END$
    return suite;
  }

}
