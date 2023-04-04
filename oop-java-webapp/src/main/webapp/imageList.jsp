<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <meta charset="utf-8">
    <title>Link image or list</title>
  </head>
  <body>
    <%
      String rowCol = (String)(request.getAttribute("rowCol"));
    %>

    <h3>Link an image or list</h3>
    <p>Please ensure that all items to be linked are <b>included in the data directory</b> too!</p>
    <form action="/runchange.html" method="POST">
      <label>
        Type of file to link: <br>
        <input type="hidden" name="rowCol" value=<%=rowCol%>>
        <input type="file" name="newItem" accept=".jpg, .jpeg, .heif, .png, .gif, .pdf, .csv" required> <br>
        <input type="submit" value="Next">
      </label>
    </form>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
