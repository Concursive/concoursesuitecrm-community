INSERT INTO system_prefs (category, modifiedby, enteredby, data) VALUES 
('system.objects.hooks', 1, 1, '<config>
  <!-- Hooks decide which items can be hooked in CFS, so if there is code
    that executes the WorkflowManager when a ticket is updated or inserted,
    the corresponding process is activated -->
  <hooks>
    <hook class="org.aspcfs.modules.troubletickets.base.Ticket">
      <actions>
        <action type="update" process="dhv.ticket.insert" enabled="true"/>
        <action type="insert" process="dhv.ticket.insert" enabled="true"/>
      </actions>
    </hook>
    <hook class="com.zeroio.iteam.base.Assignment">
      <actions>
        <action type="insert" process="dhv.assignment.insert" enabled="false"/>
      </actions>
    </hook>
    <hook class="org.aspcfs.modules.base.CustomFieldCategory">
      <actions>
        <action type="insert" process="vport.accountFolder.insert" enabled="false"/>
      </actions>
    </hook>
  </hooks>
  <!-- Each process describes the workflow for the various object and action
    that is being hooked-->
  <processes>
    <process name="dhv.ticket.insert" description="DHV Ticket alert" startId="1" entered="" modified="">
      <parameters>
        <parameter name="notification.host" value="127.0.0.1" type="java.lang.String" enabled="true"/>
        <parameter name="notification.from" value="cfs-root@darkhorseventures.com"/>
      </parameters>
      <components>
        <component id="1" parent="0" class="org.aspcfs.modules.troubletickets.components.LoadTicketDetails" enabled="true"/>
        <component id="2" parent="1" class="org.aspcfs.modules.troubletickets.components.QueryTicketJustClosed"/>
        <component id="3" parent="2" if="false" class="org.aspcfs.modules.troubletickets.components.QueryTicketJustAssigned"/>
        <component id="4" parent="2" if="true" class="org.aspcfs.modules.components.SendUserNotification">
          <parameters>
            <parameter name="notification.module" value="Tickets"/>
            <parameter name="notification.itemId" value="${this.id}"/>
            <parameter name="notification.itemModified" value="${this.modified}"/>
            <parameter name="notification.userToNotify" value="${previous.enteredBy}"/>
            <parameter name="notification.subject">CFS Ticket Closed: ${this.paddedId}</parameter>
<parameter name="notification.body"><![CDATA[
The following ticket in CFS has been closed:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Comment: ${this.comment}

Closed by: ${ticketModifiedByContact.nameFirstLast}

Solution: ${this.solution}
]]></parameter>
          </parameters>
        </component>
        <component id="5" parent="3" if="true" class="org.aspcfs.modules.components.SendUserNotification">
          <parameters>
            <parameter name="notification.module" value="Tickets"/>
            <parameter name="notification.itemId" value="${this.id}"/>
            <parameter name="notification.itemModified" value="${this.modified}"/>
            <parameter name="notification.userToNotify" value="${this.assignedTo}"/>
            <parameter name="notification.subject">CFS Ticket Assigned: ${this.paddedId}</parameter>
<parameter name="notification.body"><![CDATA[
The following ticket in CFS has been assigned to you:

--- Ticket Details ---

Ticket # ${this.paddedId}
Priority: ${ticketPriorityLookup.description}
Severity: ${ticketSeverityLookup.description}
Issue: ${this.problem}

Assigned By: ${ticketModifiedByContact.nameFirstLast}
Comment: ${this.comment}
]]></parameter>
          </parameters>
        </component>
        <component id="6" parent="1" class="org.aspcfs.modules.troubletickets.components.SendTicketToBPM" enabled="true">
          <parameters>
            <parameter name="bpm.host.url" value="http://ds21.darkhorseventures.com:8080/Horse/servlet/ProcessInitiateServlet"/>
          </parameters>
        </component>
        <component id="7" parent="2" if="true" class="org.aspcfs.modules.troubletickets.components.SendTicketSurvey" enabled="false"/>
      </components>
    </process>
    <!-- This is an example process that is not implemented-->
    <process name="dhv.assignment.insert" description="DHV Assignment alert" startId="100">
      <parameter name="notification.host" value="127.0.0.1" type="java.lang.String" enabled="true"/>
      <parameter name="notification.from" value="cfs-root@darkhorseventures.com"/>
      <components>
        <component id="100" parent="0" class="com.darkhorseventures.cfs.projectmanager.component.LoadAssignmentDetails" enabled="true"/>
        <component id="101" parent="100" if="true" class="com.darkhorseventures.cfs.projectmanager.component.QueryAssignmentJustCreated"/>
        <component id="102" parent="101" if="true" class="org.aspcfs.modules.components.SendUserNotification">
          <parameters>
            <parameter name="notification.module" value="Projects"/>
            <parameter name="notification.itemId" value="${this.id}"/>
            <parameter name="notification.itemModified" value="${this.modified}"/>
            <parameter name="notification.userToNotify" value="${this.userAssignedId}"/>
            <parameter name="notification.subject">CFS Activity assigned for project ${project.title}</parameter>
<parameter name="notification.body"><![CDATA[
The following activity was assigned to you in CFS:

--- Activity Details ---

Project Name: ${project.title}
Requirement Name: ${requirement.shortDescription}
Activity: ${this.role}
Due Date: ${this.dueDateString}
Assigned By: ${assignmentAssignedByContact.nameFirstLast}
]]></parameter>
          </parameters>
        </component>
      </components>
    </process>
    <!-- This process will send an SSL message to another server when an account folder item is inserted -->
    <process name="vport.accountFolder.insert" description="VPort Account Field notification" startId="200">
      <parameter name="notification.host" value="151.204.140.140" enabled="true"/>
      <parameter name="notification.port" value="44444"/>
      <components>
        <component id="200" parent="0" class="com.darkhorseventures.cfs.component.QueryObjectJustInserted" enabled="true"/>
        <component id="201" parent="200" if="true" class="com.darkhorseventures.cfs.folders.component.QueryHasFolderField" enabled="true">
          <parameters>
            <parameter name="customFieldCategory.fieldId" value="11"/>
          </parameters>
        </component>
        <component id="202" parent="201" if="true" class="com.darkhorseventures.cfs.component.SendSSLNotification">
          <parameters>
<parameter name="notification.body"><![CDATA[
<?xml version="1.0" standalone="yes"?>
<!-- The only field you will be editing is instructions -->
<!-- The max field length of instructions is 216 -->
<tas>
   <tsPacket>
      <transactionId>13</transactionId>
      <status>0</status>
      <callId>0</callId>
      <teamId>3</teamId>
      <opId>547</opId>
      <data>
         <psId>289</psId>
         <specialInstr>
            <instructionId>0</instructionId>
            <dayOfWeek></dayOfWeek>
            <instructions>${this.fieldValue(11):xml}</instructions>
            <status>T</status>
            <effective></effective>
            <expires></expires>
            <inactive>N</inactive>
            <opName></opName>
         </specialInstr>
      </data>
   </tsPacket>
</tas>
]]></parameter>
          </parameters>
        </component>
      </components>
    </process>
  </processes>
</config>');


