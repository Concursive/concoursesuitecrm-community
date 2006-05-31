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
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.troubletickets.base.*,org.aspcfs.modules.contacts.base.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.contacts.base.*" %>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="kbList" class="org.aspcfs.modules.troubletickets.base.KnowledgeBaseList" scope="request"/>
<jsp:useBean id="kbListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="kbListInfoPopup" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/> 
<jsp:useBean id="siteId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/executeFunction.js"></script>
<%@ include file="../initPage.jsp" %>
<%@ include file="../initPopupMenu.jsp" %>
<%@ include file="troubletickets_knowledge_base_list_menu.jsp" %>
<SCRIPT LANGUAGE="JavaScript" type="text/javascript">
  <%-- Preload image rollovers for drop-down menu --%>
  loadImages('select');
  var categoryId = -1;

  function reopen(extension) {
    window.location.href='KnowledgeBaseManager.do?command=Search'+extension;
  }

  function updateSubList1() {
    var sel = document.forms['searchKb'].elements['searchcodeCatCode'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['searchKb'].elements['searchcodeSiteId'];
    var siteId = -1;
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    siteId = site.options[site.options.selectedIndex].value;
    </dhv:evaluate><dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
    siteId = site.value;
    </dhv:evaluate>
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=searchKb&catCode=" + escape(value)+'&siteId='+siteId+'&from=kblist';
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['searchKb'].searchcodeSubCat1.value = 0;
      document.forms['searchKb'].searchcodeSubCat2.value = 0;
      document.forms['searchKb'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
  function updateSubList2() {
    var sel = document.forms['searchKb'].elements['searchcodeSubCat1'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['searchKb'].elements['searchcodeSiteId'];
    var siteId = -1;
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    siteId = site.options[site.options.selectedIndex].value;
    </dhv:evaluate><dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
    siteId = site.value;
    </dhv:evaluate>
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=searchKb&subCat1=" + escape(value)+'&siteId='+siteId+'&from=kblist';
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['searchKb'].searchcodeSubCat2.value = 0;
      document.forms['searchKb'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
<dhv:include name="ticket.subCat2" none="true">
  function updateSubList3() {
    var sel = document.forms['searchKb'].elements['searchcodeSubCat2'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['searchKb'].elements['searchcodeSiteId'];
    var siteId = -1;
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    siteId = site.options[site.options.selectedIndex].value;
    </dhv:evaluate><dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
    siteId = site.value;
    </dhv:evaluate>
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=searchKb&subCat2=" + escape(value)+'&siteId='+siteId+'&from=kblist';
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    if (value == '0') {
      document.forms['searchKb'].searchcodeSubCat3.value = 0;
    }
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var sel = document.forms['searchKb'].elements['searchcodeSubCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var site = document.forms['searchKb'].elements['searchcodeSiteId'];
    var siteId = -1;
    <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    siteId = site.options[site.options.selectedIndex].value;
    </dhv:evaluate><dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
    siteId = site.value;
    </dhv:evaluate>
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=searchKb&subCat3=" + escape(value)+'&siteId='+siteId+'&from=kblist';
    if (value == '0') {
      categoryId = -1;
    } else {
      categoryId = value;
    }
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>

  function checkForm(form) {
    return true;
  }

  function clearForm() {
    var url = "KnowledgeBaseManager.do?command=CategoryJSList&form=searchKb&catCode=0"+'&from=kblist';
    categoryId = -1;
    document.forms['searchKb'].searchcodeCatCode.value = 0;
    document.forms['searchKb'].searchcodeSubCat1.value = 0;
    document.forms['searchKb'].searchcodeSubCat2.value = 0;
    document.forms['searchKb'].searchcodeSubCat3.value = 0;
    window.frames['server_commands'].location.href=url;
  }
  
  function addKB() {
    var url = 'KnowledgeBaseManager.do?command=Add&categoryId=<%= kbList.getCategoryId() %><%= isPopup(request)?"&popup=true":"" %>';
    window.location.href=url;
  }
</script>
<form name="searchKb" action="KnowledgeBaseManager.do?command=Search" method="post" onSubmit="return checkForm(this);">
<input type="hidden" name="searchcodeExclusiveToSite" value="true"/>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<dhv:evaluate if="<%= !isPopup(request) %>">
<a href="TroubleTickets.do"><dhv:label name="tickets.helpdesk">Help Desk</dhv:label></a> > 
</dhv:evaluate>
<dhv:label name="tickets.viewKnowledgeBase">View Knowledge Base</dhv:label> 
</td>
</tr>
</table>
<%-- End Trails --%>
<dhv:evaluate if="<%= !isPopup(request) %>">
<dhv:evaluate if="<%= User.getUserRecord().getSiteId() > -1 %>">
  <input type="hidden" name="searchcodeSiteId" id="searchcodeSiteId" value="<%= (siteId != null?siteId:String.valueOf(User.getUserRecord().getSiteId())) %>"/>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" class="empty"><tr><td>
  <table cellpadding="0" cellspacing="0" class="floatWrap">
  <tr>
  <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    <td valign="top">
      <strong><dhv:label name="categories.switchToSite">Switch to Site</dhv:label></strong>
    </td>
  </dhv:evaluate>
    <td valign="top"><strong><dhv:label name="accounts.accountasset_include.Category">Category</dhv:label></strong></td>
    <td valign="top"><strong><dhv:label name="account.ticket.subLevel1">Sub-level 1</dhv:label></strong></td>
    <td valign="top"><strong><dhv:label name="account.ticket.subLevel2">Sub-level 2</dhv:label></strong></td>
    <td valign="top"><strong><dhv:label name="account.ticket.subLevel3">Sub-level 3</dhv:label></strong></td>
  </tr>
  <tr>
  <dhv:evaluate if="<%= User.getUserRecord().getSiteId() == -1 %>">
    <td valign="top">
      <% SiteIdList.setJsEvent("id=\"searchcodeSiteId\" onChange=\"reopen('&reset=true&searchcodeSiteId='+document.getElementById('searchcodeSiteId').options[document.getElementById('searchcodeSiteId').options.selectedIndex].value+'&searchcodeExclusiveToSite=true');\""); %>
      <%= SiteIdList.getHtmlSelect("searchcodeSiteId", (siteId != null?siteId:String.valueOf(User.getUserRecord().getSiteId()))) %>
    </td>                                                                    
  </dhv:evaluate>
    <td valign="top" align="right"><%= CategoryList.getHtmlSelect("searchcodeCatCode", kbList.getCatCode()) %></td>
    <td valign="top" align="right"><%= SubList1.getHtmlSelect("searchcodeSubCat1", kbList.getSubCat1()) %></td>
    <td valign="top" align="right"><%= SubList2.getHtmlSelect("searchcodeSubCat2", kbList.getSubCat2()) %></td>
    <td valign="top" align="right"><%= SubList3.getHtmlSelect("searchcodeSubCat3", kbList.getSubCat3()) %></td>
  </tr>
  </table>
</td><td nowrap>
    <table cellpadding="0" cellspacing="0" class="empty">
      <tr><td>&nbsp;</td></tr><tr><td valign="" align="center">
      <input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();"></td></tr><tr><td nowrap valign="top" align="center">
      </td></tr>
    </table>
</td></tr></table>
</dhv:evaluate>
<dhv:evaluate if="<%= request.getAttribute("canAddCategory") != null && "true".equals((String)request.getAttribute("canAddCategory")) %>">
<dhv:evaluate if="<%= kbList.getCategoryId() > 0 %>"><dhv:permission name="tickets-knowledge-base-add">
<a href="javascript:addKB();" ><dhv:label name="campaign.addADocument">Add a Document</dhv:label></a>
</dhv:permission></dhv:evaluate></dhv:evaluate>
<dhv:evaluate if="<%= isPopup(request) %>">
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="kbListInfoPopup"/>
</dhv:evaluate><dhv:evaluate if="<%= !isPopup(request) %>">
  <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="kbListInfo"/>
</dhv:evaluate>
<table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
  <tr>
    <th width="8" nowrap>&nbsp;</th>
    <th nowrap width="100%">
      <strong><a href="KnowledgeBaseManager.do?command=Search<%= isPopup(request)?"&popup=true":"" %>&column=title"><dhv:label name="accounts.accounts_contacts_add.Title">Title</dhv:label></a></strong>
      <%= isPopup(request)?kbListInfoPopup.getSortIcon("title"):kbListInfo.getSortIcon("title") %>
    </th>
    <th nowrap>
      <strong><dhv:label name="project.document">Document</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><dhv:label name="accounts.accounts_documents_details.Version">Version</dhv:label></strong>
    </th>
    <th nowrap>
      <strong><a href="KnowledgeBaseManager.do?command=Search<%= isPopup(request)?"&popup=true":"" %>&column=modified"><dhv:label name="accounts.accounts_contacts_calls_details.Modified">Modified</dhv:label></a></strong>
      <%= isPopup(request)?kbListInfoPopup.getSortIcon("modified"):kbListInfo.getSortIcon("modified") %>
    </th>
  </tr>
<%
	Iterator j = kbList.iterator();
	if ( j.hasNext() ) {
		int rowid = 0;
    int i = 0;
    while (j.hasNext()) {
      i++;
		  rowid = (rowid != 1?1:2);
      KnowledgeBase thisKb = (KnowledgeBase) j.next();
%>
  <tr class="row<%= rowid %>">
    <td valign="top">
      <a href="javascript:displayMenu('select<%= i %>','menuKnowledgeBase', '<%= thisKb.getId() %>', '<%= thisKb.getItemId() %>','<%= thisKb.getCategoryId() %>');"
      onMouseOver="over(0, <%= i %>)" onmouseout="out(0, <%= i %>); hideMenu('menuKnowledgeBase');">
      <img src="images/select.gif" name="select<%= i %>" id="select<%= i %>" align="absmiddle" border="0" /></a>
    </td>
    <td valign="top" width="100%">
      <a href="KnowledgeBaseManager.do?command=Details<%= isPopup(request)?"&popup=true":"" %>&kbId=<%= thisKb.getId() %>"><%= toHtml(thisKb.getTitle()) %></a>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= thisKb.getItemId() != -1 %>">
        <%= toHtml(thisKb.getItem().getClientFilename()) %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisKb.getItemId() == -1 %>">
        &nbsp;
      </dhv:evaluate>
    </td>
    <td valign="top">
      <dhv:evaluate if="<%= thisKb.getItemId() != -1 %>">
        <%= thisKb.getItem().getVersion() %>
      </dhv:evaluate>
      <dhv:evaluate if="<%= thisKb.getItemId() == -1 %>">
        &nbsp;
      </dhv:evaluate>
    </td>
    <td valign="top" nowrap>
      <zeroio:tz timestamp="<%= thisKb.getModified() %>" timeZone="<%= User.getTimeZone() %>" showTimeZone="false" dateOnly="true" />
    </td>
  </tr>
<%}%>
<%} else {%>
  <tr class="containerBody">
    <td colspan="5">
      <dhv:label name="tickets.noKnowledgeBaseEntriesFound.text">No knowledge base entries found.</dhv:label>
    </td>
  </tr>
<%}%>
</table>
<br />
</form>
<dhv:evaluate if="<%= isPopup(request) %>">
<dhv:pagedListControl object="kbListInfoPopup"/>
</dhv:evaluate><dhv:evaluate if="<%= !isPopup(request) %>">
<dhv:pagedListControl object="kbListInfo"/>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>

