package org.theseus.actions;

/**
 * This class provides the basis of mappings, allowing an easy single point of configuration
 * for one or more actions an application can take on. By using a Properties file, XML config
 * file, or simply hard-coding the values in the controller servlet, this class allows each
 * action to contain a "record" of its name, the fully qualified class name to instantiate
 * an instance of the action, a bean attribute name used on one or more JSP pages via request
 * or session scope to maintain a state between actions that are related to the action, and
 * the fully qualifed bean name to instantiate the bean. This class is immutable..once its
 * created you can only get the values it was created with. They can not be changed at runtime
 * so as not to cause sporadic behavior. An Admin utility can, however, allow an action to remap
 * itself so that the entire ActionMapping originally created is discarded and a new one is
 * created. This would be useful, for example, where a running application may have some
 * problems with a given Action class, and a new class is added during runtime. By remapping
 * the action name to a new mapping, the newly added class can be called upon immediately.
 * However, given the nature of Servlet 2.2 web-apps and that most servlet 2.2 containers will
 * support reloading of .war web-apps, its unlikely that this would be very useful to do during
 * the running of an application. Most Servlet 2.2 and later application containers will
 * automatically reload the entire app, serializing HttpSession data to disk while the new
 * classes are reloaded, then deserializing them back into memory, so that all the clients
 * state remains (providing all the HttpSession objects implement Serializable properly)
 * across web-app reloads.
 */
public class Mapping
  implements java.io.Serializable
{
  static final long serialVersionUID = -8484048371911908893L;
  private String actionName              = "";
  private String actionClassName         = "";
  private String beanClassName           = "";
  private String beanAttributeName       = "";
  private int beanScope                  = 2;  // default to session scope
  private Forward forward                = null;


  /**
   * Create an immutable ActionMapping object for any given action class. This can be
   * used in a class manually coded, or read in from an XML configuration file, or a
   * Properties file.
   */
  public Mapping(String aName, String aClassName, String bAttributeName, String bScope, String bClassName)
  {
    try
    {
      actionName = aName;
      actionClassName = aClassName;
      beanClassName = bClassName;
      if( bScope.indexOf("request") >= 0 )
        beanScope = 1;
      if( bScope.indexOf("session") >= 0 )
        beanScope = 2;
      if( bScope.indexOf("application") >= 0 )
        beanScope = 3;
      beanAttributeName = bAttributeName;
      forward = new Forward();
    }
    catch(Throwable t)
    {
    }
  }



  /**
   * returns the name of this action
   */
  public String getActionName()
  {
    return actionName;
  }



  /**
   * Returns the name of the action class. This should be a fully qualified
   * package name and class name
   */
  public String getActionClassName()
  {
    return actionClassName;
  }



  /**
   * Returns the javabean attribute name used on JSP pages to get the instance
   * from the HttpSession scope.
   */
  public String getBeanAttributeName()
  {
    return beanAttributeName;
  }



  /**
   * Returns the scope to use for the bean reference.
   * The three values are request, session and application
   */
  public int getBeanScope()
  {
    return beanScope;
  }

  /**
   * Returns the javabean class name associated with the action. This is used on a
   * session scope boundary, usually to store related information across a users
   * multiple requests.
   */
  public String getBeanClassName()
  {
    return beanClassName;
  }



  /**
   * Returns the forward mappings for this action mapping.
   */
  public Forward getForward()
  {
    return forward;
  }
}