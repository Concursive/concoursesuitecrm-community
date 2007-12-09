<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: error_license.jsp 18488 2007-01-15 20:12:32 +0000 (Mon, 15 Jan 2007) matt $
  - Description:
  --%>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<table cellspacing="0"><tr>
  <td valign="top"><img src="images/error.gif" border="0" align="absmiddle"/></td>
  <td>
  	<p><dhv:label name="offline.error.systemInConflictingState.text">Concourse Suite Community Edition Offline has detected the system to be in a conflicting state. Some of the required system files could be missing or corrupt.</dhv:label></p>
  	<p><dhv:label name="offline.error.systemNeedsToBeRestored.text">The system needs to be restored by reloading the offline client to continue working in offline mode.</dhv:label></p>
		<table class="note" cellspacing="0"><tr>
  		<th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
  		<td><dhv:label name="offline.error.reloadingWillResetYourEntire.text">Reloading an existing Centric Offline System will reset your entire offline database and perform a sync with the server. Any changes made while working offline after the last successful sync will be lost.</dhv:label></td>
		</tr></table>
		<p><dhv:label name="offline.error.toReloadCloseBrowser.text">To reload Centric Offline System, close the browser and click on the 'Reload' button displayed on the Centric Desktop Client Window.</dhv:label></p>
	</td>
</tr></table>
