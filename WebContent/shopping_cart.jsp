<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.sensitiver.ands.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ANDS Shopping Cart</title>
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
   <h1>Welcome To ANDS data search! <br>Entry stamp time:<%=session.getAttribute("username")%></h1>
  <br>
  <a href="shopping_cart">Shopping Cart</a> <br>
  <a href="clear_session">Clear Session</a>
</div>
<hr>
<%
	String searchField = (String) request.getAttribute("search_field");
	if(searchField == null){
		searchField="";
		}
	//out.println("search field is"+searchField);
	 %>
<div class="content">
  <div class="search_input">
    <form name="search_form" method="get" action="search">
      <label for="textfield">Search:</label>
      <input type="text"
				name="search_field" value="<%=searchField%>">
      <input
				type="submit" name="submit" value="Submit">
      <input
				type="hidden" name="start_page" value="0">
    </form>
  </div>
  <hr>
  <div class="cart">
    <%
			ArrayList<Integer> items = (ArrayList<Integer>) session
					.getAttribute("items");
			DatasetHandler dh = (DatasetHandler) session
					.getAttribute("dataset_handler");
			if (items != null && items.size()>0 ) {
		%>
    <form method="post" action="remove_from_cart">
      <table width="80%" border="0">
        <tbody>
          <tr>
            <th scope="col" width="50%">Title</th>
            <th scope="col" width="25%">Serial</th>
            <th scope="col" width="25%">Select</th>
          </tr>
          <%
						for (Integer serial : items) {
								DatasetBean db = dh.getDataset(serial);
					%>
          <tr>
            <td><a href="detail?serial=<%=serial%>"><%=db.getTitle()%></a></td>
            <td><%=db.getSerial()%></td>
            <td><input type="checkbox" name="remove_from_cart" value="<%=db.getSerial()%>">
              <label for="checkbox">Remove</label></td>
          </tr>
          <%} %>
        </tbody>
      </table>
      <input type="submit" name="submit"
				value="Remove Selection from Cart">
      <input type="reset"
				name="reset" id="reset" value="Reset">
    </form>
    <%
			
			} else {
				out.print("Shopping cart empty<br>");
			}
			//out.print(request.getAttribute("fromURL"));
		%>
  </div>
</div>
<hr>
<div class="footer"> <a href="welcome">Back to Welcome</a>
  <div class="copyright">@ Shuwen Zhou</div>
</div>
<hr>
</body>
</html>