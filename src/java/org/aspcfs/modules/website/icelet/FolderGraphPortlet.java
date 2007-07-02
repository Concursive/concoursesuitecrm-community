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
import org.aspcfs.utils.FolderUtils;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.controller.ApplicationPrefs;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.portlet.*;
import java.io.*;
import java.sql.Connection;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.*;

/**
 * Used for rendering the graph based on folder data
 *
 * @author dharmas
 * @author prakashl
 * @version $Id: FolderGraphPortlet.java  4.1 2007-04-06 11:31:46 +0530 (Fri, 06 Apr 2007) dharmas and Prakashl Exp $
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
	public final static String GRAPH_FILE_NAME = "7032115";
	public final static String GRAPH_SIZE = "7032116";
	public final static String GRAPH_TIMESTAMP = "7032117";
	
	private final static String VIEW_PAGE1 = "/portlets/folder/fields_graph.jsp";
	private final static String VIEW_PAGE2 = "/portlets/folder/fields_graph_error.jsp";
	private final static String VIEW_PAGE3 = "/portlets/folder/fields_delete_error.jsp";
	
	private String minorAxisParams = null;
	private String graphType = null;
	private String graphId = null;
	private String minorAxisFieldId = null;
	private String majorAxisFieldId = null;
	private String showLegend = null;
	private String folderRecordRange = null;
	private String majorAxis = null;
	private String folderId = null;
	private String checkFileName = null;
	
	private String majorAxisFieldName = null;
	private String minorAxisFieldName = null;
	private String minorAxisFieldNames[] = null;
	private String graphSize = null;
	private String graphTimeStamp = null;
	
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
			PortletRequestDispatcher requestDispatcher = null;			
			int renderGraph = Constants.ERROR;
			renderGraph = buildGraph(request, response, renderGraph);
			if (renderGraph == Constants.SUCCESS) {
				//forwarded to JSP page which displays Graph
				requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE1);
			} else if(renderGraph == Constants.ERROR){
				//forwards to JSP which displays error message
				requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE2);
			} else if(renderGraph == Constants.DELETED) {
				//forwards to JSP which displays error message
				requestDispatcher = getPortletContext().getRequestDispatcher(VIEW_PAGE3);
			}
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
		private int buildGraph(RenderRequest request, RenderResponse response, int renderGraph) throws Exception {
		folderId = (String) request.getPreferences().getValue(SELECT_FOLDER, "-1");
		majorAxis = (String) request.getPreferences().getValue(MAJOR_AXIS, "X");
		majorAxisFieldId = (String) request.getPreferences().getValue(MAJOR_AXIS_FIELD, "-1");
		minorAxisParams = (String) request.getPreferences().getValue(MINOR_AXIS_PARAM, "-1");
		showLegend = (String) request.getPreferences().getValue(SHOW_LEGEND, "false");
		folderRecordRange = (String) request.getPreferences().getValue(FOLDER_RECORD_RANGE, "-1");
		checkFileName = (String) request.getPreferences().getValue(GRAPH_FILE_NAME, null);
		graphSize = (String) request.getPreferences().getValue(GRAPH_SIZE, "-1");
		graphTimeStamp = (String) request.getPreferences().getValue(GRAPH_TIMESTAMP, "-1");

		String minorAxisField = null;
		Connection db = PortletUtils.getConnection(request);
    	CustomFieldRecordList recordList = null;
    	CustomFieldRecord thisRecord = null;

    	GraphTypeList graphList = new GraphTypeList();
		if (minorAxisParams != null && minorAxisParams.length() > 0) {
			graphList.setTableName("lookup_graph_type");
			graphList.setEnabledState(1);
			graphList.buildList(db);
		}

		//getting the field names for both major and minor axis
		CustomFieldList fieldList = new CustomFieldList();
		fieldList.buildList(db);
		Iterator iterator = fieldList.iterator();
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
        CustomFieldCategory customCategory = null;
        int folderDataIndex = 0;
    	int count = 0;
        Iterator fieldIterator = null;
    	recordList = new CustomFieldRecordList();
		recordList.setLinkModuleId(Constants.FOLDERS_GLOBALFOLDERS);
		recordList.setCategoryId(Integer.parseInt(folderId));
		recordList.buildList(db);
        Iterator records = recordList.iterator();
	  while (records.hasNext() && count < Integer.parseInt(folderRecordRange)) {
    		thisRecord = (CustomFieldRecord) records.next();
            customCategory = new CustomFieldCategory(db, Integer.parseInt(folderId));
			customCategory.setRecordId(thisRecord.getId());
            customCategory.setBuildResources(true);
			customCategory.buildResources(db);
        	StringTokenizer minorAxisToken = new StringTokenizer(minorAxisFieldId, ",");
		  boolean toRenderRecord = true;
			  while (minorAxisToken.hasMoreTokens()) {
				  minorAxisField = minorAxisToken.nextToken();
				  iterator = customCategory.iterator();
                  if(FolderUtils.fieldExists(minorAxisField, customCategory)) {
					  while (iterator.hasNext()) {
                          CustomFieldGroup customFieldGroup = (CustomFieldGroup) iterator.next();
						  fieldIterator  = customFieldGroup.iterator();
						  while (fieldIterator.hasNext()) {
							  CustomField customField = (CustomField) fieldIterator.next();
							  if (customField.getId() == Integer.parseInt(majorAxisFieldId.trim())) {
								  majorAxisFieldValue = customField.getValueHtml();
								//validating majorAxisFieldValue for a valid date in the Locale Format
                		Locale locale = new Locale(System.getProperty("LANGUAGE"), System.getProperty("COUNTRY"));
                		DateFormat localeFormatter = DateFormat.getDateInstance(DateFormat.SHORT,locale);
                		ParsePosition pos = new ParsePosition(0);
                		localeFormatter.setLenient(false);
                		Date date = localeFormatter.parse(majorAxisFieldValue, pos);
                		// if date is null, record is not rendered
								if (date == null) {
									toRenderRecord = false;	
								}							
							}
							if (customField.getId() == Integer.parseInt(minorAxisField.trim())) {
								double filteredValue = 0.0f;
								if (minorAxisParamsValue == null) {
									filteredValue = filterFieldValue(customField);								
									if(filteredValue != 0.0) {									
										minorAxisParamsValue = Double.toString(filteredValue);									
									} else {
										// if filteredValue is 0.0, record is not rendered
										toRenderRecord = false;										
									}
								} else {
									filteredValue = filterFieldValue(customField);
									if(filteredValue != 0.0) {
										minorAxisParamsValue = minorAxisParamsValue + "," + filteredValue;									
									} else {
										// if filteredValue is 0.0, record is not rendered
										toRenderRecord = false;										
									}
								}
							}
						}
					}
				} else {					
					toRenderRecord = false;		
					renderGraph = Constants.DELETED;
				}					
			}
			//record is added to the folderDataList for rendering only when it is not null and toRenderRecord is true
			if (minorAxisParamsValue != null && !minorAxisParamsValue.equals("null") && toRenderRecord == true) {				
				minorAxisParamsValue = majorAxisFieldValue + "," + minorAxisParamsValue;
				folderDataList.add(folderDataIndex, minorAxisParamsValue);
				folderDataIndex++;
				renderGraph = Constants.SUCCESS;
			}
			majorAxisFieldValue = null;
			minorAxisParamsValue = null;
      count++;
    }
		// drawGraph is called only when atleast one record is present in folderDataList
		if (renderGraph == Constants.SUCCESS) {
			drawGraph(request, folderDataList);
		}
		return renderGraph;
	}
	
	/**
	 * Filter the customField value if it is of type Currency or Percent by removing "," or "%" respectively
	 *
	 * @param CustomField to filter
	 * @return float filteredValue
	 **/
	private double filterFieldValue(CustomField customField) {
		double filteredValue = 0.0;
		String selectedValue = null;
		try {
			if (customField.getTypeString().equals("Number") || customField.getTypeString().equals("Decimal Number") || customField.getTypeString().equals("Percent") || customField.getTypeString().equals("Currency")) {
				if (customField.getValueHtml() != null && !customField.getValueHtml().equals("null") && !customField.getValueHtml().equals("")) {
					if (customField.getTypeString().equals("Percent")) {
						selectedValue = customField.getValueHtml().substring(0, customField.getValueHtml().length() - 1);
					} else if ("Currency".equals(customField.getTypeString())) {
						selectedValue = customField.getValueHtml().replaceAll(",", "");	
						for(int index = 0;index < selectedValue.length();index++) {									
							if(StringUtils.isNumeric(selectedValue.charAt(index))) {
								selectedValue = selectedValue.substring(index);										
								break;
							}
						}
					} else {
						selectedValue = customField.getValueHtml();
					}
					if(!Double.isNaN(Double.parseDouble(selectedValue))) {
						filteredValue = Double.parseDouble(selectedValue);
					}
				}
			}
		} catch(NumberFormatException ex) {
			// not reporting exception to the application and returning default value to stop the record from rendering
			return 0.0;
		}
		return filteredValue;
	}
	
	/**
	 * Render the Graph with the folder data based on different graph types
	 *
	 * @param request        Description of the Parameter
	 * @param folderDataList Description of the Parameter
	 */
	private void drawGraph(RenderRequest request, List folderDataList) {
		
		int width = 0,height = 0;
		String widthHeight = graphSize;
		
		ApplicationPrefs prefs = PortletUtils.getApplicationPrefs(request);
		String filePath = prefs.get("FILELIBRARY") + PortletUtils.fs + "graphs" + PortletUtils.fs;
		
		if (widthHeight.contains(",")) {
			String values[] = widthHeight.split(",");
			width = Integer.parseInt(values[0].replace("Width:", ""));
			height = Integer.parseInt(values[1].replace("Height:", ""));
		}
		
		String rangeAxisLabel = "";
		String systemLanguage = PortletUtils.getApplicationPrefs(request, "SYSTEM.LANGUAGE");
		UserBean userBean = PortletUtils.getUser(request);
		Locale locale = new Locale(systemLanguage.split("_")[0], systemLanguage.split("_")[1]);		
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
		
		File checkFile = new File(filePath + checkFileName + ".jpg");
		File mapFile = new File(filePath + checkFileName + ".map");
		
		// See if the file exists, otherwise reset the user and the checkFileName
		if (checkFileName != null) {
			/*	File checkFile = new File(getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".jpg");
			 File mapFile = new File(getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".map"); */
			
			if (!checkFile.exists() && !mapFile.exists()) {
				if (System.getProperty("DEBUG") != null) {
					System.out.println("FolderGraphPortlet-> Invalidating data, file not found: " + getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".jpg");
					System.out.println("FolderGraphPortlet-> Invalidating data, file not found: " + getPortletContext().getRealPath("/") + "graphs" + PortletUtils.fs + checkFileName + ".map");
				}
			} else{
				Date fileDateLastModified = new Date(checkFile.lastModified());
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(fileDateLastModified);
				calendar.add(Calendar.MINUTE, Integer.parseInt(graphTimeStamp));
				
				if(calendar.getTime().before( new Date(System.currentTimeMillis()))) {
					//Deleting the existing graph
					if (System.getProperty("DEBUG") != null) {
						System.out.println("FolderGraphPortlet-> Deleting the chart");
					}
					checkFile.delete();
					mapFile.delete();
				}
			}
		}
		try {
			//Determine if a graph has to be generated
			if ((checkFile.exists() && mapFile.exists()) ) {
				//Existing graph is good
				if (System.getProperty("DEBUG") != null) {
					System.out.println("FolderGraphPortlet-> Using cached chart");
				}
				ChartRenderingInfo renderingInfo = new ChartRenderingInfo(new StandardEntityCollection());
				request.setAttribute("GraphFileName", checkFileName);
				request.setAttribute("ChartRenderInfo", renderingInfo);
			} else {
				//Generate a new graph
				if (System.getProperty("DEBUG") != null) {
					System.out.println("FolderGraphPortlet-> Preparing the chart");
				}
				if (graphType != null) {
					PlotOrientation plotOrientation = null; 
					if (majorAxis.equalsIgnoreCase("X")) {
						plotOrientation = PlotOrientation.VERTICAL;
					} else if(majorAxis.equalsIgnoreCase("Y")) {
						plotOrientation = PlotOrientation.HORIZONTAL;
					}
					if (graphType.equalsIgnoreCase("Line") && minorAxisFieldId.indexOf(",") == -1) {        // Line Chart
						//Store the data in the collection
						DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);
						chart = ChartFactory.createLineChart(
								"",              												// chart title
								majorAxisFieldName,                     // domain axis label
								minorAxisFieldName,                  	  // range axis label
								collection,                             // data
								plotOrientation,			                  // plot orientation
								Boolean.parseBoolean(showLegend),       // include legend
								true,                                   // tooltips
								false                             		  // urls
						);
						// get the category plot
						CategoryPlot plot = (CategoryPlot) chart.getPlot();
						
						// Tell the chart how we would like dates to read
						CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
						categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
						
						// Customize the Range Axis
						NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
						rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
						rangeAxis.setAutoRangeIncludesZero(true);
						
						LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
						renderer.setShapesVisible(true);
						displayLineItemLabels(renderer);						
					} else
						if (graphType.equalsIgnoreCase("Line") && minorAxisFieldId.indexOf(",") > -1) {      // Combined Line Chart
							DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);
							for(int index = 0; index < minorAxisFieldNames.length; index++) {
								rangeAxisLabel = rangeAxisLabel + minorAxisFieldNames[index] + " / ";
							}
							rangeAxisLabel = rangeAxisLabel.substring(0, rangeAxisLabel.lastIndexOf("/"));
							chart = ChartFactory.createLineChart(
									"",           														// chart title
									majorAxisFieldName,                       // domain axis label
									rangeAxisLabel,									          // range axis label
									collection,                               // data
									plotOrientation,			                   	// plot orientation
									Boolean.parseBoolean(showLegend),         // include legend
									true,                                     // tooltips
									false                                 		// urls
							);
							// get the category plot
							CategoryPlot plot = (CategoryPlot) chart.getPlot();
							
							// Tell the chart how we would like dates to read
							CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
							categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
							
							// Customize the Range Axis
							NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
							rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
							rangeAxis.setAutoRangeIncludesZero(true);
							
							LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
							renderer.setShapesVisible(true);
							displayLineItemLabels(renderer);							
						} else
							if (graphType.equalsIgnoreCase("Bar") && minorAxisFieldId.indexOf(",") == -1) {      // Bar Chart
								
								DefaultCategoryDataset collection = createGraphCategoryDataset(folderDataList);
								
								chart = ChartFactory.createBarChart(
										"",              												// chart title
										majorAxisFieldName,            			    // domain axis label
										minorAxisFieldName,            			    // range axis label
										collection,                				      // dataset
										plotOrientation,				     	          // plot orientation
										Boolean.parseBoolean(showLegend),    	  // legend
										true,                  					        // tooltips
										false                 					        // urls
								);
								
								// get the category plot
								CategoryPlot plot = chart.getCategoryPlot();
								
								ValueAxis yAxis = plot.getRangeAxis();
								yAxis.setTickMarksVisible(true);
								yAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits(locale));
								
								// Tell the chart how we would like dates to read
								CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
								categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
								
								// Display data points
								//get the CategoryItemRenderer
								CategoryItemRenderer renderer = plot.getRenderer();
								
								if (renderer instanceof BarRenderer) {
									BarRenderer barRenderer = (BarRenderer) renderer;
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
									for(int index = 0; index < minorAxisFieldNames.length; index++) {
										rangeAxisLabel = rangeAxisLabel + minorAxisFieldNames[index] + " / ";
									}
									rangeAxisLabel = rangeAxisLabel.substring(0, rangeAxisLabel.lastIndexOf("/"));
									chart = ChartFactory.createStackedBarChart(
											"",     																	// chart title
											majorAxisFieldName,                       // domain axis label
											rangeAxisLabel,									          // range axis label
											collection,                               // dataset
											plotOrientation,			                    // plot orientation
											Boolean.parseBoolean(showLegend),         // legend
											true,                                     // tooltips
											false                                     // urls
									);
									GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
									renderer.setMaxBarWidth(0.05);
									CategoryToolTipGenerator toolTipGenerator = new StandardCategoryToolTipGenerator();
									renderer.setToolTipGenerator(toolTipGenerator);
									
									SubCategoryAxis domainAxis = new SubCategoryAxis(majorAxisFieldName);
									domainAxis.setCategoryMargin(0.05);
									domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
									
									CategoryPlot plot = (CategoryPlot) chart.getPlot();
									plot.setDomainAxis(domainAxis);
									plot.setRenderer(renderer);
									
								} else
									if (graphType.toLowerCase().contains("line") || graphType.toLowerCase().contains("bar")) {    //Combined Chart
										StringBuffer lineGraphFields = new StringBuffer();
										StringBuffer barGraphFields = new StringBuffer();
										String value = null;
										int index = 0;
										
										String lineFields[] = null;
										String barFields[] = null;
										
										StringTokenizer stToken = new StringTokenizer(graphType, ",");
										while(stToken.hasMoreTokens()) {
											value = stToken.nextToken();
											if (value.equalsIgnoreCase("Line")) {
												lineGraphFields.append(minorAxisFieldNames[index]);
												lineGraphFields.append(",");
											} else if (value.equalsIgnoreCase("Bar")) {
												barGraphFields.append(minorAxisFieldNames[index]);
												barGraphFields.append(",");
											}
											index++;
										}
										if (lineGraphFields.length() > 0) {
											lineGraphFields.deleteCharAt(lineGraphFields.length() - 1);
										}
										if (barGraphFields.length() > 0) {
											barGraphFields.deleteCharAt(barGraphFields.length() - 1);
										}
										
										StringTokenizer lineTokens = new StringTokenizer(lineGraphFields.toString(), ",");
										lineFields = new String[lineTokens.countTokens()];
										index = 0;
										while(lineTokens.hasMoreTokens()) {
											lineFields[index] = lineTokens.nextToken();
											index++;
										}
										
										StringTokenizer barTokens = new StringTokenizer(barGraphFields.toString(), ",");
										barFields = new String[barTokens.countTokens()];
										index = 0;
										while(barTokens.hasMoreTokens()) {
											barFields[index] = barTokens.nextToken();
											index++;
										}
										chart = createCombinedChart(folderDataList, lineFields, barFields);			
									}
					//Output the chart
					if (System.getProperty("DEBUG") != null) {
						System.out.println("FolderGraphPortlet-> Drawing the chart");
					}					
					
					// Make sure the path exists before writing the image
					File graphDirectory = new File(filePath);
					if (!graphDirectory.exists()) {
						graphDirectory.mkdirs();
					}
					
					// Write the chart image
					ChartRenderingInfo renderingInfo = new ChartRenderingInfo(
							new StandardEntityCollection());
					File imageFile = new File(filePath + checkFileName + ".jpg");
					ChartUtilities.saveChartAsJPEG(
							imageFile, 1.0f, chart, width, height, renderingInfo);
					PrintWriter pw = new PrintWriter(
							new BufferedWriter(new FileWriter(filePath + checkFileName + ".map")));
					ChartUtilities.writeImageMap(pw, checkFileName, renderingInfo, false);
					pw.flush();
					pw.close();
					
					request.setAttribute("GraphFileName", checkFileName);
					request.setAttribute("ChartRenderInfo", renderingInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			request.setAttribute("Error", e);
		}
	}		
	
	/**
	 * set the line renderer to display the values for items	 *
	 * @param renderer CategoryItemRenderer	  
	 */
	private void displayLineItemLabels(CategoryItemRenderer renderer) {		
		renderer.setItemLabelsVisible(true);						
		renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());						
		renderer.setItemLabelsVisible(true);
		renderer.setBaseItemLabelsVisible(true);
	}
	
	/**
	 * Builds the Combined Chart from the data list by overlapping both bars and lines
	 *
	 * @param folderDataList the data list
	 * @param lineGraphFields Array of line graph fields 
	 * @param barGraphFields Array of bar graph fields 
	 * @return JFreeChart Combined overlapping chart
	 */
	private JFreeChart createCombinedChart(List folderDataList, String [] lineGraphFields, String [] barGraphFields) { 	  
		String rangeAxisLabel = "";
		int index = 0;
		DefaultCategoryDataset lineDataset = null;
		DefaultCategoryDataset barDataset = null;
		for(;index < barGraphFields.length; index++) {
			rangeAxisLabel = rangeAxisLabel + barGraphFields[index] + " / ";
		}
		for(index = 0; index < lineGraphFields.length; index++) {
			rangeAxisLabel = rangeAxisLabel + lineGraphFields[index] + " / ";
		}
		rangeAxisLabel = rangeAxisLabel.substring(0, rangeAxisLabel.lastIndexOf("/"));
		CategoryPlot plot = new CategoryPlot();
		CategoryToolTipGenerator toolTipGenerator = new StandardCategoryToolTipGenerator();
		CategoryItemRenderer lineRenderer = new LineAndShapeRenderer();
		displayLineItemLabels(lineRenderer);	
		
		lineRenderer.setToolTipGenerator(toolTipGenerator);
		CategoryItemRenderer barRenderer = new BarRenderer();
		barRenderer.setToolTipGenerator(toolTipGenerator);
		barRenderer.setItemLabelsVisible(true);
		
		if(lineGraphFields.length > 0 && barGraphFields.length > 0 ){
			lineDataset = createDataset(folderDataList, lineGraphFields);
			barDataset = createDataset(folderDataList, barGraphFields);
			plot.setDataset(barDataset);
			plot.setRenderer(barRenderer);
			plot.setDataset(1, lineDataset);
			plot.setRenderer(1, lineRenderer);
		} else if(barGraphFields.length > 0){
			barDataset = createDataset(folderDataList, barGraphFields);
			plot.setDataset(barDataset);
			plot.setRenderer(barRenderer);
		}	else if(lineGraphFields.length > 0){
			lineDataset = createDataset(folderDataList, lineGraphFields);
			plot.setDataset(lineDataset);
			plot.setRenderer(lineRenderer);
		}
		
		plot.setDomainAxis(new CategoryAxis(majorAxisFieldName));
		plot.setRangeAxis(new NumberAxis(rangeAxisLabel));
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setDomainGridlinesVisible(true);
		
		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);	  
		plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		//JFreeChart chart = new JFreeChart(plot);
		// Modified JFreeChart constructor to show lezend for combined chart only. 
		JFreeChart chart = new JFreeChart(null, null, plot, Boolean.parseBoolean(showLegend));
		return chart;
	}
	
	/**
	 * Creates the dataset for rendering Combined charts
	 * @param graphFields Array of Line/Bar graph fields
	 * @param folderDataList Description of the Parameter	 
	 * @return defaultCategoryDataset dataset
	 */
	private DefaultCategoryDataset createDataset(List folderDataList, String [] graphFields) {
		DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
		List minorAxisList = new ArrayList();
		int listIndex = 0;					
		if (minorAxisFieldId.indexOf(",") > -1) {			
			for (int count = 0; count < folderDataList.size(); count++) {			
				String fieldName = null;
				int graphFieldsIndex = 0, allFieldsIndex = 0;
				String combinedValue = (String) folderDataList.get(count);
				String xValue = combinedValue.substring(0, combinedValue.indexOf(","));
				String yValue = combinedValue.substring(combinedValue.indexOf(",") + 1);
				StringTokenizer strToken = new StringTokenizer(yValue, ",");
				while (strToken.hasMoreTokens() && graphFieldsIndex < graphFields.length) {			
					fieldName = minorAxisFieldNames[allFieldsIndex];
					if(graphFields[graphFieldsIndex].equalsIgnoreCase(fieldName)) {			
						minorAxisList.add(listIndex, strToken.nextToken());
						listIndex++;
						graphFieldsIndex++;
						allFieldsIndex++;
					} else {
						strToken.nextToken();
						allFieldsIndex++;
					}
				}				
				
				for(int index = 0; index < minorAxisList.size(); index++) {
					categoryDataset.addValue(Double.parseDouble(minorAxisList.get(index).toString()), graphFields[index], xValue);
				}				
				minorAxisList.clear();
				listIndex = 0;
			}
		}
		return categoryDataset;  	
	}		
	/**
	 * Creates the dataset for rendering Line/Multi Line/Bar/Stacked Bar chart
	 *
	 * @param folderDataList Description of the Parameter
	 * @return defaultCategoryDataset dataset
	 */
	private DefaultCategoryDataset createGraphCategoryDataset(List folderDataList) {
		DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
		List minorAxisList = new ArrayList();
		int listIndex = 0;
		if (minorAxisFieldId.indexOf(",") == -1) {			// for Line & Bar
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
		} else if (minorAxisFieldId.indexOf(",") > -1) {	// for Multi Line
			if (graphType.equalsIgnoreCase("Line")) {
				String legend = "";
				for(int index = 0; index < minorAxisFieldNames.length; index++) {
					legend = legend + minorAxisFieldNames[index] + ",";
				}
				legend = legend.substring(0, legend.lastIndexOf(","));
				legend = "Sum(" + legend + ")";
				for (int count = 0; count < folderDataList.size(); count++) {
					String combinedValue = (String) folderDataList.get(count);
					String xValue = combinedValue.substring(0, combinedValue.indexOf(","));
					String yValue = combinedValue.substring(combinedValue.indexOf(",") + 1);
					StringTokenizer strToken = new StringTokenizer(yValue, ",");
					double totalValue = 0.0;					
					while (strToken.hasMoreTokens()) {					
						totalValue = totalValue + Double.parseDouble(strToken.nextToken());							
						minorAxisList.add(listIndex, totalValue);
						listIndex++;											
					}
					StringTokenizer st = new StringTokenizer(xValue);
					if (st.hasMoreTokens()) {
						xValue = st.nextToken();
					}							
					for(int index = 0; index < minorAxisList.size(); index++) {
						if (index > 0) {
							categoryDataset.addValue(Double.parseDouble(minorAxisList.get(index).toString()), "(+)" + minorAxisFieldNames[index], xValue);
						} else {
							categoryDataset.addValue(Double.parseDouble(minorAxisList.get(index).toString()), minorAxisFieldNames[index], xValue);
						}
					}
					minorAxisList.clear();
					listIndex = 0;
				}
			}	else if (graphType.equalsIgnoreCase("Bar"))  {		// for Stacked Bar				
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
					for(int index = 0; index < minorAxisList.size(); index++) {							
						if (index > 0) {
							categoryDataset.addValue(Double.parseDouble(minorAxisList.get(index).toString()), "(+)" + minorAxisFieldNames[index], xValue);
						} else {
							categoryDataset.addValue(Double.parseDouble(minorAxisList.get(index).toString()), minorAxisFieldNames[index], xValue);
						}
					}
					minorAxisList.clear();
					listIndex = 0;
				}
			}
		}		
		return categoryDataset;
	}
	
	/**
	 * Gets the minor Axis Information for rendering
	 *
	 * @param param     minor axis parameter name
	 * @param graphList Graph list
	 */
	private void getMinorAxisInfo(String param, List graphList) {
		int index = param.indexOf(":");
		graphId = param.substring(index + 1);
		String minorAxisFields = param.substring(1, param.indexOf("}"));		
		minorAxisFieldId = minorAxisFields;
		for (index = 0; index < graphList.size(); index++) {
			if (Integer.parseInt(graphId) == ((GraphType) graphList.get(index)).getId()) {
				graphType = ((GraphType) graphList.get(index)).getDescription();
			}
		}
	}
	/**
	 * Generates Field Names based on Field List
	 *
	 * @param fieldList List
	 * @return field names string
	 */
	private String generateFieldNames(List fieldList) {
		int fieldId = -1;
		Iterator iterator = null;
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