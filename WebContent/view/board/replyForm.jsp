<%@page import="model.Board"%>
<%@page import="dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/model1/board/replyForm.jsp : 답변글 쓰기 화면
   1. 원글의 num을 파라미터로 받는다.
   2. 원글의 num,ref,reflevel,refstep 정보를 저장
   3. 입력 화면 표시 
--%>
<%
int num = Integer.parseInt(request.getParameter("num"));//파라미터값읽기
BoardDao dao = new BoardDao();
Board board = dao.selectOne(num); //게시물 조회%> 
<!DOCTYPE html>
<html><head><meta charset="EUC-KR">
<title>게시판 답글 쓰기</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/chap07_jdbc/css/main.css"></head>
<body><form action="reply.jsp" method="post" name="f">
<%-- 원글 정보 :num  ref reflevel  refstep--%>
  <input type="hidden" name="num" value="<%=board.getNum()%>">
  <input type="hidden" name="ref" value="<%=board.getRef()%>" >
  <input type="hidden" name="reflevel" value="<%=board.getReflevel()%>" >
  <input type="hidden" name="refstep" value="<%=board.getRefstep()%>" >
  <table><caption>MODEL1 게시판 답글 등록</caption>
  <tr><td>글쓴이</td><td><input type="text" name="name"></td></tr>
  <tr><td>비밀번호</td><td><input type="password" name="pass"></td></tr>
  <tr><td>제목</td><td><input type="text" name="subject" 
      value="RE:<%=board.getSubject()%>"></td></tr>
  <tr><td>내용</td>
      <td><textarea name="content" rows="15"></textarea></td></tr>
  <tr><td colspan="2">
  <a href="javascript:document.f.submit()">[답변글등록]</a></td></tr>    
  </table>
</form>
</body>
</html>