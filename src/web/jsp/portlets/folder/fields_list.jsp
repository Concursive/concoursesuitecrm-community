<%@ page import="org.aspcfs.utils.StringUtils"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.aspcfs.modules.base.CustomFieldGroup"%>
<%@ page import="org.aspcfs.modules.base.CustomField"%>
<%@ include file="../../initPopupMenu.jsp" %>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ taglib uri="/WEB-INF/portlet.tld" prefix="portlet" %>
<jsp:useBean id="systemStatus" class="org.aspcfs.controller.SystemStatus" scope="request"/>

<%@ page import="java.util.Iterator" %>
<%@ page import="org.aspcfs.modules.base.CustomFieldRecord"%>
<jsp:useBean id="SelectedList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<jsp:useBean id="finalGroup" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="categoryArray" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="Category" class="org.aspcfs.modules.base.CustomFieldCategory" scope="request"/>
<jsp:useBean id="groupCount" class="java.util.ArrayList" scope="request"/>

<portlet:defineObjects/>

<%@ include file="../../initPage.jsp" %>
<%@ include file="website_fields_list_menu.jsp" %>

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/checkDate.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>

<form name="fieldListForm" method="post">
 <%
 int i=0;
 int row = 0;
 int groupsIter = 0;
 CustomField thisField;
 int groupsCount = Integer.parseInt(groupCount.get(0).toString());
 ArrayList displayInList=Category.getDisplayInList();
 %>
 <br>
 <dhv:evaluate if="<%=Category.getCanAdd()%>">
   <font size="2.5"><a href="<portlet:renderURL ><portlet:param name="viewType" value="add"/></portlet:renderURL>">
   <dhv:label name="accounts.accounts_fields_add.AddFolderRecord">Add Folder Record</dhv:label></a></font>
 </dhv:evaluate>
 <br><br>
 <table class="details" border="1" cellpadding="4" cellspacing="0" width="100%" >
   <tr>
      <th colspan="6"><strong><dhv:label name="">Folder Fields</dhv:label></strong></th>
   </tr>
   <tr>
      <td>&nbsp;</td>
    <%
    CustomFieldGroup fieldsGroup = null;
    Iterator groups = null;
    Iterator fields = null;
    for(int j=0;j<displayInList.size();j++) {
      groups=Category.iterator();
      while(groups.hasNext()) {
        fieldsGroup=(CustomFieldGroup)groups.next();
        Iterator fields1=fieldsGroup.iterator();
        while(fields1.hasNext()) {
          thisField=(CustomField)fields1.next();
          if((Integer.parseInt(displayInList.get(j).toString()))==thisField.getId()){
    %>
      <td><%=thisField.getName()%></td>
    <%
      }
     }
    }
   }
  %>
  </tr>
   <tr class="containerBody">
    <%
      for(int k=0;k<finalGroup.size();k++){
         row =0;
            fieldsGroup =(CustomFieldGroup)finalGroup.get(k);
                groupsIter++;
                  for(int j=0;j<displayInList.size();j++) {
                        fields=fieldsGroup.iterator();
                       while(fields.hasNext()) {
                        thisField=(CustomField)fields.next();
                        if((Integer.parseInt(displayInList.get(j).toString()))==thisField.getId()){
              %>
             <% if(row==0){
             %>
          <td><a href="javascript:displayMenu('select<%= k %>','menuField', '<%= thisField.getRecordId()%>');" onMouseOver="over(0, <%= k %>)" onmouseout="out(0, <%= k %>); hideMenu('menuField');"><img src="images/select.gif" name="select<%= k %>" id="select<%= k %>" align="absmiddle" border="0"></a></td>
        <%}%>
          <td><%=thisField.getValueHtml()%></td>
      <%
      }
     row++;
    }
  }
  if(groupsIter==groupsCount){
   groupsIter = 0;
   %>
   </tr>
   <%}
   }
   %>
 <dhv:evaluate if="<%=Category.isDoAvegrage() || Category.isDoTotal()%>">
    <tr class="portalTabs"><td>&nbsp;</td></tr>
 </dhv:evaluate>
<% Iterator selectedFields=displayInList.iterator(); %>
<dhv:evaluate if="<%=Category.isDoTotal()%>">
<tr class="containerBody">
    <td>&nbsp</td>
    <% while(selectedFields.hasNext()) { %>
    <td><% int fieldId=(Integer)selectedFields.next();%>
    <dhv:label name="portlets.folder.Total">Total:</dhv:label><%=(Category.getFieldTotal(fieldId)==-1)?"":Category.getFieldTotal(fieldId)%></td>
    <%} %>
</tr>
</dhv:evaluate>
<dhv:evaluate if="<%=Category.isDoAvegrage()%>">
<tr class="containerBody">
    <td>&nbsp</td>
    <%
    selectedFields=displayInList.iterator();
    while(selectedFields.hasNext()) {
    %>
    <td><%int fieldId=(Integer)selectedFields.next();%>
    <dhv:label name="portlets.folder.Avg">Avg:</dhv:label><%=(Category.getFieldAverage(fieldId)==-1)?"":Category.getFieldAverage(fieldId)%></td>
    <%} %>
</tr>
</dhv:evaluate>
</table>
</form>

