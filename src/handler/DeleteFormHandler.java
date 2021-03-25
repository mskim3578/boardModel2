package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
public class DeleteFormHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest req, 
			HttpServletResponse res) {
		
		int num = Integer.parseInt(req.getParameter("num"));
		req.setAttribute("num", num);
		return "/view/board/deleteForm.jsp";
	}}
