/*
 *  Copyright(c) 2006 Concursive Corporation (http://www.concursive.com/) All
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
package org.aspcfs.modules.sync.actions;

import org.aspcfs.modules.actions.CFSModule;

import com.darkhorseventures.framework.actions.ActionContext;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Dec 11, 2006
 *
 */
public class Sync extends CFSModule {

  /* (non-Javadoc)
   * @see org.aspcfs.modules.actions.CFSModule#executeCommandDefault(com.darkhorseventures.framework.actions.ActionContext)
   */
  public String executeCommandDefault(ActionContext context) {
    return "DefaultOK";
  }

  /**
   * Description of the Method
   *
   * @param context
   * @return Description of the Returned Value
   */
  public String executeCommandHistory(ActionContext context) {
    return "HistoryOK";
  }

  /**
   * Description of the Method
   *
   * @param context
   * @return Description of the Returned Value
   */
  public String executeCommandUpload(ActionContext context) {
    return "UploadOK";
  }

  /**
   * Description of the Method
   *
   * @param context
   * @return Description of the Returned Value
   */
  public String executeCommandDownload(ActionContext context) {
    return "DownloadOK";
  }
}
