<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<%@ page import="org.aspcfs.modules.assets.base.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="selectedQuantities" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalQuantities" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="asset" class="org.aspcfs.modules.assets.base.Asset" scope="request"/>
<jsp:useBean id="AssetMaterialsSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js?1"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkNumber.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/checkString.js"></script>
<script type="text/javascript">
  function checkForm(form) {
    formTest = true;
    message = "";
<%for (int i=1;i <= BaseList.size(); i++) {%>
    if (form.checkelement<%= i %>.checked && checkNullString(form.elementqty<%= i %>.value)) {
      message += label("check.material.quantity.one","- Please enter a valid Number for ");
      message += form.description<%= i %>.value + '\r\n';
      formTest = false;
    } else if (form.checkelement<%= i %>.checked  && !checkNullString(form.elementqty<%= i %>.value) && !checkNumber(form.elementqty<%= i %>.value)) {
      message += label("check.material.quantity.one","- Please enter a valid Number for ");
      message += form.description<%= i %>.value + '\r\n';
      formTest = false;
    }
<%} %>
    if (formTest == false) {
      alert(label("check.form","Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    return true;
  }
</script>
<%@ include file="../initPage.jsp" %>
<% if(!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))){ %>
<form name="elementListView" method="post" action="AssetMaterialsSelector.do?command=PopupSelector&popup=true" onSubmit="javascript:document.elementListView.finalsubmit.value='true';return checkForm(this);">
<input type="hidden" name="assetId" value="<%= asset.getId() %>"/>
<br />
<center><%= AssetMaterialsSelectorInfo.getAlphabeticalPageLinks("setFieldSubmit","elementListView") %></center>
<input type="hidden" name="letter"/>
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title='<%= showError(request, "actionError") %>' object="AssetMaterialsSelectorInfo" showHiddenParams="true" enableJScript="true" form="elementListView"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th align="center" nowrap width="8">
      &nbsp;
    </th>
    <th width="100%">
      <dhv:label name="accounts.assets.materials">Materials</dhv:label>
    </th>
    <th nowrap>
      <dhv:label name="product.quantity">Quantity</dhv:label>
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
      if ( thisElt.getEnabled() || (!thisElt.getEnabled() && (selectedQuantities.get(new Integer(thisElt.getCode()))!= null)) ) {
%>
  <tr class="row<%= rowid + ((selectedQuantities.get(new Integer(thisElt.getCode()))!= null)?"hl":"") %>">
    <td align="center" width="8">
      <input type="checkbox" name="checkelement<%= count %>" value="<%= thisElt.getCode() %>" <%= ((selectedQuantities.get(new Integer(thisElt.getCode()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
      <input type="hidden" name="description<%= count %>" value="<%= toHtml(thisElt.getDescription()) %>" />
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getDescription()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisElt.getCode() %>">
    </td>
    <td valign="center">
      <input type="text" size="10" name="elementqty<%= count %>" value="<%= (selectedQuantities.get(new Integer(thisElt.getCode())) != null? (String) selectedQuantities.get(new Integer(thisElt.getCode())):"") %>" />
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
        <td colspan="3">
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
<input type='submit' value="<dhv:label name="button.done">Done</dhv:label>"/>
<input type="button" value="<dhv:label name="button.cancel">Cancel</dhv:label>" onClick="javascript:window.close();">
<%-- [<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.checkAll">Check All</dhv:label></a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');"><dhv:label name="quotes.clearAll">Clear All</dhv:label></a>] --%>
<br />
</form>
<%}%>
