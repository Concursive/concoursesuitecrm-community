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
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="InventoryList" class="org.aspcfs.modules.media.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideDirectoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="statusFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="MakeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<link rel="stylesheet" href="css/photolist.css" type="text/css">
<center><%= AutoGuideDirectoryInfo.getAlphabeticalPageLinks() %><br>
<dhv:formMessage /></center>

<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="AutoGuide.do?command=List">
    <td align="left" nowrap>
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
<dhv:pagedListControl object="AutoGuideDirectoryInfo" tdClass="row1"/>

