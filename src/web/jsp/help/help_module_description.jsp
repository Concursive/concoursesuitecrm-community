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
<jsp:useBean id="helpModule" class="org.aspcfs.modules.help.base.HelpModule" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <%-- Title --%>
  <tr>
  <td> <strong>Module Name:<%=((helpModule.getModuleName() == null) || ("".equals(helpModule.getModuleName())))? "Not Specified" : helpModule.getModuleName()%></strong>
  <dhv:evaluate if="<%= hasText(helpModule.getModuleName()) %>">
    <dhv:permission name="qa-edit">[<a href="javascript:popURL('Help.do?command=ModifyDescription&id=<%= helpModule.getId() %>&action=<%= helpModule.getRelatedAction()%>&popup=true', 'Help_Description','700','500','yes','yes');">Edit</a>]</td></dhv:permission>
  </dhv:evaluate>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <th>
          <strong>Brief Description</strong> 
       </th>
      </tr>
       <tr>
         <td>
          <%=toHtml(helpModule.getBriefDescription())%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <th>
          <strong>Detail Description</strong>
       </th>
      </tr>
       <tr>
         <td>
          <%=toHtml(helpModule.getDetailDescription())%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  </table>
