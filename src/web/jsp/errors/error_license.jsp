<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<jsp:useBean id="APP_SIZE" class="java.lang.String" scope="application"/>
<img src="images/error.gif" border="0" align="absmiddle"/>
<font color="red">The requested action could not be completed</font>
<hr color="#BFBFBB" noshade>
<table class="note" cellspacing="0">
  <tr>
    <th><img src="images/icons/stock_about-16.gif" border="0" align="absmiddle"/></th>
    <td>The installed Dark Horse CRM license limits this system to <%= APP_SIZE %> active users.
    <ul>
      <li>Either disable a user that is not using the system so an additional user can be added, or</li>
      <li>Purchase a license that increases the maximum number of users</li>
      <li>For more information see <a href="http://www.darkhorsecrm.com" target="_blank">Dark Horse CRM.com</a></li>
    </ul>
  </td></tr>
</table>
