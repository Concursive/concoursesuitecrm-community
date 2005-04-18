/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
 *  rights reserved. This material cannot be distributed without written
 *  permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
 *  this material for internal use is hereby granted, provided that the above
 *  copyright notice and this permission notice appear in all copies. DARK HORSE
 *  VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
 *  IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
 *  PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
 *  INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
 *  EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
 *  ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
 *  DAMAGES RELATING TO THE SOFTWARE.
 */
package org.aspcfs.modules.help.actions;

import com.darkhorseventures.framework.actions.ActionContext;
import com.zeroio.webutils.FileDownload;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.admin.base.PermissionCategoryList;
import org.aspcfs.modules.help.base.*;
import org.aspcfs.utils.HTTPUtils;
import org.aspcfs.utils.web.LookupElement;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.RequestUtils;

import java.sql.Connection;
import java.util.StringTokenizer;

/**
 *  QA Tool Manager
 *
 *@author     Mathur
 *@created    July 9, 2003
 *@version    $id:exp$
 */
public final class QA extends CFSModule {

  /**
   *  Shows details of a page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDefault(ActionContext context) {
    if (!(hasPermission(context, "qa-view"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      String module = context.getRequest().getParameter("module");
      String section = context.getRequest().getParameter("section");
      String subsection = context.getRequest().getParameter("sub");
      db = this.getConnection(context);
      HelpItem thisItem = new HelpItem();
      thisItem.setModule(module);
      thisItem.setSection(section);
      thisItem.setSubsection(subsection);
      thisItem.setBuildFeatures(true);
      thisItem.setBuildRules(true);
      thisItem.setBuildNotes(true);
      thisItem.setBuildTips(true);
      thisItem.processRecord(db, this.getUserId(context));
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "QA");
  }


  /**
   *  Modify the introduction of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyIntro(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String helpId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpItem thisItem = new HelpItem(db, Integer.parseInt(helpId));
      context.getRequest().setAttribute("Help", thisItem);

      PermissionCategoryList pcl = new PermissionCategoryList();
      pcl.setEmptyHtmlSelectRecord("-None-");
      pcl.buildList(db);
      context.getRequest().setAttribute("permissionCategoryList", pcl);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "ModifyIntro");
  }


  /**
   *  Save the Introduction text for the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveIntro(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    HelpItem thisItem = (HelpItem) context.getFormBean();
    try {
      db = this.getConnection(context);
      thisItem.setModifiedBy(this.getUserId(context));
      thisItem.update(db);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    context.getRequest().setAttribute("refreshUrl", "QA.do?module=" + thisItem.getModule() + (thisItem.getSection() != null ? "&section=" + thisItem.getSection() : "") + (thisItem.getSubsection() != null ? "&subsection=" + thisItem.getSubsection() : "") + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    return getReturn(context, "SaveIntro");
  }


  /**
   *  Prepares the Add form
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepareFeature(ActionContext context) {
    if (!(hasPermission(context, "qa-add"))) {
      return ("PermissionError");
    }
    Connection db = null;
    try {
      db = this.getConnection(context);
      LookupList generalFeatures = new LookupList(db, "lookup_help_features");
      context.getRequest().setAttribute("GeneralFeatures", generalFeatures);
    } catch (Exception e) {
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "FeaturePrepare");
  }


  /**
   *  Modifies a feature of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyFeature(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String featureId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpFeature thisFeature = new HelpFeature(db, Integer.parseInt(featureId));
      context.getRequest().setAttribute("Feature", thisFeature);
      LookupList generalFeatures = new LookupList(db, "lookup_help_features");
      context.getRequest().setAttribute("GeneralFeatures", generalFeatures);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "FeatureModify");
  }


  /**
   *  Saves a feature of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveFeature(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    HelpFeature thisFeature = (HelpFeature) context.getFormBean();
    if (thisFeature.getLinkFeatureId() > 0) {
      if (!(hasPermission(context, "qa-edit"))) {
        return ("PermissionError");
      }
    } else {
      if (!(hasPermission(context, "qa-add"))) {
        return ("PermissionError");
      }
    }
    try {
      db = this.getConnection(context);
      thisFeature.setModifiedBy(this.getUserId(context));
      if (thisFeature.getLinkFeatureId() > 0) {
        LookupElement thisElement = new LookupElement(db, thisFeature.getLinkFeatureId(), "lookup_help_features");
        thisFeature.setDescription(thisElement.getDescription());
      }
      if (thisFeature.getComplete()) {
        thisFeature.setCompletedBy(this.getUserId(context));
      }
      if (thisFeature.getId() > 0) {
        //completedby is used only if the feature is completed
        resultCount = thisFeature.update(db);
      } else {
        thisFeature.setEnteredBy(this.getUserId(context));
        recordInserted = thisFeature.insert(db);
      }

      //set the refresh link
      HelpItem thisItem = new HelpItem(db, thisFeature.getLinkHelpId());
      context.getRequest().setAttribute("refreshUrl", "QA.do?module=" + thisItem.getModule() + (thisItem.getSection() != null ? "&section=" + thisItem.getSection() : "") + (thisItem.getSubsection() != null ? "&subsection=" + thisItem.getSubsection() : "") + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return ("SystemError");
    } finally {
      this.freeConnection(context, db);
    }

    String target = context.getRequest().getParameter("target");
    if (resultCount == 1) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpFeatures.do?command=PrepareFeature&linkHelpId=" + thisFeature.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "FeatureReInsert");
      }
      return getReturn(context, "FeatureUpdate");
    } else if (recordInserted) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpFeatures.do?command=PrepareFeature&linkHelpId=" + thisFeature.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "FeatureReInsert");
      }
      return getReturn(context, "FeatureInsert");
    }
    if (thisFeature.getId() > 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    } else {
      processErrors(context, thisFeature.getErrors());
    }
    return getReturn(context, "FeaturePrepare");
  }


  /**
   *  Deletes a feature of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteFeature(ActionContext context) {
    if (!(hasPermission(context, "qa-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String featureId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpFeature thisFeature = new HelpFeature(db, Integer.parseInt(featureId));
      thisFeature.delete(db);
      HelpItem thisItem = new HelpItem(db, thisFeature.getLinkHelpId());
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "FeatureDelete");
  }


  /**
   *  Changes the status of a feature as complete/incomplete
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcessFeature(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int featureId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      HelpFeature thisFeature = new HelpFeature(db, featureId);

      if (status == HelpFeature.DONE) {
        thisFeature.setComplete(true);
        thisFeature.setCompletedBy(this.getUserId(context));
      } else {
        thisFeature.setComplete(false);
      }

      count = thisFeature.update(db);
      this.freeConnection(context, db);
      if (count != -1) {
        String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(fileName);
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context, "image/" + imageType);
        } else {
          System.err.println("Image-> Trying to send a file that does not exist");
        }
      } else {
        processErrors(context, thisFeature.getErrors());
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println("HelpFeature -> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      this.freeConnection(context, db);
      System.out.println(e.toString());
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    }
    return ("-none-");
  }


  /**
   *  Prepares data for adding a Rule
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepareRule(ActionContext context) {
    if (!(hasPermission(context, "qa-add"))) {
      return ("PermissionError");
    }
    return getReturn(context, "RulePrepare");
  }


  /**
   *  Modifies a rule of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyRule(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String ruleId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpBusinessRule thisRule = new HelpBusinessRule(db, Integer.parseInt(ruleId));
      context.getRequest().setAttribute("BusinessRule", thisRule);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "RuleModify");
  }


  /**
   *  Saves a rule of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveRule(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    HelpBusinessRule thisRule = (HelpBusinessRule) context.getFormBean();

    try {
      db = this.getConnection(context);
      thisRule.setModifiedBy(this.getUserId(context));
      if (thisRule.getId() > 0) {
        if (!(hasPermission(context, "qa-edit"))) {
          return ("PermissionError");
        }
        //completedby is used only if the feature is completed
        thisRule.setCompletedBy(this.getUserId(context));
        resultCount = thisRule.update(db);
      } else {
        if (!(hasPermission(context, "qa-add"))) {
          return ("PermissionError");
        }
        thisRule.setEnteredBy(this.getUserId(context));
        recordInserted = thisRule.insert(db);
      }
      //set the refresh link
      HelpItem thisItem = new HelpItem(db, thisRule.getLinkHelpId());
      context.getRequest().setAttribute("refreshUrl", "QA.do?module=" + thisItem.getModule() + (thisItem.getSection() != null ? "&section=" + thisItem.getSection() : "") + (thisItem.getSubsection() != null ? "&subsection=" + thisItem.getSubsection() : "") + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    String target = context.getRequest().getParameter("target");
    if (resultCount == 1) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpRules.do?command=PrepareRule&linkHelpId=" + thisRule.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "RuleReInsert");
      }
      return getReturn(context, "RuleUpdate");
    } else if (recordInserted) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpRules.do?command=PrepareRule&linkHelpId=" + thisRule.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "RuleReInsert");
      }
      return getReturn(context, "RuleInsert");
    }
    if (thisRule.getId() > 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    } else {
      processErrors(context, thisRule.getErrors());
    }
    return getReturn(context, "RulePrepare");
  }


  /**
   *  Deletes a rule of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteRule(ActionContext context) {
    if (!(hasPermission(context, "qa-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String ruleId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpBusinessRule thisRule = new HelpBusinessRule(db, Integer.parseInt(ruleId));
      thisRule.delete(db);
      HelpItem thisItem = new HelpItem(db, thisRule.getLinkHelpId());
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "RuleDelete");
  }


  /**
   *  Toggles the status of the rule
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcessRule(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int itemId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      HelpBusinessRule thisRule = new HelpBusinessRule(db, itemId);

      if (status == HelpBusinessRule.DONE) {
        thisRule.setComplete(true);
        thisRule.setCompletedBy(this.getUserId(context));
      } else {
        thisRule.setComplete(false);
      }
      count = thisRule.update(db);
      this.freeConnection(context, db);
      if (count != -1) {
        String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(fileName);
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context, "image/" + imageType);
        } else {
          System.err.println("Image-> Trying to send a file that does not exist");
        }
      } else {
        processErrors(context, thisRule.getErrors());
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println("HelpBusinessRule -> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      this.freeConnection(context, db);
      System.out.println(e.toString());
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    }
    return ("-none-");
  }



  /**
   *  Prepares data for adding a tip
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepareTip(ActionContext context) {
    if (!(hasPermission(context, "qa-add"))) {
      return ("PermissionError");
    }
    return getReturn(context, "TipPrepare");
  }


  /**
   *  Modifies a tip of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyTip(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String tipId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpTip thisTip = new HelpTip(db, Integer.parseInt(tipId));
      context.getRequest().setAttribute("Tip", thisTip);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "TipModify");
  }


  /**
   *  Saves a Tip of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveTip(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    HelpTip thisTip = (HelpTip) context.getFormBean();

    try {
      db = this.getConnection(context);
      thisTip.setModifiedBy(this.getUserId(context));
      if (thisTip.getId() > 0) {
        if (!(hasPermission(context, "qa-edit"))) {
          return ("PermissionError");
        }
        //completedby is used only if the tip is completed
        resultCount = thisTip.update(db);
      } else {
        if (!(hasPermission(context, "qa-add"))) {
          return ("PermissionError");
        }
        thisTip.setEnteredBy(this.getUserId(context));
        recordInserted = thisTip.insert(db);
      }

      //set the refresh link
      HelpItem thisItem = new HelpItem(db, thisTip.getLinkHelpId());
      context.getRequest().setAttribute("refreshUrl", "QA.do?module=" + thisItem.getModule() + (thisItem.getSection() != null ? "&section=" + thisItem.getSection() : "") + (thisItem.getSubsection() != null ? "&subsection=" + thisItem.getSubsection() : "") + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }

    String target = context.getRequest().getParameter("target");
    if (resultCount == 1) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpTips.do?command=PrepareTip&linkHelpId=" + thisTip.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "TipReInsert");
      }
      return getReturn(context, "TipUpdate");
    } else if (recordInserted) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpTips.do?command=PrepareTip&linkHelpId=" + thisTip.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "TipReInsert");
      }
      return getReturn(context, "TipInsert");
    }
    if (thisTip.getId() > 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    } else {
      processErrors(context, thisTip.getErrors());
    }
    return getReturn(context, "TipPrepare");
  }


  /**
   *  Deletes a Tip of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteTip(ActionContext context) {
    if (!(hasPermission(context, "qa-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String tipId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpTip thisTip = new HelpTip(db, Integer.parseInt(tipId));
      thisTip.delete(db);
      HelpItem thisItem = new HelpItem(db, thisTip.getLinkHelpId());
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "TipDelete");
  }


  /**
   *  Prepares data for adding a Note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandPrepareNote(ActionContext context) {
    if (!(hasPermission(context, "qa-add"))) {
      return ("PermissionError");
    }
    return getReturn(context, "NotePrepare");
  }


  /**
   *  Modifies a Note on the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandModifyNote(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String noteId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpNote thisNote = new HelpNote(db, Integer.parseInt(noteId));
      context.getRequest().setAttribute("Note", thisNote);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "NoteModify");
  }


  /**
   *  Saves a Note of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandSaveNote(ActionContext context) {
    Connection db = null;
    boolean recordInserted = false;
    int resultCount = -1;
    HelpNote thisNote = (HelpNote) context.getFormBean();

    try {
      db = this.getConnection(context);
      thisNote.setModifiedBy(this.getUserId(context));
      if (thisNote.getComplete()) {
        thisNote.setCompletedBy(this.getUserId(context));
      }
      if (thisNote.getId() > 0) {
        if (!(hasPermission(context, "qa-edit"))) {
          return ("PermissionError");
        }
        //completedby is used only if the note is completed
        thisNote.setCompletedBy(this.getUserId(context));
        resultCount = thisNote.update(db);
      } else {
        if (!(hasPermission(context, "qa-add"))) {
          return ("PermissionError");
        }
        thisNote.setEnteredBy(this.getUserId(context));
        recordInserted = thisNote.insert(db);
      }
      //set the refresh link
      HelpItem thisItem = new HelpItem(db, thisNote.getLinkHelpId());
      context.getRequest().setAttribute("refreshUrl", "QA.do?module=" + thisItem.getModule() + (thisItem.getSection() != null ? "&section=" + thisItem.getSection() : "") + (thisItem.getSubsection() != null ? "&subsection=" + thisItem.getSubsection() : "") + RequestUtils.addLinkParams(context.getRequest(), "popup"));
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }

    String target = context.getRequest().getParameter("target");
    if (resultCount == 1) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpNotes.do?command=PrepareNote&linkHelpId=" + thisNote.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "NoteReInsert");
      }
      return getReturn(context, "NoteUpdate");
    } else if (recordInserted) {
      if ("loop".equals(target)) {
        context.getRequest().setAttribute("redirectUrl", "HelpNotes.do?command=PrepareNote&linkHelpId=" + thisNote.getLinkHelpId() + "&target=" + target);
        return getReturn(context, "NoteReInsert");
      }
      return getReturn(context, "NoteInsert");
    }
    if (thisNote.getId() > 0) {
      context.getRequest().setAttribute("Error", NOT_UPDATED_MESSAGE);
    } else {
      processErrors(context, thisNote.getErrors());
    }
    return getReturn(context, "NotePrepare");
  }


  /**
   *  Deletes a Note of the page
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandDeleteNote(ActionContext context) {
    if (!(hasPermission(context, "qa-delete"))) {
      return ("PermissionError");
    }
    Connection db = null;
    String noteId = context.getRequest().getParameter("id");
    try {
      db = this.getConnection(context);
      HelpNote thisNote = new HelpNote(db, Integer.parseInt(noteId));
      thisNote.delete(db);
      HelpItem thisItem = new HelpItem(db, thisNote.getLinkHelpId());
      context.getRequest().setAttribute("Help", thisItem);
    } catch (Exception e) {
      e.printStackTrace(System.out);
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    } finally {
      this.freeConnection(context, db);
    }
    return getReturn(context, "NoteDelete");
  }


  /**
   *  Toggles the status of a Note
   *
   *@param  context  Description of the Parameter
   *@return          Description of the Return Value
   */
  public String executeCommandProcessNote(ActionContext context) {
    if (!(hasPermission(context, "qa-edit"))) {
      return ("PermissionError");
    }
    Connection db = null;
    int count = 0;

    String id = (String) context.getRequest().getParameter("id");

    //Start the download
    try {
      StringTokenizer st = new StringTokenizer(id, "|");
      String fileName = st.nextToken();
      String imageType = st.nextToken();
      int noteId = Integer.parseInt(st.nextToken());
      int status = Integer.parseInt(st.nextToken());
      db = this.getConnection(context);
      HelpNote thisNote = new HelpNote(db, noteId);

      if (status == HelpNote.DONE) {
        thisNote.setComplete(true);
        thisNote.setCompletedBy(this.getUserId(context));
      } else {
        thisNote.setComplete(false);
      }
      count = thisNote.update(db);
      this.freeConnection(context, db);
      if (count != -1) {
        String filePath = context.getServletContext().getRealPath("/") + "images" + fs + fileName;
        FileDownload fileDownload = new FileDownload();
        fileDownload.setFullPath(filePath);
        fileDownload.setDisplayName(fileName);
        if (fileDownload.fileExists()) {
          fileDownload.sendFile(context, "image/" + imageType);
        } else {
          System.err.println("Image-> Trying to send a file that does not exist");
        }
      } else {
        processErrors(context, thisNote.getErrors());
      }
    } catch (java.net.SocketException se) {
      //User either canceled the download or lost connection
      System.out.println("HelpNote -> ProcessImage : Download canceled or connection lost");
    } catch (Exception e) {
      this.freeConnection(context, db);
      System.out.println(e.toString());
      context.getRequest().setAttribute("Error", e);
      return "SystemError";
    }
    return ("-none-");
  }

}

