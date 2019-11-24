package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/*
	 * BESOIN DE RENFORTS, Marine par ex pour le init dans le servlet, et comment laisser le servlet activé
	 */

    public controller() {
        super();
    }

    @Override 
    public void init() throws ServletException {}
    {
    	System.out.print("Init DONE");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
