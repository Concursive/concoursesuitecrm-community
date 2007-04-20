/*
 *  Copyright(c) 2006 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.website.icelet;

import org.aspcfs.modules.admin.base.User;
import org.aspcfs.modules.base.*;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.website.utils.PortletUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.portlet.*;
import java.awt.*;
import java.io.*;
import java.sql.Connection;
import java.util.*;
import java.util.List;

/**
 * Used for rendering the graph based on folder data
 *
 * @author dharmas
 * @author prakashl
 * @version $Id: Exp $
 * @created April 06, 2007
 */
/*
 *  using the convention yyyymmddhh for constants.
 */
public class FolderGraphPortlet extends GenericPortlet {

  public final static String SELECT_FOLDER = "7031914";
  public final static String MAJOR_AXIS = "7032110";
  public final static String MAJOR_AXIS_FIELD = "7032111";
  public final static String MINOR_AXIS_PARAM = "7032112";
  public final static String SHOW_LEGEND = "7032113";
  public final static String FOLDER_RECORD_RANGE = "7032114";

  private final static String VIEW_PAGE1 = "/portlets/folder/fields_graph.jsp";

  private String minorAxisParams = null;
  private String graphType = null;
  private String graphId = null;
  private String minorAxisFieldId = null;
  private String majorAxisFieldId = null;
  private String showLegend = null;
  private String folderRecordRange = null;
  private String majorAxis = null;
  private String folderId = null;

  private String majorAxisFieldName = null;
  private String minorAxisFieldName = null;
  private String minorAxisFieldNames[] = null;

  private Iterator iterator = null;

  /**
   * Description of the Method
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws javax.portlet.PortletException Description of the Exception
   * @throws java.io.IOException            Description of the Exception
   */
  public void doView(RenderRequest request, RenderResponse response)
      throws PortletException, IOException {
    try {
      buildGraph(request, response);
      PortletRequestDispatcher requestDispatcher =
          getPortletContext().getRequestDispatcher(VIEW_PAGE1);
      requestDispatcher.include(request, response);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IOException("Exception in FolderGraphPortlet");
    }
  }

