<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,com.zeroio.iteam.base.*" %>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    var link = opener.parent.reopen();
    self.close();
  }
</script>
</body>
