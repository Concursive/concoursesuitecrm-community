<%-- 
  - Copyright Notice: (C) 2000-2004 Dark Horse Ventures, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Dark Horse Ventures. This notice must
  -          remain in place.
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
