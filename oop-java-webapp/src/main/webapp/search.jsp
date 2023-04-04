<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Search list or item</title>
  </head>
  <body>
    <h1>Search a list of item</h1>
    <form action="/runsearch.html" method="POST">
      Search list or item? <br>
      <input type="radio" name="searchType" value="list" required> List <br>
      <input type="radio" name="searchType" value="item"> Item in List <br>
      Target:
      <input type="text" name="targetName" value="" required> <br>
      <input type="submit" value="Search!">
    </form>
    <jsp:include page="/footer.jsp"/>
  </body>
</html>
