<%-- 
  - Copyright Notice: (C) 2000-2004 Team Elements, All Rights Reserved.
  - License: This source code cannot be modified, distributed or used without
  -          written permission from Team Elements. This notice must remain in
  -          place.
  - Author(s): Matt Rajkowski
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="initPage.jsp" %>
<%
  boolean sslEnabled = "https".equals(request.getScheme());
  if (clientType.getType() == -1) {
    clientType.setParameters(request);
  }
  //IE5 not supported message
  if ("ie".equals(clientType.getBrowserId()) &&
      ("win".equals(clientType.getOsString()) && 
       clientType.getBrowserVersion() < 5.5 )) {
%>
<script type="text/javascript">
  function initEditor(ta) {
    return false;
  }
</script>
<%
  } else {
%>
<%-- HTML EDITOR --%>
<script type="text/javascript">
  _editor_url = "http<dhv:evaluate if="<%= sslEnabled %>">s</dhv:evaluate>://<%= getServerUrl(request) %>/htmlarea/";
  _editor_lang = "en";
</script>
<script type="text/javascript" src="./htmlarea/htmlarea.js"></script>
<script type="text/javascript">
  var editor = null;
  function initEditor(ta) {
    editor = new HTMLArea(ta);
    
    var cfg = editor.config;
    cfg.toolbar = [
      ["fontname", "space",
       "fontsize", "space",
       "formatblock", "space",
       "bold", "italic", "underline", "space", "undo", "redo"],
      [ "justifyleft", "justifycenter", "justifyright", "justifyfull", "separator",
        "insertorderedlist", "insertunorderedlist", "outdent", "indent", "separator",
        "forecolor", "hilitecolor", "separator",
        "inserthorizontalrule", "createlink", "insertimage", "inserttable", "htmlmode" ]
      ];
    cfg.pageStyle = 
        "<LINK href=\"css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>-8pt.css\" rel=\"stylesheet\" type=\"text/css\">" +
        "<LINK href=\"css/<%= applicationPrefs.get("LAYOUT.TEMPLATE") %>.css\" rel=\"stylesheet\" type=\"text/css\">";
    setTimeout(function() {
      editor.generate();
    }, 500);
    return false;
  }
</script>
<%-- END EDITOR --%>
<% } %>
