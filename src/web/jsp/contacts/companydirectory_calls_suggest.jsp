<jsp:useBean id="CallResult" class="org.aspcfs.modules.contacts.base.CallResult" scope="request"/>
<%@ page import="java.util.*,java.text.*" %>
<script language="JavaScript">
  function suggestCall() {
    <% if(CallResult.getNextRequired()){
      String tmpDate = "";
        try{
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, CallResult.getNextDays());
        tmpDate = DateFormat.getDateInstance(3).format(today.getTime());
        }catch(Exception e){}
    %>
      window.parent.addFollowup('<%= tmpDate %>','<%= CallResult.getNextCallTypeId() %>');
    <% } %>
  }
</script>

<body onLoad="javascript:suggestCall();">
</body>
