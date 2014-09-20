package com.sensitiver.ands;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sensitiver.ands.helper.DatasetBean;
import com.sensitiver.ands.helper.DatasetHandler;

/**
 * Servlet implementation class MyServlet
 */

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		SetupDatabaseHandler();
		HttpSession session = request.getSession();
		// -----------URL Redirect -----------------//
		String url = request.getRequestURL().toString();
		// response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		System.out.println("url is\t" + url );
		if (url.matches(".*add_to_cart.*") &&request.getParameter("start_page")!=null) {
			request.setAttribute("fromURL", url);			
			//out.println("serial is " + request.getParameter("serial"));
			String serialString = request.getParameter("serial");
			String searchField = request.getParameter("search_field");
			String startPageString = request.getParameter("start_page");
			int serial = Integer.parseInt(serialString);
			
			ArrayList<Integer> items = (ArrayList<Integer>) session
					.getAttribute("items");
			if (items == null) {
				items = new ArrayList<Integer>();
			}
			
			System.out.println(request.getRequestURL());
			items.add(serial);
			session.setAttribute("items", items);
			session.setAttribute("dataset_handler", dh);
			//RequestDispatcher view = request.getRequestDispatcher("shopping_cart.jsp");
			///search?search_field=HUMAN&start_page=0&submit=Submit
			RequestDispatcher view = 
					request.getRequestDispatcher("/search?search_field="+searchField+"&start_page="+startPageString+"&submit=Submit");
			view.forward(request, response);
			
		} else if(url.matches(".*add_to_cart.*") && request.getParameter("from_url").matches("(.*)detail(.*)")){
			String serialString = request.getParameter("serial");
			int serial = Integer.parseInt(serialString);
			ArrayList<Integer> items = (ArrayList<Integer>) session
					.getAttribute("items");
			if (items == null) {
				items = new ArrayList<Integer>();
			}
			
			System.out.println(request.getRequestURL());
			items.add(serial);
			session.setAttribute("items", items);
			session.setAttribute("dataset_handler", dh);
			RequestDispatcher view = 
					request.getRequestDispatcher("/detail?serial="+serialString);
			view.forward(request, response);
		} else if (url.matches(".*shopping_cart.*")) {
			System.out.println("match: .*shopping_cart.*");
			request.setAttribute("fromURL", url);
			session.setAttribute("dataset_handler", dh);
			RequestDispatcher view = request
					.getRequestDispatcher("shopping_cart.jsp");
			view.forward(request, response);
			
			
		} else if (url.matches("(.*)remove_from_cart(.*)")) {
			/*
			System.out.println("match: .*remove_from_cart.*");
			String[] removes = request.getParameterValues("remove_from_cart");
			ArrayList<Integer> items = (ArrayList<Integer>) session
					.getAttribute("items");
			if (items == null) {
				items = new ArrayList<Integer>();
			}
			if (removes != null) {
				for (String item : removes) {
					for (int i = 0; i < items.size(); i++) {
						if (Integer.parseInt(item) == items.get(i)) {
							items.remove(i);
						}
					}
				}
			}
			session.setAttribute("items", items);
			session.setAttribute("dataset_handler", dh);
			RequestDispatcher view = request
					.getRequestDispatcher("shopping_cart.jsp");
			view.forward(request, response);
*/
			System.out.println("WRONG!!!match: .*remove_from_cart.*");
		} else if (url.matches("(.*)search(.*)") && request.getParameter("search_field")!=null) {
			System.out.println("match: .*search\\?.*");
			String searchField = (String) request.getParameter("search_field");
			if(searchField==""){
				request.setAttribute("search_field", searchField);
			} else {
				int startPage = Integer
						.parseInt(request.getParameter("start_page"));
				int numOfEachPage = Integer.parseInt(getServletContext()
						.getInitParameter("result_display_number"));
				out.println("Searching " + searchField + "<br>");
				SearchResult searchResult = search(searchField, startPage,
						numOfEachPage);
				request.setAttribute("results", searchResult.datasets);
				request.setAttribute("has_next_page",
						searchResult.hasNextPage.toString());
				request.setAttribute("has_previous_page",
						searchResult.hasPrevPage.toString());
				request.setAttribute("current_page",
						Integer.toString(searchResult.currentPage));
				request.setAttribute("search_field", searchField);
				request.setAttribute("fromURL", url); // is:
														// Assignment0830/search.jsp
			}
			RequestDispatcher view = request
					.getRequestDispatcher("results.jsp");
			view.forward(request, response);
		} else if (url.matches(".*welcome.*") || url.matches("(.*)search")) {
			System.out.println("url:\t"+url+"\nmatch: \twelcome or (.*)search");
			RequestDispatcher view = request.getRequestDispatcher("search.jsp");
			view.forward(request, response);
		} else if (url.matches(".*clear_session.*")) {
			System.out.println("match: .*clear_session.*");
			session.invalidate();
			response.setContentType("text/html");
			out.println("Session Cleared!");
			out.println("<a href=\"welcome\">Go back Welcome Page</a>");

		} else if(url.matches(".*detail?.*")){
			String serialString = request.getParameter("serial");
			int serial = Integer.parseInt(serialString);
			DatasetBean db = dh.getDataset(serial);
			request.setAttribute("dataset_bean", db);
			RequestDispatcher view = request
					.getRequestDispatcher("detail.jsp");
			view.forward(request, response);
			
		} else {
			out.println("DIDN'T MATCH ANYTHING!<br> no redirect <br>");
		}
		out.flush();
		out.close();
	}

	DatasetHandler dh = null;

	private void SetupDatabaseHandler() {		
		if (dh == null) {
			ServletContext context = getServletContext();
			String filename = context.getInitParameter("data_file_name");
			InputSource xmlFile = new InputSource(context.getResourceAsStream(filename));
			Document doc = null;
			try {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    		DocumentBuilder builder;
				builder = builderFactory.newDocumentBuilder();
				doc = builder.parse(xmlFile);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// String xmlFileName =
			// "G:\\study\\COMP9321\\Assignment0830\\smallset.xml";
			dh = new DatasetHandler(doc);
		}
	}

	private class SearchResult {
		Boolean hasPrevPage = false;
		Boolean hasNextPage = false;
		ArrayList<DatasetBean> datasets = new ArrayList<DatasetBean>();
		int currentPage = 0;
	}

	private SearchResult search(String keyword, int startPage, int numOfEachPage) {
		SearchResult result = new SearchResult();
		ArrayList<DatasetBean> originDatasets = dh.search(keyword);
		result.currentPage = startPage;
		for (int i = startPage * numOfEachPage; i < originDatasets.size()
				&& i < (startPage + 1) * numOfEachPage; i++) {
			result.datasets.add(originDatasets.get(i));
		}

		if (startPage > 0)
			result.hasPrevPage = true;
		if (originDatasets.size() > (startPage + 1) * numOfEachPage)
			result.hasNextPage = true;
		return result;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {			
			SetupDatabaseHandler();
			HttpSession session = request.getSession();
			// -----------URL Redirect -----------------//
			String url = request.getRequestURL().toString();
			// response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			System.out.println("url is\t" + url );
			if (url.matches("(.*)remove_from_cart(.*)")) {
				System.out.println("match: .*remove_from_cart.*");
				String[] removes = request.getParameterValues("remove_from_cart");
				ArrayList<Integer> items = (ArrayList<Integer>) session
						.getAttribute("items");
				if (items == null) {
					items = new ArrayList<Integer>();
				}
				if (removes != null) {
					for (String item : removes) {
						for (int i = 0; i < items.size(); i++) {
							if (Integer.parseInt(item) == items.get(i)) {
								items.remove(i);
							}
						}
					}
				}
				session.setAttribute("items", items);
				session.setAttribute("dataset_handler", dh);
				RequestDispatcher view = request
						.getRequestDispatcher("shopping_cart.jsp");
				view.forward(request, response);
			} else {
				out.println("DIDN'T MATCH ANYTHING!<br> no redirect <br>");
			}
			out.flush();
			out.close();
	}

}
