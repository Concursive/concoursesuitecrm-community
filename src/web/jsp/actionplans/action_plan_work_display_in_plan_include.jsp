<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: action_plan_work_display_in_plan_include.jsp 18354 2007-01-10 10:36:09Z vadim.vishnevsky@corratech.com $
  - Description:
  --%>
              <%-- No Attachment --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_NOTHING) %>">
              <nobr>
                <%= toHtml(thisStep.getPlanListLabel()) %>
              </nobr>  
              </dhv:evaluate>
              <%-- Account Contacts --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACCOUNT_CONTACT) %>">
              <nobr>
                <%= toHtml(thisStep.getPlanListLabel()) %>: 
                <img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">
                      <%= toHtml(thisItemWork.getContact().getNameLastFirst()) %>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">                 
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlan.attachContact">Attach Contact</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
              </nobr>      
              </dhv:evaluate>

              <%-- Opportunity --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_OPPORTUNITY) %>">
              <nobr>
                  <%= toHtml(thisStep.getPlanListLabel()) %>: 
                  <img border="0" src="images/icons/stock_form-currency-field-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getComponent() != null %>">
                      <%= toHtml(CurrencyFormat.getCurrencyString(thisItemWork.getComponent().getGuess(), User.getLocale(), applicationPrefs.get("SYSTEM.CURRENCY")) + " " + NumberFormat.getPercentInstance().format(thisItemWork.getComponent().getCloseProb())) %>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getComponent() == null %>">
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.attachOpportunity.text">Attach Opportunity</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
              </nobr>
              </dhv:evaluate>

              <%-- Document --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_DOCUMENT) %>">
              <nobr>
                  <%= toHtml(thisStep.getPlanListLabel()) %>: 
                  <img border="0" src="images/icons/stock_new_bullet-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() != null %>">                          
                            <%= toHtml(thisItemWork.getFileItem().getSubject()) %>                        
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() == null %>">                          
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.attachDocument.text">Attach Document</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
              </nobr>
              </dhv:evaluate>
              
              <%-- Update Rating --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.UPDATE_RATING) %>">
              <nobr>
                <%= toHtml(thisStep.getPlanListLabel()) %>
              </nobr>
              </dhv:evaluate>
             
             <%-- Single Note (Date oriented) --%>
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE %>">
             <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <img border="0" src="images/icons/stock_form-date-field-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.attachDate.text">Attach Date</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
             </nobr>       
             </dhv:evaluate>
             
             <%-- Multiple Notes --%>
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
             <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <img border="0" src="images/icons/stock_insert-note-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.attachNote.text">Attach Note</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
             </nobr>       
             </dhv:evaluate>

             <%-- Lookup List Multiple Selection --%>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_LOOKUPLIST_MULTIPLE) %>">
             <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() != null %>">
                      <%= toHtml(thisItemWork.getSelectionList().getDisplayHtml()) %>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() == null %>">
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.attachItems.text">Attach Items</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
             </nobr>       
             </dhv:evaluate>
             
             <%-- Add Relationships --%>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_RELATIONSHIP) %>">
             <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" />
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() != null %>">
                      <%= toHtml(thisItemWork.getRelationshipList().getDisplayHtml()) %>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() == null %>">
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.addRelationships.text">Add Relationship</dhv:label>
                    </dhv:evaluate>
                    </dhv:evaluate>
             </nobr>       
             </dhv:evaluate>
             
             <%-- View Account --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.VIEW_ACCOUNT) %>">
              <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <img border="0" src="images/icons/stock_account-16.gif" align="absmiddle" />
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlans.reviewAccount.text">Review Account</dhv:label>
                    </dhv:evaluate>
              </nobr>
              </dhv:evaluate>
             
             
             <%-- Activity --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACTIVITY) %>">
              <nobr>
                <%= toHtml(thisStep.getPlanListLabel()) %>: Attach Activity
              </nobr>  
              </dhv:evaluate>


              <%-- Custom Folder --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_FOLDER) %>">
              <nobr>
                <%= toHtml(thisStep.getPlanListLabel()) %>: 
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() != null %>">
                  <img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" />
                      <%= toHtml(thisItemWork.getCustomFieldCategory().getFirstFieldValue()) %>
                </dhv:evaluate>
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() == null %>">
                  <img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" />
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="actionPlan.attachFolder">Attach Folder</dhv:label>
                    </dhv:evaluate>
                </dhv:evaluate>
              </nobr>
              </dhv:evaluate>

              <%-- Add Recipient --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ADD_RECIPIENT) %>">
              <nobr>
                    <%= toHtml(thisStep.getPlanListLabel()) %>: 
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">                         
                            <%= toHtml(thisItemWork.getContact().getNameFull()) %>
                         
                        (<dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label>)
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">                          
                      <dhv:evaluate if="<%= thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel()) %>">
                             <%=toHtml(thisItemWork.getLabel())%>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= !(thisItemWork.getLabel() != null  && !"".equals(thisItemWork.getLabel())) %>">
                      <dhv:label name="campaigns.addRecipient">Add Recipient</dhv:label>
                    </dhv:evaluate>
                        (<dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label>)
                    </dhv:evaluate>
              </nobr>      
              </dhv:evaluate>


