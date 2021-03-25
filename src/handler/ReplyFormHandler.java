package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class ReplyFormHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
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

}
