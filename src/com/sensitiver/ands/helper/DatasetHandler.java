package com.sensitiver.ands.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class DatasetHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	ArrayList<DatasetBean> datasets = null;

	public DatasetHandler(String filename) {
		datasets = new ArrayList<DatasetBean>();
		try {
			Document document = null;
			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			File file = new File(filename);
			System.out.println("data file name is\t"+filename);
			System.out.println("data file path is\t"+file.getAbsolutePath());
			document = db.parse(file);
			Element el = document.getDocumentElement();
			System.out.println("Document Element Name is: " +
			el.getTagName());
			NodeList nl = document.getElementsByTagName("dc");
			// for each item
			for (int i = 0; i < nl.getLength(); i++) {
				/*
				 * About xml file: Unique: Title Publisher Contributer
				 * 
				 * Not Unique: Identifier Description Subject (Search Matching)
				 * Coverage Right
				 */

				Node node = nl.item(i);
				// System.out.println("Argument: " +
				// node.getAttributes().item(0));
				DatasetBean datasetBean = new DatasetBean(i);
				NodeList ChildNodes = node.getChildNodes();
				// for each element
				for (int j = 0; j < ChildNodes.getLength(); j++) {
					String NodeName = ChildNodes.item(j).getNodeName().trim();
					String NodeContent = ChildNodes.item(j).getTextContent()
							.trim();
					if (!NodeName.toLowerCase().matches(".*text.*")) {
						if (NodeName.toLowerCase().matches(".*title.*")) {
							datasetBean.setTitle(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*publisher.*")) {
							datasetBean.setPublisher(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*contributor.*")) {
							datasetBean.setContributor(NodeContent);

						} else if (NodeName.toLowerCase().matches(
								".*identifier.*")) {
							datasetBean.setIdentifiers(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*description.*")) {
							datasetBean.setDescriptions(NodeContent);
						} else if (NodeName.toLowerCase()
								.matches(".*subject.*")) {
							datasetBean.setSubjects(NodeContent);
						} else if (NodeName.toLowerCase().matches(".*right.*")) {
							datasetBean.setRights(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*coverage.*")) {
							datasetBean.setCoverages(NodeContent);
						}

					}
				}
				//System.out.println(datasetBean);
				datasets.add(datasetBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DatasetHandler(Document document) {
		datasets = new ArrayList<DatasetBean>();
		try {
			//System.out.println("base URI is\t"+document.getBaseURI());
			//System.out.println("document URI is\t"+document.getDocumentURI());
			//document = db.parse(file);
			Element el = document.getDocumentElement();
			System.out.println("Document Element Name is: " +
			el.getTagName());
			NodeList nl = document.getElementsByTagName("dc");
			// for each item
			for (int i = 0; i < nl.getLength(); i++) {
				/*
				 * About xml file: Unique: Title Publisher Contributer
				 * 
				 * Not Unique: Identifier Description Subject (Search Matching)
				 * Coverage Right
				 */

				Node node = nl.item(i);
				// System.out.println("Argument: " +
				// node.getAttributes().item(0));
				DatasetBean datasetBean = new DatasetBean(i);
				NodeList ChildNodes = node.getChildNodes();
				// for each element
				for (int j = 0; j < ChildNodes.getLength(); j++) {
					String NodeName = ChildNodes.item(j).getNodeName().trim();
					String NodeContent = ChildNodes.item(j).getTextContent()
							.trim();
					if (!NodeName.toLowerCase().matches(".*text.*")) {
						if (NodeName.toLowerCase().matches(".*title.*")) {
							datasetBean.setTitle(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*publisher.*")) {
							datasetBean.setPublisher(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*contributor.*")) {
							datasetBean.setContributor(NodeContent);

						} else if (NodeName.toLowerCase().matches(
								".*identifier.*")) {
							datasetBean.setIdentifiers(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*description.*")) {
							datasetBean.setDescriptions(NodeContent);
						} else if (NodeName.toLowerCase()
								.matches(".*subject.*")) {
							datasetBean.setSubjects(NodeContent);
						} else if (NodeName.toLowerCase().matches(".*right.*")) {
							datasetBean.setRights(NodeContent);
						} else if (NodeName.toLowerCase().matches(
								".*coverage.*")) {
							datasetBean.setCoverages(NodeContent);
						}

					}
				}
				//System.out.println(datasetBean);
				datasets.add(datasetBean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<DatasetBean> getDatasets() {
		return datasets;
	}
	
	public ArrayList<DatasetBean> search(String keyword){
		ArrayList<DatasetBean> result = new ArrayList<DatasetBean>();
		if(datasets!=null){
			for(int i = 0; i<datasets.size(); i++){
				DatasetBean db = datasets.get(i);
				if(db.matchSubjects(keyword)){
					result.add(db);
				}
			}
		}
		return result;
	}
	
	
	
	public String toString(){
		String result ="";
		for(DatasetBean db: datasets){
			result += db;
		}
		return result;
	}
	
	public DatasetBean getDataset(int serial){
		for(DatasetBean db : datasets){
			if(db.getSerial() == serial){
				return db;
			}
		}
		return null;
	}

}
