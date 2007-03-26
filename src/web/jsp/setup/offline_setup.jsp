<%-- 
  - Copyright(c) 2007 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id: $
  - Description:
  --%>
<%
  String errorMessage = (String) request.getParameter("msg");
  String fullMessage = "Unknown Error";
  if (errorMessage != null) {
    if (errorMessage.equals("SetupOfflineOK")) {
      fullMessage = "Setup Offline application OK.";
    } else if (errorMessage.equals("SetuptOfflineERROR")) {
      fullMessage = "Setup Offline application failed.";
    } else if (errorMessage.equals("SetupOfflineNotOfflineERROR")) {
      fullMessage = "Application is not running in OFFLINE-mode";
    } else if (errorMessage.equals("SetupOfflineAlreadyConfiguredERROR")) {
      fullMessage = "Offline Application already configured.";
    }
  }
%>
<font color='red'><%=fullMessage%></font>
