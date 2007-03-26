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
package org.aspcfs.modules.actionplans.base;

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
    TestSuite suite = new TestSuite("Test for org.aspcfs.modules.actionplans.base");
    //$JUnit-BEGIN$
    suite.addTestSuite(ActionItemWorkListTest.class);
    suite.addTestSuite(ActionItemWorkNoteListTest.class);
    suite.addTestSuite(ActionItemWorkSelectionListTest.class);
    suite.addTestSuite(ActionPhaseListTest.class);
    suite.addTestSuite(ActionPhaseWorkListTest.class);
    suite.addTestSuite(ActionPlanCategoryListTest.class);
    suite.addTestSuite(ActionPlanListTest.class);
    suite.addTestSuite(ActionPlanWorkListTest.class);
    suite.addTestSuite(ActionPlanWorkNoteListTest.class);
    suite.addTestSuite(ActionStepListTest.class);
    suite.addTestSuite(ActionStepLookupListTest.class);
    suite.addTestSuite(PlanEditorListTest.class);
    //$JUnit-END$
    return suite;
  }

}
