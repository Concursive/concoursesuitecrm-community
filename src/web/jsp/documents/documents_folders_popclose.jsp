<%-- 
  - Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: documents_folders_popclose.jsp 28.12.2006 zhenya.zhidok $
  - Description:
  --%>
<html>
<%
  boolean scrollReload = false;
  String location = null;
  String returnPage = (String) request.getAttribute("return");
  if (returnPage == null) {
    returnPage = (String) request.getParameter("return");
  }
  String param = (String) request.getAttribute("param");
  if (param == null) {
    param = request.getParameter("param");
  }
  String param2 = (String) request.getAttribute("param2");
  if (param2 == null) {
    param2 = request.getParameter("param2");
  }
  if (returnPage != null) {
    if ("ExternalContactsDocuments".equals(returnPage)) {
      location = "ExternalContactsDocuments.do?command=View&contactId="+ param;
    }
    if ("AccountsDocuments".equals(returnPage)) {
      location = "AccountsDocuments.do?command=View&orgId="+ param;
    }
    if ("LeadsDocuments".equals(returnPage)) {
      location = "LeadsDocuments.do?command=View&headerId="+ param;
    }
    if ("TroubleTicketsDocuments".equals(returnPage)) {
      location = "TroubleTicketsDocuments.do?command=View&tId="+ param;
    }
    if ("AccountsContactsDocuments".equals(returnPage)) {
      location = "AccountsContactsDocuments.do?command=View&contactId="+ param;
    }
  }
  if (location == null) {
%>
<body onload="window.opener.location.href='MyCFS.do?command=Home'; window.close();">
<% } else { %>
<body onload="window.opener.scrollReload('<%= location %>'); window.close();">
<% } %>
</body>
</html>