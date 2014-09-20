<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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
<title>ANDS Search</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<base href="<%=basePath%>">
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
    <form id="form1" name="form1" method="get" action="search">
      <label for="textfield">Search:</label>
      <input type="text"
					name="search_field" value="HUMAN">
      <input type="hidden"
					name="start_page" value="0">
      <input type="submit"
					name="submit" id="submit" value="Submit">
    </form>
  </div>
</div>
<hr>
<div class="footer">
  <div class="copyright">@ Shuwen Zhou</div>
</div>
</body>
</html>
