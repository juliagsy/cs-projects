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

@WebServlet("/runprocess.html")
public class ProcessServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.getModel();

    String type = (String) request.getParameter("editType");
    String target = (String) request.getParameter("rowCol");
    String[] typeArray = type.split("@"); // format = [action, col/row, direction(optional)]
    String[] targetArray = target.split("@"); // format = [listname, rowIndex, colIndex]

    ServletContext context = getServletContext();
    RequestDispatcher dispatch;

    if (typeArray[0].compareTo("add") == 0)
    {
      model.add(target,typeArray[1],typeArray[2]);
      dispatch = context.getRequestDispatcher("/showEdit.jsp");
      request.setAttribute("editname",targetArray[0]);
      request.setAttribute("editlist",model.viewList(targetArray[0]));
    }
    else if (typeArray[0].compareTo("remove") == 0)
    {
      model.remove(target);
      dispatch = context.getRequestDispatcher("/showEdit.jsp");
      request.setAttribute("editname",targetArray[0]);
      request.setAttribute("editlist",model.viewList(targetArray[0]));
    }
    else if (typeArray[0].compareTo("edit") == 0)
    {
      dispatch = context.getRequestDispatcher("/change.jsp");
      request.setAttribute("rowCol",target);
    }
    else
    {
      dispatch = context.getRequestDispatcher("/link.jsp");
      request.setAttribute("rowCol",target);
    }

    dispatch.forward(request,response);
  }
}
