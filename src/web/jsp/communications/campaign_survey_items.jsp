<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.communications.base.*" %>
<jsp:useBean id="ItemList" class="org.aspcfs.modules.communications.base.ActiveSurveyQuestionItemList" scope="request"/>
<%@ include file="../initPage.jsp" %>

<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td colspan="3" valign="center" align="left">
      <strong>Survey Item Results</strong>
    </td>     
  </tr>
  
  <tr class="title">
    <td width="24" align=right nowrap>&nbsp;&nbsp;Item</td>
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
			if (rowid != 1) {
				rowid = 1;
			} else {
				rowid = 2;
			}
      ActiveSurveyQuestionItem thisItem = (ActiveSurveyQuestionItem)z.next();
%>
  <tr>
    <td align=right nowrap><%=count%></td>
    <td><a href="CampaignManager.do?command=ShowItemDetails&itemId=<%= thisItem.getId() %>"><%= toHtml(thisItem.getDescription()) %></a></td>
    <td width="24" align="center" nowrap>
      <%= toHtml(thisItem.getTotalResponse() + "") %>
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
