package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class DeleteHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
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

}
