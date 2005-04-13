<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    var link = opener.reopen();
    self.close();
  }
</script>
</body>
