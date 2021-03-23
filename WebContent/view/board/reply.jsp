<%@page import="model.Board"%>
<%@page import="dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/model1/board/reply.jsp : 답글 등록
   1. 파라미터 값을 Board 객체에 저장하기
            원글정보 : num, ref, reflevel(ㄴ), refstep(print)
            답글정보  : name, pass, subject, content 
   2. 같은 ref 값을 사용하는 게시물들의 refstep 값을 1 증가 시키기
                     1    1    0         0
                     2    2    0         0
                     3    1    1         2
                     4    1    1         1 
                     ========================  print
                     2    2    0         0
                     1    1    0         0
                     4    1    1         1
                     3    1    1         2 
   3. Board 객체를 db에 insert 하기.
   4. 등록 성공시 : "답변등록 완료"메시지 출력 후,  list.jsp로 페이지 이동
            등록 실패시 : "답변등록시 오류발생"메시지 출력 후, replyForm.jsp로 페이지 이동하기 
--%>    1. 파라미터 값을 Board 객체에 저장하기 --%>
<%	request.setCharacterEncoding("euc-kr");%>
<jsp:useBean id="board" class="model.Board" />
<jsp:setProperty name="board" property="*"/>
<%   BoardDao dao = new BoardDao();
   dao.refstepadd(board.getRef(),board.getRefstep()); 
   //3. Board 객체를 db에 insert 하기.
  String msg = "답변등록시 오류발생";
   String url = "replyForm.jsp?num="+board.getNum();
  if(dao.insert(board)) {  	  msg = "답변등록 완료"; 
  url = "list.jsp";   }
%>
<script>
   alert("<%=msg%>");
   location.href="<%=url%>";
</script>