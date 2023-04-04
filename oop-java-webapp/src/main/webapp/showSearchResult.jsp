<%@ page import="java.util.ArrayList" %>
<%@ page import="cw3.pckg.model.Validator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Search Result</title>
  </head>
  <body>
    <%
      Validator validator = new Validator();
      ArrayList<Object> result = (ArrayList<Object>) request.getAttribute("result");
      ArrayList<String> labels = (ArrayList<String>) result.get(0);
      ArrayList<ArrayList<ArrayList<String>>> rowData = (ArrayList<ArrayList<ArrayList<String>>>) result.get(1);

      int index = 0;
      for (String name : labels)
      {
    %>
    <h1>"<%=name%>" Search Result</h1>
    <table border="1">
      <tbody>
    <%
          ArrayList<ArrayList<String>> row = rowData.get(index);
          for (ArrayList<String> dataResult : row)
          {
        %>
        <tr>
          <%
            for (String data : dataResult)
            {
                if (validator.isURL(data)) // include link for redirecting
                {
            %>
            <td><a href=<%=data%>><%=data%></a></td>
            <%
                }
                else if (validator.isList(data)) // include button for viewing sublists
                {
                  String listname = data.substring(0,data.lastIndexOf("."));
            %>
            <td>
            <form action="/runsearch.html" method="POST">
              <input type="hidden" name="searchType" value="list">
              <button type="submit" name="targetName" value=<%=listname%>><%=data%></button>
            </form>
            </td>
            <%
                }
                else if (validator.isImage(data)) // include button for image viewing
                {
            %>
            <td>
            <form action="showImage.jsp" method="POST">
              <button type="submit" name="image" value=<%=data%>><%=data%></button>
            </form>
            </td>
            <%
                }
                else
                {
            %>
            <td><%=data%></td>
            <%
                }
            }
          %>
        </tr>
        <%
          }
          index ++;
        %>
  </tbody>
</table>
<%
  }
    %>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
