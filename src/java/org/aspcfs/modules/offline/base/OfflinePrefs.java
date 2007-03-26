/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.offline.base;

import java.util.prefs.Preferences;

/**
 * Description of the Class
 *
 * @author holub
 * @version $Id: Exp $
 * @created Dec 26, 2006
 *
 */
public class OfflinePrefs {
  private final static String OFFLINE_PREFS_PATH = "org/aspcfs/offline";  
  
  private static Preferences getOfflinePrefs(){
    return Preferences.userRoot().node(OFFLINE_PREFS_PATH);
  }

  public static int getClientId(){
    return getOfflinePrefs().getInt("sync-client.id", 0);
  }
  
  public static String getUrl(){
    return getOfflinePrefs().get("centric-crm.url", "");
  }
  
  public static String getUserName(){
    return getOfflinePrefs().get("centric-crm.user", "");
  }
  
  public static String getCode(){
    return getOfflinePrefs().get("centric-crm.code", "");
  }
}