  /**
   * Builds the data required for rendering graph based on FolderGraph configuration
   *
   * @param request  Description of the Parameter
   * @param response Description of the Parameter
   * @throws Exception Description of the Exception
   */
  private void buildGraph(RenderRequest request, RenderResponse response) throws Exception {

    folderId = (String) request.getPreferences().getValue(SELECT_FOLDER, "-1");
    majorAxis = (String) request.getPreferences().getValue(MAJOR_AXIS, "X");
    majorAxisFieldId = (String) request.getPreferences().getValue(MAJOR_AXIS_FIELD, "-1");
    minorAxisParams = (String) request.getPreferences().getValue(MINOR_AXIS_PARAM, "-1");
    showLegend = (String) request.getPreferences().getValue(SHOW_LEGEND, "false");
    folderRecordRange = (String) request.getPreferences().getValue(FOLDER_RECORD_RANGE, "-1");

    String minorAxisField = null;
    Connection db = PortletUtils.getConnection(request);

    GraphTypeList graphList = new GraphTypeList();
    if (minorAxisParams != null && minorAxisParams.length() > 0) {
      graphList.setTableName("lookup_graph_type");
      graphList.setEnabledState(1);
      graphList.buildList(db);
    }

    //getting the field names for both major and minor axis
    CustomFieldList fieldList = new CustomFieldList();
    fieldList.buildList(db);
    iterator = fieldList.iterator();
    if (iterator.hasNext()) {
      while (iterator.hasNext()) {
        CustomField field = (CustomField) iterator.next();
        if (field.getId() == Integer.parseInt(majorAxisFieldId)) {
          majorAxisFieldName = field.getName();
        }
      }
    }

    if (minorAxisParams.indexOf(";") > -1) {
      String combinedMinorAxisFields = "";
      String combinedGraphTypes = "";
      StringTokenizer minorAxisToken = new StringTokenizer(minorAxisParams, ";");
      while (minorAxisToken.hasMoreTokens()) {
        getMinorAxisInfo(minorAxisToken.nextToken(), graphList);
        if (combinedMinorAxisFields.length() > 0 & combinedGraphTypes.length() > 0) {
          combinedMinorAxisFields = combinedMinorAxisFields + "," + minorAxisFieldId;
          combinedGraphTypes = combinedGraphTypes + "," + graphType;
        } else {
          combinedMinorAxisFields = minorAxisFieldId;
          combinedGraphTypes = graphType;
        }
      }
      minorAxisFieldId = combinedMinorAxisFields;
      graphType = combinedGraphTypes;
      minorAxisFieldName = generateFieldNames(fieldList);
    } else {
      getMinorAxisInfo(minorAxisParams, graphList);
      minorAxisFieldName = generateFieldNames(fieldList);
    }

    // Database
    List folderDataList = new ArrayList();
    String majorAxisFieldValue = null;
    String minorAxisParamsValue = null;
    UserBean userBean = PortletUtils.getUser(request);

    String rangeRecordIds = generateRecordIds(db);

    CustomFieldCategory categoryList = new CustomFieldCategory(db, Integer.parseInt(folderId));
    StringTokenizer recordId = new StringTokenizer(rangeRecordIds, ",");
    int folderDataIndex = 0;
    while (recordId.hasMoreTokens()) {

      categoryList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
      categoryList.setLinkItemId(userBean.getUserId());
      categoryList.setIncludeEnabled(Constants.TRUE);
      categoryList.setIncludeScheduled(Constants.TRUE);
      categoryList.setRecordId(Integer.parseInt(recordId.nextToken()));
      categoryList.setBuildResources(true);
      categoryList.setEnteredBy(userBean.getUserId());
      categoryList.setModifiedBy(userBean.getUserId());
      categoryList.buildResources(db);

      StringTokenizer minorAxisToken = new StringTokenizer(minorAxisFieldId, ",");
      while (minorAxisToken.hasMoreTokens()) {
        minorAxisField = minorAxisToken.nextToken();
        iterator = categoryList.iterator();
        while (iterator.hasNext()) {
          CustomFieldGroup customFieldGroup = (CustomFieldGroup) iterator.next();
          Iterator fieldIter = customFieldGroup.iterator();
          while (fieldIter.hasNext()) {
            CustomField customField = (CustomField) fieldIter.next();
            if (customField.getId() == Integer.parseInt(majorAxisFieldId.trim())) {
              majorAxisFieldValue = customField.getValueHtml();
            }
            if (customField.getId() == Integer.parseInt(minorAxisField.trim())) {
              if (minorAxisParamsValue == null) {
                minorAxisParamsValue = customField.getValueHtml();
              } else {
                minorAxisParamsValue = minorAxisParamsValue + "," + customField.getValueHtml();
              }
            }
          }
        }
      }
      minorAxisParamsValue = majorAxisFieldValue + "," + minorAxisParamsValue;
      folderDataList.add(folderDataIndex, minorAxisParamsValue);
      folderDataIndex++;
      majorAxisFieldValue = null;
      minorAxisParamsValue = null;
    }
    drawGraph(request, folderDataList);
  }

