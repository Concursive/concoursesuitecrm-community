<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*" %>
<%@ page import="org.aspcfs.modules.pipeline.base.*" %>
<%@ page import="com.zeroio.iteam.base.*" %>
<%@ page import="org.aspcfs.modules.admin.base.User" %>
<jsp:useBean id="ShortChildList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="Viewpoints" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="PipelineViewpointInfo" class="org.aspcfs.utils.web.ViewpointInfo" scope="session"/>
<jsp:useBean id="oppList" class="org.aspcfs.modules.pipeline.base.OpportunityHeaderList" scope="request"/>
<jsp:useBean id="GraphTypeList" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%-- Read in the image map for the graph --%>
<% String includePage = "../graphs/" + (String) request.getAttribute("GraphFileName") + ".map";%>          
<jsp:include page="<%= includePage %>" flush="true"/>
<form name="Dashboard" action="Leads.do?command=Dashboard" method=POST>
<a href="Leads.do">Pipeline Management</a> > 
Dashboard<br>
<hr color="#BFBFBB" noshade>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="275" valign="top">
      <%-- Graphic --%>
      <table width="275" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <dhv:evaluate exp="<%= Viewpoints.size() > 1 %>">
        <tr class="<%= (PipelineViewpointInfo.isVpSelected(User.getUserId())?"warning":"title") %>">
          <td valign="top" align="center" nowrap>
            <% Viewpoints.setJsEvent("onChange=\"javascript:document.forms[0].reset.value='true';document.forms[0].submit();\""); %>
            Viewpoint: <%= Viewpoints.getHtmlSelect("viewpointId", PipelineViewpointInfo.getVpUserId()) %><br>
          </td>
        </tr>
        </dhv:evaluate>
        <tr class="title">
          <td valign="top" align="center" nowrap>
          <% if (request.getSession().getAttribute("leadsoverride") != null) { %>
            Dashboard: <%= toHtml((String)request.getSession().getAttribute("leadsothername")) %>
          <%} else {%>
            My Dashboard
          <%}%>
          </td>
        </tr>
        <tr>
          <td>
            <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="275" height="200" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>">
          </td>
        </tr>
        <tr>
          <td align="center">
            <%= GraphTypeList.getHtml() %>&nbsp;
          </td>
        </tr>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td align="center" width="100%">
            <% if (!(((String)request.getSession().getAttribute("leadsoverride")) == null)) {%>
            <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("leadsoverride"))%>">
            <a href="Leads.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("leadspreviousId"))%>">Up One Level</a> |
            <a href="Leads.do?command=Dashboard&reset=true">Back to My Dashboard</a>
            <%} else {%>
                &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td valign="center" nowrap>
            Reports ($Gr. Pipe.)
          </td>
          <td width="125" valign=center>
            Title
          </td>
        </tr>
<%
    Iterator x = ShortChildList.iterator();
      if ( x.hasNext() ) {
        int rowid = 0;
        while (x.hasNext()) {
          if (rowid != 1) {
            rowid = 1;
          } else {
            rowid = 2;
          }
          User thisRec = (User)x.next();
%>
        <tr>
          <td class="row<%= rowid %>" valign=center nowrap>
            <a href="Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            ($<%=thisRec.getGrossPipelineCurrency(1000)%>K)
            <dhv:evaluate exp="<%=!(thisRec.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width=125 class="row<%= rowid %>" valign=center>
            <%= toHtml(thisRec.getContact().getTitle()) %>
          </td>
        </tr>
<%      }
      } else {
%>
        <tr>
          <td valign=center colspan=3>No Reporting staff.</td>
        </tr>
      <%}%>
      </table>
    </td>
    <%-- Right Column --%>
    <td valign=top width="100%">
      <%-- Opportunity List --%>
      <table width="100%" cellpadding="3" cellspacing="0" border="1" bordercolorlight="#000000" bordercolor="#FFFFFF">
        <tr class="title">
          <td>Opportunity</td>
          <td align="left">Amnt</td>
        </tr>
<%
	Iterator n = oppList.iterator();
  FileItem thisFile = new FileItem();
  if ( n.hasNext() ) {
    int rowid = 0;
    while (n.hasNext()) {
      rowid = (rowid != 1?1:2);
      OpportunityHeader thisHeader = (OpportunityHeader) n.next();
%>    
				<tr>
          <td width="100%" class="row<%= rowid %>" valign=center>
            <a href="Leads.do?command=DetailsOpp&headerId=<%= thisHeader.getId() %>&return=dashboard&reset=true"><%= toHtml(thisHeader.getAccountName()) %>:&nbsp;<%= toHtml(thisHeader.getDescription()) %></a>
            (<%= thisHeader.getComponentCount() %>)
            <dhv:evaluate if="<%= thisHeader.hasFiles() %>">
              <%= thisFile.getImageTag() %>
            </dhv:evaluate>
          </td>
          <td width="55" class="row<%= rowid %>">
            $<%= thisHeader.getTotalValue(1000) %>K
          </td>
        </tr>
<%
      }
	  } else {
%>
        <tr>
          <td valign="center" colspan="7">No opportunities found.</td>
        </tr>
<%}%>
      </table>
      <table width="100%" border="0" cellpadding="3">
        <tr>
          <td align="center">
            <dhv:pagedListStatus object="DashboardListInfo" showRefresh="false" showControlOnly="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="reset" value="false">
</form>
