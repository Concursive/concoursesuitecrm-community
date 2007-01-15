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
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*, org.aspcfs.utils.web.*,java.util.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat, org.aspcfs.modules.base.Filter" %>
<jsp:useBean id="assetList" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
<jsp:useBean id="finalAssets" class="org.aspcfs.modules.assets.base.AssetList" scope="request"/>
<jsp:useBean id="assetManufacturerList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AssetListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="selectedAssets" class="java.util.ArrayList" scope="session"/>
<jsp:useBean id="categoryList1" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList2" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="categoryList3" class="org.aspcfs.modules.base.CategoryList" scope="request"/>
<jsp:useBean id="assetStatusList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Filters" class="org.aspcfs.modules.base.FilterList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	function init() {
	<% 
		String serialNumber = request.getParameter("serialNumber") ;
		String contractNumber = request.getParameter("contractNumber") ;
		if (serialNumber == null || "".equals(serialNumber.trim())){
	%>
		  document.assetListView.serialNumber.value = label("label.account.sc.serialnumber", "Serial Number");
	<%}		
		if (contractNumber == null || "".equals(contractNumber.trim())){
	%>
		  document.assetListView.contractNumber.value = label("label.account.sc.number", "Service Contract Number");
	<%}%>		

	}

	function clearSearchFields(clear, field) {
		if (clear) {
			// Clear the search fields since clear button was clicked
			document.assetListView.serialNumber.value = label("label.account.sc.serialnumber", "Serial Number");
			document.assetListView.contractNumber.value = label("label.account.sc.number", "Service Contract Number");
		} else {
			// The search fields recieved focus
			if (field.value == label("label.account.sc.serialnumber", "Serial Number")) {
				field.value = "" ;
			}
			if (field.value == label("label.account.sc.number", "Service Contract Number")) {
				field.value = "" ;
			}
		}
	}  
</SCRIPT>  
<%
  if (!"true".equals(request.getParameter("finalsubmit"))) {
%>
<body onLoad="init()">
<form name="assetListView" method="post" action="AssetSelector.do?command=ListAssets">
	<table cellpadding="6" cellspacing="0" width="100%" border="0">
		<tr>
			<td align="center" valign="center" bgcolor="#d3d1d1">
				<strong><dhv:label name="button.search">Search</dhv:label></strong>
				<input type="text" name="serialNumber" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("serialNumber")) %>">
				<input type="text" name="contractNumber" onFocus="clearSearchFields(false, this)" value="<%= toHtmlValue(request.getParameter("contractNumber")) %>">
				<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
				<input type="button" value="<dhv:label name="accounts.accountasset_include.clear">Clear</dhv:label>" onClick="clearSearchFields(true, '')">
			</td>
		</tr>
	</table>
&nbsp;<br>
<input type="hidden" name="letter">
  <table width="100%" border="0">
    <tr>
      <td>
        <select size="1" name="listView" onChange="javascript:setFieldSubmit('listFilter1','-1','assetListView');">
          <%
            Iterator filters = Filters.iterator();
            while(filters.hasNext()){
            Filter thisFilter = (Filter) filters.next();
          %>
            <option <%= AssetListInfo.getOptionValue(thisFilter.getValue()) %>><%= toHtml(thisFilter.getDisplayName()) %></option>
          <%}%>
         </select>
      </td>
      <td>
        <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AssetListInfo" showHiddenParams="true" enableJScript="true" form="assetListView"/>
      </td>
    </tr>
  </table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      &nbsp;
    </th>
    <th width="15%">
      <strong><dhv:label name="accounts.accountasset_include.SerialNumber">Serial Number</dhv:label></strong>
    </th>
    <th width="15%">
      <strong><dhv:label name="accounts.accountasset_include.ServiceContract">Service Contract</dhv:label></strong>
    </th>
    <th width="15%" nowrap>
      <strong><dhv:label name="accounts.accountasset_include.Manufacturer">Manufacturer</dhv:label></strong>
    </th>
    <th width="15%">
      <strong><dhv:label name="calendar.modelVersion">Model Version</dhv:label></strong>
    </th>
    <th width="25%">
      <strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong>
    </th>
    <th width="15%" nowrap>
      <strong><dhv:label name="accounts.accountasset_include.Status">Status</dhv:label></strong>
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
        <a href="javascript:document.assetListView.finalsubmit.value = 'true';javascript:setFieldSubmit('rowcount','<%= i %>','assetListView');"><dhv:label name="button.add">Add</dhv:label></a>
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
        <dhv:evaluate if="<%= thisAsset.getManufacturerCode() > 0 %>">
          <%=toHtml(assetManufacturerList.getSelectedValue(thisAsset.getManufacturerCode())) %>    
        </dhv:evaluate>&nbsp;      
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
        <dhv:label name="accounts.accounts_asset_list_include.NoAssetsFound">No assets found.</dhv:label>
      </td>
    </tr>
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
  <input type="button" value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:setFieldSubmit('finalsubmit','true','assetListView');">
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
  <a href="javascript:SetChecked(1,'asset','assetListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>
  <a href="javascript:SetChecked(0,'asset','assetListView','<%=User.getBrowserId()%>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>
<%}else{%>
  <input type="button" value="<dhv:label name="global.button.cancel">Cancel</dhv:label>" onClick="javascript:window.close()">
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