  /**
   * Render the Graph with the folder data based on different graph types
   *
   * @param request        Description of the Parameter
   * @param folderDataList Description of the Parameter
   */
  private void drawGraph(RenderRequest request, List folderDataList) {

    String systemLanguage = PortletUtils.getApplicationPrefs(request, "SYSTEM.LANGUAGE");
    UserBean userBean = PortletUtils.getUser(request);
    Locale locale = new Locale(systemLanguage.split("_")[0], systemLanguage.split("_")[1]);
    String checkFileName = null;
    String graphString = "gmr";
    User user = userBean.getUserRecord();
    JFreeChart chart = null;

    if (minorAxisFieldName.indexOf(",") > -1) {
      StringTokenizer strToken = new StringTokenizer(minorAxisFieldName, ",");
      int size = strToken.countTokens();
      minorAxisFieldNames = new String[size];
      for (int index = 0; index < minorAxisFieldNames.length; index++) {
        minorAxisFieldNames[index] = strToken.nextToken().trim();
      }
    }
    //Check the cache and see if the current graph exists and is valid
    if (user.getIsValid()) {
      if (graphString.equals("gmr")) {
        checkFileName = user.getGmr().getLastFileName();
      } else if (graphString.equals("ramr")) {
        checkFileName = user.getRamr().getLastFileName();
      } else if (graphString.equals("cgmr")) {
        checkFileName = user.getCgmr().getLastFileName();
      } else if (graphString.equals("cramr")) {
        checkFileName = user.getCramr().getLastFileName();
      }
    }

    // See if the file exists, otherwise reset the user and the checkFileName
    if (checkFileName != null) {
      File checkFile = new File(getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".jpg");
      if (!checkFile.exists()) {
        if (System.getProperty("DEBUG") != null) {
          System.out.println("FolderGraphPortlet-> Invalidating data, file not found: " + getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".jpg");
        }
        user.setIsValid(false, true);
        checkFileName = null;
      }
    }
    try {
      //Determine if a graph has to be generated
      if (checkFileName != null) {
        //Existing graph is good
        if (System.getProperty("DEBUG") != null) {
          System.out.println("FolderGraphPortlet-> Using cached chart");
        }
        request.setAttribute("GraphFileName", checkFileName);
      } else {
        //Need to generate a new graph
        if (System.getProperty("DEBUG") != null) {
          System.out.println("FolderGraphPortlet-> Preparing the chart");
        }
        if (graphType != null) {

          if (graphType.equalsIgnoreCase("Line") && minorAxisFieldId.indexOf(",") == -1) {        // Line Chart

            //Store the data in the collection
            DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);

            chart = ChartFactory.createLineChart(
                "FolderGraph",                       // chart title
                majorAxisFieldName,                       // domain axis label
                minorAxisFieldName,                  // range axis label
                collection,                               // data
                PlotOrientation.VERTICAL,                // orientation
                Boolean.parseBoolean(showLegend),                   // include legend
                true,                                    // tooltips
                false                             // urls
            );
            chart.setBackgroundPaint(Color.white);
            // get the category plot
            CategoryPlot plot = (CategoryPlot) chart.getPlot();

            // Tell the chart how we would like dates to read
            CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

            // Customize the Range Axis
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setAutoRangeIncludesZero(true);

            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.BLUE);

          } else
          if (graphType.equalsIgnoreCase("Line") && minorAxisFieldId.indexOf(",") > -1) {      // Combined Line Chart
            DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);

            chart = ChartFactory.createLineChart(
                "FolderGraph",                           // chart title
                majorAxisFieldName,                           // domain axis label
                minorAxisFieldNames[0] + " / " + minorAxisFieldNames[1],    // range axis label
                collection,                                   // data
                PlotOrientation.VERTICAL,                    // orientation
                Boolean.parseBoolean(showLegend),                          // include legend
                true,                                        // tooltips
                false                                 // urls
            );
            chart.setBackgroundPaint(Color.white);
            // get the category plot
            CategoryPlot plot = (CategoryPlot) chart.getPlot();

            // Tell the chart how we would like dates to read
            CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

            // Customize the Range Axis
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setAutoRangeIncludesZero(true);

            LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, Color.BLUE);
            renderer.setSeriesPaint(1, Color.RED);

          } else
          if (graphType.equalsIgnoreCase("Bar") && minorAxisFieldId.indexOf(",") == -1) {      // Bar Chart

            DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);

