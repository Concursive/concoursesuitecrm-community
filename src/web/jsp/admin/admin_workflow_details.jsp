<%-- NOTE: This is a proof of concept, taglibs would simplify the layout tremendously --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.apps.workFlowManager.*" %>
<%@ page import="org.aspcfs.utils.ObjectUtils" %>
<%@ page import="org.aspcfs.modules.base.Constants" %>
<jsp:useBean id="steps" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="process" class="org.aspcfs.apps.workFlowManager.BusinessProcess" scope="request"/>
<jsp:useBean id="PermissionCategory" class="org.aspcfs.modules.admin.base.PermissionCategory" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/div.js"></script>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/spanDisplay.js"></script>
<SCRIPT LANGUAGE="JavaScript">
  function toggle(id, size) {
    if (isSpanVisible(id)) {
      hideSpan(id);
      for (i = 0; i < size; i++) {
        hideSpan(id + '-' + (i + 1));
      }
      changeDivContent(id+'-label', 'Show');
    } else {
      showSpan(id);
      for (i = 0; i < size; i++) {
        showSpan(id + '-' + (i + 1));
      }
      changeDivContent(id+'-label', 'Hide');
    }
  }
</SCRIPT>
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Config">Configure Modules</a> >
<a href="Admin.do?command=ConfigDetails&moduleId=<%= PermissionCategory.getId() %>"><%= toHtml(PermissionCategory.getCategory()) %></a> >
<dhv:evaluate if="<%= "AdminObjectEvents".equals(request.getParameter("return")) %>">
<a href="<%= request.getParameter("return") %>.do?moduleId=<%= PermissionCategory.getId() %>">Object Events</a> >
</dhv:evaluate>
<dhv:evaluate if="<%= "AdminScheduledEvents".equals(request.getParameter("return")) %>">
<a href="<%= request.getParameter("return") %>.do?moduleId=<%= PermissionCategory.getId() %>">Scheduled Events</a> >
</dhv:evaluate>
Process Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
  <tr>
    <th>
      <strong>Process: <%= toHtml(process.getDescription()) %></strong>
    </th>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="empty">
<%!
  //Looks up the count of the step involved with this component
  public int getCount(LinkedHashMap map, int id) {
    int count = 0;
    Iterator i = map.keySet().iterator();
    while (i.hasNext()) {
      ++count;
      Integer thisId = (Integer) i.next();
      if (thisId.intValue() == id) {
        return count;
      }
    }
    return -1;
  }
%>
<dhv:evaluate if="<%= process.hasParameters() %>">
<%-- Global Parameters --%>
  <tr>
    <td colspan="2">
      <strong>Global Parameters</strong>
      (<%= process.getParameters().size() %>)
      [<a href="javascript:toggle('params-global', <%= process.getParameters().size() %>)"><div id="params-global-label" style="display:inline">Show</div></a>]
    </td>
  </tr>
  <tr id="params-global" style="display:none" class="title">
    <td width="50%">
      <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name</strong>
    </td>
    <td width="50%">
      <strong>Value</strong>
    </td>
  </tr>
<%
  int globalCount = 0;
  Iterator globalParams = process.getParameters().iterator();
  while (globalParams.hasNext()) {
    ++globalCount;
    ProcessParameter param = (ProcessParameter) globalParams.next();
%>
  <tr id="params-global-<%= globalCount %>" style="display:none" class="containerBack">
    <td width="50%" valign="top" nowrap>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= toHtml(param.getName()) %><dhv:evaluate if="<%= !param.getEnabled() %>"> <font color="red">(disabled)</font></dhv:evaluate>
    </td>
    <td width="50%" valign="top" nowrap>
      <%= toHtml(param.getValue()) %>
    </td>
  </tr>
<%
  }
