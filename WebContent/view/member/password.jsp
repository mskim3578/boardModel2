<%@page import="model.Member"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/chap08_member/password.jsp
   1. 로그인 상태임. 다른 사용자의 비밀번호 변경 불가.
            로그아웃상태인 경우. 로그인 하세요. 메세지 출력. 
            opener 페이지를 loginForm.jsp 페이지 이동.
                         현재페이지 닫기
   2. pass, chgpass 파라미터 값 저장
   3. pass 비밀번호가 db에 저장된 비밀번호와 틀리면
            비밀번호 오류 메세지 출력. passwordForm.jsp 페이지 이동
   4. pass 비밀번호가 db에 저장된 비밀번호와 같으면
            비밀번호 수정. opener 페이지를 memberInfo.jsp 페이지 이동.
                                   현재페이지 닫기
 --%>
<%  String login = (String)session.getAttribute("login");
	boolean  opener = false;	String msg = null;	String url = null;
	if(login == null || login.trim().equals("")) {  //로그아웃 상태
		opener = true;
		msg = "로그인 하세요.";		url = "loginForm.jsp";
	} else { //로그인 상태
	  String pass= request.getParameter("pass");
	  String chgpass= request.getParameter("chgpass");
	  MemberDao dao = new MemberDao();
	  Member mem = dao.selectOne(login);
	  if(pass.equals(mem.getPass())) {//입력된 비밀번호와 db에 저장된 비밀번호가 같은 경우
		  if(dao.updatePass(login,chgpass) > 0) {
		 	  msg="비밀번호가 변경되었습니다.";
			  url = "memberInfo.jsp?id=" + login;		  opener = true;
		  } else {
			  msg="비밀번호가 변경시 오류가 발생 되었습니다.";	  url = "passwordForm.jsp";
		  }
	  } else {  //입력된 비밀번호와 db에 저장된 비밀번호가 다른 경우
		  msg="비밀번호 오류 입니다. 확인하세요.";
		  url = "passwordForm.jsp";	  }	}%>     
<script>
   alert("<%=msg%>");
   <% if(opener) { %>
      opener.location.href="<%=url%>";
      self.close(); //현재 window 닫기.
   <% }  else { %>
       location.href="<%=url%>";
   <% } %>
</script>
