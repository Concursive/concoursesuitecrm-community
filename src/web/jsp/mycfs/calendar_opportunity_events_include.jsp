<%-- draws the opportunity events for a specific day --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="org.aspcfs.modules.pipeline.base.OpportunityComponent" %>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%
  OpportunityEventList oppEventList = (OpportunityEventList) thisDay.get(category);
%>

<%-- include alert activities --%>
<dhv:evaluate if="<%= oppEventList.getAlertOpps().size() > 0 %>">
<table border="0">
  <tr>
    <td colspan="6">
      <%-- event name --%>
      <img border="0" src="images/alertopp.gif" align="texttop" title="Opportunities"><a href="javascript:changeImages('alertoppsimage<%=toFullDateString(thisDay.getDate()) %>','images/arrowdown.gif','images/arrowright.gif');javascript:switchStyle(document.getElementById('alertoppdetails<%=toFullDateString(thisDay.getDate()) %>'));" onMouseOver="window.status='View Details';return true;" onMouseOut="window.status='';return true;"><img src="<%= firstEvent ? "images/arrowdown.gif" : "images/arrowright.gif"%>" name="alertoppsimage<%=toFullDateString(thisDay.getDate())%>" id="<%= firstEvent ? "0" : "1"%>" border="0" title="Click To View Details">Opportunities</a>&nbsp;(<%= oppEventList.getAlertOpps().size() %>)
    </td>
  </tr>
</table>
<table border="0" id="alertoppdetails<%= toFullDateString(thisDay.getDate()) %>" style="<%= firstEvent ? "display:" : "display:none"%>">
  <%-- include opportunity details --%>
  <%
    Iterator j = oppEventList.getAlertOpps().iterator();
    if(j.hasNext()){
  %>
    <tr>
      <th>
        &nbsp
      </th>
      <th class="weekSelector" nowrap>
        <strong>Alert</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Component</strong>
      </th>
      <th class="weekSelector" width="100%" nowrap>
        <strong>Guess Amount</strong>
      </th>
      <th class="weekSelector" nowrap>
        <strong>Close Date</strong>
      </th>
    </tr>
  <%  
      while(j.hasNext()){
      OpportunityComponent alertOpp = (OpportunityComponent) j.next();
      menuCount++;
    %>
    <tr>
     <td>
       <%-- Use the unique id for opening the menu, and toggling the graphics --%>
       <a href="javascript:displayOppMenu('selectOpp<%= menuCount %>','menuOpp', '<%= alertOpp.getId() %>');"
       onMouseOver="over(0, <%= menuCount %>)" onmouseout="out(0, <%= menuCount %>);hideMenu('menuOpp');"><img src="images/select.gif" name="selectOpp<%= menuCount %>" id="selectOpp<%= menuCount %>" align="absmiddle" border="0"></a>
     </td>
     <td nowrap>
       <%= toHtml(alertOpp.getAlertText()) %>
     </td>
     <td nowrap>
       <%= toString(alertOpp.getDescription()) %>
     </td>
     <td nowrap>
       <zeroio:currency value="<%= alertOpp.getGuess() %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/> 
     </td>
     <td nowrap>
       <zeroio:tz timestamp="<%= alertOpp.getCloseDate() %>" dateOnly="true" default="&nbsp;"/>
     </td>
    </tr>
   <% }
   } %>
</table>
</dhv:evaluate>


