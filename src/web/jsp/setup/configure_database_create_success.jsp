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
<jsp:useBean id="database" class="org.aspcfs.modules.setup.beans.DatabaseBean" scope="request"/>
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupUser.do?command=ConfigureUserCheck" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Centric CRM Configuration (Step 3 of 4)<br>
      Database Settings &amp; Installation
    </th>
  </tr>
  <tr>
    <td>
      Database Creation Successful!<br>
      <br>
      Centric CRM now has all of the necessary database tables as well
      as initial data installed.<br>
      <br>
      The final step is to create an administrative user login.<br>
      <br>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
