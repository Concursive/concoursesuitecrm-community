/*
 *  Copyright 2002 Dark Horse Ventures
 *  Class begins with "Process" so it bypasses security
 */
package com.darkhorseventures.cfsmodule;

import javax.servlet.*;
import javax.servlet.http.*;
import org.theseus.actions.*;
import java.sql.*;
import java.util.*;
import com.darkhorseventures.cfsbase.*;
import com.darkhorseventures.webutils.*;
import com.Ostermiller.util.*;
import com.zeroio.iteam.base.*;
import com.zeroio.webutils.*;
import com.isavvix.tools.*;
import java.io.*;
import com.darkhorseventures.utils.*;

public final class ProcessEditData extends CFSModule {
	
	//OFFICE PAYOR CATEGORY ID
	public final static int PAYOR_DETAILS_CATEGORY = 24;
	public final static int PROVIDER_TRANSACTION_CATEGORY = 25;
	public final static int OFFICE_TRANSACTION_CATEGORY = 26;
	
  public String executeCommandDefault(ActionContext context) {
    Exception errorMessage = null;
    String id = (String)context.getRequest().getParameter("id");
    StringBuffer testBuffer = new StringBuffer();
    Connection db = null;
    Organization thisOrganization = null;
    
    ArrayList ids = new ArrayList();
    ArrayList finalIds = new ArrayList();
    ArrayList providerArray = new ArrayList();
    
    HashMap thisMap = new HashMap();
    HashMap providerMap = new HashMap();
    
    HashMap orgTotalsMap = new HashMap();
    HashMap perDayTotalsMap = new HashMap();
    
    String recordId = null;
    boolean showRecords = true;
    //Start the download
    try {
      AuthenticationItem auth = new AuthenticationItem();
      db = auth.getConnection(context, false);
		
      StringTokenizer st = new StringTokenizer(id, "|");
      String dbName = st.nextToken();
      String fileName = st.nextToken();
      
      	      CustomFieldCategoryList thisList = new CustomFieldCategoryList();
	      thisList.setLinkModuleId(Constants.ACCOUNTS);
	      thisList.setIncludeEnabled(Constants.TRUE);
	      thisList.setIncludeScheduled(Constants.TRUE);
	      thisList.buildList(db);
      
      OrganizationList orgList = new OrganizationList();
      orgList.buildList(db);
      
      Iterator x = orgList.iterator();
      while (x.hasNext()) {
	      thisOrganization = (Organization)x.next();
	      //System.out.println("Org Found: " + thisOrganization.getName());

	      //GLOBAL
	      CustomFieldCategory thisCategory = thisList.getCategory(PAYOR_DETAILS_CATEGORY);
	      thisCategory.setBuildResources(true);
	      thisCategory.buildResources(db);

		CustomFieldRecordList recordList = new CustomFieldRecordList();
		recordList.setLinkModuleId(Constants.ACCOUNTS);
		recordList.setLinkItemId(thisOrganization.getOrgId());
		recordList.setCategoryId(thisCategory.getId());
		recordList.buildList(db);
		recordList.buildRecordColumns(db, thisCategory);
		
		Iterator r = recordList.iterator();
		while(r.hasNext()) {
			CustomFieldRecord thisRecord = (CustomFieldRecord)r.next();
			//System.out.println("Record: " + thisRecord.getId() + " " + thisRecord.getFieldData().getValueHtml(false));
			
				Iterator groups = thisCategory.iterator();
				while (groups.hasNext()) {
					CustomFieldGroup thisGroup = (CustomFieldGroup)groups.next();
					//System.out.println(thisGroup.getName());
					Iterator fields = thisGroup.iterator();
						if (fields.hasNext()) {
								while (fields.hasNext()) {
									CustomField thisField = (CustomField)fields.next();
									//THIS IS IT - DONT FORGET
									thisField.setRecordId(thisRecord.getId());
									thisField.buildResources(db);
									//!!!!
									
									if (thisField.getNameHtml().indexOf("Provider ID Number") > -1) {
										//thisMap.put((new Integer(thisOrganization.getOrgId())), ((Object)thisField.getValueHtml()));
										thisMap.put( ((Object)thisField.getValueHtml()), (new Integer(thisOrganization.getOrgId())) );
										//System.out.println("Putting " + thisField.getValueHtml() + " at " + thisOrganization.getOrgId());
									}
									//System.out.println(thisField.getNameHtml() + " " + thisField.getValueHtml());
								}
						}
				}

		}
		
      }
      //
      
	//GET FIELD IDS FOR THE PROVIDER TRANS DETAILS
	CustomFieldCategory thisCategory2 = thisList.getCategory(PROVIDER_TRANSACTION_CATEGORY);
	thisCategory2.setBuildResources(true);
	thisCategory2.buildResources(db);

	Iterator groups2 = thisCategory2.iterator();
	while (groups2.hasNext()) {
		CustomFieldGroup thisGroup2 = (CustomFieldGroup)groups2.next();
		Iterator fields2 = thisGroup2.iterator();
			if (fields2.hasNext()) {
					while (fields2.hasNext()) {
						CustomField thisField2 = (CustomField)fields2.next();
						//System.out.println("adding..." + thisField2.getId());
						ids.add(new Integer(thisField2.getId()));
					}
			}

	}
	//END
	
	//GET FINAL IDS
	CustomFieldCategory thisCategory3 = thisList.getCategory(OFFICE_TRANSACTION_CATEGORY);
	thisCategory3.setBuildResources(true);
	thisCategory3.buildResources(db);

	Iterator groups3 = thisCategory3.iterator();
	while (groups3.hasNext()) {
		CustomFieldGroup thisGroup3 = (CustomFieldGroup)groups3.next();
		Iterator fields3 = thisGroup3.iterator();
			if (fields3.hasNext()) {
					while (fields3.hasNext()) {
						CustomField thisField3 = (CustomField)fields3.next();
						System.out.println("adding to finalIds: " + thisField3.getId());
						finalIds.add(new Integer(thisField3.getId()));
					}
			}

	}
	//END
      
      String filePath = this.getPath(context) + "cdb_" + dbName + fs + "data" + fs + fileName + ".csv";
      
      if (System.getProperty("DEBUG") != null) {
        System.out.println("ProcessData-> Sending " + filePath);
      }
      
      FileInputStream fs = new FileInputStream(filePath);
      
	ExcelCSVParser shredder = new ExcelCSVParser(fs);
	String t;
	String sub;
	String subsub;
	String compareDate = "";
	int count = 0;
	int transType = -1;
	boolean moreToProcess = true;
	CustomFieldCategory thisCategory = null;
	
	while ((t = shredder.nextValue()) != null) {
		//System.out.println("" + shredder.lastLineNumber() + " " + t);
		//testBuffer.append(t + "\n");
		//we want this
		
		if (t.indexOf("PROVIDER") > -1) {
			t = t.substring(10);
			if (t.length() > 0) {
				
				providerArray.add(0, new Integer(0));
				providerArray.add(1, new Integer(0));
				providerArray.add(2, new Integer(0));
				providerArray.add(3, new Integer(0));
				providerArray.add(4, new Integer(0));
				
			//testBuffer.append("-->" + t + "\n");
				while ((sub = shredder.nextValue()) != null) {
					if (sub.indexOf("TRANS TYPE") > -1) {
						moreToProcess = true;
						count = 0;
						
						//context.getRequest().setAttribute("cf" + ids.get(1), providerArray.get(0)+"");
						//context.getRequest().setAttribute("cf" + ids.get(2), providerArray.get(1)+"");
						//context.getRequest().setAttribute("cf" + ids.get(3), providerArray.get(2)+"");
						
						sub = shredder.nextValue();
						sub = shredder.nextValue();
						sub = shredder.nextValue();
						//testBuffer.append("---->" + sub + "\n");
						
						//strip header and first ID	
						for (int z=0; z<16; z++) {
							subsub = shredder.nextValue();
						}
								subsub = shredder.nextValue();
								subsub = shredder.nextValue();
								
								//testBuffer.append(t + " > " + sub + "\n>> " + subsub + " " + count + "\n");
								compareDate = subsub;
								
								
								if ( thisMap.get(t) != null ) {
									thisCategory = new CustomFieldCategory(db,PROVIDER_TRANSACTION_CATEGORY);
									thisCategory.setLinkModuleId(Constants.ACCOUNTS);
									thisCategory.setLinkItemId(((Integer)thisMap.get(t)).intValue());
									thisCategory.setIncludeEnabled(Constants.TRUE);
									thisCategory.setIncludeScheduled(Constants.TRUE);
									thisCategory.setEnteredBy(2);
									thisCategory.setModifiedBy(2);
									thisCategory.setBuildResources(true);
									thisCategory.buildResources(db);
									
									if (sub.indexOf("Eligibility") != -1) {
										transType = 0;
									} else if (sub.indexOf("Claim Status") != -1) {
										transType = 1;
									} else if (sub.indexOf("Claim Trans") != -1) {
										transType = 2;
									} else if (sub.indexOf("EOB Trans") != -1) {
										transType = 3;
									} else if (sub.indexOf("EFT Trans") != -1) {
										transType = 4;
									} else {
										transType = 4;
									}
									
									//count++;
									//providerArray.add(transType, new Integer(1));
									//providerMap.put( ((Object)compareDate), providerArray );
									
									if ( providerMap.get(compareDate) != null ) {
										//has this date in the hashmap already
										ArrayList tempArrayList = (ArrayList)providerMap.get(compareDate);
										int tempNum = ((Integer)tempArrayList.get(transType)).intValue();
										((ArrayList)providerMap.get(compareDate)).set(transType, new Integer(tempNum + 1));
										
										/**
										if (((ArrayList)providerMap.get("01/10/2001")) != null) {
											testBuffer.append(providerArray.get(0) + ", " + providerArray.get(1) + " VALUE: " + ((ArrayList)providerMap.get("01/10/2001")).get(0) + ", " + ((ArrayList)providerMap.get("01/10/2001")).get(1) + "\n");
										}
										*/
										
										testBuffer.append(compareDate + " 0a " + ((ArrayList)providerMap.get(compareDate)).get(0) + ", " + ((ArrayList)providerMap.get(compareDate)).get(1) + " type: " + transType + "\n");
									} else {
										//not in hashmap already
										providerArray = new ArrayList();
										providerArray.add(0, new Integer(0));
										providerArray.add(1, new Integer(0));
										providerArray.add(2, new Integer(0));
										providerArray.add(3, new Integer(0));
										providerArray.add(4, new Integer(0));
										
										providerArray.add(transType, new Integer(((Integer)providerArray.get(transType)).intValue() + 1));
										providerMap.put( ((Object)compareDate), providerArray );
										
										testBuffer.append(compareDate + " 0b " + ((ArrayList)providerMap.get(compareDate)).get(0) + ", " + ((ArrayList)providerMap.get(compareDate)).get(1) + " type: " + transType + "\n");
									}
																		
									while (moreToProcess) {
										for (int h=0; h<8 ; h++) {
											subsub = shredder.nextValue();
										}
										
										if (subsub.indexOf("/") > -1) {
											if (subsub.equals(compareDate)) {
												//second consecutive date.  Increment value.
												//count++;
												
												if ( providerMap.get(compareDate) != null ) {
													//has this date in the hashmap already
													ArrayList tempArrayList = (ArrayList)providerMap.get(compareDate);
													int tempNum = ((Integer)tempArrayList.get(transType)).intValue();
													((ArrayList)providerMap.get(compareDate)).set(transType, new Integer(tempNum + 1));
													
													testBuffer.append(compareDate + " 1a " + ((ArrayList)providerMap.get(compareDate)).get(0) + ", " + ((ArrayList)providerMap.get(compareDate)).get(1) + " type: " + transType + "\n");
												} else {
													//not in hashmap already
													providerArray.add(transType, new Integer(((Integer)providerArray.get(transType)).intValue() + 1));
													providerMap.put( ((Object)compareDate), providerArray );
													
													testBuffer.append(compareDate + " 1b " + ((ArrayList)providerMap.get(compareDate)).get(0) + ", " + ((ArrayList)providerMap.get(compareDate)).get(1) + " type: " + transType + "\n");
												}
												//testBuffer.append(">>" + subsub + " " + count + " (SAME)\n");
											} else {
												//new date, not the same as the previous
												
												//testBuffer.append("add here..." + " " + compareDate + " " + count + "\n");
												
												if ( providerMap.get(subsub) != null ) {
													ArrayList tempArrayList = (ArrayList)providerMap.get(subsub);
													int tempNum = ((Integer)tempArrayList.get(transType)).intValue();
													((ArrayList)providerMap.get(subsub)).set(transType, new Integer(tempNum + 1));
													providerArray = ((ArrayList)providerMap.get(subsub));
													
													testBuffer.append(subsub + " 2a " + ((ArrayList)providerMap.get(subsub)).get(0) + ", " + ((ArrayList)providerMap.get(subsub)).get(1) + " type: " + transType + "\n");
												} else {
													//System.out.println(compareDate + " " + subsub);
													//providerMap.put( ((Object)compareDate), providerArray );
													//not in hashmap already
													
													providerMap.put( compareDate, providerArray );
													//testBuffer.append(((ArrayList)providerMap.get("01/04/2001")).get(1) + " " + subsub + " 2b " + ((ArrayList)providerMap.get(compareDate)).get(0) + ", " + ((ArrayList)providerMap.get(compareDate)).get(1) + " type: " + transType + "\n");
													//providerMap.remove(compareDate);
													//providerMap.put( ((Object)compareDate), providerArray );
													
													//testBuffer.append(((ArrayList)providerMap.get("01/04/2001")).get(1) + "\n");
													providerArray = new ArrayList();
													providerArray.add(0, new Integer(0));
													providerArray.add(1, new Integer(0));
													providerArray.add(2, new Integer(0));
													providerArray.add(3, new Integer(0));
													providerArray.add(4, new Integer(0));
													//testBuffer.append(((ArrayList)providerMap.get("01/04/2001")).get(1) + "\n");

													providerArray.add(transType, new Integer(1));
													providerMap.put( ((Object)subsub), providerArray );
													
													testBuffer.append(subsub + " 2b " + ((ArrayList)providerMap.get(subsub)).get(0) + ", " + ((ArrayList)providerMap.get(subsub)).get(1) + " type: " + transType + "\n");

												}
												
												//count=1;
												compareDate = subsub;
											}
										} else {
											moreToProcess = false;
										}
									}

								} else {
									testBuffer.append("Skipping ID " + t + "- could not locate in CFS!\n");
								}
								
							
							//System.out.println(thisMap.get(t));
						
					} else if (sub.indexOf("TOTAL TRANSACTIONS") > -1) {
						break;
					}
					
				}
				
				//is this when we insert??
				
				Iterator hashIterator = providerMap.keySet().iterator();
				
				while(hashIterator.hasNext()) {
					String tempKey = (String)hashIterator.next();
					
					ArrayList anotherList = (ArrayList)providerMap.get(tempKey);
					
					context.getRequest().setAttribute("cf" + ids.get(6), t);
					context.getRequest().setAttribute("cf" + ids.get(0), tempKey);
									
					for (int h=0; h<5; h++) {
						context.getRequest().setAttribute("cf" + ids.get(h+1), anotherList.get(h)+"");
					}
									
					thisCategory.setParameters(context);
					int resultCode = thisCategory.insert(db);
						
					for (int z=1; z<6; z++) {
						context.getRequest().setAttribute("cf" + ids.get(z), "");
					}
					
					if (orgTotalsMap.get(((Integer)thisMap.get(t))) != null) {
						perDayTotalsMap = (HashMap)(orgTotalsMap.get(((Integer)thisMap.get(t))));
						ArrayList tempList = null;
						if ( perDayTotalsMap.get(tempKey) != null ) {
							tempList = (ArrayList)perDayTotalsMap.get(tempKey);
						} else {
							tempList = new ArrayList();
							
							tempList.add(0, new Integer(0));
							tempList.add(1, new Integer(0));
							tempList.add(2, new Integer(0));
							tempList.add(3, new Integer(0));
							tempList.add(4, new Integer(0));
						}
							
							for (int y=0; y<5; y++) {
								int number = ((Integer)tempList.get(y)).intValue();
								
								if ( perDayTotalsMap.get(tempKey) != null ) {
									((ArrayList)perDayTotalsMap.get(tempKey)).set(y, new Integer( number + ((Integer)anotherList.get(y)).intValue() ));
								} else {
									tempList.set(y, new Integer( number + ((Integer)anotherList.get(y)).intValue() ));
									perDayTotalsMap.put(tempKey, tempList);
								}
							}
						orgTotalsMap.put((Integer)thisMap.get(t), perDayTotalsMap);
					} else {
						ArrayList tempList = new ArrayList();
						
						for (int y=0; y<5; y++) {
							tempList.add(y, (Integer)anotherList.get(y));
						}
						
						perDayTotalsMap.put((Object)tempKey, tempList);
						orgTotalsMap.put(thisMap.get(t), perDayTotalsMap);
					}
					
					testBuffer.append("[" + t + "] : " + "INSERTING: " + tempKey + ": " + anotherList.get(0) + ", " + anotherList.get(1) + "\n");
				}
				
				providerMap.clear();
				providerArray = new ArrayList();
				
			}
		} 
		
	}
	
	Iterator firstIterator = orgTotalsMap.keySet().iterator();
			
	while(firstIterator.hasNext()) {
		Integer tempKey = (Integer)firstIterator.next();
		System.out.println("Organization: " + tempKey);
		HashMap secondMap = (HashMap)orgTotalsMap.get(tempKey);
		
		Iterator secondIterator = secondMap.keySet().iterator();
		
		while(secondIterator.hasNext()) {
			String anotherKey = (String)secondIterator.next();
			System.out.println("Date: " + anotherKey);
			
			CustomFieldCategory thisCat = new CustomFieldCategory(db,OFFICE_TRANSACTION_CATEGORY);
			thisCat.setLinkModuleId(Constants.ACCOUNTS);
			thisCat.setLinkItemId(tempKey.intValue());
			thisCat.setIncludeEnabled(Constants.TRUE);
			thisCat.setIncludeScheduled(Constants.TRUE);
			thisCat.setEnteredBy(2);
			thisCat.setModifiedBy(2);
			thisCat.setBuildResources(true);
			thisCat.buildResources(db);
			
			context.getRequest().setAttribute("cf" + finalIds.get(0), anotherKey);
			
			ArrayList finalList = (ArrayList)secondMap.get(anotherKey);
			
			for (int h=0; h<5; h++) {
				context.getRequest().setAttribute("cf" + finalIds.get(h+1), finalList.get(h)+"");
			}
									
			thisCat.setParameters(context);
			int resultCode = thisCat.insert(db);
						
			for (int z=0; z<6; z++) {
				context.getRequest().setAttribute("cf" + finalIds.get(z), "");
			}
		
			//for (int z=0; z<finalList.size(); z++) {
			//	System.out.println(z + " = " + finalList.get(z));
			//}
		}
	}
		
      //if (fileDownload.fileExists()) {
      //  fileDownload.sendFile(context, "image/" + imageType);
      //} else {
      //  System.err.println("ProcessData-> Trying to send a file that does not exist");
      //}
    } catch (Exception e) {
      errorMessage = e;
      System.out.println(e.toString());
    } finally {
      this.freeConnection(context, db);
    }

    context.getRequest().setAttribute("FileString", testBuffer.toString());
    //return ("-none-");
    return ("ProcessDataOK");
  }
}

