<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="com.darkhorseventures.cfsbase.Organization" scope="request"/>
<jsp:useBean id="InventoryList" class="com.darkhorseventures.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideAccountInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<form name="listView" method="post" action="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>">
<a href="Accounts.do?command=View">Back to Account List</a><br>&nbsp;
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="containerHeader">
    <td>
      <strong><%= toHtml(OrgDetails.getName()) %></strong>
    </td>
  </tr>
  <tr class="containerMenu">
    <td>
      <% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
      <dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" />
    </td>
  </tr>
  <tr>
    <td class="containerBack">
<dhv:permission name="autoguide-inventory-add"><a href="AccountsAutoGuide.do?command=AccountAdd&orgId=<%= OrgDetails.getOrgId() %>">Add a Vehicle</a></dhv:permission>
<dhv:permission name="autoguide-inventory-add" none="true"><br></dhv:permission>
<center><%= AutoGuideAccountInfo.getAlphabeticalPageLinks() %></center>
<table width="100%" border="0">
  <tr>
    <td align="left">
      View: <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AutoGuideAccountInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideAccountInfo.getOptionValue("slides") %>>Ad View</option>
      </select>
      <%= showAttribute(request, "actionError") %>
    </td>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" border="1" width="100%" class="pagedlist" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete">
    <td valign="center" align="left" class="title">
      <strong>Action</strong>
    </td>
    </dhv:permission>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Year</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Make</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Model</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Mileage</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Color</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Ad</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Photo</a></strong>
    </td>
    <td>
      <strong><a href="AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>&column=i.inventory_id">Sold</a></strong>
    </td>
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
      <tr>
        <dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="autoguide-inventory-edit"><a href="AccountsAutoGuide.do?command=AccountModify&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId()%>&return=list">Edit</a></dhv:permission><dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete" all="true">|</dhv:permission><dhv:permission name="autoguide-inventory-delete"><a href="javascript:confirmDelete('AccountsAutoGuide.do?command=Delete&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        <td class="row<%= rowid %>" nowrap>
          <a href="AccountsAutoGuide.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId() %>"><%= thisItem.getVehicle().getYear() %></a>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisItem.getVehicle().getMake().getName()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisItem.getVehicle().getModel().getName()) %>
        </td>
        <td class="row<%= rowid %>" align="right">
          <%= toHtml(thisItem.getMileageString()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisItem.getExteriorColor()) %>
        </td>
        <td class="row<%= rowid %>">
          <%= (thisItem.hasAdRuns()?toDateString(thisItem.getAdRuns().getNextAdRun().getRunDate()):"&nbsp;") %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= (thisItem.hasPictureId()?"yes":"&nbsp;") %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <input type="hidden" name="vehicle<%= itemCount %>id" value="<%= thisItem.getId() %>">
          <input type="checkbox" name="vehicle<%= thisItem.getId() %>sold"<%= (thisItem.getSold()?" checked":"") %>>
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="row2" valign="center" colspan="9">
      No vehicles found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= AutoGuideAccountInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= AutoGuideAccountInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= AutoGuideAccountInfo.getNumericalPageLinks() %>
</td>
</tr>
</table>
</form>
