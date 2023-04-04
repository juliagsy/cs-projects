<%@ page import="cw3.pckg.model.Validator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Image Viewer</title>
  </head>

  <body>
    <%
      String imageName = request.getParameter("image");
      String imagePath = "./data/" + imageName;

      // check existence of image
      if ((new Validator()).validFile(imagePath))
      {
    %>
    <img src=<%=imagePath%> alt=<%=imageName%>/>
    <%
      }
      else
      {
    %>
    <p>Image does not exist, please ensure that it is in the <b>data</b> directory for viewing</p>
    <%
      }
    %>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
