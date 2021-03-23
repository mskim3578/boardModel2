<%@page import="dao.BoardDao"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="model.Board"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/model1/board/update.jsp
  1. 파라미터정보들을 Board 객체 저장.
  2. 비밀번호 검증
         비밀번호 일치 : 수정으로.
         비밀번호 불일치 : 비밀번호 오류 메세지 출력하고, updateForm.jsp로 페이지 이동
  3. 수정성공 : 수정성공 메시지 출력 후  list.jsp 페이지 이동
         수정실패   : 수정실패 메시지 출력 후 updateForm.jsp 페이지 이동         
--%>    
<% //파라미터 정보 Board 객체에 저장 
   Board board = new Board();
   String uploadpath = application.getRealPath("/") +"chap09_board/upfile/";
  MultipartRequest multi = new MultipartRequest
		                    (request,uploadpath,10*1024*1024,"euc-kr");
   board.setNum(Integer.parseInt(multi.getParameter("num")));
   board.setName(multi.getParameter("name"));
   board.setPass(multi.getParameter("pass"));
   board.setSubject(multi.getParameter("subject"));
   board.setContent(multi.getParameter("content"));
   board.setFile1(multi.getFilesystemName("file1"));
   //수정시 첨부파일의 수정이 발생하지 않은 경우
   if(board.getFile1()==null || board.getFile1().equals("")) {
	   board.setFile1(multi.getParameter("file2"));
   }
   //비밀번호 검증
   BoardDao dao = new BoardDao(); 
   Board dbBoard = dao.selectOne(board.getNum());
   String msg = "비밀번호가 틀렸습니다.";
   String url = "updateForm.jsp?num=" + board.getNum();
   if(board.getPass().equals(dbBoard.getPass())) {
	   //수정하기
 	  if(dao.update(board)) {	  msg = "게시물 수정 완료";		  url = "list.jsp";
	  } else {		  msg = "게시물 수정 실패";	  }
   }%>
<script>
   alert("<%=msg%>");   location.href="<%=url%>";</script>