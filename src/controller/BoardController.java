package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import command.CommandHandler;



public class BoardController extends HttpServlet {
	 private Map<String, CommandHandler> commandHandlerMap = 
	    		new HashMap<>();
	 	@Override
	    public void init() throws ServletException {
	        String configFile = getInitParameter("configFile");
	        Properties prop = new Properties();
	        String configFilePath = getServletContext().getRealPath(configFile);
	       System.out.println(configFilePath);
	        try (FileReader fis = new FileReader(configFilePath)) {
	            prop.load(fis);  // key(String) :value(String)
	        } catch (IOException e) {
	            throw new ServletException(e);
	        }
	        Iterator keyIter = prop.keySet().iterator();
	        while (keyIter.hasNext()) {
	            String command = (String) keyIter.next();
	            String handlerClassName = prop.getProperty(command);
	            try {
	                Class<?> handlerClass = Class.forName(handlerClassName);
	                CommandHandler handlerInstance = 
	                        (CommandHandler) handlerClass.newInstance();
	                
	                commandHandlerMap.put(command, handlerInstance);
	            } catch (ClassNotFoundException | InstantiationException 
	            		| IllegalAccessException e) {
	                throw new ServletException(e);
	            }
	        }
	    }	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		processRequest(request, response);	}
	public void doPost(HttpServletRequest request,HttpServletResponse response)	throws ServletException, IOException {
		processRequest(request, response);	}
	private void processRequest(HttpServletRequest request,	HttpServletResponse response)
			throws ServletException, IOException {
		String command = request.getRequestURI();
		System.out.println("1:"+command);
		if (command.indexOf(request.getContextPath()) == 0) {
			command = command.substring(request.getContextPath().length());
		}
		System.out.println("2:"+command);
        CommandHandler handler = commandHandlerMap.get(command);
		
        String viewPage = null;
        try {
            viewPage = handler.process(request, response);
        } catch (Throwable e) {
            throw new ServletException(e);
        }
        if (viewPage != null) {
	        RequestDispatcher dispatcher 
	        = request.getRequestDispatcher(viewPage);
	        dispatcher.forward(request, response);
        }
		
		
	}}
