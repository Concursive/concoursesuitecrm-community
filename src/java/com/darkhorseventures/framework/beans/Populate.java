package org.theseus.beans;

import java.util.*;
import java.lang.reflect.*;
import javax.servlet.http.*;

public interface Populate
{
  public void populateObject(Object bean, HttpServletRequest request, String nestedAttribute, String indexAttribute);
}
