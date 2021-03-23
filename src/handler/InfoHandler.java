package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class InfoHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
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

}
