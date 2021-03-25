package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class UpdateFormHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
	/*	 /WebContent/model1/board/updateForm.jsp
	    1. num 값의 게시물을 조회화여 화면 출력하기 */
		int num = Integer.parseInt(request.getParameter("num"));
	Board board = new BoardDao().selectOne(num);
	request.setAttribute("board", board);
		return "/view/board/updateForm.jsp";
	}

}
