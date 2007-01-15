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
  - Version: $Id: $
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.admin.base.*,com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.utils.CurrencyFormat" %>
<%@ page import="java.util.*,java.text.*,org.aspcfs.modules.website.base.*,org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="parentCategory" class="org.aspcfs.modules.website.base.PortfolioCategory" scope="request"/>
<jsp:useBean id="categoryList" class="org.aspcfs.modules.website.base.PortfolioCategoryList" scope="request"/>
<jsp:useBean id="portfolioCategoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></SCRIPT>
<script language="javascript">
  function submitPage(headerId, displayValue, hiddenFieldId, displayFieldId) {
    opener.setParentHiddenField(hiddenFieldId, headerId);
    opener.changeDivContent(displayFieldId, displayValue);
    self.close();
  }
</script>
&nbsp;<br />
<%-- Category Trails --%>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
  <tr>
    <td>
      <dhv:productCategoryHierarchy link='<%= "PortfolioEditor.do?command=PopupSingleSelector&listType=single&flushtemplist=true&displayFieldId="+ (String) request.getAttribute("displayFieldId") + "&hiddenFieldId="+ (String) request.getAttribute("hiddenFieldId") %>' />
    </td>
  </tr>
</table>
&nbsp;<br />
<%-- End Category Trails --%>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="portfolioCategoryInfo"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th colspan="3" width="100%">
      <strong><dhv:label name="">Portfolio Category List</dhv:label></strong>
    </th>
  </tr>
  <tr class="containerBack">
    <td norwap>&nbsp;</td>
    <td width="100%">
      <strong><dhv:label name="">Category Name</dhv:label></strong>
    </td>
    <td nowrap>
      <strong><dhv:label name="">Enabled</dhv:label></strong>
    </td>
  </tr>
<%
	Iterator j = (Iterator) categoryList.iterator();
	if ( categoryList.size() > 0 ) {
		int rowid = 0;
    int i = 0;
	    while (j.hasNext()) {
        i++;
		    rowid = (rowid !=1?1:2);
        PortfolioCategory category = (PortfolioCategory)j.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top" nowrap><a href="javascript:submitPage('<%= category.getId() %>', '<%= toHtml(category.getName()) %>', '<%= (String) request.getAttribute("hiddenFieldId") %>','<%= (String) request.getAttribute("displayFieldId") %>');">
      <dhv:label name="">Select</dhv:label></a>
    </td>
    <td valign="top" width="100%">
      <a href="PortfolioEditor.do?command=PopupSingleSelector&listType=single&flushtemplist=true&displayFieldId=<%= (String) request.getAttribute("displayFieldId") %>&hiddenFieldId=<%= (String) request.getAttribute("hiddenFieldId") %>&categoryId=<%= category.getId() %>"><%= toHtml(category.getName()) %></a>
    </td>
    <td valign="top" nowrap>
      <% if (category.getEnabled()) { %>
        <dhv:label name="">Yes</dhv:label>
      <%} else {%>
        <dhv:label name="">No</dhv:label>
      <%}%>
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td valign="top" colspan="3"><dhv:label name="">No categories exist in the current category</dhv:label></td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="portfolioCategoryInfo"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:self.close();"/>
</td>
</tr>
</table>
<br />

