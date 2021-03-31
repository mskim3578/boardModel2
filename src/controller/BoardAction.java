package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import dao.BoardDao;
import kic.msk.com.Action;
import model.Board;

public class BoardAction extends Action{

	public String list(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		/*
		 * 게시물 목록 보기 
		 * 1. pageNum 파라미터 존재. pageNum 파라미터 없으면 1로 설정.
		 * 2. 10건의 게시물 출력. => db에서 해당 페이지에 출력되는 게시물만 조회. 순서 : 
		 *                        최근 게시물 순으로
		 * 3. 화면에 출력.
		 */ 
		System.out.println("list 입니다");
		 int pageNum = 1;
		   try {
			   pageNum = Integer.parseInt(request.getParameter("pageNum"));
		   } catch (NumberFormatException e) {}
		   int limit = 3; //한페이지에 출력할 게시물 건수
		   BoardDao dao = new BoardDao();
		   int boardcount = dao.boardCount();//등록된 전체 게시물의 건수
		   
		   List<Board> list = dao.list(pageNum,limit, boardcount); //화면에 출력된 게시물 데이터
		   //13 --->   boardcount/limit : 4 + 1
		   int maxpage = (int)(boardcount/limit)+(boardcount%limit==0?0:1);
		   int bottomLine=3;
		   // page 1,2,3 : 1,   4,5,6: 2
		   int startpage = 1 + (pageNum - 1) / bottomLine * bottomLine;
		   int endpage = startpage + bottomLine - 1;
		   if(endpage > maxpage) endpage = maxpage;
		   int boardnum = boardcount - (pageNum -1) * limit;
		
		   request.setAttribute("boardcount", boardcount);
		   request.setAttribute("list", list);
		   request.setAttribute("boardnum", boardnum);
		   request.setAttribute("startpage", startpage);
		   request.setAttribute("bottomLine", bottomLine);
		   request.setAttribute("endpage", endpage);
		   request.setAttribute("maxpage", maxpage);
		   request.setAttribute("pageNum", pageNum);
		   
		   return "/view/board/list.jsp";
	}
	
	
public String writeForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
	return "/view/board/writeForm.jsp";
	}
