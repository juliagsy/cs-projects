<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Edit item in a list</title>
  </head>
  <body>
    <%
      String rowCol = (String)(request.getAttribute("rowCol"));
    %>

    <h3>Edit an item</h3>
    <form action="/runchange.html" method="POST">
      <label>
        Text of new item:
        <input type="hidden" name="rowCol" value=<%=rowCol%>>
        <input type="text" name="newItem" value="" required> <br>
        <input type="submit" value="Change!">
      </label>
    </form>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
