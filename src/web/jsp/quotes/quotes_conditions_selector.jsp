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
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.quotes.base.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="quote" class="org.aspcfs.modules.quotes.base.Quote" scope="request"/>
<jsp:useBean id="QuoteConditionSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="type" class="java.lang.String" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<% if(!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))){ %>
<form name="elementListView" method="post" action="QuotesConditions.do?command=PopupSelector">
<input type="hidden" name="quoteId" value="<%= quote.getId() %>"/>
<input type="hidden" name="type" value="<%= type %>"/>
<br />
<center><%= QuoteConditionSelectorInfo.getAlphabeticalPageLinks("setFieldSubmit","elementListView") %></center>
<input type="hidden" name="letter"/>
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="QuoteConditionSelectorInfo" showHiddenParams="true" enableJScript="true" form="elementListView"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" nowrap width="8">
      &nbsp;
    </th>
    <th width="100%">
      <%= toHtml(type) %>
    </th>
  </tr>
<%
  Iterator j = BaseList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      LookupElement thisElt = (LookupElement)j.next();
      if ( thisElt.getEnabled() || (!thisElt.getEnabled() && (selectedElements.get(new Integer(thisElt.getCode()))!= null)) ) {
%>
  <tr class="row<%= rowid + ((selectedElements.get(new Integer(thisElt.getCode()))!= null)?"hl":"") %>">
    <td align="center" width="8">
      <input type="checkbox" name="checkelement<%= count %>" value=<%= thisElt.getCode() %><%= ((selectedElements.get(new Integer(thisElt.getCode()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getDescription()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisElt.getCode() %>">
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml(thisElt.getDescription()) %>">
    </td>
  </tr>
<%
      } else {
        count--;
      }
    }
  } else {
%>
      <tr class="containerBody">
        <td colspan="2">
          <dhv:label name="quotes.noOptionsMatchedQuery">No options matched query.</dhv:label>
        </td>
      </tr>
<%}%>
</table>
&nbsp;<br />
<input type="hidden" name="finalsubmit" value="false">
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="hidden" name="table" value="<%= Table %>">
<input type='button' value="<dhv:label name="button.done">Done</dhv:label>" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
[<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>]
<br />
</form>
<%}%>
