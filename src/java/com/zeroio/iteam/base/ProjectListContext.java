package com.zeroio.iteam.base;

import javax.naming.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;

/**
 * Description of the Class
 *
 * @author matt rajkowski
 * @version $Id$
 * @created July 17, 2004
 */
public class ProjectListContext implements Context {

  /**
   * Constructor for the ProjectListContext object
   */
  public ProjectListContext() {
  }


  protected Hashtable myEnv;
  protected Hashtable bindings = new Hashtable(11);
  protected final static NameParser myParser = new ProjectListContextParser();


  /**
   * Constructor for the ProjectListContext object
   *
   * @param inEnv Description of the Parameter
   */
  ProjectListContext(Hashtable inEnv) {
    myEnv = (inEnv != null)
        ? (Hashtable) (inEnv.clone())
        : null;
  }


  /**
   * Constructor for the ProjectListContext object
   *
   * @param inEnv    Description of the Parameter
   * @param bindings Description of the Parameter
   */
  private ProjectListContext(Hashtable inEnv, Hashtable bindings) {
    this(inEnv);
    this.bindings = bindings;
  }


  /**
   * Description of the Method
   *
   * @return Description of the Return Value
   */
  private ProjectListContext cloneCtx() {
    return new ProjectListContext(myEnv, bindings);
  }


