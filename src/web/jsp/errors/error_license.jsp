<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description:
  --%>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color="red"><dhv:label name="errors.requestedActionNotCompleted.text">The requested action could not be completed</dhv:label></font>
<hr color="#BFBFBB" noshade>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td><dhv:label name="admin.installedLicenseLimits.text" param='<%= "appsize="+APP_SIZE %>'>The installed license limits this system to <%= APP_SIZE %> active users.</dhv:label>
    <ul>
      <dhv:label name="errors.toAddNewUser.points" param='<%= "li=<li>|sli=</li>|urlStart=<a href=\"http://www.centriccrm.com\" target=\"_blank\">|urlEnd=</a>" %>'><li>Either disable a user that is not using the system so an additional user can be added, or</li><li>Purchase a license that increases the maximum number of users</li><li>For more information see <a href="http://www.centriccrm.com" target="_blank">www.centriccrm.com</a></li></dhv:label>
    </ul>
  </td></tr>
</table>
