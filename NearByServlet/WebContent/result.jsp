<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
	<form action = "NearServlet" method = "get">
	��J�g��
	<input type = text name = "longitude"/>
	��J�n��
	<input type = text name = "latitude"/>
	<input type = submit value = "Go"/>
	</form>
	<p>
	�򶩤������g�סG121.738367   �n�סG25.131674
	</p>
	<p>
	���׮ժ��g�סG121.772429   �n�סG25.150904
	</p>
   <!-- 	
	<p>
		���W�g�n��<%= (String)request.getAttribute("LTLN") %>�A<%= (String)request.getAttribute("LTLA") %>-----------�k�W�g�n��<%= (String)request.getAttribute("RTLN") %>�A<%= (String)request.getAttribute("RTLA") %>
    </p>
    <p>
    	���U�g�n��<%= (String)request.getAttribute("LBLN") %>�A<%= (String)request.getAttribute("LBLA") %>-----------�k�U�g�n��<%= (String)request.getAttribute("RBLN") %>�A<%= (String)request.getAttribute("RBLA") %>		
	</p>
	 -->
</body>
</html>