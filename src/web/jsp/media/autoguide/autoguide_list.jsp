<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.media.autoguide.base.*" %>
<jsp:useBean id="InventoryList" class="org.aspcfs.modules.media.autoguide.base.InventoryList" scope="request"/>
<jsp:useBean id="AutoGuideDirectoryInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="listFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="statusFilterSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="MakeSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<%@ include file="../../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<center><%= AutoGuideDirectoryInfo.getAlphabeticalPageLinks() %><br>
<%= showError(request, "actionError") %></center>
<table width="100%" border="0">
  <tr>
    <form name="listView" method="post" action="AutoGuide.do?command=List">
    <td align="left" nowrap>
      Layout: <select size="1" name="listView" onChange="javascript:document.forms['listView'].submit();">
        <option <%= AutoGuideDirectoryInfo.getOptionValue("list") %>>List View</option>
        <option <%= AutoGuideDirectoryInfo.getOptionValue("slides") %>>Ad View</option>
      </select>
      <% listFilterSelect.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
      View: <%= listFilterSelect.getHtml("listFilter1", AutoGuideDirectoryInfo.getFilterKey("listFilter1")) %>
      <% statusFilterSelect.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
      Status: <%= statusFilterSelect.getHtml("listFilter2", AutoGuideDirectoryInfo.getFilterKey("listFilter2")) %>
      <% MakeSelect.setJsEvent("onChange=\"javascript:document.forms['listView'].submit();\""); %>
      Make: <%= MakeSelect.getHtml("listFilter3", AutoGuideDirectoryInfo.getFilterKey("listFilter3")) %>
    </td>
    </form>
  </tr>
</table>

<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
<%--
    <dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete">
    <th valign="center" align="left" class="title">
      <strong>Action</strong>
    </th>
    </dhv:permission>
--%>
    <th nowrap>
      <strong><a href="AutoGuide.do?command=List&column=o.name,i.stock_no">Account</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("o.name,i.stock_no") %>
    </th>
    <th nowrap>
      <strong><a href="AutoGuide.do?command=List&column=i.stock_no">Stock No</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("i.stock_no") %>
    </th>
    <th nowrap>
      <strong><a href="AutoGuide.do?command=List&column=v.year">Year</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("v.year") %>
    </th>
    <th nowrap>
      <strong><a href="AutoGuide.do?command=List&column=make.make_name">Make</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("make.make_name") %>
    </th>
    <th nowrap>
      <strong><a href="AutoGuide.do?command=List&column=model.model_name">Model</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("model.model_name") %>
    </th>
    <th nowrap>
      <strong>Next Incomplete Ad</strong>
      <%-- Can't sort by ad run because there can be many ad runs per vehicle --%>
      <%-- <strong><a href="AutoGuide.do?command=List&column=ad.run_date">Next Incomplete Ad</a></strong>
      <%= AutoGuideDirectoryInfo.getSortIcon("ad.run_date") %> --%>
    </th>
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
      <tr class="row<%= rowid %>">
<%--
        <dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete">
        <td width="8" valign="center" nowrap class="row<%= rowid %>">
          <dhv:permission name="autoguide-inventory-edit"><a href="AutoGuide.do?command=Details&id=<%= thisItem.getId()%>&action=modify&return=list">Edit</a></dhv:permission><dhv:permission name="autoguide-inventory-edit,autoguide-inventory-delete" all="true">|</dhv:permission><dhv:permission name="autoguide-inventory-delete"><a href="javascript:confirmDelete('AutoGuide.do?command=Delete&id=<%= thisItem.getId() %>');">Del</a></dhv:permission>
        </td>
        </dhv:permission>
--%>
        <td nowrap>
          <a href="AutoGuide.do?command=Details&id=<%= thisItem.getId() %>"><%= toHtml(thisItem.getOrganization().getName()) %></a>
        </td>
        <td>
          <%= toHtml(thisItem.getStockNo()) %>
        </td>
        <td nowrap>
          <%= thisItem.getVehicle().getYear() %>
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
        <td>
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
    <td class="containerBody" valign="center" colspan="6">
      No vehicles found.
    </td>
  </tr>
<%}%>
</table>
<br>
<dhv:pagedListControl object="AutoGuideDirectoryInfo" tdClass="row1"/>

