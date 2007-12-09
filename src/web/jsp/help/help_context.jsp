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
        <td><h3><dhv:label name="documents.details.generalInformation">General Information</dhv:label></h3></td>
      </tr>
      <tr>
        <td>
          <% if(!"".equals(toString(Help.getDescription()))) {%>
            <%= toHtml(Help.getDescription()) %>
          <%} else {%>
            <dhv:label name="help.noIntroductionAvailable">No Introduction available</dhv:label>
          <%}%>
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
       <td><h3><dhv:label name="help.generalFeaturesSupported.text">General Features supported on this page</dhv:label></h3></td>
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
        <td><h3><dhv:label name="help.businessRulesSupported.text">Business Rules supported on this page</dhv:label></h3></td>
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
      <td><h3><dhv:label name="help.tipsOnThisPage">Tips on this page</dhv:label></h3></td>
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

