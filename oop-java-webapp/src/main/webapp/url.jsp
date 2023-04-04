<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Link item</title>
  </head>
  <body>
    <%
      String rowCol = (String)(request.getAttribute("rowCol"));
    %>

    <h3>Link a URL</h3>
    <form action="/runchange.html" method="POST">
      <label>
        Please input your valid URL: <br>
        <input type="hidden" name="rowCol" value=<%=rowCol%>>
        <input type="url" name="newItem" required> <br>
        <input type="submit" value="Next">
      </label>
    </form>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
