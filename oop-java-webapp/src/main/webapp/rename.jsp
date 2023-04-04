<%@ page import="java.util.ArrayList" %>
<%@ page import="cw3.pckg.model.Model" %>
<%@ page import="cw3.pckg.model.ModelFactory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Rename a list</title>
  </head>
  <body>
    <h1>Rename Existing List</h1>
    <h4>Please select a list to be renamed.</h4>
    <%
      Model model = ModelFactory.getModel();
      ArrayList<String> listNames = model.getLabels();
      if (listNames.size() == 0)
      {
    %>
    <p>Sorry, app is empty. Please add data files into the <b>data</b> directory to be loaded.</p>
    <%
      }
      else
      {
    %>
    <form method="POST" action="/runrename.html">
      <label>
        List to be renamed:<br>
      <%
        for (String name : listNames)
        {
      %>
        <input type="radio" name="list" value=<%=name%> required><%=name%><br>
      <%
        }
      %>
      </label>
      <br>
      <label>New name: <input type="text" name="newname" value="" required></label>
      <br>
      <input type="submit" value="Rename!">
    </form>
      <%
      }
      %>
      <jsp:include page="/footer.jsp"/>
  </body>
</html>
