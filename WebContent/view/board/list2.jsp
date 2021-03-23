<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
${info }
<%=request.getAttribute("info") %>
info:${info }<br>
hello:${hello }<br>
mskim:${mskim }<br>
</body>
</html>