              <%-- No Attachment --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_NOTHING) %>">
                &nbsp;
              </dhv:evaluate>
              <%-- Account Contacts --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACCOUNT_CONTACT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">
                      <td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&siteId=<%= actionPlanWork.getOrganization().getSiteId() %>&listView=accountcontacts&reset=true&addNewContact=true&hiddensource=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changecontact<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getContact().getNameLastFirst()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">
                      <td valign="middle">
                        <a href="javascript:popContactsListSingle('contactid','changecontact<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&siteId=<%= actionPlanWork.getOrganization().getSiteId() %>&listView=accountcontacts&reset=true&addNewContact=true&hiddensource=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changecontact<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachContact\">Attach Contact</dhv:label>" %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>

              <%-- Opportunity --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_OPPORTUNITY) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_form-currency-field-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getComponent() != null %>">
                      <td valign="middle"><a href="javascript:popOppForm('opportunityid','changeopportunity<%= thisItemWork.getId() %>','<%= thisItemWork.getComponent().getHeaderId() %>','<%= thisItemWork.getComponent().getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeopportunity<%= thisItemWork.getId() %>"><%= toHtml(CurrencyFormat.getCurrencyString(thisItemWork.getComponent().getGuess(), User.getLocale(), applicationPrefs.get("SYSTEM.CURRENCY")) + " " + NumberFormat.getPercentInstance().format(thisItemWork.getComponent().getCloseProb())) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getComponent() == null %>">
                      <td valign="middle"><a href="javascript:popOppForm('opportunityid','changeopportunity<%= thisItemWork.getId() %>','-1','-1','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&source=attachplan&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeopportunity<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachOpportunity.text\">Attach Opportunity</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>

              <%-- Document --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_DOCUMENT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_new_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() != null %>">
                      <td valign="middle">
                        <a href="javascript:popAccountFileItemList('fileitemid','changefileitem<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&addNewFile=true&source=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changefileitem<%= thisItemWork.getId() %>">
                            <%= toHtml(thisItemWork.getFileItem().getSubject()) %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getFileItem() == null %>">
                      <td valign="middle">
                        <a href="javascript:popAccountFileItemList('fileitemid','changefileitem<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&addNewFile=true&source=attachplan&actionplan=true&actionStepWork=<%= thisItemWork.getId() %>');">
                          <div id="changefileitem<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachDocument.text\">Attach Document</dhv:label>" %>
                          </div>
                        </a>
                      </td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>
              
              <%-- Update Rating --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.UPDATE_RATING) %>">
                <dhv:evaluate if="<%= thisItemWork.userHasPermission(User.getUserRecord().getId()) %>">
                  <%-- The user logged-in is the steps owner --%>
                  <%= ratingLookup.getHtmlSelect("rating", actionPlanWork.getOrganization().getRating()) %>
                  <input type="hidden" name="ratingItemId" value=<%= thisItemWork.getId() %>/>
                </dhv:evaluate>
                <dhv:evaluate if="<%= !thisItemWork.userHasPermission(User.getUserRecord().getId()) %>">
                  <%-- The user logged-in is NOT the steps owner, so disable the lookup --%>
                  <%= ratingLookup.getHtmlSelect("rating", actionPlanWork.getOrganization().getRating(), true) %>
                </dhv:evaluate>
             </dhv:evaluate>
             
             <%-- Single Note (Date oriented) --%>
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_SINGLE %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_form-date-field-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachDate.text\">Attach Date</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- Multiple Notes --%>
             <dhv:evaluate if="<%= thisItemWork.getActionId() == ActionStep.ATTACH_NOTE_MULTIPLE %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_insert-note-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getNote() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><zeroio:tz timestamp="<%=  thisItemWork.getNote().getSubmitted() %>" timeZone="<%= User.getUserRecord().getTimeZone() %>" dateOnly="true"/></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getNote() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('note', 'noteid','changenote<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachnote&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changenote<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachNote.text\">Attach Note</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>

             <%-- Lookup List Multiple Selection --%>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_LOOKUPLIST_MULTIPLE) %>">
               <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('selection', 'selectionid','changeselection<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachselection&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeselection<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getSelectionList().getDisplayHtml()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getSelectionList() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('selection' ,'selectionid','changeselection<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&source=attachselection&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changeselection<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.attachItems.text\">Attach Items</dhv:label>" %></div></a></td>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- Add Relationships --%>
             <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_RELATIONSHIP) %>">
               <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_list_bullet-16.gif" align="absmiddle" /></td>
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() != null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('relation', 'relationid','changerelation<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&init=true&source=attachrelation&planWorkId=<%= actionPlanWork.getId() %>&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changerelation<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getRelationshipList().getDisplayHtml()) %></div></a></td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getRelationshipList() == null %>">
                      <td valign="middle"><a href="javascript:popActionPlanAttachment('relation' ,'relationid','changerelation<%= thisItemWork.getId() %>','orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&init=true&source=attachrelation&planWorkId=<%= actionPlanWork.getId() %>&actionStepWork=<%= thisItemWork.getId() %>');"><div id="changerelation<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.addRelationships.text\">Add Relationship</dhv:label>" %></div></a>
                    </dhv:evaluate>
                  </tr>
                </table>
             </dhv:evaluate>
             
             <%-- View Account --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.VIEW_ACCOUNT) %>">
                <table class="empty" border="0">
                  <tr>
                    <td><img border="0" src="images/icons/stock_account-16.gif" align="absmiddle" /></td>
                    <td valign="middle"><a href="javascript:popURL('Accounts.do?command=Details&orgId=<%= actionPlanWork.getOrganization().getOrgId() %>&actionplan=true&popup=true','Action_Plan','700','425','yes','yes');"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlans.reviewAccount.text\">Review Account</dhv:label>" %></a></td>
                  </tr>
                </table>
              </dhv:evaluate>
             
             
             <%-- Activity --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_ACTIVITY) %>">
              Attach Activity
              <%--
                  <dhv:evaluate if="<%= thisItemWork.getActivity() != null %>">
                    <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td><td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact','listView=accountcontacts&actionplan=true&itemId=<%= thisItemWork.getId() %>');"><div id="changecontact"><%= toHtml(thisItemWork.getContact().getNameFirstLast()) %></div></a></td></tr></table>
                  </dhv:evaluate>
                  <dhv:evaluate if="<%= thisItemWork.getActivity() == null %>">
                    <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td><td valign="middle"><a href="javascript:popContactsListSingle('contactid','changecontact','listView=accountcontacts&actionplan=true&itemId=<%= thisItemWork.getId() %>');"><div id="changecontact"><%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachContact\">Attach Contact</dhv:label>" %></div></a></td></tr></table>
                  </dhv:evaluate>
                  <input type="hidden" name="contact" id="contactid" value=""/>
                --%>
              </dhv:evaluate>


              <%-- Custom Folder --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_FOLDER) %>">
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() != null %>">
                  <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td>
                    <td valign="middle"><a href="javascript:popFolderForm('recordid<%= thisItemWork.getId() %>','changefolder<%= thisItemWork.getId() %>','<%= thisItemWork.getStep().getCustomFieldCategoryId() %>','<%= thisItemWork.getCustomFieldCategory().getRecordId() %>','<%= thisItemWork.getPlanWork().getOrganization().getOrgId() %>','actionplan=true&source=attachplan&actionStepId=<%= thisItemWork.getId() %>');">
                      <div id="changefolder<%= thisItemWork.getId() %>"><%= toHtml(thisItemWork.getCustomFieldCategory().getFirstFieldValue()) %></div></a></td></tr></table>
                </dhv:evaluate>
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() == null %>">
                  <table class="empty" border="0"><tr><td><img border="0" src="images/icons/stock_bcard-16.gif" align="absmiddle" /></td>
                    <td valign="middle"><a href="javascript:popFolderForm('recordid<%= thisItemWork.getId() %>','changefolder<%= thisItemWork.getId() %>','<%= thisItemWork.getStep().getCustomFieldCategoryId() %>','-1','<%= thisItemWork.getPlanWork().getOrganization().getOrgId() %>','actionplan=true&source=attachplan&actionStepId=<%= thisItemWork.getId() %>');">
                      <div id="changefolder<%= thisItemWork.getId() %>"><%= thisItemWork.getLabel() != null && !"".equals(thisItemWork.getLabel()) ? thisItemWork.getLabel() : "<dhv:label name=\"actionPlan.attachFolder\">Attach Folder</dhv:label>" %></div></a></td></tr></table>
                </dhv:evaluate>
                <input type="hidden" name="recordid<%= thisItemWork.getId() %>" id="recordid<%= thisItemWork.getId() %>" value=""/>
              </dhv:evaluate>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ATTACH_FOLDER) %>">
                <dhv:evaluate if="<%= thisItemWork.getCustomFieldCategory() != null %>">
                  <input type="hidden" name="contact" id="contactid" value=""/>
                </dhv:evaluate>
              </dhv:evaluate>

              <%-- Add Recipient --%>
              <dhv:evaluate if="<%= (thisItemWork.getActionId() == ActionStep.ADD_RECIPIENT) %>">
                <table class="empty" border="0">
                  <tr>
                    <dhv:evaluate if="<%= thisItemWork.getContact() != null %>">
                      <td align="middle" valign="top">
                        <a href="javascript:popContactsListSingle('recipientId','<%= thisItemWork.getStep().getCampaignId() %>','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&recipient=true&orgId=<%= (actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1) %>&hiddensource=actionplanrecipients&actionItemId=<%= thisItemWork.getId() %>&allowDuplicateRecipient=<%= thisItemWork.getStep().getAllowDuplicateRecipient() %>&listView=accountcontacts&filters=accountcontacts');">
                          <div id="changerecipient<%= thisItemWork.getId() %>">
                            <%= toHtml(thisItemWork.getContact().getNameFull()) %>
                          </div>
                        </a> (<a href="CampaignManager.do?command=PreviewRecipients&id=<%= thisItemWork.getStep().getCampaignId() %>"><dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label></a>)</td>
                    </dhv:evaluate>
                    <dhv:evaluate if="<%= thisItemWork.getContact() == null %>">
                      <td align="middle" valign="top">
                        <a href="javascript:popContactsListSingle('recipientId','<%= thisItemWork.getStep().getCampaignId() %>','<%= User.getUserRecord().getSiteId() == -1?"includeAllSites=true&siteId=-1":"mySiteOnly=true&siteId="+User.getUserRecord().getSiteId() %>&recipient=true&orgId=<%= (actionPlanWork.getOrganization() != null ? actionPlanWork.getOrganization().getOrgId() : -1) %>&hiddensource=actionplanrecipients&actionItemId=<%= thisItemWork.getId() %>&allowDuplicateRecipient=<%= thisItemWork.getStep().getAllowDuplicateRecipient() %>&listView=accountcontacts&filters=accountcontacts');">
                          <div id="changerecipient<%= thisItemWork.getId() %>">
                            <%= thisItemWork.getLabel() != null ? thisItemWork.getLabel() : "<dhv:label name=\"\">Add Recipient</dhv:label>" %>
                          </div>
                        </a> (<a href="CampaignManager.do?command=PreviewRecipients&id=<%= thisItemWork.getStep().getCampaignId() %>"><dhv:label name="accounts.accounts_contacts_messages_details.Campaign">Campaign</dhv:label></a>)
                      </td>
                    </dhv:evaluate>
                  </tr>
                </table>
              </dhv:evaluate>
            </td>

