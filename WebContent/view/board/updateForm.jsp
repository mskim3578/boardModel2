<%@page import="model.Board"%>
<%@page import="dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- /WebContent/model1/board/updateForm.jsp
    1. num 값의 게시물을 조회화여 화면 출력하기 
--%>
<%
	int num = Integer.parseInt(request.getParameter("num"));
Board board = new BoardDao().selectOne(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시판 수정 화면</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/chap07_jdbc/css/main.css">
<script>
	function file_delete() {
		file_desc = document.getElementById("file_desc")
		document.f.file2.value = "";
		file_desc.style.display = "none";
	}
</script>
</head>
<body>
	<form action="update.jsp" method="post" enctype="multipart/form-data"
		name="f">
		<input type="hidden" name="num" value="<%=board.getNum()%>">
		<input type="hidden" name="file2"
			value="<%=((board.getFile1() == null) ? "" : board.getFile1())%>">
		<table>
			<caption>MODEL1 게시판 수정 화면</caption>
			<tr>
				<td>글쓴이</td>
				<td><input type="text" name="name" value="<%=board.getName()%>"></td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="subject"
					value="<%=board.getSubject()%>"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea rows="15" name="content"><%=board.getContent()%></textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td style="text-align: left">
					<%
						if (board.getFile1() != null && (!board.getFile1().equals(""))) {
					%>
					<div id="file_desc"><%=board.getFile1()%>
						<a href="javascript:file_delete()">[첨부파일 삭제]</a>
					</div> <%
 	}
 %> <input type="file" name="file1">
				</td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:document.f.submit()">[게시물수정]</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>