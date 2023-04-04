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

@WebServlet("/runchange.html")
public class ChangeServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.getModel();

    String target = request.getParameter("rowCol");
    String[] targetArray = target.split("@");

    model.edit(target,request.getParameter("newItem"));

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/showEdit.jsp");

    request.setAttribute("editname",targetArray[0]);
    request.setAttribute("editlist",model.viewList(targetArray[0]));
    dispatch.forward(request,response);
  }
}
