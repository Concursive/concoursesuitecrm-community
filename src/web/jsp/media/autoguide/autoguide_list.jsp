<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="InventoryList" class="com.darkhorseventures.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideDirectoryInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>

<form name="listView" method="post" action="AutoGuide.do?command=List">
<dhv:permission name="autoguide-inventory-add">
<a href="AutoGuide.do?command=InsertForm">Add a Vehicle</a>
</dhv:permission>

<dhv:permission name="autoguide-inventory-add" none="true">
<br>
</dhv:permission>

<center><%= AutoGuideDirectoryInfo.getAlphabeticalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      View: <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AutoGuideDirectoryInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideDirectoryInfo.getOptionValue("slides") %>>Ad View</option>
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
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Account</a></strong>
    </td>
    <td>
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Stock No</a></strong>
    </td>
    <td>
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Year</a></strong>
    </td>
    <td>
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Make</a></strong>
    </td>
    <td>
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Model</a></strong>
    </td>
    <td>
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Photo</a></strong>
    </td>
  </tr>
<%    
	Iterator i = InventoryList.iterator();
	
	if (i.hasNext()) {
	int rowid = 0;
	
		while (i.hasNext()) {
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
          <dhv:permission name="autoguide-inventory-edit"><a href="AutoGuide.do?command=Details&id=<%= thisItem.getId()%>&action=modify&return=list">Edit</a></dhv:permission><dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete" all="true">|</dhv:permission><dhv:permission name="autoguide-inventory-delete"><a href="javascript:confirmDelete('AutoGuide.do?command=Delete&id=<%= thisItem.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
        <td class="row<%= rowid %>" nowrap>
          <a href="AutoGuide.do?command=Details&id=<%= thisItem.getId() %>"><%= toHtml(thisItem.getOrganization().getName()) %></a>
        </td>
        <td class="row<%= rowid %>">
          <%= toHtml(thisItem.getStockNo()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= thisItem.getVehicle().getYear() %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisItem.getVehicle().getMake().getName()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          <%= toHtml(thisItem.getVehicle().getModel().getName()) %>
        </td>
        <td class="row<%= rowid %>" nowrap>
          &nbsp;
        </td>
      </tr>
<%
    }
  } else {%>  
  <tr>
    <td class="row2" valign="center" colspan="7">
      No vehicles found.
    </td>
  </tr>
<%}%>
</table>
<br>
[<%= AutoGuideDirectoryInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %> <%= AutoGuideDirectoryInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>] <%= AutoGuideDirectoryInfo.getNumericalPageLinks() %>
</form>
