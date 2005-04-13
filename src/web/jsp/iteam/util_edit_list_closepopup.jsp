<%-- 
  - Copyright(c) 2004 Team Elements LLC (http://www.teamelements.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Team Elements LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. TEAM ELEMENTS
  - LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL TEAM ELEMENTS LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.aspcfs.utils.web.HtmlOption" %>
<jsp:useBean id="editList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<html>
<script language="javascript" type="text/javascript">
  function updateParentList() {
    // Get handle to list
    var list = opener.document.forms['<%= request.getParameter("form") %>'].elements['<%= request.getParameter("field") %>'];
    // TODO: Get the currently selected value
    
    // Set the option count to 0
    list.options.length = 0;
<%
    Iterator i = editList.iterator();
    while (i.hasNext()) {
      HtmlOption thisOption = (HtmlOption) i.next();
%>
      // Add each option, determine if it was previously selected
      var newOpt = opener.document.createElement("OPTION");
      newOpt.text='<%= thisOption.getText() %>';
      newOpt.value='<%= thisOption.getValue() %>';
      list.options[list.length] = newOpt;
<%
    }
%>
  }
</script>
<body onload="updateParentList();window.close();">
Changes successful.
</body>
</html>
