<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="serviceContractList" class="org.aspcfs.modules.servicecontracts.base.ServiceContractList" scope="request"/>
<jsp:useBean id="finalServiceContracts" class="org.aspcfs.modules.servicecontracts.base.ServiceContractList" scope="request"/>
<jsp:useBean id="serviceContractCategorySelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="serviceContractTypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ServiceContractListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="selectedServiceContracts" class="java.util.ArrayList" scope="session"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
     String source = request.getParameter("source");
%>
<%-- Navigating the contact list --%>
<br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ServiceContractListInfo" showHiddenParams="true" enableJScript="true" />
<br>

<form name="serviceContractListView" method="post" action="ServiceContractSelector.do?command=ListServiceContracts">
<!-- Make sure that when the list selection changes previous selected entries are saved -->
  <input type="hidden" name="letter">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="20%">
      <strong>Service Contract Number</strong>
    </th>
    <th width="20%">
      <strong>Category</strong>
    </th>
    <th width="20%" nowrap>
      <strong>Type</strong>
    </th>
    <th width="20%">
      <strong>Current Contract Date</strong>
    </th>
    <th width="20%" nowrap>
      <strong>End Date</strong>
    </th>
  </tr>
  
  <% 
    Iterator itr = serviceContractList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        ServiceContract thisContract = (ServiceContract)itr.next();
        String contractId = String.valueOf(thisContract.getId());
    %>      
    <tr class="row<%= rowid+(selectedServiceContracts.indexOf(contractId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { 
  %>  
        <input type="checkbox" name="serviceContract<%= i %>" value="<%= contractId %>" <%= (selectedServiceContracts.indexOf(contractId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%=User.getBrowserId()%>');">
<%} else {%>
        <a href="javascript:document.serviceContractListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','serviceContractListView');">Add</a>
<%}%>
        <input type="hidden" name="hiddenServiceContractId<%= i %>" value="<%= contractId %>" />
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
        <dhv:tz timestamp="<%=thisContract.getCurrentStartDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
      </td>
      <td width="20%" valign="center" nowrap>
        <dhv:tz timestamp="<%=thisContract.getCurrentEndDate()%>" dateOnly="true" dateFormat="<%= DateFormat.SHORT %>" default="&nbsp;"/>
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="6">
        No service contracts found.
      </td>
    <tr>
  <%}%>
  <input type="hidden" name="finalsubmit" value="false" />
  <input type="hidden" name="listType" value="single" />
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="hiddenFieldId" value="<%= toHtmlValue(request.getParameter("hiddenFieldId")) %>" />
  <input type="hidden" name="displayFieldId" value="<%= toHtmlValue(request.getParameter("displayFieldId")) %>">
  <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>" />
  </table>
  <br>
 <dhv:pagedListControl object="ServiceContractListInfo" /> 

<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','serviceContractListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'serviceContract','serviceContractListView','<%=User.getBrowserId()%>');">Check All</a>
  <a href="javascript:SetChecked(0,'serviceContract','serviceContractListView','<%=User.getBrowserId()%>');">Clear All</a>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close()">
<%}%>
</form>

<%} else { %>
<%-- The final submit --%>
  <body OnLoad="javascript:setContractList(scIds, scNumbers, '<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId") %>','<%= request.getParameter("hiddenFieldId") %>');window.close()">
  <script>scIds = new Array();scNumbers = new Array();</script>
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

