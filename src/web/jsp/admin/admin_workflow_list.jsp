<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.apps.workFlowManager.*" %>
<!--<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>-->
<jsp:useBean id="hookManager" class="org.aspcfs.controller.objectHookManager.ObjectHookManager" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
Object Workflow<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Action</strong>
    </td>
    <td width="100%" nowrap>
      <strong>Process Name</strong>
    </td>
    <td nowrap>
      <strong>Number of<br>components</strong>
    </td>
  </tr>
<%
  BusinessProcessList processList = hookManager.getProcessList();
  if (processList != null) {
    Iterator i = processList.values().iterator();
    while (i.hasNext()) {
      BusinessProcess thisProcess = (BusinessProcess) i.next();
%>  
  <tr class="containerBody">
    <td align="center">
      Edit
    </td>
    <td>
       <a href="Admin.do?command=WorkflowDetails&process=<%= thisProcess.getName() %>"><%= toHtml(thisProcess.getDescription()) %></a>
    </td>
    <td>
       <%= thisProcess.size() %>
    </td>
  </tr>
<%
    }
  }
%>
</table>
