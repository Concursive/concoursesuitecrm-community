<%--
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
   -  Version: $Id: preadsheet_view.jsp 4.1 2007-06-14 12:16:53 +0530 (Thu,14 Jun 2007) nagarajay $
  - Description:
  --%>
<jsp:useBean id="preview" class="java.lang.String" scope="request"/>
<jsp:useBean id="spreadsheet" class="java.lang.String" scope="request"/>
<jsp:useBean id="rowsColumnsCount" class="java.lang.String" scope="request"/>
<%@ page import="org.aspcfs.utils.StringUtils" %>

<br><br>
<%
    String rows = "0";
    String columns = "0";
    if (rowsColumnsCount.contains(",")) {
      String values[] = rowsColumnsCount.split(",");
      rows = values[0].replace("Rows:", "");
      columns = values[1].replace("Columns:", "");
    }
%>

<table class="details" border="1" cellpadding="4" cellspacing="0" width="100%">  
<%
  int count = 0;
  String value = null;
  String content[] = spreadsheet.split("0xfff");
 for (int row = 0; row <Integer.parseInt(rows); row++) {
 %>
<tr>
<%
    for (int col = 0; col <Integer.parseInt(columns); col++) {
      value = !"EMPTY".equals(content[count]) ? content[count] : "&nbsp;";
       if(StringUtils.isNumeric(value)) {
%>
<td align='right' width="<%=100/Integer.parseInt(columns)%>%"><%=StringUtils.parseStringForNumber(value,2,true)%></td>
<% } else { %>
<td width='<%=100/Integer.parseInt(columns)%>%'><%=value%></td>
<% }
  count++;
  }%>
</tr>
<%
  }
%>
</table>