public String write(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*
	 * 1. 파라미터 값을 model.Board 객체 저장. 2. 게시물 번호 num 현재 등록된 num의 최대값을 조회. 최대값 +1 등록된
	 * 게시물의 번호. db에서 maxnum 을 구해서 +1 값으로 num 설정하기
	 */
	// 1. 파라미터 값을 model.Board 객체 저장.
	// String uploadpath = application.getRealPath("/") +"chap09_board/upfile/";
	String uploadpath = request.getServletContext().getRealPath("/") + "view/board/upfile/";
	int size = 10 * 1024 * 1024;
	MultipartRequest multi;

	try {
		multi = new MultipartRequest(request, uploadpath, size, "euc-kr");

		Board board = new Board();
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		board.setFile1(multi.getFilesystemName("file1"));
		// 2. sequence nextval 입력
		// db에서 maxnum 을 구해서 +1 값으로 num 설정하기
		BoardDao dao = new BoardDao();
		// 3. board 객체의 내용을 db에 insert 하기
		String msg = "게시물 등록 실패";
		String url = "writeForm";
		if (dao.insert(board)) {
			msg = "게시물 등록 성공";
			url = "list";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return "/view/board/alert.jsp";
}
public String info(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/* : 게시물 상세 보기    :board/info?num=41
	  1. num 파라미터를 이용하여 db에 해당 게시물 조회
	     Board board = new BoardDao().selectOne(num); 
	  2. 조회수 증가시키기.  readcnt+1
	      new BoardDao().readcntadd(num);
	  3. 1번에서 조회한 게시물데이터를 화면에 출력하기   */ 
	

	int num = Integer.parseInt(request.getParameter("num"));
	//파라미터값읽기
	BoardDao dao = new BoardDao();
	Board board = dao.selectOne(num); //게시물 조회
	dao.readcntadd(num); //조회건수증가
	request.setAttribute("board", board);
	request.setAttribute("num", num);
	return "/view/board/info.jsp";
}
public String updateForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*	 /WebContent/model1/board/updateForm.jsp
    1. num 값의 게시물을 조회화여 화면 출력하기 */
	int num = Integer.parseInt(request.getParameter("num"));
Board board = new BoardDao().selectOne(num);
request.setAttribute("board", board);
	return "/view/board/updateForm.jsp";
}
public String update(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/* /WebContent/model1/board/update.jsp
	  1. 파라미터정보들을 Board 객체 저장.
	  2. 비밀번호 검증
	         비밀번호 일치 : 수정으로.
	         비밀번호 불일치 : 비밀번호 오류 메세지 출력하고, updateForm.jsp로 페이지 이동
	  3. 수정성공 : 수정성공 메시지 출력 후  list.jsp 페이지 이동
	         수정실패   : 수정실패 메시지 출력 후 updateForm.jsp 페이지 이동         */
	    
	 //파라미터 정보 Board 객체에 저장 
	   Board board = new Board();
	   String uploadpath = request.getServletContext().getRealPath("/") +"view/board/upfile/";
	  MultipartRequest multi;
	try {
		multi = new MultipartRequest
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
	   String url = "updateForm?num=" + board.getNum();
	   
	   
	   if(board.getPass().equals(dbBoard.getPass())) {
		   //수정하기
	 	  if(dao.update(board)) {	  msg = "게시물 수정 완료";		  url = "list";
		  } else {		  msg = "게시물 수정 실패";	  }
	   }
	   request.setAttribute("url", url);
	   request.setAttribute("msg", msg);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return "/view/board/alert.jsp";
}
public String deleteForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	
	int num = Integer.parseInt(request.getParameter("num"));
	request.setAttribute("num", num);
	return "/view/board/deleteForm.jsp";
}
public String delete(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*	 /WebContent/model1/board/delete.jsp
	   1. num,pass 파라미터를 변수에 저장.
	   2. 입력된 비밀번호와 db 비밀번호 검증
	            틀린경우 : 비밀번호 오류 메시지 출력, deleteForm.jsp 페이지 이동
	   3. 게시물 삭제.
	           삭제 성공 : 삭제 성공 메시지 출력, list.jsp 페이지 이동
	           삭제 실패 : 삭제 실패 메시지 출력, info.jsp 페이지 이동   */           
	 
	
	int num = Integer.parseInt(request.getParameter("num"));
	String pass = request.getParameter("pass"); //입력된 비밀번호
	String msg = "비밀번호가 틀렸습니다!";String url = "deleteForm?num=" + num;
	BoardDao dao = new BoardDao();
	Board board = dao.selectOne(num);
	//board.getPass() : db에 저장된 비밀번호
	if (pass.equals(board.getPass())) { 
		if (dao.delete(num)) {		msg = "게시글을 성공적으로 삭제하였습니다.";		
		url = "list";
		} else {		msg = "게시글을 삭제하는데 실패하였습니다!";
			url = "info?num=" + num;
		}}
	
	  request.setAttribute("url", url);
	   request.setAttribute("msg", msg);
	
	
	return "/view/board/alert.jsp";
}
public String replyForm(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/* /WebContent/model1/board/replyForm.jsp : 답변글 쓰기 화면
	   1. 원글의 num을 파라미터로 받는다.
	   2. 원글의 num,ref,reflevel,refstep 정보를 저장
	   3. 입력 화면 표시 */
	
	
	int num = Integer.parseInt(request.getParameter("num"));//파라미터값읽기
	BoardDao dao = new BoardDao();
	Board board = dao.selectOne(num); //게시물 조회
	
	request.setAttribute("board", board);
	
	return "/view/board/replyForm.jsp";
}
public String reply(HttpServletRequest request, HttpServletResponse response) throws Throwable {
	/*	 /WebContent/model1/board/reply.jsp : 답글 등록
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
	    1. 파라미터 값을 Board 객체에 저장하기 */
		try {
			request.setCharacterEncoding("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   Board board = new Board();
	   board.setNum(Integer.parseInt(request.getParameter("num")));
	   board.setRef(Integer.parseInt(request.getParameter("ref")));
	   board.setReflevel(Integer.parseInt(request.getParameter("reflevel"))); 
	   board.setRefstep(Integer.parseInt(request.getParameter("refstep")));
	   board.setName(request.getParameter("name"));
	   board.setPass(request.getParameter("pass"));
	   board.setSubject(request.getParameter("subject"));
	   board.setContent(request.getParameter("content"));
	   board.setFile1("");
	   
	   BoardDao dao = new BoardDao();
	   dao.refstepadd(board.getRef(),board.getRefstep()); 
	   //3. Board 객체를 db에 insert 하기.
	  String msg = "답변등록시 오류발생";
	   String url = "replyForm?num="+board.getNum();
	  if(dao.insert(board)) {  	  msg = "답변등록 완료"; 
	  url = "list";   }
	
	request.setAttribute("url", url);
	request.setAttribute("msg", msg);
	
	
	
	return "/view/board/alert.jsp";
}

	
}
