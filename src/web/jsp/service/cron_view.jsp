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
