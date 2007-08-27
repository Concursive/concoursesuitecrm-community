<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request"/>
<script language="JavaScript" TYPE="text/javascript">
parent.opener.window.location.href='<%=refreshUrl%><%= request.getAttribute("actionError") != null ? "&actionError=" + request.getAttribute("actionError") :""%>';
window.close();
</script>
