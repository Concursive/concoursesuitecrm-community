<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="InventoryList" class="org.aspcfs.modules.media.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideAccountInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Accounts.do">Accounts</a> > 
<a href="Accounts.do?command=Search">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>">Account Details</a> >
Vehicle Inventory List
</td>
</tr>
</table>
<%-- End Trails --%>
<%@ include file="../../accounts/accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-autoguide-inventory-add"><a href="AccountsAutoGuide.do?command=AccountAdd&orgId=<%= OrgDetails.getOrgId() %>">Add a Vehicle</a></dhv:permission>
<dhv:permission name="accounts-autoguide-inventory-add" none="true"><br></dhv:permission>
<center>&nbsp;</center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>">
    <td align="left" nowrap>
      Layout: <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AutoGuideAccountInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideAccountInfo.getOptionValue("slides") %>>Ad View</option>
      </select>
      &nbsp;
      <% listFilterSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			View: <%= listFilterSelect.getHtml("listFilter1", AutoGuideAccountInfo.getFilterKey("listFilter1")) %>
    </td>
    <td>
      <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="AutoGuideAccountInfo"/>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <dhv:permission name="accounts-autoguide-inventory-edit,accounts-autoguide-inventory-delete">
    <th valign="center">
      <strong>Action</strong>
    </th>
    </dhv:permission>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Year</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Make</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Model</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Mileage</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Color</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Next Ad</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Photo</a></strong>
    </th>
    <th>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Sold</a></strong>
    </th>
  </tr>
<%    
	Iterator i = InventoryList.iterator();
	
	if (i.hasNext()) {
    int itemCount = 0;
    int rowid = 0;
    
    while (i.hasNext()) {
      ++itemCount;
      if (rowid != 1) {
        rowid = 1;
      } else {
        rowid = 2;
      }
			
      Inventory thisItem = (Inventory)i.next();
%>
      <tr class="row<%= rowid %>">
        <dhv:permission name="accounts-autoguide-inventory-edit,accounts-autoguide-inventory-delete">
        <td width="8" valign="center" nowrap>
          <dhv:permission name="accounts-autoguide-inventory-edit"><a href="AccountsAutoGuide.do?command=AccountModify&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="accounts-autoguide-inventory-edit,accounts-autoguide-inventory-delete" all="true">|</dhv:permission><dhv:permission name="accounts-autoguide-inventory-delete"><a href="javascript:confirmDelete('AccountsAutoGuide.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        <td nowrap>
          <a href="AccountsAutoGuide.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId() %>"><%= thisItem.getVehicle().getYear() %></a>
        </td>
        <td nowrap>
          <%= toHtml(thisItem.getVehicle().getMake().getName()) %>
        </td>
        <td nowrap>
          <%= toHtml(thisItem.getVehicle().getModel().getName()) %>
					<dhv:evaluate exp="<%= hasText(thisItem.getStyle()) %>">
						<%= toHtml(thisItem.getStyle()) %>
					</dhv:evaluate>
        </td>
        <td style="text-align: right;">
          <%= toHtml(thisItem.getMileageString()) %>
        </td>
        <td>
          <%= toHtml(thisItem.getExteriorColor()) %>
        </td>
        <td nowrap>
          <%= (thisItem.hasAdRuns()?toDateString(thisItem.getAdRuns().getNextAdRun().getRunDate()):"&nbsp;") %>
        </td>
        <td nowrap>
          <%= (thisItem.hasPictureId()?"yes":"&nbsp;") %>
        </td>
        <td nowrap>
          <%= (thisItem.getSold()?"yes":"no") %>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="containerBody" valign="center" colspan="9">
      No vehicles found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="AutoGuideAccountInfo"/>
</td>
</tr>
</table>

