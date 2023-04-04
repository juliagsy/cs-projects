package cw3.pckg.servlets;

import cw3.pckg.model.Model;
import cw3.pckg.model.ModelFactory;

import java.util.ArrayList;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/runsearch.html")
public class SearchServlet extends HttpServlet
{
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  {
    Model model = ModelFactory.getModel();

    String type = request.getParameter("searchType");
    String target = request.getParameter("targetName");

    ServletContext context = getServletContext();
    RequestDispatcher dispatch;

    if (type.compareTo("list") == 0)
    {
      int resultIndex = model.searchList(target);
      if (resultIndex == -1)
      {
        dispatch = context.getRequestDispatcher("/searchError.jsp");
      }
      else
      {
        dispatch = context.getRequestDispatcher("/showTableView.jsp");
        request.setAttribute("viewname",target);
        request.setAttribute("viewlist",model.viewList(target));
      }
    }
    else
    {
      ArrayList<Object> result = model.searchItem(target);
      dispatch = context.getRequestDispatcher("/showSearchResult.jsp");
      request.setAttribute("result",result);
    }

    dispatch.forward(request,response);
  }
}
