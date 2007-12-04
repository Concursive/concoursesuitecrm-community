Centric CRM Language Pack; build: @BUILD.NUMBER@; @BUILD.DATE@
$Id: readme.txt 17418 2006-11-16 14:21:13Z matt $

Centric CRM Language Pack

----------------------------------------------------------------------------
| INTRODUCTION                                                             |
----------------------------------------------------------------------------

Translations in Centric CRM are made from several supporting files.  This
archive includes everything that is needed.  Once the files are ready to be
submitted, they can be posted to the www.centriccrm.com web site.

To learn more about translating, visit the Centric CRM Language Gateway:
http://www.centriccrm.com/Translation.do


----------------------------------------------------------------------------
| FILE STRUCTURE                                                           |
----------------------------------------------------------------------------

Each language is associated with a specific Java Locale.  For example,
files ending in en_US are for US English.

In this archive you will find the following directories:

data          Default database data that is installed in Centric CRM
dictionary    All of the user interfase phrases used in Centric CRM
javascript    Warning text that is displayed to the website user
permissions   The module names, permission names, and related module data
templates     Dynamic messages that are used when sending email to users
workflow      Dynamic events that are used when interacting with business
              rules


----------------------------------------------------------------------------
| Making changes                                                           |
----------------------------------------------------------------------------

Care must be taken when working with these application files directly.  The
Centric CRM application will be reading them and expecting certain mandatory
programming code to be intact.

When editing these files, color-syntax is useful, so you might use an
editor like JEdit from http://www.jedit.org


----------------------------------------------------------------------------
| Directory: data                                                          |
----------------------------------------------------------------------------

File: lookuplists_*.xml

Contains a translatable file that is used during the installation of a new
Centric CRM database.  These items are commonly viewed in a drop-down list
to the user.


----------------------------------------------------------------------------
| Directory: dictionary                                                    |
----------------------------------------------------------------------------

File: dictionary_*.xml

The phrase dictionary contains the majority of text that is displayed in
Centric CRM.  This file can be edited online using the Centric CRM
Language gateway.  This allows one or more developers to work on the
translation at the same time, and to just work on the changes since the
last update.

New translations may find it useful to work offline initially and then
send the document to Centric CRM for processing.


----------------------------------------------------------------------------
| Directory: javascript                                                    |
----------------------------------------------------------------------------

File: dictionary_*.js

Javascript messages are used throughout the web application.  These messages
typically appear to the user in an alert box.


----------------------------------------------------------------------------
| Directory: permissions                                                   |
----------------------------------------------------------------------------

File: permissions_*.xml

The application uses this file to understand the available modules,
the permissions associated with the module, and any related module
capabilities.

- Translate the "description" value
- Translate the "title" value

DO NOT translate the name, class, and other fields because the application uses
them across all languages.

<lookup constantId="206061500" class="lookupList" table="lookup_call_types"
        description="Activity Type"/>
<permission name="pipeline" attributes="v---"
            description="Access to Pipeline module" viewpoints="true"/>
<permission name="pipeline-quotes" attributes="vaed" description="Quotes"/>
<report file="opportunity_pipeline.xml" type="user"
        permission="pipeline-opportunities"
        title="Opportunities by Stage"
        description="What are my upcoming opportunities by stage?"/>


----------------------------------------------------------------------------
| Directory: templates                                                     |
----------------------------------------------------------------------------

File: templates_*.xml

This XML file contains messages that are typically emailed to the user.
Be careful because you DO NOT want to translate HTML and ${} tags.


----------------------------------------------------------------------------
| Directory: workflow                                                      |
----------------------------------------------------------------------------

File: templates_*.xml

The workflow XML file contains processing of events that occur in Centric
CRM.  Many of these events have names and parameters for translation.

- Translate the "description" field in each process
- Translate the "value" field
- Translate free-form text
