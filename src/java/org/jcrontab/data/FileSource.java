/**
 *  This file is part of the jcrontab package Copyright (C) 2001-2002 Israel
 *  Olalla This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or (at your
 *  option) any later version. This library is distributed in the hope that it
 *  will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 *  of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 *  General Public License for more details. You should have received a copy of
 *  the GNU Lesser General Public License along with this library; if not, write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston,
 *  MA 02111-1307, USA For questions, suggestions: iolalla@yahoo.com
 */

package org.jcrontab.data;

import org.jcrontab.Crontab;

import java.io.*;
import java.util.Vector;

/**
 * This class Is the implementation of DataSource to access Info in a
 * FileSystem
 *
 * @author Israel Olalla
 * @version $Id$
 * @created November, 2002
 */
public class FileSource implements DataSource {

  private CrontabParser cp = new CrontabParser();

  private static FileSource instance;

  private CrontabEntryBean[] cachedBeans = null;

  protected long lastModified;

  private String crontab_file = "crontab";


  /**
   * Creates new FileSource
   */

  protected FileSource() {
    if (Crontab.getInstance().getProperty("org.jcrontab.data.file") == null) {
      Crontab.getInstance().setProperty(
          "org.jcrontab.data.file", crontab_file);
    }
  }


  /**
   * This method returns the singleton is very important to grant That only a
   * Thread accesses at a time
   *
   * @return The instance value
   */
  public synchronized DataSource getInstance() {
    if (instance == null) {
      instance = new FileSource();
    }
    return instance;
  }


  /**
   * This method searches the given Bean from the File
   *
   * @param ceb Description of the Parameter
   * @return CrontabEntryBean beans Array the result of
   *         the search
   * @throws CrontabEntryException when it can't parse the line correctly
   * @throws IOException           If it can't access correctly to the File
   * @throws DataNotFoundException whe it can't find nothing in the file
   */
  public synchronized CrontabEntryBean find(CrontabEntryBean ceb)
      throws CrontabEntryException, IOException, DataNotFoundException {
    CrontabEntryBean[] cebra = findAll();
    for (int i = 0; i < cebra.length; i++) {
      if (cebra[i].equals(ceb)) {
//System.out.println("cebra encontrada : " + cebra[i]);
        return cebra[i];
      }
    }
    throw new DataNotFoundException("Unable to find :" + ceb);
  }


  /**
   * Description of the Method
   *
   * @param name Description of the Parameter
   * @return Description of the Return Value
   * @throws IOException Description of the Exception
   */
  protected InputStream createCrontabStream(String name)
      throws IOException {
    return new FileInputStream(name);
  }


  /**
   * Gets the changed attribute of the FileSource object
   *
   * @param name Description of the Parameter
   * @return The changed value
   */
  protected boolean isChanged(String name) {
    // Don't like those three lines. But are the only way i have to grant
    // It works in any O.S.
    final File filez = new File(name);
    if (lastModified != filez.lastModified()) {
      // This line is added to avoid reading the file if it didn't
      // change
      lastModified = filez.lastModified();
      return true;
    }
    return false;
  }


  /**
   * Description of the Method
   *
   * @param cp Description of the Parameter
   * @return Description of the Return Value
   * @throws CrontabEntryException Description of the Exception
   * @throws IOException           Description of the Exception
   * @throws DataNotFoundException Description of the Exception
   */
  public synchronized CrontabEntryBean[] findAll(Object cp) throws CrontabEntryException,
      IOException, DataNotFoundException {
    return findAll();
  }


  /**
   * This method searches all the CrontabEntryBean from the File
   *
   * @return CrontabEntryBean beans Array the result of
   *         the search
   * @throws CrontabEntryException when it can't parse the line correctly
   * @throws IOException           If it can't access correctly to the File
   * @throws DataNotFoundException whe it can't find nothing in the file
   */
  public synchronized CrontabEntryBean[] findAll() throws CrontabEntryException,
      IOException, DataNotFoundException {

    boolean[] bSeconds = new boolean[60];
    boolean[] bYears = new boolean[10];

    Vector listOfLines = new Vector();
    Vector listOfBeans = new Vector();
    // Class cla = FileSource.class;
    // BufferedReader input = new BufferedReader(new FileReader(strFileName));
    // This Line allows the crontab to be included in a jar file
    // and accessed from anywhere
    String filename = Crontab.getInstance().getProperty(
        "org.jcrontab.data.file");

    if (isChanged(filename)) {
      // open the file
      final InputStream fis = createCrontabStream(filename);
      BufferedReader input = new BufferedReader(new InputStreamReader(fis));

      String strLine;

      while ((strLine = input.readLine()) != null) {
        //System.out.println(strLine);
        strLine = strLine.trim();
        listOfLines.add(strLine);
      }
      input.close();
      if (listOfLines.size() > 0) {
        for (int i = 0; i < listOfLines.size(); i++) {
          String strLines = (String) listOfLines.get(i);
          // Skips blank lines and comments
          if (strLines.equals("") || strLines.charAt(0) == '#') {
          } else {
            //System.out.println(strLines);

            CrontabEntryBean entry = cp.marshall(strLines);

            cp.parseToken("*", bYears, false);
            entry.setBYears(bYears);
            entry.setYears("*");

            cp.parseToken("0", bSeconds, false);
            entry.setBSeconds(bSeconds);
            entry.setSeconds("0");

            listOfBeans.add(entry);
          }
        }
      } else {
        throw new DataNotFoundException("No CrontabEntries available");
      }

      int sizeOfBeans = listOfBeans.size();
      if (sizeOfBeans == 0) {
        throw new DataNotFoundException("No CrontabEntries available");
      } else {
        CrontabEntryBean[] finalBeans =
            new CrontabEntryBean[sizeOfBeans];
        for (int i = 0; i < sizeOfBeans; i++) {
          //Added to have different Beans identified
          finalBeans[i] = (CrontabEntryBean) listOfBeans.get(i);
          finalBeans[i].setId(i);
        }
        cachedBeans = finalBeans;
      }
    }
    if (cachedBeans != null) {
      return cachedBeans;
    } else {
      throw new DataNotFoundException("No CrontabEntries available");
    }
  }


