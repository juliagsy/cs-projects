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

    <h3>Link an item</h3>
    <form action="/runlink.html" method="POST">
      <label>
        Type of file to link: <br>
        <input type="hidden" name="rowCol" value=<%=rowCol%>>
        <input type="radio" name="linkType" value="image" required>Image or List <br>
        <input type="radio" name="linkType" value="url">URL <br>
        <input type="submit" value="Next">
      </label>
    </form>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
