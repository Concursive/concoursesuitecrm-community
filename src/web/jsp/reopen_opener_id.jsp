<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="id" class="java.lang.String" scope="request"/>
<body onLoad="forwardLink();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function forwardLink(){
    if ('<%= (id != null && !"".equals(id)) %>' == 'true') {
      var link = opener.reopenId('<%= id %>');
    } else {
      var link = opener.reopen();
    }
    self.close();
  }
</script>
</body>
