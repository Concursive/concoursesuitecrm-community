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
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemList" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
Item List
</td>
</tr>
</table>
<%-- End Trails --%>
<table cellpadding="4" cellspacing="0" width="100%" class="details">
  <tr>
    <th colspan="3">
      <strong>Survey Item Results</strong>
    </th>
  </tr>
  <tr>
    <th width="24" align="right" nowrap>&nbsp;&nbsp;Item</th>
    <th>Text</th>
    <th width="30" nowrap>Total Response.</th>
  </tr>
  <%
	Iterator z = ItemList.iterator();
	if ( z.hasNext() ) {
		int rowid = 0;
		int count = 0;
		while (z.hasNext()) {
			count++;		
			rowid = (rowid != 1?1:2);
      ActiveSurveyQuestionItem thisItem = (ActiveSurveyQuestionItem)z.next();
%>
  <tr>
    <td align="right" nowrap><%= count %></td>
    <td><a href="CampaignManager.do?command=ShowItemDetails&questionId=<%= request.getParameter("questionId") %>&itemId=<%= thisItem.getId() %>&popup=true"><%= toHtml(thisItem.getDescription()) %></a></td>
    <td width="24" align="center" nowrap>
      <%= toHtml(String.valueOf(thisItem.getTotalResponse())) %>
    </td>
  </tr>
<%  }
	} else{ 
 %>
    <tr>
      <td align="center" nowrap>
        No Items Found
      </td>
    </tr>
  <%}
%>
</table>
<br>
<input type="button" value="Close Window" onClick="javascript:window.close();">
