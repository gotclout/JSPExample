<jsp:useBean id="miner" class="miner.MinerData" scope="page"/>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Student Data<title>
      </head>

      <body>

        <h1>Student Table</h1>

        <table border="1">
          <tr><td>Student_Id</td><td>Year</td><td>GPA</td><td>Probation</td></tr>
          <%= miner.MineDataFrom("Student") %>
        </table>

        <%
        String studentTable = "Student";
        String argv[] = new String[4];
        int argc = 4;
        argv[0] = request.getParameter("studentId");
        argv[1] = request.getParameter("year");
        argv[2] =  request.getParameter("gpa");
        argv[3] =  request.getParameter("probation");
        String srow = request.getParameter("sRow");
        String rn = request.getParameter("rownum");
        String filename = request.getParameter("filename");
        %>

        <form ACTION='<% miner.AddRow(studentTable, argv, argc); %>'>
          <INPUT TYPE=TEXT NAME=studentId SIZE=9>
            <INPUT TYPE=TEXT NAME=year SIZE=8>
              <INPUT TYPE=TEXT NAME=gpa SIZE=3>
                <INPUT TYPE=TEXT NAME=probation SIZE=8>
                  <p><INPUT TYPE =SUBMIT NAME=name VALUE="Add Row"></p>
                    <form ACTION='<% miner.DeleteRow(studentTable,rn); %>'> 
                      <INPUT TYPE =SUBMIT NAME=name VALUE="Delete Row">
                        <INPUT TYPE=TEXT NAME=rownum SIZE=3> (Student_Id)
                        </form>
                        <form ACTION='<% miner.UpdateRow(studentTable, srow, argv, argc); %>'>
                          <INPUT TYPE=SUBMIT NAME=rowButton VALUE="Update Row">
                            <INPUT TYPE=TEXT NAME=sRow SIZE=8> (Student_Id)
                            </form>
                            <br>

                              <br>
                              </form>

                              <h1>Other Course Table</h1>
                              <table border="1">
                                <tr><td>Course_Id</td><td>Topic</td><td>Credits</td></tr>
                                <%= miner.MineDataFrom("Other_Course") %>
                              </table>

                              <%
                              String otherCourseTable = "Other_Course";
                              String oArgv[] = new String[3];
                              int oArgc = 3;
                              oArgv[0] = request.getParameter("oCourseId");
                              oArgv[1] = request.getParameter("oTopic");
                              oArgv[2] = request.getParameter("oCredits");
                              String orow = request.getParameter("oRow");
                              String key = request.getParameter("oRownum");
                              %>

                              <form ACTION='<% miner.AddRow(otherCourseTable, oArgv, oArgc); %>'>

                                <INPUT TYPE=TEXT NAME=oCourseId  SIZE=10>
                                  <INPUT TYPE=TEXT NAME=oTopic  SIZE=5>
                                    <INPUT TYPE=TEXT NAME=oCredits SIZE=5>
                                      <p><INPUT TYPE =SUBMIT NAME=name VALUE="Add Row"></p>
                                        <form ACTION='<% miner.DeleteRow(otherCourseTable,key); %>'>
                                          <INPUT TYPE =SUBMIT NAME=name VALUE="Delete Row">
                                            <INPUT TYPE=TEXT NAME=oRownum SIZE=3>(Course_Id)
                                            </form>
                                          </form>
                                          <form ACTION='<% miner.UpdateRow(otherCourseTable, orow, oArgv, oArgc); %>' METHOD="POST">
                                            <INPUT TYPE=SUBMIT NAME=rowButton VALUE="Update Row">
                                              <INPUT TYPE=TEXT NAME=oRow SIZE=3> (Course_Id)
                                              </form>
                                              <br>


                                                <h1>KSU Course Table</h1>
                                                <table border="1">
                                                  <tr><td>Course_Id</td><td>Department</td><td>Number</td><td>Topic</td><td>Credits</td></tr>
                                                  <%= miner.MineDataFrom("KSU_Course") %>
                                                </table>

                                                <%
                                                String ksuCourseTable = "KSU_Course";
                                                String kArgv[] = new String[5];
                                                int kArgc = 5;
                                                kArgv[0] = request.getParameter("kCourseId");
                                                kArgv[1] = request.getParameter("kDepartment");
                                                kArgv[2] = request.getParameter("kNumber");
                                                kArgv[3] = request.getParameter("kTopic");
                                                kArgv[4] = request.getParameter("kCredits");
                                                String krow = request.getParameter("kRow");
                                                String kKey = request.getParameter("kRownum");
                                                %>

                                                <form ACTION='<% miner.AddRow(ksuCourseTable, kArgv, kArgc); %>'>

                                                  <INPUT TYPE=TEXT NAME=kCourseId  SIZE=10>
                                                    <INPUT TYPE=TEXT NAME=kDepartment  SIZE=10>
                                                      <INPUT TYPE=TEXT NAME=kNumber SIZE=6>
                                                        <INPUT TYPE=TEXT NAME=kTopic SIZE=7>
                                                          <INPUT TYPE=TEXT NAME=kCredits SIZE=5>
                                                            <p><INPUT TYPE =SUBMIT NAME=name VALUE="Add Row"></p>
                                                              <form ACTION='<% miner.DeleteRow(studentTable,kKey); %>'>
                                                                <INPUT TYPE =SUBMIT NAME=name VALUE="Delete Row">
                                                                  <INPUT TYPE=TEXT NAME=kRownum SIZE=3>(Course_Id)
                                                                  </form>
                                                                </form>
                                                                <form ACTION='<% miner.UpdateRow(ksuCourseTable, krow, kArgv, kArgc); %>' METHOD="POST">
                                                                  <INPUT TYPE=SUBMIT NAME=rowButton VALUE="Update Row">
                                                                    <INPUT TYPE=TEXT NAME=kRow SIZE=3> (Course_Id)
                                                                    </form>
                                                                    <br>

                                                                      <form>
                                                                        <h2>AddTable</h2>
                                                                        input table name:
                                                                        <br>
                                                                          <form ACTION='<% miner.ReadFile(filename); %>'>
                                                                            <INPUT TYPE=TEXT NAME=filename VALUE=/home/grads/rfoster/Foster_Robert_MP4-RLF.zip_FILES/MP4-RLF/table1" SIZE=20>
                                                                              <INPUT TYPE=SUBMIT NAME=ok VALUE = "Add Table">
                                                                              </form>

                                                                              <%
                                                                              String table = request.getParameter("selName");
                                                                              String skey = request.getParameter("key");
                                                                              %>

                                                                              <form ACTION='<% miner.Search(skey, table); %>' >
                                                                                <h2>Search</h2>
                                                                                input a primary key value:
                                                                                <br>
                                                                                  <INPUT TYPE=TEXT NAME=key SIZE=20>
                                                                                    <br>
                                                                                      choose a table:
                                                                                      <br>
                                                                                        <select NAME=selName>
                                                                                          <option VALUE=Student>Student Table</option>
                                                                                          <option VALUE=Other_Course>Other Course Table </option>
                                                                                          <option VALUE=KSU_Course>Ksu Course Table </option>
                                                                                        </select>
                                                                                        <br>
                                                                                          <INPUT TYPE=SUBMIT NAME=ok VALUE = "Find">

                                                                                          </form>
                                                                                          Search Results (refresh for updates)
                                                                                          <%= miner.Results() %>
                                                                                        </body>
                                                                                      </html>


