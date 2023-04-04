package cw3.pckg.servlets;

import cw3.pckg.model.Model;
import cw3.pckg.model.ModelFactory;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rundelete.html")
public class DeleteServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.getModel();

    model.deleteList(request.getParameter("list"));

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/action.jsp");
    dispatch.forward(request,response);
  }
}
