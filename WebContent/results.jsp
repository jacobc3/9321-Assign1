<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page import="com.sensitiver.ands.helper.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="style.css" type="text/css" />
<base href="<%=basePath%>">
<%
	String searchField = (String) request.getAttribute("search_field");
	if (searchField == null) {
		searchField = "HUMAN";
	}
	String cp = (String) request.getAttribute("current_page");
	int currentPage = 0;
	int previousPage = 0;
	int nextPage = 0;
	if (cp != null) {
		currentPage = Integer.parseInt(cp);
		previousPage = currentPage - 1;
		nextPage = currentPage + 1;
	}
%>
<title>ANDS Search Result of<%=searchField%></title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>

<body>
	<div class="header">
		<%
			Date date = new java.util.Date();

			if (session.isNew()) {
				out.println("New User! Create Session!<br>");
				session.setAttribute("username", date);
			}
		%>
		<h1>
			Welcome To ANDS data search! <br>Entry stamp time:<%=session.getAttribute("username")%></h1>
		<br> <a href="shopping_cart?search_field=<%=searchField%>">Shopping
			Cart</a> <br> <a href="clear_session">Clear Session</a>
	</div>
	<hr>
	<div class="content">
		<div class="search_input">
			<form name="search_form" method="get" action="search">
				<label for="textfield">Search:</label> <input type="text"
					name="search_field" value="<%=searchField%>"> <input
					type="submit" name="submit" value="Submit"> <input
					type="hidden" name="start_page" value="0">
			</form>
		</div>
		<hr>
		<div class="results">
			<%
				ArrayList<DatasetBean> results = (ArrayList<DatasetBean>) request
						.getAttribute("results");
			%>
			<p>Results:</p>
			<table width="80%" border="0">
				<tbody>
					<tr>
						<th width="50%" scope="col" align="left">Title</th>
						<th width="30%" scope="col" align="left">Publisher</th>
						<th width="20%" scope="col" align="left">Action</th>
					</tr>
					<%
						if (results != null) {
							for (int i = 0; i < results.size(); i++) {
								DatasetBean ds = results.get(i);
								String serial = ((Integer) ds.getSerial()).toString();
					%>
					<tr>
						<td><a href="detail?serial=<%=serial%>"><%=ds.getTitle()%></a></td>
						<td><%=ds.getPublisher()%></td>
						<td><form id="form2" name="form2" method="get"
								action="add_to_cart">
								<input type="hidden" name="serial" value="<%=serial%>">
								<input type="hidden" name="search_field"
									value="<%=searchField%>"> <input type="hidden"
									name="start_page" value="<%=currentPage%>">
								<%
									ArrayList<Integer> items = (ArrayList<Integer>) session
													.getAttribute("items");
											boolean contains = false;
											if (items != null && items.size() > 0) {
												for (Integer integer : items) {
													if (integer == Integer.parseInt(serial)) {
														contains = true;
													}
												}
											}
											if (contains) {
								%>
								Item Already In Cart
								<%
									} else {
								%>
								<input type="submit" name="submit" value="Add to Cart">
								</input>
								<%
									}
								%>
							</form></td>
					</tr>
					<%
						}
						}
					%>
				</tbody>
			</table>
			<p>
				<%
					if (results == null || results.size() == 0) {
						out.print("Sorry! No Match Found");
					}
				%>
			
		</div>
		<hr>
		<%
			if (results != null && results.size() > 0) {
		%>
		<div class="navigation" id="navigation" align="center">
			<table width="40%" border="0">
				<tbody>
					<tr>
						<td width="40%"><form name="page_navi_form" method="get"
								action="search">
								<%
									if (request.getAttribute("has_previous_page") == "true") {
								%>
								<div align="right">
									<input type="submit" value="<--- Previous Page"> <input
										type="hidden" name="search_field" value="<%=searchField%>">
									<input type="hidden" name="start_page"
										value="<%=previousPage%>">
									<%
										}
									%>
								</div>
							</form></td>
						<td width="20%"><div align="center">
								Page<%=cp%></div></td>
						<td width="40%"><form name="page_navi_form" method="get"
								action="search">
								<%
									if (request.getAttribute("has_next_page") == "true") {
											//out.println(searchField+" "+request.getAttribute("current_page"));

											//out.println("current Page is "+request.getAttribute("current_page")+"int: "+Integer.parseInt(cp));
											//int nextPage = (Integer)request.getAttribute("current_page"));
											//nextPage++;
								%>
								<input type="hidden" name="search_field"
									value="<%=searchField%>"> <input type="hidden"
									name="start_page" value="<%=nextPage%>"> <input
									type="submit" value="Next Page --->">
								<%
									}
								%>
							</form></td>
					</tr>
				</tbody>
			</table>
		</div>
		<%
			}
		%>
	</div>
	<hr>
	<div class="footer">
		<div class="copyright" align="center">@ Shuwen Zhou</div>
	</div>
</body>
</html>
