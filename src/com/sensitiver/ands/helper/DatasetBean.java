package com.sensitiver.ands.helper;

import java.io.Serializable;
import java.util.ArrayList;

public class DatasetBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0;

	private int serial;
	
	private String title;
	private String publisher;
	private String contributor;
	
	private ArrayList<String> subjects = null;
	private ArrayList<String> descriptions = null;
	private ArrayList<String> identifiers = null;
	private ArrayList<String> rights = null;
	private ArrayList<String> coverages = null;
	
	public DatasetBean(int serial) {
		this.setSerial(serial);
	}

	//--------------set: single value---------------------------//
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	
	//--------------get: single value---------------------------//
	public String getPublisher() {
		return publisher;
	}	
	public String getContributor() {
		return contributor;
	}
	public String getTitle() {
		return title;
	}	
	public int getSerial() {
		return serial;
	}
	//--------------set: multiple values------------------------//
	public void setIdentifiers(String identifier) {
		if(identifiers == null ){
			identifiers = new ArrayList<String>();
		}
		identifiers.add(identifier);
	}
	public void setSubjects(String subject) {
		if(subjects == null ){
			subjects = new ArrayList<String>();
		}
		subjects.add(subject);
	}
	public void setDescriptions(String description) {
		if(descriptions == null ){
			descriptions = new ArrayList<String>();
		}
		descriptions.add(description);
	}
	public void setRights(String right) {
		if(rights == null ){
			rights = new ArrayList<String>();
		}
		rights.add(right);
	}
	public void setCoverages(String coverage) {
		if(coverages == null ){
			coverages = new ArrayList<String>();
		}
		coverages.add(coverage);
	}
	
	//--------------get: multiple lines------------------------//
	public String getCoveragesString() {
		String result = "";
		if(coverages != null ){
			for (String s : coverages){
				result += "<br>"+s+"\n";
			}
		}
		return result;
	}
	public String getSubjectsString() {
		String result = "";
		if(subjects != null ){
			for (String s : subjects){
				result += "<br>"+s+"\n";
			}
		}
		return result;
	}
	public String getRightsString() {
		String result = "";
		if(rights != null ){
			for (String s : rights){
				result += "<br>"+s+"\n";
			}
		}
		return result;
	}
	public String getDescriptionsString() {
		String result = "";
		if(descriptions != null ){
			for (String s : descriptions){
				result += "<br>"+s+"\n";
			}
		}
		return result;
	}
	public String getIdentifiersString() {
		String result = "";
		if(identifiers != null ){
			for (String s : identifiers){
				result += "<br>"+s+"\n";
			}
		}
		return result;
	}	
	//--------------other method------------------------//
	public String toString(){
		String result ="";
		result+="Title:\t\t"+getTitle()+"<br>\n"+getSubjectsString()+"<br>\n"+getCoveragesString();
		return result;
	}
	public boolean matchSubjects(String keyword) {
		if(subjects!=null){
			for(String subject : subjects){
				if(subject.matches(".*\\b"+keyword+"\\b.*")){
					return true;
				}
			}
		}
		return false;
	}
	

}
