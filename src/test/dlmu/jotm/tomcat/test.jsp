<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

    <%
    String completion = request.getParameter("completion");
    foo.DBTest test = new foo.DBTest();
    test.init(completion);
    %>
    <h2>Transaction completion</h2>
    Transaction completion is :<strong><%= completion %></strong>

    <h2>Results</h2>
    Int stored in JDBC : <strong><%= test.getFoo() %></strong><br />

    <hr />

    <form action="test.jsp" method="get">
      <input type="radio" name="completion" value="commit" checked="true"> Commit<BR>
      <input type="radio" name="completion" value="rollback">Rollback<BR>
      <button type="submit">Completion</button>
    </form>
  </body>
</html>