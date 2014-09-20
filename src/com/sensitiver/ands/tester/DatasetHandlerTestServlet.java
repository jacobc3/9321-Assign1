package com.sensitiver.ands.tester;

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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sensitiver.ands.helper.DatasetBean;
import com.sensitiver.ands.helper.DatasetHandler;


/**
 * Servlet implementation class DatasetHandlerTestServlet
 */
@WebServlet("/DatasetHandlerTestServlet")
public class DatasetHandlerTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DatasetHandlerTestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		ServletContext context = getServletContext();
		
		//String xmlFileName = context.getInitParameter("data_file_name");
		String xmlFileName = "G:\\study\\COMP9321\\Assignment0830\\smallset.xml";
		out.println("xml file name is: " + xmlFileName + "<br>");
		try {
			DatasetHandler handler = new DatasetHandler(xmlFileName);
			
			//String output = handler.toString();
			//output.replaceAll("\n", "<br>\n");
			out.println(handler.search("HUMAN"));
		} catch (Exception e) {
		}
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
