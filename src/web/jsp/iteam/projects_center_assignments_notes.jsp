<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="project" class="com.zeroio.iteam.base.Project" scope="request"/>
<jsp:useBean id="assignment" class="com.zeroio.iteam.base.Assignment" scope="request"/>
<jsp:useBean id="assignmentNoteList" class="com.zeroio.iteam.base.AssignmentNoteList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<table cellpadding="0" cellspacing="0" width="100%" border="0">
  <tr>
    <td>
      The following notes have been entered for this activity:
    </td>
  </tr>
</table>
<br />

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="100%">Note</th>
    <th nowrap>Date</th>
    <th nowrap>Entered By</th>
  </tr>
<%
  if (assignmentNoteList.size() == 0) {
%>
  <tr class="row2">
    <td colspan="3">No notes to display.</td>
  </tr>
<%
  }
  int rowid = 0;
  Iterator i = assignmentNoteList.iterator();
  while (i.hasNext()) {
    rowid = (rowid != 1?1:2);
    AssignmentNote thisNote = (AssignmentNote) i.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top"><%= toHtml(thisNote.getDescription()) %></td>
    <td valign="top" nowrap><zeroio:tz timestamp="<%= thisNote.getEntered() %>" default="&nbsp;" /></td>
    <td valign="top"><dhv:username id="<%= thisNote.getUserId() %>"/></td>
  </tr>
<%
  }
%>
</table>
<dhv:evaluate if="<%= isPopup(request) %>">
<br />
<input type="button" value="Close" onClick="javascript:window.close()">
</dhv:evaluate>
