<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%-- draws the account events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.base.Constants" %>
<%
  OrganizationEventList orgEventList = (OrganizationEventList) thisDay.get(category);
%>

<%-- include pending accounts --%>
<dhv:evaluate if="<%= orgEventList.getAlertOrgs().size() > 0 %>">
<table border="0">
  <tr>
    <td colspan="6" nowrap>
      <%-- event name --%>
      <img border="0" src="images/accounts.gif" align="texttop" title="Accounts"><a href="javascript:changeImages('alertorgsimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('alertorgdetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="alertorgsimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details"><dhv:label name="accounts.account.alerts">Account Alerts</dhv:label></a>&nbsp;(<%= orgEventList.getAlertOrgs().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="alertorgdetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include account details --%>
  <%
    Iterator j = orgEventList.getAlertOrgs().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp
      </th>
      <th class="weekSelector" width="100%">
        <strong>Alert</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Account Name</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Owner</strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Organization thisOrg = (Organization) j.next();
      menuCount++;
    %>
    <tr>
     <td>
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayAccountMenu('select<%= menuCount %>','menuAccount', '<%= thisOrg.getOrgId() %>');"
         onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0"></a>
     </td>
     <td nowrap>
       <%= toHtml(thisOrg.getAlertText()) %>
     </td>
     <td nowrap>
       <%= thisOrg.getName() %>
     </td>
     <td nowrap>
       <dhv:username id="<%= thisOrg.getEnteredBy() %>"/>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>

<dhv:evaluate if="<%= orgEventList.getContractEndOrgs().size() > 0 %>">
<table border="0">
  <tr>
    <td colspan="6" nowrap>
      <%-- event name --%>
      <img border="0" src="images/accounts.gif" align="texttop" title="Accounts"><a href="javascript:changeImages('alertcontractorgsimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('alertcontractorgdetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="alertcontractorgsimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details"><dhv:label name="accounts.accountContractEndAlerts">Account Contract End Alerts</dhv:label></a>&nbsp;(<%= orgEventList.getContractEndOrgs().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="alertcontractorgdetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include account details --%>
  <%
    Iterator j = orgEventList.getContractEndOrgs().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp
      </th>
      <th class="weekSelector" nowrap>
        <strong>Account Name</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Owner</strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      Organization thisOrg = (Organization) j.next();
      menuCount++;
    %>
    <tr>
     <td>
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayAccountMenu('select<%= menuCount %>','menuAccount', '<%= thisOrg.getOrgId() %>');"
         onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuAccount');"><img src="images/select.gif" name="select<%= menuCount %>" id="select<%= menuCount %>" align="absmiddle" border="0"></a>
     </td>
     <td nowrap>
       <%= thisOrg.getName() %>
     </td>
     <td nowrap>
       <dhv:username id="<%= thisOrg.getEnteredBy() %>"/>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>

