<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.base.*,org.aspcfs.utils.web.*,org.aspcfs.utils.*" %>
<%@ page import="org.aspcfs.modules.communications.base.*,org.aspcfs.utils.StringUtils" %>
<jsp:useBean id="selectedList" class="java.util.HashMap" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onLoad="javascript:init_page();">
<script type="text/javascript">
  function init_page() {
    var groups = '';
<%
  Iterator iter = (Iterator) selectedList.keySet().iterator();
  while (iter.hasNext()) {
    Integer key = (Integer) iter.next();
%>
    groups = groups+'<%= key.intValue()+","+StringUtils.jsStringEscape((String) selectedList.get(key))+"|" %>';
<%}%>
    opener.resetUserGroups(groups);
    self.close();
  }
</script>
</body>
