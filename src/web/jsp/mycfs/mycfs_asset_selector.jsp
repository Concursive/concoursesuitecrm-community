<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>
<jsp:useBean id="assetList" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
<jsp:useBean id="finalAssets" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
<jsp:useBean id="AssetListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="selectedAssets" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
     String source = request.getParameter("source");
%>
<%-- Navigating the contact list --%>
<br>
<dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AssetListInfo" showHiddenParams="true" enableJScript="true" />
<br>

<form name="assetListView" method="post" action="AssetSelector.do?command=ListAssets">
  <input type="hidden" name="letter">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th>
      <strong>Action</strong>
    </th>
    <th width="15%">
      <strong>Serial Number</strong>
    </th>
    <th width="15%">
      <strong>Service Contract</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Manufacturer</strong>
    </th>
    <th width="15%">
      <strong>Model Version</strong>
    </th>
    <th width="25%">
      <strong>Category</strong>
    </th>
    <th width="15%" nowrap>
      <strong>Status</strong>
    </th>
  </tr>
  
  <% 
    Iterator itr = assetList.iterator();
    if (itr.hasNext()){
      int rowid = 0;
      int i = 0;
      while (itr.hasNext()){
        i++;
        rowid = (rowid != 1 ? 1 : 2);
        Asset thisAsset = (Asset)itr.next();
        String assetId = String.valueOf(thisAsset.getId());
    %>      
    <tr class="row<%= rowid+(selectedAssets.indexOf(assetId) != -1 ? "hl" : "") %>">
      <td align="center" nowrap width="8">
<% 
  if ("list".equals(request.getParameter("listType"))) { 
  %>  
        <input type="checkbox" name="asset<%= i %>" value="<%= assetId %>" <%= (selectedAssets.indexOf(assetId) != -1 ? " checked" : "") %> onClick="highlight(this,'<%=User.getBrowserId()%>');">
<%} else {%>
        <a href="javascript:document.assetListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','assetListView');">Add</a>
<%}%>
        <input type="hidden" name="hiddenAssetId<%= i %>" value="<%= assetId %>" />
      </td>
      
      <td width="15%">
        <%= toHtml(thisAsset.getSerialNumber()) %>
      </td>
      <td width="15%" valign="center">
        <%= toHtml(thisAsset.getServiceContractNumber()) %>
      </td>
      <td width="15%" valign="center" nowrap>
        <%=toHtml(thisAsset.getManufacturer())%>
      </td>
      <td width="15%">
        <%= toHtml(thisAsset.getModelVersion()) %>
      </td>
      <td width="25%">
        <dhv:evaluate if="<%= thisAsset.getLevel1() > 0 %>"><%= toHtml(categoryList1.getSelectedValue(thisAsset.getLevel1())) %></dhv:evaluate><dhv:evaluate if="<%= thisAsset.getLevel2() > 0 %>">,
        <%= toHtml(categoryList2.getSelectedValue(thisAsset.getLevel2())) %></dhv:evaluate><dhv:evaluate if="<%= thisAsset.getLevel3() > 0 %>">,
        <%= toHtml(categoryList3.getSelectedValue(thisAsset.getLevel3())) %></dhv:evaluate>
        &nbsp;
      </td>
      <td width="15%" nowrap>
        <dhv:evaluate if="<%= thisAsset.getStatus() > 0 %>">
          <%= toHtml(assetStatusList.getSelectedValue(thisAsset.getStatus())) %>
        </dhv:evaluate>&nbsp;      
      </td>
    </tr>
    <%  
      }
    }else{
    %>
    <tr>
      <td colspan="7">
        No assets found.
      </td>
    <tr>
  <%}%>
  <input type="hidden" name="finalsubmit" value="false" />
  <input type="hidden" name="listType" value="single" />
  <input type="hidden" name="rowcount" value="0">
  <input type="hidden" name="hiddenFieldId1" value="<%= toHtmlValue(request.getParameter("hiddenFieldId1")) %>" />
  <input type="hidden" name="displayFieldId1" value="<%= toHtmlValue(request.getParameter("displayFieldId1")) %>">
  <input type="hidden" name="hiddenFieldId2" value="<%= toHtmlValue(request.getParameter("hiddenFieldId2")) %>" />
  <input type="hidden" name="displayFieldId2" value="<%= toHtmlValue(request.getParameter("displayFieldId2")) %>">
  <input type="hidden" name="orgId" value="<%=request.getParameter("orgId")%>" />
  <input type="hidden" name="contractId" value="<%=request.getParameter("contractId")%>" />
  </table>
  <br>
 <dhv:pagedListControl object="AssetListInfo" /> 

<% if("list".equals(request.getParameter("listType"))){ %>
  <input type="button" value="Done" onClick="javascript:setFieldSubmit('finalsubmit','true','assetListView');">
  <input type="button" value="Cancel" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'asset','assetListView','<%=User.getBrowserId()%>');">Check All</a>
  <a href="javascript:SetChecked(0,'asset','assetListView','<%=User.getBrowserId()%>');">Clear All</a>
<%}else{%>
  <input type="button" value="Cancel" onClick="javascript:window.close()">
<%}%>
</form>

<%} else { %>
<%-- The final submit --%>
  <body OnLoad="javascript:setAssetList(assetIds, assetSerialNumbers, scIds, scNumbers,'<%= request.getParameter("listType") %>','<%= request.getParameter("displayFieldId1") %>','<%= request.getParameter("hiddenFieldId1") %>','<%= request.getParameter("displayFieldId2") %>','<%= request.getParameter("hiddenFieldId2") %>');window.close()">
  <script>
    assetIds = new Array();assetSerialNumbers = new Array();
    scIds = new Array();scNumbers = new Array();
  </script>
  <%
  Iterator i = finalAssets.iterator();
  int count = -1;
  while (i.hasNext()) {
    count++;
    Asset thisAsset = (Asset) i.next();
%>
    <script>
      assetIds[<%= count %>] = "<%= thisAsset.getId() %>";
      assetSerialNumbers[<%= count %>] = "<%= toJavaScript(thisAsset.getSerialNumber()) %>";
      
      scIds[<%= count %>] = "<%= thisAsset.getContractId() %>";
      scNumbers[<%= count %>] = "<%= toJavaScript(thisAsset.getServiceContractNumber()) %>";
  </script>
<%	
  }
%>
  </body>
  
<%	
  }
%>

