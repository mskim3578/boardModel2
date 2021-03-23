package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import command.CommandHandler;
public class WriteFormHandler implements CommandHandler {
	@Override
	public String process(HttpServletRequest req, 
			HttpServletResponse res) {
		return "/view/board/writeForm.jsp";
	}

}
