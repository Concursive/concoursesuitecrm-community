<jsp:useBean id="clientType" class="org.aspcfs.utils.web.ClientType" scope="session"/>
<%
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
  _editor_url = "htmlarea/";
  _editor_lang = "en";
</script>
<script type="text/javascript" src="htmlarea/htmlarea.js"></script>
<script type="text/javascript">
  HTMLArea.loadPlugin("TableOperations");
  HTMLArea.loadPlugin("ContextMenu");
  var editor = null;
  function initEditor(ta) {
    editor = new HTMLArea(ta);
    editor.registerPlugin(TableOperations);
    editor.registerPlugin("ContextMenu");
    editor.config.toolbar = [
      ["fontname", "space",
       "fontsize", "space",
       "formatblock", "space",
       "bold", "italic", "underline", "space", "undo", "redo"],
      [ "justifyleft", "justifycenter", "justifyright", "justifyfull", "separator",
        "insertorderedlist", "insertunorderedlist", "outdent", "indent", "separator",
        "forecolor", "hilitecolor", "separator",
        "inserthorizontalrule", "createlink", "insertimage", "inserttable", "htmlmode" ]
      ];
    editor.config.pageStyle = 'body, table, td, th, li, p, ul, ol, textarea, input, select, a, th, td { font-size: 8pt; font-family: verdana,helvetica,arial,sans-serif } ';
    setTimeout(function() {
      editor.generate();
    }, 500);
    return false;
  }
</script>
<%-- END EDITOR --%>
<% } %>
