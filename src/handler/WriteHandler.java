package handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import command.CommandHandler;
import dao.BoardDao;
import model.Board;

public class WriteHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, HttpServletResponse res) {
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
}