<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
  - Version: $Id$
  - Description: 
  --%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<%@ page import="java.util.*,java.text.*,org.aspcfs.utils.DateUtils" %>
<script language="JavaScript">
  function suggestCall() {
    <% if(CallResult.getNextRequired()){
      String tmpDate = "";
        try{
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, CallResult.getNextDays());

        SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(
                                                          DateFormat.SHORT, User.getUserRecord().getLocale());
        formatter.applyPattern(DateUtils.get4DigitYearDateFormat(formatter.toPattern()));
        tmpDate = formatter.format(today.getTime());
        }catch(Exception e){}
    %>
      window.parent.addFollowup('<%=tmpDate%>','<%= CallResult.getNextCallTypeId() %>');
    <% } %>
  }
</script>

<body onLoad="javascript:suggestCall();">
</body>
