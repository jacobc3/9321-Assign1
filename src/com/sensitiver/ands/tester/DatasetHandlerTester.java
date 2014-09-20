package com.sensitiver.ands.tester;

import java.util.ArrayList;

import com.sensitiver.ands.helper.DatasetBean;
import com.sensitiver.ands.helper.DatasetHandler;

public class DatasetHandlerTester {

	public DatasetHandlerTester() {
		
	}
	
	public static void main(String arge[]) {

		DatasetHandler dh = new DatasetHandler("smallset.xml");
		ArrayList<DatasetBean> dbs = dh.getDatasets();
		for(DatasetBean db: dbs){
			System.out.println(db);
		}
		/*
		String keyword = "science";
		String a1 = "Earth science";
		String a2 = "science";
		if(a1.matches(".*\\b"+keyword+"\\b.*")){
			System.out.println(a1);
		}
		if(a2.matches(".*\\b"+keyword+"\\b.*")){
			System.out.println(a2);
		}*/
		System.out.println("==============================================");
		ArrayList<DatasetBean> dbs2 = dh.search("HUMAN");
		for(DatasetBean db: dbs2){
			System.out.println(db);
		}
		
		
	}
	

}
