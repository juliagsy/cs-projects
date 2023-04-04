<html>

<head>
  <meta charset="utf-8">
  <title>Create New List</title>
</head>

<body>
  <h1>Create New List</h1>
  <p>All data files will be saved in the <b>data</b> directory as the name of the list.</p>
  <form method="POST" action="/runcreate.html">
    <label for="listname">Name of new list: </label>
    <input type="text" name="listname" value="" required>
    <br>
    <input type="submit" value="Create!">
  </form>
  <jsp:include page="/footer.jsp"/>
</body>

</html>
