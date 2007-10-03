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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page
    import="org.aspcfs.modules.servicecontracts.base.ServiceContract,java.util.Iterator" %>
<jsp:useBean id="serviceContractList"
             class="org.aspcfs.modules.servicecontracts.base.ServiceContractList"
             scope="request"/>
<jsp:useBean id="finalServiceContracts"
             class="org.aspcfs.modules.servicecontracts.base.ServiceContractList"
             scope="request"/>
<jsp:useBean id="serviceContractCategorySelect"
             class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeSelect"
             class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ServiceContractListInfo"
             class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
             scope="session"/>
<jsp:useBean id="selectedServiceContracts" class="java.util.ArrayList"
             scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript"
        SRC="javascript/popServiceContracts.js"></script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<%-- Navigating the contact list --%>
<br>
<dhv:pagedListStatus title='<%= showError(request, "actionError") %>'
                     object="ServiceContractListInfo" showHiddenParams="true"
                     enableJScript="true" form="serviceContractListView"/>
<br>

<form name="serviceContractListView" method="post"
      action="ServiceContractSelector.do?command=ListServiceContracts">
  <input type="hidden" name="letter">
  <table cellpadding="4" cellspacing="0" border="0" width="100%"
         class="pagedList">
    <tr>
      <th>
        &nbsp;
      </th>
      <th width="20%">
        <strong><dhv:label
            name="accounts.accountasset_include.ServiceContractNumber">Service
          Contract Number</dhv:label></strong>
      </th>
      <th width="20%">
        <strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong>
      </th>
      <th width="20%" nowrap>
        <strong><dhv:label
            name="accounts.accounts_add.Type">Type</dhv:label></strong>
      </th>
      <th width="20%">
        <strong><dhv:label name="account.sc.currentContractDate">Current
          Contract Date</dhv:label></strong>
      </th>
      <th width="20%" nowrap>
        <strong><dhv:label name="product.endDate">End Date</dhv:label></strong>
      </th>
    </tr>

    <%
      Iterator itr = serviceContractList.iterator();
      if (itr.hasNext()) {
        int rowid = 0;
        int i = 0;
        while (itr.hasNext()) {
          i++;
          rowid = (rowid != 1 ? 1 : 2);
          ServiceContract thisContract = (ServiceContract) itr.next();
          String contractId = String.valueOf(thisContract.getId());
    %>
    <tr class="row<%= rowid+(selectedServiceContracts.indexOf(contractId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
        <%
          if ("list".equals(request.getParameter("listType"))) {
        %>
        <input type="checkbox" name="serviceContract<%= i %>"
               value="<%= contractId %>" <%= (selectedServiceContracts.indexOf(contractId) != -1 ? " checked" : "") %>
               onClick="highlight(this,'<%=User.getBrowserId()%>');">
        <%} else {%>
        <a href="javascript:document.serviceContractListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','serviceContractListView');"><dhv:label
            name="button.add">Add</dhv:label></a>
        <%}%>
        <input type="hidden" name="hiddenServiceContractId<%= i %>"
               value="<%= contractId %>"/>
      </td>

      <td width="20%">
        <%=toHtml(thisContract.getServiceContractNumber())%>
      </td>
      <td width="20%" valign="center">
        <%=toHtml(serviceContractCategorySelect.getSelectedValue(thisContract.getCategory()))%>
      </td>
      <td width="20%" valign="center" nowrap>
        <%=toHtml(serviceContractTypeSelect.getSelectedValue(thisContract.getType()))%>
      </td>
      <td width="20%" valign="center">
        <zeroio:tz timestamp="<%=thisContract.getCurrentStartDate()%>"
                   dateOnly="true" default="&nbsp;"/>
      </td>
      <td width="20%" valign="center" nowrap>
        <zeroio:tz timestamp="<%=thisContract.getCurrentEndDate()%>"
                   dateOnly="true" default="&nbsp;"/>
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="6">
        <dhv:label name="account.sc.noServiceContractsFound">No service
          contracts found.</dhv:label>
      </td>
    </tr>
    <%}%>
    <input type="hidden" name="finalsubmit" value="false"/>
    <input type="hidden" name="listType" value="single"/>
    <input type="hidden" name="rowcount" value="0">
    <input type="hidden" name="hiddenFieldId"
           value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>"/>
    <input type="hidden" name="displayFieldId"
           value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
    <input type="hidden" name="orgId"
           value="<%=request.getParameter("orgId")%>"/>
  </table>
  <br>
  <dhv:pagedListControl object="ServiceContractListInfo"/>

  <% if ("list".equals(request.getParameter("listType"))) { %>
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>"
         onClick="javascript:setFieldSubmit('finalsubmit','true','serviceContractListView');">
  <input type="button"
         value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
         onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'serviceContract','serviceContractListView','<%=User.getBrowserId()%>');"><dhv:label
      name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'serviceContract','serviceContractListView','<%=User.getBrowserId()%>');"><dhv:label
      name="quotes.clearAll">Clear All</dhv:label></a>
  <%} else {%>
  <input type="button"
         value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
         onClick="javascript:window.close()">
  <%}%>
</form>

<%} else { %>
<%-- The final submit --%>
<% if (User.getUserRecord().isPortalUser()) { %>
<body
    OnLoad="javascript:setContractList2(scIds, scNumbers, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>');window.close()">
<% } else { %>
<body
    OnLoad="javascript:setContractList(scIds, scNumbers, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>');window.close()">
<% } %>
<script>scIds = new Array();
scNumbers = new Array();</script>
<%
  Iterator i = finalServiceContracts.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    ServiceContract thisContract = (ServiceContract) i.next();
%>
<script>
  scIds[<%= count %>] = "<%= thisContract.getId() %>";
  scNumbers[<%= count %>] = "<%= toJavaScript(thisContract.getServiceContractNumber()) %>";
</script>
<%
  }
%>
</body>

<%

      session.removeAttribute("selectedServiceContracts");
  }

%>

