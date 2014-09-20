<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.sensitiver.ands.helper.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="style.css" type="text/css" />
<%
DatasetBean db =  (DatasetBean) request.getAttribute("dataset_bean");

 %>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Detail of<%=db.getTitle() %></title>
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
<div class="content">
  <div class="search_input" >
    <form name="search_form" method="get" action="search">
      <label for="textfield">Search:</label>
      <input type="text"
				name="search_field" value="">
      <input type="submit"
				name="submit" value="Submit">
      <input type="hidden"
				name="start_page" value="0">
    </form>
  </div>
  <hr>
  <hr>
  <div class="add_to_cart" align="right" width="80%">
    <%
ArrayList<Integer> items = (ArrayList<Integer>) session.getAttribute("items");
				boolean contains = false;
				if (items != null && items.size() > 0) {
					for (int integer : items) {
						if (integer == db.getSerial()) {
								contains = true;
						}
					}
				}
				if (!contains) {%>
    <form method="get" action="add_to_cart">
      <input type="submit" value="Add to Cart">
      <input
				type="hidden" name="serial" value="<%=db.getSerial()%>">
      <input
				type="hidden" name="from_url" value="detail">
    </form>
    <%	} else {
				out.println("This item is in cart");
			}%>
  </div>
  <div class="detail">
    <form>
      <% if(db!=null){ %>
      <table width="80%" border="0">
        <tbody>
          <tr>
            <th width="20%" scope="row">Serial</th>
            <td width="10%">&nbsp;</td>
            <td width="70%"><%=db.getSerial() %></td>
          </tr>
          <tr>
            <th scope="row">Title</th>
            <td>&nbsp;</td>
            <td><%=db.getTitle() %></td>
          </tr>
          <tr>
            <th scope="row">Publisher</th>
            <td>&nbsp;</td>
            <td><%=db.getPublisher() %></td>
          </tr>
          <tr>
            <th scope="row">Contributor</th>
            <td>&nbsp;</td>
            <td><%=db.getContributor() %></td>
          </tr>
          <tr>
            <th scope="row">Identifier(s)</th>
            <td>&nbsp;</td>
            <td><%=db.getIdentifiersString() %></td>
          </tr>
          <tr>
            <th scope="row">Subject(s)</th>
            <td>&nbsp;</td>
            <td><%=db.getSubjectsString() %></td>
          </tr>
          <tr>
            <th scope="row">Coverage(s)</th>
            <td>&nbsp;</td>
            <td><%=db.getCoveragesString() %></td>
          </tr>
          <tr>
            <th scope="row">Right(s)</th>
            <td>&nbsp;</td>
            <td><%=db.getRightsString() %></td>
          </tr>
          <tr>
            <th scope="row">Descprition(s)</th>
            <td>&nbsp;</td>
            <td><%=db.getDescriptionsString() %></td>
          </tr>
        </tbody>
      </table>
      <%} %>
    </form>
  </div>
</div>
<hr>
<div class="footer"> <a href="welcome">Back to Welcome</a>
  <div class="copyright">@ Shuwen Zhou</div>
</div>
</body>
</html>