<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
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
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<%@ include file="../initPage.jsp" %>
<%-- Read in the image map for the graph --%>
<% String includePage = "../graphs/" + (String) request.getAttribute("GraphFileName") + ".map";%>          
<jsp:include page="<%= includePage %>" flush="true"/>
<form name="Dashboard" action="Leads.do?command=Dashboard" method=POST>
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Leads.do">Pipeline</a> > 
Dashboard
</td>
</tr>
</table>
<%-- End Trails --%>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr>
    <%-- Left Column --%>
    <td width="275" valign="top">
      <%-- Graphic --%>
      <table width="275" cellpadding="3" cellspacing="0" border="0" class="pagedList">
        <dhv:evaluate exp="<%= Viewpoints.size() > 1 %>">
        <tr>
          <th valign="top" style="text-align: center;" nowrap<dhv:evaluate if="<%= PipelineViewpointInfo.isVpSelected(User.getUserId()) %>"> class="warning"</dhv:evaluate>>
            <% Viewpoints.setJsEvent("onChange=\"javascript:document.forms[0].reset.value='true';document.forms[0].submit();\""); %>
            Viewpoint: <%= Viewpoints.getHtmlSelect("viewpointId", PipelineViewpointInfo.getVpUserId()) %><br>
          </th>
        </tr>
        </dhv:evaluate>
        <tr>
          <th valign="top" style="text-align: center;" nowrap>
          <% if (request.getSession().getAttribute("leadsoverride") != null) { %>
            Dashboard: <%= toHtml((String)request.getSession().getAttribute("leadsothername")) %>
          <%} else {%>
            My Dashboard
          <%}%>
          </th>
        </tr>
        <tr>
          <td>
            <img src="graphs/<%= request.getAttribute("GraphFileName") %>.jpg" width="275" height="200" border="0" usemap="#<%= request.getAttribute("GraphFileName") %>">
          </td>
        </tr>
        <tr>
          <td style="text-align: center;">
            <img src="images/icons/stock_chart-reorganize-16.gif" align="absMiddle" alt="" />
            <%= GraphTypeList.getHtml() %>&nbsp;
          </td>
        </tr>
      </table>
      <%-- Up a level --%>
      <table width="285" border="0" cellspacing="0" cellpadding="3">
        <tr>
          <td style="text-align: center;" width="100%">
            <% if (!(((String)request.getSession().getAttribute("leadsoverride")) == null)) {
              int prevId =  Integer.parseInt((String)request.getSession().getAttribute("leadspreviousId"));
              %>
            <input type="hidden" name="oid" value="<%=((String)request.getSession().getAttribute("leadsoverride"))%>">
            <a href="Leads.do?command=Dashboard&oid=<%=((String)request.getSession().getAttribute("leadspreviousId"))%><%= PipelineViewpointInfo.getVpUserId() == prevId || ((String)request.getSession().getAttribute("leadspreviousId")).equals(String.valueOf(User.getUserId())) ? "&reset=true" : ""%>">Up One Level</a> |
            <a href="Leads.do?command=Dashboard&reset=true">Back to My Dashboard</a>
            <% } else {%>
                &nbsp;
            <%}%>
          </td>
        </tr>
      </table>
      <%-- User List --%>
      <table width="285" cellpadding="3" cellspacing="0" border="0" class="pagedList">
        <tr>
          <th valign="center" nowrap>
            Reports (Gr. Pipe.)
          </th>
          <th width="125" valign="center">
            Title
          </th>
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
        <tr class="row<%= rowid %>">
          <td valign="center" nowrap>
            <a href="Leads.do?command=Dashboard&oid=<%=thisRec.getId()%>"><%= toHtml(thisRec.getContact().getNameLastFirst()) %></a>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) == 0.0 %>">
              (0K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) > 0 && thisRec.getGrossPipeline(1000) < 1 %>">
              (&lt;1K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisRec.getGrossPipeline(1000) >= 1 %>">
              (<zeroio:currency value="<%= thisRec.getGrossPipeline(1000) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>K)
            </dhv:evaluate>
            <dhv:evaluate exp="<%=!(thisRec.getEnabled())%>"><font color="red">*</font></dhv:evaluate>
          </td>
          <td width="125" valign="center">
            <%= toHtml(thisRec.getContact().getTitle()) %>
          </td>
        </tr>
<%      }
      } else {
%>
        <tr>
          <td valign="center" colspan="3">No Reporting staff.</td>
        </tr>
      <%}%>
      </table>
    </td>
    <%-- Right Column --%>
    <td valign="top" width="100%">
      <%-- Opportunity List --%>
      <table cellpadding="3" cellspacing="0" border="0" width="100%" class="pagedList">
        <tr>
          <th>Opportunity</th>
          <th>Amnt</th>
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
          <td width="100%" class="row<%= rowid %>" valign="center">
            <a href="Leads.do?command=DetailsOpp&headerId=<%= thisHeader.getId() %>&viewSource=dashboard&reset=true"><%= toHtml(thisHeader.getDisplayName()) %>:
            <%= toHtml(thisHeader.getDescription()) %></a>
            <dhv:evaluate if="<%= thisHeader.getComponentCount() > 1 %>">
              (<%= thisHeader.getOwnerComponentCount() %>/<%= thisHeader.getComponentCount() %>)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.hasFiles() %>">
              <%= thisFile.getImageTag() %>
            </dhv:evaluate>
          </td>
          <td width="55" class="row<%= rowid %>">
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) == 0.0 %>">
              (0K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) > 0 && thisHeader.getTotalValue(1000) < 1 %>">
              (&lt;1K)
            </dhv:evaluate>
            <dhv:evaluate if="<%= thisHeader.getTotalValue(1000) >= 1 %>">
              (<zeroio:currency value="<%= thisHeader.getTotalValue(1000) %>" code="<%= applicationPrefs.get("SYSTEM.CURRENCY") %>" locale="<%= User.getLocale() %>" default="&nbsp;"/>K)
            </dhv:evaluate>
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
          <td style="text-align: center;">
            <dhv:pagedListStatus object="DashboardListInfo" showRefresh="false" showControlOnly="true"/>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<input type="hidden" name="reset" value="false">
</form>
