<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.accounts.base.Organization" scope="request"/>
<jsp:useBean id="InventoryList" class="org.aspcfs.modules.media.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideAccountInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<a href="Accounts.do">Account Management</a> > 
<a href="Accounts.do?command=View">View Accounts</a> >
<a href="Accounts.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>">Account Details</a> >
Vehicle Inventory List<br>
<hr color="#BFBFBB" noshade>
<%@ include file="../../accounts/accounts_details_header_include.jsp" %>
<% String param1 = "orgId=" + OrgDetails.getOrgId(); %>      
<dhv:container name="accounts" selected="vehicles" param="<%= param1 %>" style="tabs"/>
<table cellpadding="4" cellspacing="0" border="0" width="100%">
  <tr>
    <td class="containerBack">
<dhv:permission name="accounts-autoguide-inventory-add"><a href="AccountsAutoGuide.do?command=AccountAdd&orgId=<%= OrgDetails.getOrgId() %>">Add a Vehicle</a></dhv:permission>
<dhv:permission name="accounts-autoguide-inventory-add" none="true"><br></dhv:permission>
<center><%= showError(request, "actionError") %></center>
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
    </form>
  </tr>
</table>

<table cellpadding="10" cellspacing="0" border="0" width="100%">
<%
  int rowcount = 0;
  int count = 0; 
	Iterator i = InventoryList.iterator();
	if (i.hasNext()) {
    while (i.hasNext()) {
      Inventory thisItem = (Inventory)i.next();
      ++count;
      if ((count+2)%3 == 0) {
        ++rowcount;
      }
%>
<dhv:evaluate exp="<%= (count+2)%3 == 0 %>">  
  <tr>
</dhv:evaluate>
    <td class="PhotoList<%= (rowcount == 1?"":"AdditionalRow") %>">
      <span>
        <a href="AccountsAutoGuide.do?command=Details&orgId=<%= OrgDetails.getOrgId() %>&id=<%= thisItem.getId()%>"><img src="<%= (thisItem.hasPictureId()?"AutoGuide.do?command=ShowImage&id=" + thisItem.getId() + "&fid=" + thisItem.getPictureId():"images/vehicle_unavailable.gif") %>" border="0"/></a><br>
        <a href="javascript:popURLReturn('AutoGuide.do?command=UploadForm&id=<%= thisItem.getId() %>&orgId=<%= OrgDetails.getOrgId() %>&popup=true', 'AccountsAutoGuide.do?command=AccountList&orgId=<%= OrgDetails.getOrgId() %>','Photo_Upload','500','300','no','no');">Upload Photo</a><br>
        &nbsp;<br>
<dhv:evaluate exp="<%= hasText(thisItem.getStockNo()) %>">
        #<%= toHtml(thisItem.getStockNo()) %><br>
</dhv:evaluate>
        <%= thisItem.getVehicle().getYear() %>
				<%= toHtml(thisItem.getVehicle().getMake().getName()) %>
				<%= toHtml(thisItem.getVehicle().getModel().getName()) %>
<dhv:evaluate exp="<%= hasText(thisItem.getStyle()) %>">
        <%= toHtml(thisItem.getStyle()) %>
</dhv:evaluate>
<dhv:evaluate exp="<%= (thisItem.getSellingPrice() > 0) %>">
        <br><%= thisItem.getSellingPriceString() %>
</dhv:evaluate>
      </span>
    </td>
<dhv:evaluate exp="<%= count%3 == 0 %>">
  </tr>
</dhv:evaluate>
<%
    }
%>    
<dhv:evaluate exp="<%= count%3 != 0 %>">  
  </tr>
</dhv:evaluate>
<%  
  } else {%>  
  <tr>
    <td class="PhotoList" valign="center" colspan="7">
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
