<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<%@ include file="../initPage.jsp" %>
<link rel="stylesheet" href="css/template-help.css" type="text/css">
<html>
<body>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <%-- Introduction --%>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="empty">
<dhv:evaluate if="<%= hasText(Help.getTitle()) %>">
      <tr>
        <td><h2><%= toHtml(Help.getTitle())%></h2></td>
      </tr>
</dhv:evaluate>
      <tr>
        <td><h3>General Information</h3></td>
      </tr>
      <tr>
         <td>
         <%=  !"".equals(toString(Help.getDescription())) ?  toHtml(Help.getDescription()) : "No Introduction available"%>
        </td>
      </tr>
     </table><br>
    </td>
  </tr>
  <tr>
    <td>
    <%-- List the General Features --%>
     <table cellpadding="4" cellspacing="0" width="100%" class="empty">
     <tr>
     <%
        Iterator i = Help.getFeatures().iterator();
        if(i.hasNext()){
      %>
       <td><h3>General Features supported on this page</h3></td>
      </tr>
<%
          while(i.hasNext()){
           HelpFeature thisFeature = (HelpFeature) i.next();
%>
          <tr>
            <td>
            <table cellpadding="0" cellspacing="0" class="empty">
              <tr>
                <td>
                 <li><%= toHtml(thisFeature.getDescription())%></li>
                </td>
              </tr>
            </table>
            </td>
          </tr>
       <% }
        }%>
      </table><br>
      <%-- List the Business Rules --%>
      <table cellpadding="4" cellspacing="0" width="100%" class="empty">
       <tr>
<%
          Iterator br = Help.getBusinessRules().iterator();
          if(br.hasNext()){
%>
        <td><h3>Business Rules supported on this page</h3></td>
      </tr>
<%
            while(br.hasNext()){
             HelpBusinessRule thisRule = (HelpBusinessRule) br.next();
%>
            <tr>
              <td align="left">
              <table cellpadding="0" cellspacing="0" class="empty">
                <tr>
                  <td>
                    <li><%= toHtml(thisRule.getDescription()) %></li>
                  </td>
                </tr>
              </table>
              </td>
            </tr>
         <% }
	 }%>
      </table><br>
      <%-- List the Tips --%>
      <table cellpadding="4" cellspacing="0" width="100%" class="empty">
     <tr>
<%
          Iterator tips = Help.getTips().iterator();
          if(tips.hasNext()){
%>
      <td><h3>Tips on this page</h3></td>
     </tr>
        <%
            while(tips.hasNext()){
             HelpTip thisTip = (HelpTip) tips.next();
         %>
            <tr>
              <td>
              <table class="empty" cellpadding="0" cellspacing="0">
               <tr>
                  <td>
                    <li><%= toHtml(thisTip.getDescription()) %></li>
                  </td>
                </tr>
               </table>
              </td>
            </tr>
         <% }
          }%>
      </table>
    </td>
  </tr>
</table>
</body>
</html>

