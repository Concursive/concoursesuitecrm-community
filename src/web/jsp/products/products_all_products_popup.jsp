<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.products.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="productList" class="org.aspcfs.modules.products.base.ProductCatalogList" scope="request"/>
<jsp:useBean id="ProductCatalogSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="request"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<% if (!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))) { %>
<%-- Show the list of types to choose from --%>
<form name="elementListView" method="post" action="ProductsCatalog.do?command=PopupSelector">
<input type="hidden" name="letter">
<table width="100%" border="0">
  <tr>
      <td align="right">
        <dhv:pagedListStatus title="<%= showError(request, "actionError") %>" object="ProductCatalogSelectorInfo" showHiddenParams="true" enableJScript="true"/>
      </td>
  </tr>
</table>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="pagedList">
  <tr>
    <th width="8">
      &nbsp;
    </th>
    <th width="20%">
      Code
    </th>
    <th width="80%">
      Description
    </th>
  </tr>
<%
  Iterator j = productList.iterator();
  if ( j.hasNext() ) {
    int rowid = 0;
    int count = 0;
    while (j.hasNext()) {
      count++;
      rowid = (rowid != 1?1:2);
      ProductCatalog thisElt = (ProductCatalog)j.next();
      if ( thisElt.getEnabled() || (!thisElt.getEnabled() && (selectedElements.get(new Integer(thisElt.getId()))!= null)) ) {
%>
  <tr class="row<%= rowid+((selectedElements.get(new Integer(thisElt.getId()))!= null)?"hl":"") %>">
    <td align="center">
      <input type="checkbox" name="checkelement<%= count %>" value=<%= thisElt.getId() %><%= ((selectedElements.get(new Integer(thisElt.getId()))!= null)?" checked":"") %> onClick="highlight(this,'<%= User.getBrowserId() %>');">
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getSku()) %>
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= thisElt.getId() %>">
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml(thisElt.getSku()) %>">
    </td>
    <td valign="center">
      <%= toHtml(thisElt.getName()) %>
    </td>
  </tr>
<%
      } else {
        count--;
      }
    }
    
    /*Removing the elements that are in view*/
    Iterator itr = productList.iterator();
    while (itr.hasNext()){
      ProductCatalog thisElt = (ProductCatalog)itr.next();
      if (selectedElements.containsKey(new Integer(thisElt.getId()))){
        selectedElements.remove(new Integer(thisElt.getId()));
      }
    }
    /*Creating hidden objects for the items not in this page*/
    Iterator itr1 = selectedElements.keySet().iterator();
    if (itr1.hasNext()){
      while (itr1.hasNext()){
        count++;
        int id = ((Integer)itr1.next()).intValue();%>
      <input type="hidden" name="checkelement<%= count %>" value="<%= id %>" />
      <input type="hidden" name="hiddenelementid<%= count %>" value="<%= id %>" />
      <input type="hidden" name="elementvalue<%= count %>" value="<%= toHtml((String)selectedElements.get(new Integer(id))) %>" />
<%    
      }
    }
  } else {
%>
      <tr class="containerBody">
        <td colspan="3">
          No products found.
        </td>
      </tr>
<%
  }
%>
</table>
<input type="hidden" name="finalsubmit" value="false">
<input type="hidden" name="rowcount" value="0">
<input type="hidden" name="displayFieldId" value="<%= DisplayFieldId %>">
<input type="button" value="Done" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
<input type="button" value="Cancel" onClick="javascript:window.close()">
[<a href="javascript:SetChecked(1,'checkelement','elementListView','<%= User.getBrowserId() %>');">Check All</a>]
[<a href="javascript:SetChecked(0,'checkelement','elementListView','<%= User.getBrowserId() %>');">Clear All</a>]
<br>
&nbsp;<br>
</form>
<%
  } else {
%>
<%-- Save the selected items to the parent form, then close the window --%>
<body OnLoad="javascript:setParentList(selectedValues,selectedIds,'list','<%= DisplayFieldId %>');window.close();">
  <script>selectedValues = new Array();selectedIds = new Array();</script>
<%
    Set s = selectedElements.keySet();
    Iterator i = s.iterator();
    int count = -1;
    while (i.hasNext()) {
      count++;
      Object id = i.next();
      Object st = selectedElements.get(id);
      String value = st.toString();
%>
  <script>
    selectedValues[<%= count %>] = "<%= value %>";
    selectedIds[<%= count %>] = "<%= id %>";
  </script>
<%
    }
%>
</body>
<%
  }
%>
