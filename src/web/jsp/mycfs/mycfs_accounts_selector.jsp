<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.modules.base.Filter" %>
<jsp:useBean id="AccountList" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="AccountListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SelectedAccounts" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="FinalAccounts" class="org.aspcfs.modules.accounts.base.OrganizationList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAccounts.js"></script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
     String source = request.getParameter("source");
%>
<%-- Navigating the contact list, not the final submit --%>
<br>
<center><%= AccountListInfo.getAlphabeticalPageLinks("setFieldSubmit","acctListView") %></center>
<form name="acctListView" method="post" action="AccountSelector.do?command=ListAccounts">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
  <table width="100%" border="0">
    <tr>
      <td>
        <select size="1" name="listView" onChange="javascript:setFieldSubmit('listFilter1','-1','acctListView');">
          <%
            Iterator filters = Filters.iterator();
            while(filters.hasNext()){
            Filter thisFilter = (Filter) filters.next();
          %>
            <option <%= AccountListInfo.getOptionValue(thisFilter.getValue()) %>><%= toHtml(thisFilter.getDisplayName()) %></option>
          <%}%>
         </select>
        <% TypeSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
        <%= TypeSelect.getHtmlSelect("listFilter1", AccountListInfo.getFilterKey("listFilter1")) %>
      </td>
      <td>
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AccountListInfo" showHiddenParams="true" enableJScript="true"/>
      </td>
    </tr>
  </table>
  <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th align="center" width="8">
        &nbsp;
      </th>
      <th>
        <strong>Name</strong>
      </th>
      <th>
        <strong>Phone</strong>
      </th>
    </tr>
<%
	Iterator j = AccountList.iterator();
	if (j.hasNext()) {
		int rowid = 0;
		int count = 0;
    while (j.hasNext()) {
			count++;
      rowid = (rowid != 1 ? 1 : 2);
      Organization thisAcct = (Organization)j.next();
      String orgId = String.valueOf(thisAcct.getOrgId());
%>
    <tr class="row<%= rowid+(SelectedAccounts.indexOf(orgId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { 
  %>  
        <input type="checkbox" name="account<%= count %>" value="<%= thisAcct.getOrgId() %>" <%= (SelectedAccounts.indexOf(orgId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%=User.getBrowserId()%>');">
<%} else {%>
        <a href="javascript:document.acctListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= count %>','acctListView');">Add</a>
<%}%>
        <input type="hidden" name="hiddenAccountId<%= count %>" value="<%= thisAcct.getOrgId() %>">
      </td>
      <td nowrap>
          <%= toHtml(thisAcct.getName()) %>
      </td>
      <dhv:evaluate exp="<%=(thisAcct.getPrimaryContact() == null)%>">
        <td nowrap> <%= (!"".equals(thisAcct.getPhoneNumber("Main")) ? toHtml(thisAcct.getPhoneNumber("Main")) : "None") %></td> 
      </dhv:evaluate>
      <dhv:evaluate exp="<%=(thisAcct.getPrimaryContact() != null)%>">
        <td nowrap> <%= (!"".equals(thisAcct.getPrimaryContact().getPhoneNumber("Business")) ? toHtml(thisAcct.getPrimaryContact().getPhoneNumber("Business")) : "None") %></td> 
      </dhv:evaluate>
    </tr>
<%
    }
  } else {
%>
    <tr>
      <td class="containerBody" colspan="4">
        No accounts matched query.
      </td>
    </tr>
<%}%>
    <input type="hidden" name="finalsubmit" value="false">
    <input type="hidden" name="rowcount" value="0">
    <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>">
    <input type="hidden" name="listType" value="<%= toHtmlValue(request.getParameter("listType")) %>">
    <input type="hidden" name="showMyCompany" value="<%= toHtmlValue(request.getParameter("showMyCompany")) %>">
  </table>
<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','acctListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'account','acctListView','<%=User.getBrowserId()%>');">Check All</a>
  <a href="javascript:SetChecked(0,'account','acctListView','<%=User.getBrowserId()%>');">Clear All</a>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close()">
<%}%>
</form>
<%} else { %>
<%-- The final submit --%>
  <body OnLoad="javascript:setParentList(acctIds, acctNames, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>','<%= User.getBrowserId() %>');window.close()">
  <script>acctIds = new Array();acctNames = new Array();</script>
<%
  Iterator i = FinalAccounts.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    Organization thisOrg = (Organization) i.next();
%>
  <script>
    acctIds[<%= count %>] = "<%= thisOrg.getOrgId() %>";
    acctNames[<%= count %>] = "<%= thisOrg.getName() %>";
  </script>
<%	
  }
%>
  </body>
<%
      session.removeAttribute("SelectedAccounts");
  }
%>


