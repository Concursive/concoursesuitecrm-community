<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*" %>
<jsp:useBean id="BaseList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="selectedElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="finalElements" class="java.util.HashMap" scope="session"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="DisplayFieldId" class="java.lang.String" scope="request"/>
<jsp:useBean id="Table" class="java.lang.String" scope="request"/>
<jsp:useBean id="LookupSelectorInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="/javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<% if(!"true".equalsIgnoreCase(request.getParameter("finalsubmit"))){ %>
<br>
<center><%= LookupSelectorInfo.getAlphabeticalPageLinks("setFieldSubmit","elementListView") %></center>
<form name="elementListView" method="post" action="LookupSelector.do?command=PopupSelector">
<input type=hidden name="letter">
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
   <tr class="title">
    <td align=center nowrap width=8>
    &nbsp;
    </td>
    <td width=100% align=left>
    Option
    </td>
   </tr>

  <%
  Iterator j = BaseList.iterator();
  
  if ( j.hasNext() ) {
    int rowid = 0;
    int count = 0;
  
  while (j.hasNext()) {
    count++;		
    if (rowid != 1) {
      rowid = 1;
    } else {
      rowid = 2;
    }
		
		LookupElement thisElt = (LookupElement)j.next();
   
  %>      
  
  <% if ( thisElt.getEnabled() || (!thisElt.getEnabled() && (selectedElements.get(new Integer(thisElt.getCode()))!= null)) ) {%>
  <tr class="row<%= rowid+((selectedElements.get(new Integer(thisElt.getCode()))!= null)?"hl":"") %>">
  <td align="center" width=8>
  <input type=checkbox name="checkelement<%=count%>" value=<%=thisElt.getCode()%><%=((selectedElements.get(new Integer(thisElt.getCode()))!= null)?" checked":"")%> onClick="highlight(this,'<%=User.getBrowserId()%>');">
  </td>
  <td valign=center>
  <%=thisElt.getDescription()%>
  <input type=hidden name="hiddenelementid<%=count%>" value="<%=thisElt.getCode()%>">
  <input type=hidden name="elementvalue<%=count%>" value="<%=thisElt.getDescription()%>">
  </td>
  </tr>
  <%} else {
    count--;
  }%>
  
  <%}
  } else {%>
      <tr bgcolor="white">
        <td colspan="2" valign="center">
          No options matched query.
        </td>
      </tr>
      <%}%>
  
</table>
    <input type=hidden name="finalsubmit" value="false">
    <input type=hidden name="rowcount" value="0">
    <input type=hidden name="displayFieldId" value="<%=DisplayFieldId%>">
    <input type=hidden name="table" value="<%=Table%>">
    <input type='button' value="Done" onClick="javascript:document.elementListView.finalsubmit.value='true';document.elementListView.submit();">
    <input type="button" value="Cancel" onClick="javascript:window.close()">
    <a href="javascript:SetChecked(1,'checkelement','elementListView','<%=User.getBrowserId()%>');">Check All</a>
    <a href="javascript:SetChecked(0,'checkelement','elementListView','<%=User.getBrowserId()%>');">Clear All</a>
    <dhv:pagedListControl object="LookupSelectorInfo" showForm="false" resetList="false"/>
    <br>
    <br>
</form>
  
  <%} else {%>
  <body OnLoad="javascript:setParentList(selectedValues,selectedIds,'list','<%=DisplayFieldId%>','<%=User.getBrowserId()%>');window.close();">
  
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
  selectedValues[<%=count%>] = "<%=value%>";
  selectedIds[<%=count%>] = "<%=id%>";
  </script>
  
  <%}%>

  </body>
  
  <%
  session.removeAttribute("selectedElements");
  session.removeAttribute("finalElements");
  %>
  
  <%}%>
  
