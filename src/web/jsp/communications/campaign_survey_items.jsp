<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemList" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<br>
View Items<br>
<hr color="#BFBFBB" noshade>
<br>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="3">
      <strong>Survey Item Results</strong>
    </td>     
  </tr>
  <tr class="title">
    <td width="24" align="right" nowrap>&nbsp;&nbsp;Item</td>
    <td>Text</td>
    <td width="30" nowrap>Total Response.</td>
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
