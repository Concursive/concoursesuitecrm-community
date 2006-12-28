package org.aspcfs.modules.components;

import bsh.Interpreter;
import org.aspcfs.apps.workFlowManager.ComponentContext;
import org.aspcfs.apps.workFlowManager.ComponentInterface;
import org.aspcfs.controller.objectHookManager.ObjectHookComponent;

import java.sql.Connection;

public class RunScript extends ObjectHookComponent implements
    ComponentInterface {
  public final static String SCRIPT = "runscript.script";

  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  public String getDescription() {
    return "Run Beanshell script";
  }

  public boolean execute(ComponentContext context) {
    boolean result = true;
    Interpreter script = new Interpreter();
    Connection db = null;
    try {
      db = getConnection(context);
      script.set("db", db);
      script.set("context", context);
      script.eval(context.getParameter(SCRIPT));
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    } finally {
      freeConnection(context, db);
    }
    return result;
  }

}
