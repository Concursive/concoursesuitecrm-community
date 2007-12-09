<%-- 
  - Copyright(c) 2004 Concursive Corporation (http://www.concursive.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Concursive Corporation. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. CONCURSIVE
  - CORPORATION MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL CONCURSIVE CORPORATION OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id$
  - Description: 
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.help.base.*" %>
<jsp:useBean id="Help" class="org.aspcfs.modules.help.base.HelpItem" scope="request"/>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/images.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/confirmDelete.js"></SCRIPT>
<%@ include file="../initPage.jsp" %>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <%-- Title --%>
  <tr>
    <td><dhv:label name="qa.pageTitle.colon" param='<%= "help.title="+toHtml(Help.getTitle()) %>'><strong>Page Title:</strong> <%= toHtml(Help.getTitle()) %></dhv:label><br />
    &nbsp;</td>
  </tr>
  <%-- Introduction --%>
  <tr>
    <td>
      <table cellpadding="4" cellspacing="0" width="100%" class="details">
      <tr>
       <th>
          <strong><dhv:label name="qa.introduction">Introduction</dhv:label></strong>
          <dhv:permission name="qa-edit">[<a href="javascript:popURL('QA.do?command=ModifyIntro&id=<%= Help.getId() %>&popup=true', 'QA_Intro','500','250','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>]</dhv:permission>
       </th>
      </tr>
       <tr>
         <td>
<% if(!"".equals(toString(Help.getDescription()))) {%>
  <%= toHtml(Help.getDescription()) %>
<%} else {%>
  <dhv:label name="help.noIntroductionAvailable">No Introduction available</dhv:label>
<%}%>
        </td>
       </tr>
     </table><br>
    </td>
  </tr>
  <tr>
    <td>
    <%-- List the General Features --%>
     <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
     <tr>
       <th>
          <strong><dhv:label name="help.generalFeaturesSupported.text">General Features supported on this page</dhv:label></strong><dhv:permission name="qa-add"> [<a href="javascript:popURL('HelpFeatures.do?command=PrepareFeature&linkHelpId=<%= Help.getId()%>&popup=true', 'QA_Feature_Add','600','250','yes','yes');"><dhv:label name="qa.addNew">Add New</dhv:label></a>]</dhv:permission>
       </th>
      </tr>
      <%
        Iterator i = Help.getFeatures().iterator();
        if(i.hasNext()){
          while(i.hasNext()){
           HelpFeature thisFeature = (HelpFeature) i.next();
       %>
          <tr>
            <td align="left">
            <table cellpadding="0" cellspacing="0" class="empty">
              <tr>
                <dhv:permission name="qa-edit,qa-delete">
                 <td valign="top" nowrap>
                  <dhv:permission name="qa-edit"><a href="javascript:popURL('HelpFeatures.do?command=ModifyFeature&id=<%= thisFeature.getId() %>&linkHelpId=<%= Help.getId() %>&popup=true', 'QA_Feature_Add','600','250','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>&nbsp;</dhv:permission><dhv:permission name="qa-delete"><a href="javascript:confirmDelete('HelpFeatures.do?command=DeleteFeature&id=<%= thisFeature.getId() %>')"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Del">Del</dhv:label></a>&nbsp;</dhv:permission>
                  <dhv:permission name="qa-edit"><a href="javascript:changeImages('feature<%= thisFeature.getId() %>','QA.do?command=ProcessFeature&id=box.gif|gif|'+<%= thisFeature.getId() %>+'|<%= thisFeature.getComplete() ? "0" : "1" %>','QA.do?command=ProcessFeature&id=box-checked.gif|gif|'+<%= thisFeature.getId() %>+'|1');"  onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/<%= thisFeature.getComplete() ? "box-checked.gif" : "box.gif" %>" name="feature<%= thisFeature.getId() %>" id='<%= thisFeature.getComplete() ? "1" : "0" %>' border="0" title="Click to change"></a></dhv:permission>
                </td>
                </dhv:permission>
                <td>
                 <%= toHtml(thisFeature.getDescription())%>
                </td>
              </tr>
            </table>
            </td>
          </tr>
       <% }
        }else{
      %>
        <tr>
          <td>
            <dhv:label name="qa.noFeaturesFound">No features found</dhv:label>
          </td>
        </tr>
      <%}%>
      </table><br>
      <%-- List the Business Rules --%>
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
       <tr>
        <th>
          <strong><dhv:label name="help.businessRulesSupported.text">Business Rules supported on this page</dhv:label></strong> <dhv:permission name="qa-add">[<a href="javascript:popURL('HelpRules.do?command=PrepareRule&linkHelpId=<%= Help.getId()%>&popup=true', 'QA_Rule_Add','600','175','yes','yes');"><dhv:label name="qa.addNew">Add New</dhv:label></a>]</dhv:permission>
        </th>
      </tr>
        <%
          Iterator br = Help.getBusinessRules().iterator();
          if(br.hasNext()){
            while(br.hasNext()){
             HelpBusinessRule thisRule = (HelpBusinessRule) br.next();
         %>
            <tr>
              <td align="left">
              <table cellpadding="0" cellspacing="0" class="empty">
                <tr>
                <dhv:permission name="qa-edit,qa-delete">
                 <td valign="top"  nowrap>
                  <dhv:permission name="qa-edit"><a href="javascript:popURL('HelpRules.do?command=ModifyRule&id=<%= thisRule.getId() %>&linkHelpId=<%= Help.getId() %>&popup=true', 'QA_Rule_Add','600','175','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>&nbsp;</dhv:permission><dhv:permission name="qa-delete"><a href="javascript:confirmDelete('HelpRules.do?command=DeleteRule&id=<%= thisRule.getId() %>')"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Del">Del</dhv:label></a>&nbsp;</dhv:permission>
                  <dhv:permission name="qa-edit"><a href="javascript:changeImages('rule<%= thisRule.getId() %>','QA.do?command=ProcessRule&id=box.gif|gif|'+<%= thisRule.getId() %>+'|<%= thisRule.getComplete() ? "0" : "1" %>','QA.do?command=ProcessRule&id=box-checked.gif|gif|'+<%= thisRule.getId() %>+'|1');"  onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/<%= thisRule.getComplete() ? "box-checked.gif" : "box.gif" %>" name="rule<%= thisRule.getId() %>" id='<%= thisRule.getComplete() ? "1" : "0" %>' border="0" title="Click to change"></a></dhv:permission>
                  </td>
                  </dhv:permission>
                  <td>
                    <%= toHtml(thisRule.getDescription()) %>
                  </td>
                </tr>
              </table>
              </td>
            </tr>
         <% }
          }else{
        %>
          <tr>
            <td>
              <dhv:label name="qa.noRulesFound">No rules found</dhv:label>
            </td>
          </tr>
        <%}%>
      </table><br>
      <%-- List the Notes --%>
      <table cellpadding="4" cellspacing="0" width="100%" class="pagedList">
     <tr>
      <th>
        <strong><dhv:label name="qa.notesOnThisPage">Notes on this page</dhv:label></strong> <dhv:permission name="qa-add">[<a href="javascript:popURL('HelpNotes.do?command=PrepareNote&linkHelpId=<%= Help.getId()%>&popup=true', 'QA_Note_Add','600','175','yes','yes');"><dhv:label name="qa.addNew">Add New</dhv:label></a>]</dhv:permission>
      </th>
     </tr>
        <%
          Iterator notes = Help.getNotes().iterator();
          if(notes.hasNext()){
            while(notes.hasNext()){
             HelpNote thisNote = (HelpNote) notes.next();
         %>
            <tr>
              <td align="left">
              <table class="empty" cellpadding="0" cellspacing="0">
               <tr>
               <dhv:permission name="qa-edit,qa-delete">
                <td valign="top" nowrap>
                  <dhv:permission name="qa-edit"><a href="javascript:popURL('HelpNotes.do?command=ModifyNote&id=<%= thisNote.getId() %>&linkHelpId=<%= Help.getId() %>&popup=true', 'QA_Note_Add','600','175','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>&nbsp;</dhv:permission><dhv:permission name="qa-delete"><a href="javascript:confirmDelete('HelpNotes.do?command=DeleteNote&id=<%= thisNote.getId() %>')"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Del">Del</dhv:label></a>&nbsp;</dhv:permission>
                  <dhv:permission name="qa-edit"><a href="javascript:changeImages('note<%= thisNote.getId() %>','QA.do?command=ProcessNote&id=box.gif|gif|'+<%= thisNote.getId() %>+'|<%= thisNote.getComplete() ? "0" : "1" %>','QA.do?command=ProcessNote&id=box-checked.gif|gif|'+<%= thisNote.getId() %>+'|1');"  onMouseOver="this.style.color='blue';window.status='Change Status';return true;" onMouseOut="this.style.color='black';window.status='';return true;"><img src="images/<%= thisNote.getComplete() ? "box-checked.gif" : "box.gif" %>" name="note<%= thisNote.getId() %>" id='<%= thisNote.getComplete() ? "1" : "0" %>' border="0" title="Click to change"></a></dhv:permission>
                  </td>
                  </dhv:permission>
                  <td>
                    <%= toHtml(thisNote.getDescription()) %>
                 </td>
                </tr>
               </table>
              </td>
            </tr>
         <% }
          }else{
        %>
        <tr>
          <td>
            <dhv:label name="qa.noNotesFound">No notes found</dhv:label>
          </td>
        </tr>
      <%}%>
      </table><br>
      <%-- List the Tips --%>
      <table cellpadding="2" cellspacing="0" width="100%" class="pagedList">
     <tr>
      <th>
        <strong><dhv:label name="help.tipsOnThisPage">Tips on this page</dhv:label></strong> <dhv:permission name="qa-add">[<a href="javascript:popURL('HelpTips.do?command=PrepareTip&linkHelpId=<%= Help.getId()%>&popup=true', 'QA_Tip_Add','600','175','yes','yes');"><dhv:label name="qa.addNew">Add New</dhv:label></a>]</dhv:permission>
      </th>
     </tr>
        <%
          Iterator tips = Help.getTips().iterator();
          if(tips.hasNext()){
            while(tips.hasNext()){
             HelpTip thisTip = (HelpTip) tips.next();
         %>
            <tr>
              <td align="left">
              <table class="empty" cellpadding="0" cellspacing="0">
               <tr>
               <dhv:permission name="qa-edit,qa-delete">
                  <td valign="top" nowrap>
                  <dhv:permission name="qa-edit"><a href="javascript:popURL('HelpTips.do?command=ModifyTip&id=<%= thisTip.getId() %>&linkHelpId=<%= Help.getId() %>&popup=true', 'QA_Tip_Add','600','175','yes','yes');"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Edit">Edit</dhv:label></a>&nbsp;</dhv:permission><dhv:permission name="qa-delete"><a href="javascript:confirmDelete('HelpTips.do?command=DeleteTip&id=<%= thisTip.getId() %>')"><dhv:label name="accounts.accounts_contacts_oppcomponent_list.Del">Del</dhv:label></a></dhv:permission>
                  </td>
                  </dhv:permission>
                  <td>
                    <%= toHtml(thisTip.getDescription()) %>
                  </td>
                </tr>
               </table>
              </td>
            </tr>
         <% }
          }else{
        %>
        <tr>
          <td>
            <dhv:label name="qa.noTipsFound">No tips found</dhv:label>
          </td>
        </tr>
      <%}%>
      </table>
    </td>
  </tr>
  <tr>
    <td>
      <br><input type="button" value="<dhv:label name="button.close">Close</dhv:label>" onClick="javascript:window.close();">
    </td>
  </tr>
</table>
