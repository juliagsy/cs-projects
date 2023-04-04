<%@ page import="java.util.ArrayList" %>
<%@ page import="cw3.pckg.model.Validator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Viewing Data in Table</title>
  </head>
  <body>
    <%
      Validator validator = new Validator();
      String name = (String) request.getAttribute("viewname");
      ArrayList<ArrayList<String>> allDataByRow = (ArrayList<ArrayList<String>>)request.getAttribute("viewlist");
    %>
    <h1>"<%=name%>" Table</h1>

    <table border="1">
      <tbody>
        <%
          for (ArrayList<String> row : allDataByRow)
          {
        %>
        <tr>
          <%
            for (String rowData : row)
            {
              if (validator.isURL(rowData)) // include link for redirecting
              {
          %>
          <td><a href=<%=rowData%>><%=rowData%></a></td>
          <%
              }
              else if (validator.isList(rowData)) // include button for viewing sublists
              {
                String listname = rowData.substring(0,rowData.lastIndexOf("."));
          %>
          <td>
          <form action="/runsearch.html" method="POST">
            <input type="hidden" name="searchType" value="list">
            <button type="submit" name="targetName" value=<%=listname%>><%=rowData%></button>
          </form>
          </td>
          <%
              }
              else if (validator.isImage(rowData)) // include button for image viewing
              {
          %>
          <td>
          <form action="showImage.jsp" method="POST">
            <button type="submit" name="image" value=<%=rowData%>><%=rowData%></button>
          </form>
          </td>
          <%
              }
              else
              {
          %>
          <td><%=rowData%></td>
          <%
              }
            }
          %>
        </tr>
        <%
          }
        %>
      </tbody>
    </table>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
