package com.darkhorseventures.controller;

import java.util.ArrayList;

public class CustomFormTab extends ArrayList {

  String name = "";
  String next = "";
  String prev = "";
  int id = 0;

  public CustomFormTab() {
  }
  
  public void setName(String name) {
	this.name = name;
  }
  
  public String getName() {
	return name;
  }
  
    public void setNext(String tmp) { this.next = tmp; }
public void setPrev(String tmp) { this.prev = tmp; }
public String getNext() { return next; }
public String getPrev() { return prev; }

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

public void setId(String id) {
	this.id = Integer.parseInt(id);
}
	

}

