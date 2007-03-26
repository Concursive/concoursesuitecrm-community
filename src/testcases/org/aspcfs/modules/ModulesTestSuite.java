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
package org.aspcfs.modules;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ModulesTestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for org.aspcfs.modules");
    //$JUnit-BEGIN$
    suite.addTest(org.aspcfs.modules.base.BaseTestSuite.suite());
    suite.addTest(org.aspcfs.modules.actionplans.ActionplansTestSuite.suite());
    suite.addTest(org.aspcfs.modules.contacts.ContactsTestSuite.suite());
    suite.addTest(org.aspcfs.modules.pipeline.PipelineTestSuite.suite());
    suite.addTest(org.aspcfs.modules.products.ProductsTestSuite.suite());
    suite.addTest(org.aspcfs.modules.relationships.RelationshipsTestSuite.suite());
    suite.addTest(org.aspcfs.modules.service.ServiceTestSuite.suite());
    suite.addTest(org.aspcfs.modules.troubletickets.TroubleticketsTestSuite.suite());
    //$JUnit-END$
    return suite;
  }

}
