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
<%@ include file="../initPage.jsp" %>
<form name="configure" action="SetupDirectory.do?command=ConfigureDirectoryMake" method="post">
<table border="0" width="100%">
  <tr class="sectionTitle">
    <th>
      Dark Horse CRM Configuration (Step 1 of 4)<br>
      File Library Settings
    </th>
  </tr>
  <tr>
    <td>
      Note: The specified target directory does not exist.
      Press continue to create the directory, or cancel to
      specify a different directory.<br>
      <br>
      Directory to create:<br>
      <b><%= toHtml(request.getParameter("fileLibrary")) %></b><br>
      <br>
      <input type="hidden" name="fileLibrary" value="<%= toHtmlValue(request.getParameter("fileLibrary")) %>"/>
      <input type="button" value="< Cancel" onClick="javascript:window.location.href='SetupDirectory.do?command=ConfigureDirectoryCheck&fileLibrary=<%= toJavaScript(request.getParameter("fileLibrary")) %>'"/>
      <input type="submit" value="Continue >"/>
    </td>
  </tr>
</table>
</form>
