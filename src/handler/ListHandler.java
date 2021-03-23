package handler;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
import dao.BoardDao;
import model.Board;
public class ListHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest request, 
			HttpServletResponse res) {
		/*
		 * 게시물 목록 보기 
		 * 1. pageNum 파라미터 존재. pageNum 파라미터 없으면 1로 설정.
		 * 2. 10건의 게시물 출력. => db에서 해당 페이지에 출력되는 게시물만 조회. 순서 : 
		 *                        최근 게시물 순으로
		 * 3. 화면에 출력.
		 */ 
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
	}}