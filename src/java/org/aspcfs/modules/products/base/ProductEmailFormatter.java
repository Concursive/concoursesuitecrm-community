/*
 *  Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
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
package org.aspcfs.modules.products.base;

import org.aspcfs.modules.base.*;
import org.aspcfs.utils.StringUtils;
import org.aspcfs.utils.Template;
import org.aspcfs.utils.XMLUtils;
import org.aspcfs.utils.CurrencyFormat;
import org.aspcfs.utils.DatabaseUtils;

import org.w3c.dom.Element;
import java.util.Locale;
import java.io.File;
import java.util.Iterator;

/**
 *  Formats product information
 *
 *@author     kailash
 *@created    June 13, 2006 $Id: Exp $
 *@version    $Id: Exp $
 */
public class ProductEmailFormatter {

	private String introduction = null;
	private boolean showSku = false;
	private boolean showPrice = false;
	private boolean showPriceSavings = false;
	private String productURL = null;
	private String siteURL = null;

	private String fromName = null;
	private String skuText = null;
	private String originalPriceText = null;
	private String priceSavingsText = null;
	private String priceText = null;

	private Locale locale = Locale.US;


	/**
	 *  Constructor for the ProductEmailFormatter object
	 */
	public ProductEmailFormatter() { }


	/**
	 *  Sets the introduction attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new introduction value
	 */
	public void setIntroduction(String tmp) {
		this.introduction = tmp;
	}


	/**
	 *  Sets the productURL attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new productURL value
	 */
	public void setProductURL(String tmp) {
		this.productURL = tmp;
	}


	/**
	 *  Sets the siteURL attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new siteURL value
	 */
	public void setSiteURL(String tmp) {
		this.siteURL = tmp;
	}


	/**
	 *  Sets the fromName attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new fromName value
	 */
	public void setFromName(String tmp) {
		this.fromName = tmp;
	}


	/**
	 *  Sets the showSku attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showSku value
	 */
	public void setShowSku(boolean tmp) {
		this.showSku = tmp;
	}


