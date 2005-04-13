package com.zeroio.iteam.base;

import javax.naming.NameParser;
import javax.naming.Name;
import javax.naming.CompoundName;
import javax.naming.NamingException;
import java.util.Properties;

/**
 *  Description of the Class
 *
 *@author     matt rajkowski
 *@created    July 17, 2004
 *@version    $Id$
 */
class ProjectListContextParser implements NameParser {

  static Properties syntax = new Properties();
  static {
    syntax.put("jndi.syntax.direction", "flat");
    syntax.put("jndi.syntax.ignorecase", "false");
  }


  /**
   *  Description of the Method
   *
   *@param  name                 Description of the Parameter
   *@return                      Description of the Return Value
   *@exception  NamingException  Description of the Exception
   */
  public Name parse(String name) throws NamingException {
    return new CompoundName(name, syntax);
  }
}

