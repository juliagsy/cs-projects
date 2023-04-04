<%@ page import="java.util.ArrayList" %>
<%@ page import="cw3.pckg.model.Validator " %>
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
      String name = (String) request.getAttribute("editname");
      ArrayList<ArrayList<String>> allDataByRow = (ArrayList<ArrayList<String>>) request.getAttribute("editlist");

    %>
    <h1>"<%=name%>" Table</h1>
    <form method="POST" action="/runprocess.html">
      <table border="1">
        <tbody>
          <%
          String val;
          int rowNum = 0;
          for (ArrayList<String> row : allDataByRow)
            {
          %>
          <tr>
            <%
              int colNum = 0;
              for (String data : row)
              {
                val = name + "@" + Integer.toString(rowNum) + "@" + Integer.toString(colNum);
                if (validator.isURL(data))
                {
            %>
            <td>
            <input type="radio" name="rowCol" value=<%=val%> required>
            <a href=<%=data%>><%=data%></a>
            </td>
            <%
                }
                else
                {
            %>
            <td><input type="radio" name="rowCol" value=<%=val%> required><%=data%></td>
            <%
                }
                colNum ++;
              }
            %>
          </tr>
          <%
              rowNum ++;
            }
          %>
        </tbody>
      </table>
      <label>
        <input type="radio" name="editType" value="add@col@left" required>Add Grid to the Left<br>
        <input type="radio" name="editType" value="add@col@right">Add Grid to the Right<br>
        <input type="radio" name="editType" value="add@row@up">Add Row Above<br>
        <input type="radio" name="editType" value="add@row@down">Add Row Bottom<br>
        <input type="radio" name="editType" value="remove">Remove Grid<br>
        <input type="radio" name="editType" value="edit">Edit this Grid<br>
        <input type="radio" name="editType" value="link">Link URL, Image or List to this Grid<br>
      </label>
      <input type="submit" value="Process!">
    </form>
    <p>All changes will be <b>saved automatically</b> to its original file</p>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