  /**
   * This method removes the CrontabEntryBean array from the File
   *
   * @param ceb Description of the Parameter
   * @throws Exception
   */

  public synchronized void remove(CrontabEntryBean[] ceb) throws Exception {

    CrontabEntryBean[] thelist = findAll();
    CrontabEntryBean[] result = new CrontabEntryBean[thelist.length - ceb.length];
    CrontabEntryBean nullCeb = new CrontabEntryBean();
    nullCeb.setId(-1);
    for (int i = 0; i < thelist.length; i++) {
      for (int y = 0; y < ceb.length; y++) {
        if (thelist[i].equals(ceb[y])) {
          thelist[i] = nullCeb;
        }
      }
    }
    int resultCounter = 0;
    for (int i = 0; i < thelist.length; i++) {
      if (!thelist[i].equals(nullCeb)) {
        result[resultCounter] = thelist[i];
        resultCounter++;
      }
    }
    storeAll(result);
  }


  /**
   * This method saves the CrontabEntryBean array the actual problem with this
   * method is that doesn?t store comments and blank lines from the original
   * file any ideas?
   *
   * @param list Description of the Parameter
   * @throws FileNotFoundException Description of the Exception
   * @throws CrontabEntryException when it can't parse the line correctly
   * @throws IOException           If it can't access correctly to the File
   * @throws DataNotFoundException whe it can't find nothing in the file
   *                               usually Exception should'nt this
   */
  public synchronized void storeAll(CrontabEntryBean[] list) throws
      CrontabEntryException, FileNotFoundException, IOException {

    File fl = new File(
        Crontab.getInstance()
        .getProperty("org.jcrontab.data.file"));
    PrintStream out = new PrintStream(new FileOutputStream(fl));
    CrontabEntryBean nullCeb = new CrontabEntryBean();
    nullCeb.setId(-1);
    for (int i = 0; i < list.length; i++) {
      if (!list[i].equals(nullCeb)) {
        out.println("#");
        out.println(cp.unmarshall(list[i]));
      }
    }
    out.println("#");
  }


  /**
   * This method saves the CrontabEntryBean array the actual problem with this
   * method is that doesn?t store comments and blank lines from the original
   * file any ideas?
   *
   * @param beans Description of the Parameter
   * @throws CrontabEntryException when it can't parse the line correctly
   * @throws IOException           If it can't access correctly to the File
   * @throws DataNotFoundException whe it can't find nothing in the file
   *                               usually Exception should'nt this
   */
  public synchronized void store(CrontabEntryBean[] beans) throws CrontabEntryException,
      IOException, DataNotFoundException {
    CrontabEntryBean[] thelist = null;
    boolean succedded = false;
    try {
      thelist = findAll();
      succedded = true;
    } catch (Exception e) {
      if (e instanceof DataNotFoundException) {
        storeAll(beans);
      } else {
        throw new
            DataNotFoundException("Unable to find CrontabEntries");
      }
    }
    if (succedded) {
      int size = (thelist.length + 1);

      CrontabEntryBean[] resultlist = new CrontabEntryBean[size];
      Vector ve = new Vector();
      for (int i = 0; i < thelist.length; i++) {
        ve.add(thelist[i]);
      }
      for (int i = 0; i < beans.length; i++) {
        ve.add(beans[i]);
      }
      for (int i = 0; i < ve.size(); i++) {
        resultlist[i] = (CrontabEntryBean) ve.get(i);
      }
      storeAll(resultlist);
    }
  }


  /**
   * This method saves the CrontabEntryBean the actual problem with this method
   * is that doesn?t store comments and blank lines from the original file any
   * ideas?
   *
   * @param bean Description of the Parameter
   * @throws CrontabEntryException when it can't parse the line correctly
   * @throws IOException           If it can't access correctly to the File
   * @throws DataNotFoundException whe it can't find nothing in the file
   *                               usually Exception should'nt this
   */
  public synchronized void store(CrontabEntryBean bean) throws CrontabEntryException,
      IOException, DataNotFoundException {
    CrontabEntryBean[] thelist = null;
    boolean succedded = false;
    try {
      thelist = findAll();
      succedded = true;
    } catch (Exception e) {
      if (e instanceof DataNotFoundException) {
        CrontabEntryBean[] ilist = new CrontabEntryBean[1];
        ilist[0] = bean;
        storeAll(ilist);
      } else {
        throw new
            DataNotFoundException("Unable to find CrontabEntries");
      }
    }
    if (succedded) {
      int size = (thelist.length + 1);

      CrontabEntryBean[] resultlist = new CrontabEntryBean[size];
      Vector ve = new Vector();
      for (int i = 0; i < thelist.length; i++) {
        ve.add(thelist[i]);
      }
      ve.add(bean);
      for (int i = 0; i < ve.size(); i++) {
        resultlist[i] = (CrontabEntryBean) ve.get(i);
      }
      storeAll(resultlist);
    }
  }
}