  /**
   * Utility method for processing composite/compound name.
   *
   * @param name The non-null composite or compound name to
   *             process.
   * @return The non-null string name in this namespace to
   *         be processed.
   * @throws NamingException Description of the Exception
   */
  protected String getMyComponents(Name name) throws NamingException {
    if (name instanceof CompositeName) {
      if (name.size() > 1) {
        throw new InvalidNameException(
            name.toString() +
            " has more components than namespace can handle");
      }
      return name.get(0);
    } else {
      // compound name
      return name.toString();
    }
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookup(String name) throws NamingException {
    return lookup(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookup(Name name) throws NamingException {
    if (name.isEmpty()) {
      // Asking to look up this context itself.  Create and return
      // a new instance with its own independent environment.
      return (cloneCtx());
    }

    // Extract components that belong to this namespace
    String nm = getMyComponents(name);

    // Find object in internal hash table
    Object answer = bindings.get(nm);
    if (answer == null) {
      throw new NameNotFoundException(name + " not found");
    }
    return answer;
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @param obj  Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void bind(String name, Object obj) throws NamingException {
    bind(new CompositeName(name), obj);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @param obj  Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void bind(Name name, Object obj) throws NamingException {
    if (name.isEmpty()) {
      throw new InvalidNameException("Cannot bind empty name");
    }

    // Extract components that belong to this namespace
    String nm = getMyComponents(name);

    // Find object in internal hash table
    if (bindings.get(nm) != null) {
      throw new NameAlreadyBoundException("Use rebind to override");
    }

    // Add object to internal hash table
    bindings.put(nm, obj);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @param obj  Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void rebind(String name, Object obj) throws NamingException {
    rebind(new CompositeName(name), obj);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @param obj  Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void rebind(Name name, Object obj) throws NamingException {
    if (name.isEmpty()) {
      throw new InvalidNameException("Cannot bind empty name");
    }

    // Extract components that belong to this namespace
    String nm = getMyComponents(name);

    // Add object to internal hash table
    bindings.put(nm, obj);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void unbind(String name) throws NamingException {
    unbind(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void unbind(Name name) throws NamingException {
    if (name.isEmpty()) {
      throw new InvalidNameException("Cannot unbind empty name");
    }

    // Extract components that belong to this namespace
    String nm = getMyComponents(name);

    // Remove object from internal hash table
    bindings.remove(nm);
  }


  /**
   * Description of the Method
   *
   * @param oldname Description of the Parameter
   * @param newname Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void rename(String oldname, String newname) throws NamingException {
    rename(new CompositeName(oldname), new CompositeName(newname));
  }


  /**
   * Description of the Method
   *
   * @param oldname Description of the Parameter
   * @param newname Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void rename(Name oldname, Name newname) throws NamingException {
    if (oldname.isEmpty() || newname.isEmpty()) {
      throw new InvalidNameException("Cannot rename empty name");
    }

    // Extract components that belong to this namespace
    String oldnm = getMyComponents(oldname);
    String newnm = getMyComponents(newname);

    // Check if new name exists
    if (bindings.get(newnm) != null) {
      throw new NameAlreadyBoundException(
          newname.toString() +
          " is already bound");
    }

    // Check if old name is bound
    Object oldBinding = bindings.remove(oldnm);
    if (oldBinding == null) {
      throw new NameNotFoundException(oldname.toString() + " not bound");
    }

    bindings.put(newnm, oldBinding);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration list(String name) throws NamingException {
    return list(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration list(Name name) throws NamingException {
    if (name.isEmpty()) {
      // listing this context
      return new ListOfNames(bindings.keys());
    }

    // Perhaps 'name' names a context
    Object target = lookup(name);
    if (target instanceof Context) {
      try {
        return ((Context) target).list("");
      } finally {
        ((Context) target).close();
      }
    }
    throw new NotContextException(name + " cannot be listed");
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration listBindings(String name) throws NamingException {
    return listBindings(name);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public NamingEnumeration listBindings(Name name) throws NamingException {
    if (name.isEmpty()) {
      // listing this context
      return new ListOfBindings(bindings.keys());
    }

    // Perhaps 'name' names a context
    Object target = lookup(name);
    if (target instanceof Context) {
      try {
        return ((Context) target).listBindings("");
      } finally {
        ((Context) target).close();
      }
    }
    throw new NotContextException(name + " cannot be listed");
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void destroySubcontext(String name) throws NamingException {
    destroySubcontext(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @throws NamingException Description of the Exception
   */
  public void destroySubcontext(Name name) throws NamingException {
    throw new OperationNotSupportedException(
        "ProjectListContext does not support subcontexts");
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Context createSubcontext(String name) throws NamingException {
    return createSubcontext(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Context createSubcontext(Name name) throws NamingException {
    throw new OperationNotSupportedException(
        "ProjectListContext does not support subcontexts");
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookupLink(String name) throws NamingException {
    return lookupLink(new CompositeName(name));
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object lookupLink(Name name) throws NamingException {
    // This flat context does not treat links specially
    return lookup(name);
  }


  /**
   * Gets the nameParser attribute of the ProjectListContext object
   *
   * @param name Description of the Parameter
   * @return The nameParser value
   * @throws NamingException Description of the Exception
   */
  public NameParser getNameParser(String name) throws NamingException {
    return getNameParser(new CompositeName(name));
  }


  /**
   * Gets the nameParser attribute of the ProjectListContext object
   *
   * @param name Description of the Parameter
   * @return The nameParser value
   * @throws NamingException Description of the Exception
   */
  public NameParser getNameParser(Name name) throws NamingException {
    // Do lookup to verify name exists
    Object obj = lookup(name);
    if (obj instanceof Context) {
      ((Context) obj).close();
    }
    return myParser;
  }


  /**
   * Description of the Method
   *
   * @param name   Description of the Parameter
   * @param prefix Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public String composeName(String name, String prefix)
      throws NamingException {
    Name result = composeName(
        new CompositeName(name),
        new CompositeName(prefix));
    return result.toString();
  }


  /**
   * Description of the Method
   *
   * @param name   Description of the Parameter
   * @param prefix Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Name composeName(Name name, Name prefix)
      throws NamingException {
    Name result = (Name) (prefix.clone());
    result.addAll(name);
    return result;
  }


  /**
   * Adds a feature to the ToEnvironment attribute of the ProjectListContext
   * object
   *
   * @param propName The feature to be added to the ToEnvironment
   *                 attribute
   * @param propVal  The feature to be added to the ToEnvironment
   *                 attribute
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object addToEnvironment(String propName, Object propVal)
      throws NamingException {
    if (myEnv == null) {
      myEnv = new Hashtable(5, 0.75f);
    }
    return myEnv.put(propName, propVal);
  }


  /**
   * Description of the Method
   *
   * @param propName Description of the Parameter
   * @return Description of the Return Value
   * @throws NamingException Description of the Exception
   */
  public Object removeFromEnvironment(String propName)
      throws NamingException {
    if (myEnv == null) {
      return null;
    }

    return myEnv.remove(propName);
  }


  /**
   * Gets the environment attribute of the ProjectListContext object
   *
   * @return The environment value
   * @throws NamingException Description of the Exception
   */
  public Hashtable getEnvironment() throws NamingException {
    if (myEnv == null) {
      // Must return non-null
      return new Hashtable(3, 0.75f);
    } else {
      return (Hashtable) myEnv.clone();
    }
  }


  /**
   * Gets the nameInNamespace attribute of the ProjectListContext object
   *
   * @return The nameInNamespace value
   * @throws NamingException Description of the Exception
   */
  public String getNameInNamespace() throws NamingException {
    return "";
  }


  /**
   * Description of the Method
   *
   * @throws NamingException Description of the Exception
   */
  public void close() throws NamingException {
  }


  // Class for enumerating name/class pairs
  /**
   * Description of the Class
   *
   * @author matt rajkowski
   * @version $Id$
   * @created July 17, 2004
   */
  class ListOfNames implements NamingEnumeration {
    protected Enumeration names;


    /**
     * Constructor for the ListOfNames object
     *
     * @param names Description of the Parameter
     */
    ListOfNames(Enumeration names) {
      this.names = names;
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     */
    public boolean hasMoreElements() {
      try {
        return hasMore();
      } catch (NamingException e) {
        return false;
      }
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public boolean hasMore() throws NamingException {
      return names.hasMoreElements();
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public Object next() throws NamingException {
      String name = (String) names.nextElement();
      String className = bindings.get(name).getClass().getName();
      return new NameClassPair(name, className);
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     */
    public Object nextElement() {
      try {
        return next();
      } catch (NamingException e) {
        throw new NoSuchElementException(e.toString());
      }
    }


    /**
     * Description of the Method
     */
    public void close() {
    }
  }


  // Class for enumerating bindings
  /**
   * Description of the Class
   *
   * @author matt rajkowski
   * @version $Id$
   * @created July 17, 2004
   */
  class ListOfBindings extends ListOfNames {

    /**
     * Constructor for the ListOfBindings object
     *
     * @param names Description of the Parameter
     */
    ListOfBindings(Enumeration names) {
      super(names);
    }


    /**
     * Description of the Method
     *
     * @return Description of the Return Value
     * @throws NamingException Description of the Exception
     */
    public Object next() throws NamingException {
      String name = (String) names.nextElement();
      return new Binding(name, bindings.get(name));
    }
  }

}

