<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
function init_page() {
  try {
    opener.reopenOnDelete();
    return;
  } catch (oException) {
  }
  window.close();
}
</script>
</body>

