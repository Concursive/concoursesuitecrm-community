<%--
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description:
  --%>
<%@ page import="java.util.*" %>
<jsp:useBean id="entries" scope="request" class="java.util.ArrayList" />
<table width="100%">
<%
  Iterator i = entries.iterator();
  while (i.hasNext()) {
    org.jcrontab.data.CrontabEntryBean entry = (org.jcrontab.data.CrontabEntryBean)i.next();
    
%>
  <tr>
    <td>
      <%= entry.toXML() %>
    </td>
  </tr>
<%
  }
%>
</table>
