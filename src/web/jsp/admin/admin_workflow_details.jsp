<%-- NOTE: This is a proof of concept, taglibs would simplify the layout tremendously --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.apps.workFlowManager.*" %>
<%@ page import="java.lang.reflect.*,org.aspcfs.utils.ObjectUtils" %>
<!--<jsp:useBean id="Timeout" class="java.lang.String" scope="request"/>-->
<jsp:useBean id="steps" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="process" class="org.aspcfs.apps.workFlowManager.BusinessProcess" scope="request"/>
<%@ include file="../initPage.jsp" %>
<a href="Admin.do">Setup</a> >
<a href="Admin.do?command=Workflow">Object Workflow</a> >
Process Details<br>
<hr color="#BFBFBB" noshade>
<table cellpadding="4" cellspacing="0" border="1" width="100%" bordercolorlight="#000000" bordercolor="#FFFFFF">
  <tr class="title">
    <td>
      <strong>Process: <%= toHtml(process.getDescription()) %></strong>
    </td>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" border="0" width="100%">
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
  //Looks to see if both paths are going to the exact same place
  public int sameResult(ArrayList trueChildren, ArrayList falseChildren) {
    if (trueChildren != null && falseChildren != null) {
      int currentId = 0;
      Iterator tr = trueChildren.iterator();
      while (tr.hasNext()) {
        BusinessProcessComponent component = (BusinessProcessComponent) tr.next();
        if (component.getId() != currentId) {
          if (currentId == 0 && currentId != -1) {
            currentId = component.getId();
          } else {
            currentId = -1;
          }
        }
      }
      Iterator fa = falseChildren.iterator();
      while (fa.hasNext()) {
        BusinessProcessComponent component = (BusinessProcessComponent) fa.next();
        if (component.getId() != currentId) {
          if (currentId == 0 && currentId != -1) {
            currentId = component.getId();
          } else {
            currentId = -1;
          }
        }
      }
      if (currentId > 0) {
        return currentId;
      }
    }
    return -1;
  }
%>
<%
  int count = 0;
  Iterator i = steps.values().iterator();
  while (i.hasNext()) {
    //Show each step of the process
    ++count;
    BusinessProcessComponent component = (BusinessProcessComponent) i.next();
    Object classRef = Class.forName(component.getClassName()).newInstance();
%>
  <tr class="row1">
    <td colspan="2">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" nowrap>
            <strong>Step <%= count %>:</strong>
          </td>
          <td>
            <strong><%= ObjectUtils.getParam(classRef, "description") %></strong><%--<br>
            <%= component.getClassName() %>--%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="50%">
      <strong>Results</strong>
    </td>
    <td width="50%" nowrap>
      <strong>Go to</strong>
    </td>
  </tr>
<%
    ArrayList trueChildren = component.getTrueChildren();
    ArrayList falseChildren = component.getFalseChildren();
    int sameId = sameResult(trueChildren, falseChildren);
    if (sameId > -1) {
      //There is the same result, so this must be end of this step
%>
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End of step
    </td>
    <td>
<%
    int nextCount = getCount(steps, sameId);
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
<%
    } else {
      if (trueChildren != null) {
        //Go through the true children
        Iterator trueList = trueChildren.iterator();
        while (trueList.hasNext()) {
          BusinessProcessComponent thisComponent = (BusinessProcessComponent) trueList.next();
%>  
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes <%= (thisComponent.getEnabled()?"":"<font color=\"red\">(disabled)</font>") %>
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
<%
        }
      }
      if (component.getTrueChildren() == null && component.getFalseChildren() != null) {
        //If no true and there are false, then show end
%>  
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yes
    </td>
    <td>
      <img src="images/pr-end.gif" border="0" align="absbottom"> Stop
    </td>
  </tr>
<%
      }
      if (falseChildren != null) {
        //Go through the false children
        Iterator falseList = falseChildren.iterator();
        while (falseList.hasNext()) {
          BusinessProcessComponent thisComponent = (BusinessProcessComponent) falseList.next();
%>  
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No <%= (thisComponent.getEnabled()?"":"<font color=\"red\">(disabled)</font>") %>
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
<%
        }
      }
      if (component.getFalseChildren() == null && component.getTrueChildren() != null) {
        //If no false and there are true, then show end
%>  
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No
    </td>
    <td>
      <img src="images/pr-end.gif" border="0" align="absbottom"> Stop
    </td>
  </tr>
<%
      }
      if (component.getTrueChildren() == null && component.getFalseChildren() == null) {
      //If no true and no false, then end of process
%>  
  <tr>
    <td>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Process Finished
    </td>
    <td>
      <img src="images/pr-end.gif" border="0" align="absbottom"> Stop
    </td>
  </tr>
<%
      }
    }
  }
%>
      </table>
    </td>
  </tr>
</table>
