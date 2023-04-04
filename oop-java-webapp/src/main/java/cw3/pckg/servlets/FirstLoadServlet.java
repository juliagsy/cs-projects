package cw3.pckg.servlets;

import cw3.pckg.model.Model;
import cw3.pckg.model.ModelFactory;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/firstload.html")
public class FirstLoadServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.resetModel(); // in the case where user reloads files, model will not have duplicates

    model.loadDataDirectory(new File("./data/"));

    ServletContext context = getServletContext();
    RequestDispatcher dispatch = context.getRequestDispatcher("/action.jsp");
    dispatch.forward(request,response);
  }
}
