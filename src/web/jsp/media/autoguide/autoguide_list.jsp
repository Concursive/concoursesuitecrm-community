<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="InventoryList" class="com.darkhorseventures.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideDirectoryInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>

<form name="listView" method="post" action="AutoGuide.do?command=List">
<br>
<center><%= AutoGuideDirectoryInfo.getNumericalPageLinks() %></center>

<table width="100%" border="0">
  <tr>
    <td align="left">
      Layout: <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AutoGuideDirectoryInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideDirectoryInfo.getOptionValue("slides") %>>Ad View</option>
      </select>
      &nbsp;
      <% listFilterSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			View: <%= listFilterSelect.getHtml("listFilter1", AutoGuideDirectoryInfo.getFilterKey("listFilter1")) %>
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
      <strong><a href="AutoGuide.do?command=List&column=i.inventory_id">Next Ad</a></strong>
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
        <td class="row<%= rowid %>">
          <dhv:evaluate exp="<%= thisItem.hasAdRuns() %>">
            <img border="0" src="<%= (thisItem.getAdRuns().getNextAdRun().isComplete()?"images/box-checked.gif":"images/box.gif") %>" alt="" align="absmiddle"><%= toDateString(thisItem.getAdRuns().getNextAdRun().getRunDate()) %>
            <%= toHtml(thisItem.getAdRuns().getNextAdRun().getAdTypeName().substring(0,1)) %>
            <%= (thisItem.getAdRuns().getNextAdRun().getIncludePhoto()?"/ Include Photo":"") %>
          </dhv:evaluate>
          <dhv:evaluate exp="<%= !thisItem.hasAdRuns() %>">
            &nbsp;
          </dhv:evaluate>
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
