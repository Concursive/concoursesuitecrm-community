package org.theseus.actions;

import java.io.*;
import java.util.*;

public class Forward
  implements Serializable
{
  static final long serialVersionUID = 3699281129673395436L;
  private Hashtable forwards = null;

  public Forward()
  {
    if( forwards == null )
      forwards = new Hashtable();
  }



  /**
   * Adds a forwarding resource to the hashtable
   */
  public void addForward(String forwardName, Resource resource)
  {
    forwards.put(forwardName, resource);
  }



  /**
   * Returns the Hashtable of forwards
   */
  public Hashtable getForwards()
  {
    return forwards;
  }

  /**
   * Retunrs the <code>Forward</code> object at the specified location
   * or null if the location is too large.
   */
  public Forward getForwardAt(int pos)
  {
    if( forwards.size() > pos )
    {
      int cntr = 0;
      Enumeration e = forwards.keys();
      while( e.hasMoreElements() && cntr++ < (pos-1) )
       e.nextElement();

      return (Forward) e.nextElement();
    }

    return null;
  }


  /**
   * Looks up a forward resource name and if it exists, returns the
   * attribute equated to the resource name.
   */
  public Resource findResource(String resourceName)
  {
    if( forwards.containsKey(resourceName) )
      return (Resource) forwards.get(resourceName);
    else
      return null;
  }


  /**
   * Looks for a resource, and if found, returns the XSL file associated with
   * that resource to handle conversion of the xml output of the resource
   * to another format, specified via the xsl xpath language
   */
  public String findXSL(String resourceName)
  {
    if( forwards.containsKey(resourceName) )
      return (String) ( (Resource) forwards.get(resourceName)).getXSL();
    else
      return null;
  }
}