%>
</dhv:evaluate>
<%
  int count = 0;
  Iterator i = steps.values().iterator();
  while (i.hasNext()) {
    //Show each step of the process
    ++count;
    BusinessProcessComponent component = (BusinessProcessComponent) i.next();
    //Object classRef = Class.forName(component.getClassName()).newInstance();
%>
  <tr class="row1">
    <td colspan="2">
      <table border="0" cellpadding="0" cellspacing="0" class="empty">
        <tr>
          <td width="100" nowrap>
            <strong>Step <%= count %>:</strong><dhv:evaluate if="<%= !component.getEnabled() %>"><br><font color="red">(disabled)</font></dhv:evaluate>
          </td>
          <td>
            <strong><%= component.getDescription() %></strong>
          </td>
        </tr>
      </table>
    </td>
  </tr>
<dhv:evaluate if="<%= component.hasParameters() %>">
<%-- Component Parameters --%>
  <tr>
    <td colspan="2">
      <strong>Parameters</strong>
      (<%= component.getParameters().size() %>)
      [<a href="javascript:toggle('params-<%= component.getId() %>', <%= component.getParameters().size() %>)"><div id="params-<%= component.getId() %>-label" style="display:inline">Show</div></a>]
    </td>
  </tr>
  <tr id="params-<%= component.getId() %>" style="display:none" class="title">
    <td width="50%" nowrap>
      <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Name</strong>
    </td>
    <td width="50%" nowrap>
      <strong>Value</strong>
    </td>
  </tr>
<%
  int paramCount = 0;
  Iterator params = component.getParameters().iterator();
  while (params.hasNext()) {
    ++paramCount;
    ComponentParameter param = (ComponentParameter) params.next();
%>
  <tr id="params-<%= component.getId() %>-<%= paramCount %>" style="display:none" class="containerBack">
    <td width="50%" valign="top" nowrap>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= toHtml(param.getName()) %><dhv:evaluate if="<%= !param.getEnabled() %>"> <font color="red">(disabled)</font></dhv:evaluate>
    </td>
    <td width="50%" valign="top" nowrap>
      <%= toHtml(param.getValue()) %>
    </td>
  </tr>
<%
  }
%>
</dhv:evaluate>
  <tr>
    <td width="50%">
      <strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Results</strong>
    </td>
    <td width="50%" nowrap>
      <strong>Go to</strong>
    </td>
  </tr>
<%
    int sameId = -1;
    //For each component, get the children components
    HashMap childrenMap = component.getChildren();
    //For each child component, get the possible result types
    Set resultSet = childrenMap.keySet();
    Iterator results = resultSet.iterator();
    //For each result type, output the next step information
    while (results.hasNext()) {
      Integer thisResult = (Integer) results.next();
      ArrayList stepResults = ((ArrayList) childrenMap.get(thisResult));
      Iterator children = stepResults.iterator();
      while (children.hasNext()) {
        BusinessProcessComponent thisComponent = (BusinessProcessComponent) children.next();
%>
<%-- Handle when no result is important --%>
  <dhv:evaluate if="<%= thisResult.intValue() == -1 %>">
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End of step
    </td>
    <td>
<%
    int nextCount = getCount(steps, thisComponent.getId());
    if (count < nextCount) {
      out.print("<img src=\"images/pr-next.gif\" border=\"0\" align=\"absbottom\"> Step " + nextCount);
    } else if (count > nextCount) {
      out.print("<img src=\"images/pr-previous.gif\" border=\"0\" align=\"absbottom\"> Step " + nextCount);
    } else {
      out.print("<img src=\"images/refresh.gif\" border=\"0\" align=\"absbottom\"> Repeat");
    }
%>
    </td>
  </tr>
  </dhv:evaluate>
<%-- Handle Yes/No result --%>
  <dhv:evaluate if="<%= thisResult.intValue() > -1 %>">
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= thisResult.intValue() == Constants.TRUE ? "Yes" : "No" %>
      <%= (thisComponent.getEnabled()?"":"<font color=\"red\">(disabled)</font>") %>
    </td>
    <td>
<%
    int nextCount = getCount(steps, thisComponent.getId());
    if (count < nextCount) {
      out.print("<img src=\"images/pr-next.gif\" border=\"0\" align=\"absbottom\"> Step " + nextCount);
    } else if (count > nextCount) {
      out.print("<img src=\"images/pr-previous.gif\" border=\"0\" align=\"absbottom\"> Step " + nextCount);
    } else {
      out.print("<img src=\"images/refresh.gif\" border=\"0\" align=\"absbottom\"> Repeat");
    }
%>
    </td>
  </tr>
  </dhv:evaluate>
<%-- Handle when there is only 1 boolean result type, show the other --%>
  <dhv:evaluate if="<%= resultSet.size() == 1 && (thisResult.intValue() == 0 || thisResult.intValue() == 1) %>">
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= thisResult.intValue() == Constants.TRUE ? "No" : "Yes" %>
    </td>
    <td>
      <img src="images/pr-end.gif" border="0" align="absbottom">
      Stop
    </td>
  </tr>
  </dhv:evaluate>
<%
      }
    }
%>
<%-- Handle when there are no more components to process (End of process) --%>
  <dhv:evaluate if="<%= childrenMap.isEmpty() %>">
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Process Finished
    </td>
    <td>
      <img src="images/pr-end.gif" border="0" align="absbottom">
      Stop
    </td>
  </tr>
  </dhv:evaluate>
<%
  }
%>
      </table>
    </td>
  </tr>
</table>