	/**
	 *  Sets the showSku attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showSku value
	 */
	public void setShowSku(String tmp) {
		this.showSku = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Sets the showPrice attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showPrice value
	 */
	public void setShowPrice(boolean tmp) {
		this.showPrice = tmp;
	}


	/**
	 *  Sets the showPrice attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showPrice value
	 */
	public void setShowPrice(String tmp) {
		this.showPrice = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Sets the showPriceSavings attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showPriceSavings value
	 */
	public void setShowPriceSavings(boolean tmp) {
		this.showPriceSavings = tmp;
	}


	/**
	 *  Sets the showPriceSavings attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new showPriceSavings value
	 */
	public void setShowPriceSavings(String tmp) {
		this.showPriceSavings = DatabaseUtils.parseBoolean(tmp);
	}


	/**
	 *  Sets the skuText attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new skuText value
	 */
	public void setSkuText(String tmp) {
		this.skuText = tmp;
	}


	/**
	 *  Sets the originalPriceText attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new originalPriceText value
	 */
	public void setOriginalPriceText(String tmp) {
		this.originalPriceText = tmp;
	}


	/**
	 *  Sets the priceSavingsText attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new priceSavingsText value
	 */
	public void setPriceSavingsText(String tmp) {
		this.priceSavingsText = tmp;
	}


	/**
	 *  Sets the priceText attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new priceText value
	 */
	public void setPriceText(String tmp) {
		this.priceText = tmp;
	}


	/**
	 *  Sets the locale attribute of the ProductEmailFormatter object
	 *
	 *@param  tmp  The new locale value
	 */
	public void setLocale(Locale tmp) {
		this.locale = tmp;
	}


	/**
	 *  Gets the introduction attribute of the ProductEmailFormatter object
	 *
	 *@return    The introduction value
	 */
	public String getIntroduction() {
		return introduction;
	}


	/**
	 *  Gets the productURL attribute of the ProductEmailFormatter object
	 *
	 *@return    The productURL value
	 */
	public String getProductURL() {
		return productURL;
	}


	/**
	 *  Gets the siteURL attribute of the ProductEmailFormatter object
	 *
	 *@return    The siteURL value
	 */
	public String getSiteURL() {
		return siteURL;
	}


	/**
	 *  Gets the fromName attribute of the ProductEmailFormatter object
	 *
	 *@return    The fromName value
	 */
	public String getFromName() {
		return fromName;
	}


	/**
	 *  Gets the showSku attribute of the ProductEmailFormatter object
	 *
	 *@return    The showSku value
	 */
	public boolean getShowSku() {
		return showSku;
	}


	/**
	 *  Gets the showPrice attribute of the ProductEmailFormatter object
	 *
	 *@return    The showPrice value
	 */
	public boolean getShowPrice() {
		return showPrice;
	}


	/**
	 *  Gets the showPriceSavings attribute of the ProductEmailFormatter object
	 *
	 *@return    The showPriceSavings value
	 */
	public boolean getShowPriceSavings() {
		return showPriceSavings;
	}


	/**
	 *  Gets the skuText attribute of the ProductEmailFormatter object
	 *
	 *@return    The skuText value
	 */
	public String getSkuText() {
		return skuText;
	}


	/**
	 *  Gets the originalPriceText attribute of the ProductEmailFormatter object
	 *
	 *@return    The originalPriceText value
	 */
	public String getOriginalPriceText() {
		return originalPriceText;
	}


	/**
	 *  Gets the priceSavingsText attribute of the ProductEmailFormatter object
	 *
	 *@return    The priceSavingsText value
	 */
	public String getPriceSavingsText() {
		return priceSavingsText;
	}


	/**
	 *  Gets the priceText attribute of the ProductEmailFormatter object
	 *
	 *@return    The priceText value
	 */
	public String getPriceText() {
		return priceText;
	}


	/**
	 *  Gets the locale attribute of the ProductEmailFormatter object
	 *
	 *@return    The locale value
	 */
	public Locale getLocale() {
		return locale;
	}


	/**
	 *  Gets the productInformation attribute of the ProductEmailFormatter class
	 *
	 *@param  productCatalog  Description of the Parameter
	 *@param  propertyFile    Description of the Parameter
	 *@return                 The productInformation value
	 *@exception  Exception   Description of the Exception
	 */
	public String getProductInformation(ProductCatalog productCatalog, String propertyFile) throws Exception {
    File configFile = new File(propertyFile);
		XMLUtils xml = new XMLUtils(configFile);
		Element mappings = xml.getFirstChild("mappings");

    Template template = new Template();
		template.setText(
				XMLUtils.getNodeText(
				XMLUtils.getElement(
				mappings, "map", "id", "productInformation.details")));
		template.addParseElement("${email.fromName}", this.getFromName());
		template.addParseElement("${product.name}", productCatalog.getName());
		if (productCatalog.getLargeImageId() != -1) {
			template.addParseElement("${product.imageSource}", "<img src=\"cid:productImage\">");
		} else {
			template.addParseElement("${product.imageSource}", "");
		}
		StringBuffer additionalInformation = new StringBuffer();
		if (showSku) {
			additionalInformation.append("<br />");
			additionalInformation.append(skuText);
			additionalInformation.append(productCatalog.getSku());
		}
		if (showPriceSavings) {
			if (productCatalog.getActivePrice() != null) {
				additionalInformation.append("<br />");
				additionalInformation.append(originalPriceText);
				additionalInformation.append(CurrencyFormat.getCurrencyString(productCatalog.getActivePrice().getPriceAmount(), locale));
				if (productCatalog.getActivePrice().getMsrpAmount() > productCatalog.getActivePrice().getPriceAmount()) {
					additionalInformation.append("<br />");
					additionalInformation.append(priceSavingsText);
					double savings = productCatalog.getActivePrice().getMsrpAmount() - productCatalog.getActivePrice().getPriceAmount();
					additionalInformation.append(CurrencyFormat.getCurrencyString(savings, locale));
				}
			} else {
				if (System.getProperty("DEBUG") != null) {
					System.out.println("ProductEmailFormatter.getProductInformation() 'showPriceSavings' ==> active price is NULL");
				}
			}
		}
		if (showPrice && !showPriceSavings) {
			if (productCatalog.getActivePrice() != null) {
				additionalInformation.append("<br />");
				additionalInformation.append(priceText);
				additionalInformation.append(CurrencyFormat.getCurrencyString(productCatalog.getActivePrice().getPriceAmount(), locale));
			} else {
				if (System.getProperty("DEBUG") != null) {
					System.out.println("ProductEmailFormatter.getProductInformation() 'showPriceSavings && !showPriceSavings' ==> active price is NULL");
				}
			}
		}

		template.addParseElement("${product.allowedInformation}", additionalInformation.toString());
		template.addParseElement("${email.comments}", productCatalog.getComments());
		template.addParseElement("${product.link}", this.getProductURL());
		template.addParseElement("${site.link}", this.getSiteURL());

		return template.getParsedText();
	}
}

