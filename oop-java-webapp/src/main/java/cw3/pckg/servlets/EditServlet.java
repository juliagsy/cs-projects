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

@WebServlet("/runedit.html")
public class EditServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.getModel();

    String name =(String) request.getParameter("list");
    request.setAttribute("editname",name);
    request.setAttribute("editlist",model.viewList(name));

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/showEdit.jsp");;
    dispatch.forward(request,response);
  }
}
