<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
