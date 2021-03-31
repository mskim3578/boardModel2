package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import kic.msk.com.Action;
import model.Member;

public class MemberController extends Action {

	public String main(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		/*
		 * 1. 로그인 후에 보여지는 페이지. => 로그인 여부 확인 => 로그인상태가 아닌 경우, loginForm.jsp로 페이지 이동하기
		 */
		String login = (String) request.getSession().getAttribute("login");

		if (login == null || login.trim().equals("")) {
			return "/view/member/loginForm.jsp";
		} else {
			request.setAttribute("login", login);
			return "/view/member/main.jsp";
		}
	}

	public String loginForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		return "/view/member/loginForm.jsp";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		/*
		 * 1. id, pass 파라미터 저장 2. db에서 id 에 해당하는 데이터를 읽어서 Member 전달받기 3. 결과분석 Member객체가
		 * null 인경우 : 아이디를 확인하세요 메세지 출력. --->loginForm.jsp 페이지 이동 Member객체가 null이 아닌 경우
		 * : 화면에서 입력된 비밀번호와 db 비밀번호 검증 같은경우 : 로그인 성공. -----> main.jsp 페이지 이동 다른 경우 :
		 * 비밀번호 확인하세요 ----> loginForm.jsp로 페이지 이동
		 */
		String id = request.getParameter("id"); // 입력된 id값
		String pass = request.getParameter("pass"); // 입력된 pass 값
		// mem : db에 저장된 회원정보 저장
		Member mem = new MemberDao().selectOne(id);
		String msg = "아이디를 확인하세요";
		String url = "loginForm";
		if (mem != null) {
			if (pass.equals(mem.getPass())) {
				request.getSession().setAttribute("login", id);
				msg = mem.getName() + "님이 로그인 하셨습니다.";
				url = "main";
			} else {
				msg = "비밀번호를 확인 하세요";
			}
		}
		request.setAttribute("url", url);
		request.setAttribute("msg", msg);
		return "/view/alert.jsp";
	}

	public String logout(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		/*
		 * 1. session에 등록된 로그인 정보 제거 2. id님 로그아웃되었습니다. 메세지 출력 후 loginForm.jsp로 페이지 이동하기
		 */

		String login = (String) request.getSession().getAttribute("login");
		request.getSession().invalidate();
		String msg = login + "님 로그아웃되었습니다.";
		request.setAttribute("msg", msg);
		request.setAttribute("url", "loginForm");
		return "/view/alert.jsp";
	}

	public String memberInfo(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		/*
		 * 1. id 파라미터값 저장하기 2. 로그아웃상태 : 로그인이 필요합니다. 메세지 출력하고, loginForm.jsp 페이지 이동 3.
		 * 로그인 상태 3-1 : id 파라미터정보와 login 정보를 비교해서 다르면 자신의 정보만 조회 가능합니다. 메세지 출력. main.jsp
		 * 페이지 이동. 3-2 : id 파라미터정보와 login 정보를 비교하여 다르지만 login이 관리자인 경우 이거나 id와 로그인 정보가
		 * 같은 경우 화면에 내용 출력하기 MemberDao().selectOne(id) 메서드를 이용하여 db 내용 조회하기 4. 관리자로 로그인
		 * 한 경우나, 자신정보 조회시 화면에 출력하기
		 */
		String login = (String) request.getSession().getAttribute("login");
		String id = request.getParameter("id");
		String msg = "";
		String url = "";
		if (login == null || login.trim().equals("")) { // 2. logout 경우
			msg = "로그인이 필요합니다.로그인 하세요";
			url = "loginForm";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		} else if (!login.equals("admin") && !login.equals(id)) {
			msg = "자신의 정보만 조회가 가능합니다.";
			url = "loginForm";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		} else { // login admin or id==login
			Member mem = new MemberDao().selectOne(id);
			request.setAttribute("mem", mem);
			return "/view/member/memberInfo.jsp";
		}
	}

	public String updateForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		/*
		 * 1. id 파라미터 2. 로그인상태 검증. 로그아웃상태 : 로그인이 필요합니다. 메세지 출력하고, loginForm.jsp 페이지 이동
		 * 3. 로그인 상태 3-1 : id 파라미터정보와 login 정보를 비교해서 다르면 자신의 정보만 수정 가능합니다. 메세지 출력.
		 * info.jsp 페이지 이동. 3-2 : id 파라미터정보와 login 정보를 비교하여 다르지만 login이 관리자인 경우 와 id와
		 * 로그인 정보가 같은 경우 화면에 내용 출력하기 MemberDao().selectOne(id) 메서드를 이용하여 db 내용 조회하기 4.
		 * id에 해당하는 회원의 정보를 db에서 조회 화면 출력
		 */
		String login = (String) request.getSession().getAttribute("login");
		String id = request.getParameter("id");
		String msg = "";
		String url = "";
		if (login == null || login.trim().equals("")) {
			msg = "로그인이 필요합니다";
			url = "loginForm";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		} else if (!login.equals("admin") && !login.equals(id)) {
			msg = "자신의 정보만 수정이 가능합니다.";
			url = "loginForm";
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "/view/alert.jsp";
		} else {
			Member mem = new MemberDao().selectOne(id);
			request.setAttribute("mem", mem);

			return "/view/member/updateForm.jsp";
		}
	}

	public String update(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		/*
		 * 1. 모든 파라미터를 Member 객체에 저장하기 2. 입력된 비밀번호와 db의 비밀번호가 같으면 3번으로 실행. 다르면 비밀번호 오류
		 * 메세지 출력. updateForm.jsp 페이지 이동 3. 1번의 객체를 db에 수정하기. int update(Member) 결과가
		 * 1이상이면 : 수정 성공 메세지 출력. main.jsp 페이지 이동 0이하면 : 수정 실패 메세지 출력. updateForm.jsp 페이지
		 * 이동
		 */

		request.setCharacterEncoding("euc-kr");
		String login = (String) request.getSession().getAttribute("login");
		Member mem = new Member();
		mem.setId(request.getParameter("id"));
		mem.setPass(request.getParameter("pass"));
		mem.setName(request.getParameter("name"));
		mem.setGender(Integer.parseInt(request.getParameter("gender")));
		mem.setPicture(request.getParameter("picture"));
		mem.setTel(request.getParameter("tel"));
		mem.setEmail(request.getParameter("email"));
		String msg = null;
		String url = null;
		MemberDao dao = new MemberDao();
		Member dbMem = dao.selectOne(mem.getId());
		if (!login.equals("admin") && !mem.getPass().equals(dbMem.getPass())) {
			msg = "비밀번호가 틀립니다. 확인 후 다시 거래 하세요.";
			url = "updateForm?id=" + mem.getId();
		} else {
			if (dao.update(mem) > 0) {
				msg = mem.getId() + "님의 회원 정보가 수정되었습니다.";
				url = "main";
			} else {
				msg = mem.getId() + "님 회원 정보 수정 실패.";
				url = "updateForm?id=" + mem.getId();
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "/view/alert.jsp";
	}

	public String deleteForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		String id = request.getParameter("id");
		request.setAttribute("id", id);

		return "/view/member/deleteForm.jsp";
	}

	public String delete(HttpServletRequest request, HttpServletResponse response) throws Throwable {

		/*
		 * 1. 로그아웃상태 : 로그인 하세요. 메세지 출력 후 loginForm.jsp 페이지 이동 2. 로그인 상태 - 일반사용자 (1) 비밀번호
		 * 검증 (2) -비밀번호가 일치하면 db에서 id 해당하는 정보 삭제성공. 로그아웃 후, '탈퇴 성공' 메시지 출력,
		 * loginForm.jsp 페이지 이동 db에서 id 해당하는 정보 삭제실패. "삭제 실패" 메세지 출력. main.jsp 페이지 이동
		 * -비밀번호 불일치 "비밀번호 불일치" 메세지 출력. deleteForm.jsp 페이지 이동 - 관리자 (1) db에서 해당 id 정보 삭제
		 * db에서 id 해당하는 정보 삭제실패. "삭제 실패" 메세지 출력. list.jsp 페이지 이동 (2) 탈퇴 성공 메서지 출력.
		 * list.jsp 페이지 이동
		 */

		String login = (String) request.getSession().getAttribute("login");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String msg = null;
		String url = null;
		if (login == null || login.trim().equals("")) {
			msg = "로그인이 필요합니다.";
			url = "loginForm";
		} else if (!login.equals(id) && !login.equals("admin")) {
			msg = "본인만 탈퇴가 가능합니다.";
			url = "main";
		} else if (id.equals("admin")) {
			msg = "관리자는 탈퇴할 수 없습니다.";
			url = "main";
		} else {
			MemberDao dao = new MemberDao();
			Member mem = dao.selectOne(id);
			if (login.equals("admin") || pass.equals(mem.getPass())) {
				int result = dao.delete(id);
				if (result > 0) { // 삭제 성공
					if (login.equals("admin")) { // 관리자인 경우
						msg = id + " 사용자를 강제 탈퇴 성공";
						url = "memberList";
					} else { // 일반사용자인 경우
						msg = id + "님의  회원 탈퇴가 완료되었습니다.";
						url = "loginForm";
						request.getSession().invalidate();
					}
				} else { // 삭제 실패
					msg = id + "님의 탈퇴시 오류 발생.";
					if (login.equals("admin")) { // 관리자인 경우
						url = "memberList";
					} else { // 일반사용자인 경우
						url = "main";
					}
				}
			} else {
				msg = id + "님의 비빌번호가 틀립니다.";
				url = "deleteForm?id=" + id;
			}
		}
		request.setAttribute("url", url);
		request.setAttribute("msg", msg);
		return "/view/alert.jsp";
	}

	public String memberList(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		/*
		   1. 로그아웃상태 : 로그인 하세요. 메세지 출력. loginForm.jsp 페이지 이동
		   2. 로그인상태  
		           일반사용자 로그인: 관리자만 조회가능 메세지 출력. main.jsp 페이지 이동          
		                관리자로그인 : 목록 출력
		   3. 회원 목록을 조회하여 화면에 출력하기       */
		  String msg = ""; 
		  String url="";
		  String login = (String)request.getSession().getAttribute("login");
		   if(login == null || login.trim().equals("")) {
			   msg="관리자로 로그인 하세요";
			   url="loginForm";
			   request.setAttribute("msg", msg);
			   request.setAttribute("url", url);
			   return "/view/alert.jsp";
		   } 
		   else if (!login.equals("admin"))  {
			   msg="관리자만 가능한 거래 입니다.";
			   url="main";
			   request.setAttribute("msg", msg);
			   request.setAttribute("url", url);
			   return "/view/alert.jsp";
			   
		   	
	        } else {  //관리자만 조회
			List<Member> list = new MemberDao().list();
			request.setAttribute("list", list);
			return "/view/member/memberList.jsp";
			
	        }
		
	}

}