package handler;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class ReplyHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
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
