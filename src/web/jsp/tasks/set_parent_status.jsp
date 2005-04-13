<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat,org.aspcfs.modules.products.base.*,org.aspcfs.modules.quotes.base.*,com.zeroio.iteam.base.*" %>
<jsp:useBean id="image" class="java.lang.String" scope="request" />
<jsp:useBean id="imageId" class="java.lang.String" scope="request" />
<body onLoad="setCurrentStatus();">
<%@ include file="../initPage.jsp" %>
<script type="text/javascript">
  function setCurrentStatus(){
    var img = parent.window.document['<%= imageId %>'];
    img.src = '<%= image %>';
  }
</script>
</body>