            chart = ChartFactory.createBarChart(
                "FolderGraph",              // chart title
                majorAxisFieldName,            // domain axis label
                minorAxisFieldName,            // range axis label
                collection,                // dataset
                PlotOrientation.VERTICAL,        // the plot orientation
                Boolean.parseBoolean(showLegend),    // legend
                true,                  // tooltips
                false                  // urls
            );

            chart.setBackgroundPaint(Color.white);
            // get the category plot
            CategoryPlot plot = chart.getCategoryPlot();

            ValueAxis yAxis = plot.getRangeAxis();
            yAxis.setTickMarksVisible(true);
            yAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));

            // Tell the chart how we would like dates to read
            CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
            categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

            // Display data points
            //get the CategoryItemRenderer
            CategoryItemRenderer renderer = plot.getRenderer();

            if (renderer instanceof BarRenderer) {
              BarRenderer barRenderer = (BarRenderer) renderer;
              renderer.setSeriesPaint(0, Color.CYAN);
              barRenderer.setDrawBarOutline(true);
              barRenderer.setItemLabelsVisible(true);
              barRenderer.setMaxBarWidth(0.05);
              // Tool tip formatting using locale {1} = Date, {2} = Amount
              CategoryToolTipGenerator toolTipGenerator = new StandardCategoryToolTipGenerator();
              barRenderer.setToolTipGenerator(toolTipGenerator);
            }
          } else
          if (graphType.equalsIgnoreCase("Bar") && minorAxisFieldId.indexOf(",") > -1) {        // Stacked Bar Chart

            DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);

            chart = ChartFactory.createStackedBarChart(
                "FolderGraph",     // chart title
                majorAxisFieldName,                                 // domain axis label
                minorAxisFieldNames[0] + " / " + minorAxisFieldNames[1],    // range axis label
                collection,                                      // dataset
                PlotOrientation.VERTICAL,                       // the plot orientation
                Boolean.parseBoolean(showLegend),               // legend
                true,                                           // tooltips
                false                                          // urls
            );
            GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
            Color color1 = Color.BLUE;
            renderer.setSeriesPaint(0, color1);
            Color color2 = Color.GREEN;
            renderer.setSeriesPaint(1, color2);
            renderer.setMaxBarWidth(0.05);
            SubCategoryAxis domainAxis = new SubCategoryAxis(majorAxisFieldName);
            domainAxis.setCategoryMargin(0.05);
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setDomainAxis(domainAxis);
            plot.setRenderer(renderer);

          } else
          if (graphType.toLowerCase().contains("line") && graphType.toLowerCase().contains("bar")) {    //Combined Chart
            AbstractCategoryItemRenderer renderer1 = null;
            AbstractCategoryItemRenderer renderer2 = null;
            int plotId = 0;
            CategoryDataset dataset1 = createCombinedGraphCategoryDataset(folderDataList, plotId);
            NumberAxis rangeAxis1 = new NumberAxis(minorAxisFieldNames[plotId]);
            rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            if (graphType.toLowerCase().indexOf("line") < graphType.toLowerCase().indexOf("bar")) {
              renderer1 = new LineAndShapeRenderer();
            } else {
              renderer1 = new BarRenderer();
              if (renderer1 instanceof BarRenderer) {
                BarRenderer barRenderer = (BarRenderer) renderer1;
                barRenderer.setMaxBarWidth(0.05);
              }
            }
            renderer1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
            CategoryPlot subPlot1 = new CategoryPlot(dataset1, null, rangeAxis1, renderer1);
            subPlot1.setDomainGridlinesVisible(true);

            plotId = 1;
            CategoryDataset dataset2 = createCombinedGraphCategoryDataset(folderDataList, plotId);
            NumberAxis rangeAxis2 = new NumberAxis(minorAxisFieldNames[plotId]);
            rangeAxis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            if (graphType.toLowerCase().indexOf("line") < graphType.toLowerCase().indexOf("bar")) {
              renderer2 = new BarRenderer();
              if (renderer2 instanceof BarRenderer) {
                BarRenderer barRenderer = (BarRenderer) renderer2;
                barRenderer.setMaxBarWidth(0.05);
              }
            } else {
              renderer2 = new LineAndShapeRenderer();
            }
            renderer2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
            CategoryPlot subPlot2 = new CategoryPlot(dataset2, null, rangeAxis2, renderer2);
            subPlot2.setDomainGridlinesVisible(true);

            CategoryAxis domainAxis = new CategoryAxis(majorAxisFieldName);
            domainAxis.setCategoryMargin(0.10);
            domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
            CombinedDomainCategoryPlot plot = new CombinedDomainCategoryPlot(domainAxis);
            plot.add(subPlot1, 1);
            plot.add(subPlot2, 1);

            chart = new JFreeChart(
                "FolderGraph",                // chart title
                new Font("SansSerif", Font.BOLD, 12),    // font information
                plot,                    // plot
                true                    // legend
            );
          }
          //Output the chart
          if (System.getProperty("DEBUG") != null) {
            System.out.println("FolderGraphPortlet-> Drawing the chart");
          }
          int width = 600;
          int height = 400;

          String filePath = getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs;
          // Make sure the path exists before writing the image
          File graphDirectory = new File(filePath);
          if (!graphDirectory.exists()) {
            graphDirectory.mkdirs();
          }
          java.util.Date testDate = new java.util.Date();

          String fileName = "FolderGraph" + String.valueOf(
              testDate.getTime()) + String.valueOf(
              request.getPortletSession().getCreationTime());

          // Write the chart image
          ChartRenderingInfo renderingInfo = new ChartRenderingInfo(
              new StandardEntityCollection());
          File imageFile = new File(filePath + fileName + ".jpg");
          ChartUtilities.saveChartAsJPEG(
              imageFile, 1.0f, chart, width, height, renderingInfo);
          PrintWriter pw = new PrintWriter(
              new BufferedWriter(new FileWriter(filePath + fileName + ".map")));
          ChartUtilities.writeImageMap(pw, fileName, renderingInfo, false);
          pw.flush();
          pw.close();

          //Update the cached filename
          if (graphString.equals("gmr")) {
            user.getGmr().setLastFileName(fileName);
          } else if (graphString.equals("ramr")) {
            user.getRamr().setLastFileName(fileName);
          } else if (graphString.equals("cgmr")) {
            user.getCgmr().setLastFileName(fileName);
          } else if (graphString.equals("cramr")) {
            user.getCramr().setLastFileName(fileName);
          }
          request.setAttribute("GraphFileName", fileName);
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.out);
      request.setAttribute("Error", e);
    }
  }

  /**
   * Builds the list based on the Range used for rendering
   *
   * @param dataList Description of the Parameter
   * @param start    Range start
   * @param end      Range end
   * @return list
   */
  private ArrayList buildListForGraphRelative(List dataList, int start, int end) {
    ArrayList list = new ArrayList();
    iterator = dataList.iterator();
    int count = 1;
    CustomFieldData element = null;
    boolean flag = true;

    for (int index = start; index <= end && iterator.hasNext(); index++) {
      while (count < index) {
        count++;
        element = (CustomFieldData) iterator.next();
        flag = false;
      }
      if (count == index) {
        if (!flag) {
          element = (CustomFieldData) iterator.next();
          list.add(element);
          flag = true;
        } else {
          element = (CustomFieldData) iterator.next();
          list.add(element);
        }
      }
      count++;
    }
    return list;
  }

  /**
   * Builds the list based on the Range used for rendering
   *
   * @param dataList Description of the Parameter
   * @param start    Range start
   * @param end      Range end
   * @return list
   */
  private ArrayList buildListForGraphAbsolute(List dataList, int start, int end) {
    ArrayList list = new ArrayList();
    iterator = dataList.iterator();
    CustomFieldData element = null;
    for (int count = start; iterator.hasNext(); count++) {
      element = (CustomFieldData) iterator.next();
      if ((start <= element.getRecordId()) && (element.getRecordId() <= end)) {
        list.add(element);
      }
    }
    return list;
  }

  /**
   * Creates the dataset for rendering Line/Bar/Stacked Bar chart
   *
   * @param folderDataList Description of the Parameter
   * @return defaultCategoryDataset dataset
   */
  private DefaultCategoryDataset createGraphCategoryDataset(List folderDataList) {
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    if (minorAxisFieldId.indexOf(",") == -1) {
      for (int count = 0; count < folderDataList.size(); count++) {
        String combinedValue = (String) folderDataList.get(count);
        String xValue = combinedValue.substring(0, combinedValue.indexOf(","));
        String yValue = combinedValue.substring(combinedValue.indexOf(",") + 1);
        StringTokenizer strToken = new StringTokenizer(xValue);
        if (strToken.hasMoreTokens()) {
          xValue = strToken.nextToken();
        }
        categoryDataset.addValue(Double.parseDouble(yValue), minorAxisFieldName, xValue);
      }
    } else if (minorAxisFieldId.indexOf(",") > -1) {
      List minorAxisList = new ArrayList();
      int listIndex = 0;
      for (int count = 0; count < folderDataList.size(); count++) {
        String combinedValue = (String) folderDataList.get(count);
        String xValue = combinedValue.substring(0, combinedValue.indexOf(","));
        String yValue = combinedValue.substring(combinedValue.indexOf(",") + 1);
        StringTokenizer strToken = new StringTokenizer(yValue, ",");

        while (strToken.hasMoreTokens()) {
          minorAxisList.add(listIndex, strToken.nextToken());
          listIndex++;
        }
        StringTokenizer st = new StringTokenizer(xValue);
        if (st.hasMoreTokens()) {
          xValue = st.nextToken();
        }
        categoryDataset.addValue(Double.parseDouble(minorAxisList.get(0).toString()), minorAxisFieldNames[0], xValue);
        categoryDataset.addValue(Double.parseDouble(minorAxisList.get(1).toString()), minorAxisFieldNames[1], xValue);
        minorAxisList.clear();
        listIndex = 0;
      }
    }
    return categoryDataset;
  }

  /**
   * Creates the dataset for rendering Combined Chart
   *
   * @param folderDataList Description of the Parameter
   * @return defaultCategoryDataset dataset
   */
  private DefaultCategoryDataset createCombinedGraphCategoryDataset(List folderDataList, int plotId) {
    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();

    List minorAxisList = new ArrayList();
    int listIndex = 0;
    for (int count = 0; count < folderDataList.size(); count++) {
      String combinedValue = (String) folderDataList.get(count);
      String xValue = combinedValue.substring(0, combinedValue.indexOf(","));
      String yValue = combinedValue.substring(combinedValue.indexOf(",") + 1);
      StringTokenizer strToken = new StringTokenizer(yValue, ",");
      while (strToken.hasMoreTokens()) {
        minorAxisList.add(listIndex, strToken.nextToken());
        listIndex++;
      }
      StringTokenizer st = new StringTokenizer(xValue);
      if (st.hasMoreTokens()) {
        xValue = st.nextToken();
      }
      if (plotId == 0) {
        categoryDataset.addValue(Double.parseDouble(minorAxisList.get(plotId).toString()), minorAxisFieldNames[plotId], xValue);
      } else if (plotId == 1) {
        categoryDataset.addValue(Double.parseDouble(minorAxisList.get(plotId).toString()), minorAxisFieldNames[plotId], xValue);
      }
      minorAxisList.clear();
      listIndex = 0;
    }
    return categoryDataset;
  }

  /**
   * Creates the dataset for rendering Bar chart
   *
   * @param param     Description of the Parameter
   * @param graphList Description of the Parameter
   */
  private void getMinorAxisInfo(String param, List graphList) {
    int index = param.indexOf(":");
    graphId = param.substring(index + 1);
    String minorAxisFields = param.substring(1, param.indexOf("}"));
    if (minorAxisFields.indexOf(",") == -1) {
      minorAxisFieldId = minorAxisFields;
    } else {
      minorAxisFieldId = minorAxisFields;
    }
    for (index = 0; index < graphList.size(); index++) {
      if (Integer.parseInt(graphId) == ((GraphType) graphList.get(index)).getId()) {
        graphType = ((GraphType) graphList.get(index)).getDescription();
      }
    }
  }

  /**
   * Generates record ids based on FolderGraph Range
   *
   * @param db Description of the Parameter
   * @return record id's string
   */
  private String generateRecordIds(Connection db) {
    String firstPart = null;
    String secondPart = null;
    String start = null;
    String end = null;
    ArrayList rangeList = new ArrayList();
    try {
      if (folderRecordRange.startsWith("Start")) {
        String parts[] = folderRecordRange.split(";");
        if (parts.length == 2) {
          firstPart = parts[0];
          secondPart = parts[1];
          int valueIndex1 = firstPart.indexOf(",");
          start = firstPart.substring(valueIndex1 + 1);

          int valueIndex2 = secondPart.indexOf(",");
          end = secondPart.substring(valueIndex2 + 1);

          // MajorAxis List
          CustomFieldDataList dataList = new CustomFieldDataList();
          dataList.setCategoryId(folderId);
          dataList.setFieldId(majorAxisFieldId);
          dataList.buildList(db);

          int startValue = Integer.parseInt(start);
          int endValue = Integer.parseInt(end);

          //for Absolute Case
          if (endValue >= startValue) {
            rangeList = buildListForGraphAbsolute(dataList, startValue, endValue);
          }
          //for Relative Case
          else {
            if (startValue > dataList.size()) {
              startValue = dataList.size();
            }
            startValue = dataList.size() - startValue + 1;
            endValue = dataList.size() - endValue;
            rangeList = buildListForGraphRelative(dataList, startValue, endValue);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    StringBuffer buffer = new StringBuffer();
    CustomFieldData record = null;
    iterator = rangeList.iterator();
    while (iterator.hasNext()) {
      record = (CustomFieldData) iterator.next();
      buffer.append(record.getRecordId());
      buffer.append(",");
    }
    if (buffer.length() > 0) {
      buffer.deleteCharAt(buffer.length() - 1);
    }
    return buffer.toString();
  }

  /**
   * Generates Field Names based on Field List
   *
   * @param fieldList List
   * @return field names string
   */
  private String generateFieldNames(List fieldList) {
    int fieldId = -1;
    if (minorAxisFieldId.indexOf(",") > -1) {
      StringTokenizer minorAxisFieldToken = new StringTokenizer(minorAxisFieldId, ",");
      StringBuffer buffer = new StringBuffer();
      while (minorAxisFieldToken.hasMoreTokens()) {
        fieldId = Integer.parseInt(minorAxisFieldToken.nextToken().trim());
        iterator = fieldList.iterator();
        if (iterator.hasNext()) {
          while (iterator.hasNext()) {
            CustomField field = (CustomField) iterator.next();
            if (field.getId() == fieldId) {
              minorAxisFieldName = field.getName();
              buffer.append(minorAxisFieldName);
              buffer.append(",");
            }
          }
        }
      }
      if (buffer.length() > 0) {
        buffer.deleteCharAt(buffer.length() - 1);
      }
      minorAxisFieldName = buffer.toString();
    } else {
      iterator = fieldList.iterator();
      if (iterator.hasNext()) {
        while (iterator.hasNext()) {
          CustomField field = (CustomField) iterator.next();
          if (field.getId() == Integer.parseInt(minorAxisFieldId)) {
            minorAxisFieldName = field.getName();
          }
        }
      }
    }
    return minorAxisFieldName;
  }
}
