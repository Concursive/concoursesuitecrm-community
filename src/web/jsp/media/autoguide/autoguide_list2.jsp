<%@ taglib uri="WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,com.darkhorseventures.cfsbase.*,com.darkhorseventures.autoguide.base.*" %>
<jsp:useBean id="InventoryList" class="com.darkhorseventures.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideDirectoryInfo" class="com.darkhorseventures.webutils.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="statusFilterSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<jsp:useBean id="MakeSelect" class="com.darkhorseventures.webutils.HtmlSelect" scope="request"/>
<%@ include file="initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/confirmDelete.js"></SCRIPT>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<center><%= AutoGuideDirectoryInfo.getAlphabeticalPageLinks() %><br>&nbsp;</center>

<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="AutoGuide.do?command=List">
    <td align="left">
      Layout: <select size="1" name="listView" onChange="javascript:document.forms[0].submit();">
        <option <%= AutoGuideDirectoryInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideDirectoryInfo.getOptionValue("slides") %>>Ad View</option>
      </select>
      &nbsp;
      <% listFilterSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			View: <%= listFilterSelect.getHtml("listFilter1", AutoGuideDirectoryInfo.getFilterKey("listFilter1")) %>
      &nbsp;
      <% statusFilterSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			Status: <%= statusFilterSelect.getHtml("listFilter2", AutoGuideDirectoryInfo.getFilterKey("listFilter2")) %>
      &nbsp;
      <% MakeSelect.setJsEvent("onChange=\"javascript:document.forms[0].submit();\""); %>
			Make: <%= MakeSelect.getHtml("listFilter3", AutoGuideDirectoryInfo.getFilterKey("listFilter3")) %>
      <%= showAttribute(request, "actionError") %>
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
        <a href="AutoGuide.do?command=Details&id=<%= thisItem.getId()%>"><img src="<%= (thisItem.hasPictureId()?"AutoGuide.do?command=ShowImage&id=" + thisItem.getId() + "&fid=" + thisItem.getPictureId():"images/vehicle_unavailable.gif") %>" border="0"/></a><br>
        &nbsp;<br>
        <%= toHtml(thisItem.getOrganization().getName()) %><br>
        <%= thisItem.getVehicle().getYear() %> <%= toHtml(thisItem.getVehicle().getMake().getName()) %> <%= toHtml(thisItem.getVehicle().getModel().getName()) %>
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
<table cellpadding="0" cellspacing="0" border="0" width="100%">
  <%= AutoGuideDirectoryInfo.getListPropertiesHeader("2") %>
  <tr>
    <td valign="middle" align="center" class="row1">
      <font color="#666666">
      [<%= AutoGuideDirectoryInfo.getPreviousPageLink("<font class='underline'>Previous</font>", "Previous") %>|<%= AutoGuideDirectoryInfo.getNextPageLink("<font class='underline'>Next</font>", "Next") %>]
      Page <%= AutoGuideDirectoryInfo.getNumericalPageEntry() %>
      of <%= AutoGuideDirectoryInfo.getNumberOfPages() %>,
      Items per page: <%= AutoGuideDirectoryInfo.getItemsPerPageEntry() %>
      <input type="submit" value="go">
      </font>
    </td>
  </tr>
  <%= AutoGuideDirectoryInfo.getListPropertiesFooter() %>
</table>
