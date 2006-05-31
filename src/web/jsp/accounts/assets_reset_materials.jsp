<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.modules.assets.base.*" %>
<jsp:useBean id="selectedQtys" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
  function init_page() {
    var materials = '';
<%
  Iterator iter = (Iterator) selectedQtys.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
%>
    materials = materials+'<%= key.intValue()+","+ (selectedQtys.get(key) != null && !"".equals(((String) selectedQtys.get(key)).trim()) ? (String) selectedQtys.get(key):"0")+"|" %>';
<%}%>
    if ('<%= isPopup(request) %>'== 'true') {
      opener.resetMaterials(materials);
      self.close();
    } else {
      parent.resetMaterials(materials);
    }
  }
</script>
</body>
