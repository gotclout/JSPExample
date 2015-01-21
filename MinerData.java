package miner;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;

public class MinerData extends HttpServlet{
	//Members
	private String sqlConnect;
	private String userName;
	private String pwd;
	private Connection connect;
	private ResultSet rs;
	private Statement st;
	private ResultSetMetaData md;
	private String test;
	private String res;
	private String update;
	//default constructor
	
	public MinerData()
	{
	//	System.out.println("Constructing\n");
		sqlConnect = "jdbc:mysql://mysql.cis.ksu.edu/rfoster";
		userName   = "rfoster";
		pwd        = "insecurepassword";
		rs = null;
		boolean ok;
		
		test = "Hello world";
		try{
			Class.forName("com.mysql.jdbc.Driver");//connect driver
		}
		catch(Exception ce){
	//		System.out.println("jdbc failed\n");
			System.out.print(ce.getMessage());
		}
																						
		//try to connect
		try{
			 connect = DriverManager.getConnection(sqlConnect, userName, pwd);
			 st = connect.createStatement();
	//		 System.out.println("ok\n");
		}
		catch(SQLException se){
			System.out.print(se.getMessage());
		}
	}
	
	public String showTest(){return "test";}
	//get all data frome existing table
	public String MineDataFrom(String tableName)
	{
		int count;
		int rowNum = 1;
		String query = "SELECT * FROM " + tableName;
		String str = "";

		try{
			rs    = st.executeQuery(query);
			md    = rs.getMetaData();
			count = md.getColumnCount();
	//		System.out.println("in function\n");
			//generate tags for each attribute
			while(rs.next()){
				str += "<tr>";

				for(int i=1; i <= count; i++)
				{
					str += "<td>" + rs.getString(i) + "</td>"; 
				}
				str += "</tr>";
			}
			//rs.close();
			//st.close();
			//connect.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return str;
	}
	public boolean AddRow(String tableName, String argv[], int argc)
	{
		boolean ok = true;
		String add;
		add = "Insert INTO " + tableName + " VALUES(" + "'" + argv[0] + "'";
		for(int i = 1; i < argc; i++)
		{
			add += ", " + "'" + argv[i] + "'";
		}
		add += ");";
		try{
			ok = st.execute(add);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("adding\n\n");
		return ok;
	}
	public boolean DeleteRow(String tableName, String key)
	{
		boolean ok = true;
		String remove = "DELETE FROM " + tableName + " WHERE ";
		try{
			if(tableName.equals("Student"))
			{
				remove += "Student_Id = '" + key + "'";
			}
			else if(tableName.equals("Other_Course") ||tableName.equals("KSU_Course"))
			{
				remove += "Course_Id = '" + key + "'";
			}
			try{
				 ok = st.execute(remove);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return ok;
	}
	public boolean Search(String key, String table)
	{
		int count;
		boolean ok = true;
		String query = "";
		int numRows = 0;

		try
		{
			if(table.equals("Student"))
			{
				res = "<table border=\"1\"><tr><td>Student_Id</td><td>Year</td><td>GPA</td><td>Probation</td></tr>";
				query = "SELECT * FROM " + table + " WHERE Student_Id ='" + key + "';";
			}
			else if(table.equals("Other_Course"))
			{
				res = "<table border=\"1\"><tr><td>Course_Id</td><td>Topic</td><td>Credits</td></tr>";
				query = "SELECT * FROM " + table + " WHERE Course_Id ='" + key + "';";
			}
			else if(table.equals("KSU_Course"))
			{
				res = "<table border=\"1\"><tr><td>Course_Id</td><td>Department</td><td>Number</td><td>Topic</td>"+
				"<td>Credits</td></tr>";
				query = "SELECT * FROM " + table + " WHERE Course_Id ='" + key + "';";
			}
			rs    = st.executeQuery(query);
                        md    = rs.getMetaData();
                        count = md.getColumnCount();
 
                        //generate tags for each attribute
                        while(rs.next()){
                                res += "<tr>";
                                numRows++;
				for(int i=1; i <= count; i++)
                                {
                                        res += "<td>" + rs.getString(i) + "</td>";
                                }
                                res += "</tr>";
                        }
			res += "</table>";
			//this check acts as a trigger for invalid key searches.
			if(numRows < 1)
				res += "Your Search Yielded No Results!";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ok;
	}
	public String Results()
	{
		return res;
	}
	public boolean UpdateRow(String table, String key, String argv[], int argc)
	{
		boolean ok = true;
		update = "UPDATE " + table + " SET ";
		try{
			//Since the Key values are strings, the equals "" check acts as a trigger so if there is no data
			//no string will be passed in and the execute command will fail returning a false value to the jsp
			if(table.equals("Student"))
			{
				 for(int i = 0; i < argc; i++)
				 {
				 	if(i == 0 && !argv[i].equals(""))
						update += "Student_Id = '" + argv[0] +"'";
					else if(i == 1 && !argv[i].equals(""))
						update += ", Year = '" + argv[1] + "'";
					else if (i == 2 && !argv[i].equals(""))
						update += ", GPA = '" + argv[2] + "'";
					else if(!argv[i].equals(""))
						update += ", Probation = '" + argv[3] + "'";
				}
				update += " WHERE Student_Id = '" + key + "';" ;
			}
			else if(table.equals("Other_Course"))
			{
				 for(int i = 0; i < argc; i++)
				 {
				 	if(i == 0 && !argv[i].equals(""))
						update += "Course_Id = '" + argv[0] +"'";
					else if(i == 1 && !argv[i].equals(""))
						update += ", Topic = '" + argv[1] + "'";
					else if(!argv[i].equals(""))
						update += ", Credits = '" + argv[2] + "'";
				}
				update += " WHERE Course_Id = '" + key + "';" ;

			}
			else if(table.equals("KSU_Course"))
			{
				 for(int i = 0; i < argc; i++)
				 {
				 	if(i == 0 && !argv[i].equals(""))
						update += "Course_Id = '" + argv[0] +"'";
					else if(i == 1 && !argv[i].equals(""))
						update += ", Department = '" + argv[1] + "'";
					else if (i == 2 && !argv[i].equals(""))
						update += ", Number = '" + argv[2] + "'";
					else if(i == 3 && !argv[i].equals(""))
						update += ", Topic = '" + argv[3] + "'";
					else if(!argv[i].equals(""))
						update += ", Credits = '" + argv[4] + "'";
				}
				update += " WHERE Course_Id = '" + key + "';" ;

			}

			ok = st.execute(update);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return ok;
	}
	public String dis()
	{
		return update;
	}

	public void ReadFile(String file)
	{
	         //String filename = request.getParameter("filename");
		try{
			BufferedReader in = new BufferedReader(new FileReader(file));
			if(file.length() < 0){
				System.out.println("Please input commands in the following format:\n" +
					           "Program Name Input File 1 Input File 2\n");
		}
		String str, attStr = "", 
		table1Str = "CREATE TABLE tableTest ( ", 
			
		table1ins = "INSERT INTO tableTest ", 
		table1ats = "(",
		row = "",
		subStr;
		int rows = 0, cols = 0;
			
		boolean ok;
		    st.execute("drop table if exists tableTest");
		    //st.execute("drop table if exists table2");
			while ((str = in.readLine()) != null) 
			{
				if(str.startsWith("@"))
				{
					str = str.replaceAll("@", "");
					if(cols > 0){
						attStr += ", ";
						table1ats += ", ";
					}
					attStr += str + " varchar(100) default '' not null";
					table1ats += str;
					cols++;
				}
				else{
					if(rows == 0){
						table1Str += attStr + ");";
						table1ats += " )";
					  	ok = st.execute(table1Str); 
					}
					StringTokenizer stz = new StringTokenizer(str, " ");
					while(stz.hasMoreTokens()){
						if(!row.equals(""))
							row +=", ";
						subStr =  stz.nextToken() ;
						row += "'" + subStr +"'";
					}
					String newRow = "";
					newRow += table1ins;
					newRow += table1ats + " VALUES(" + row + ")";
					ok = st.execute(newRow);
					rows++;
					row = "";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	//main
	public static void main(String args[])
	{
		MinerData md = new MinerData();
		String s = md.MineDataFrom("Student");
		System.out.println(s);
		System.out.println(md.showTest());
		md.ReadFile("/home/grads/rfoster/Foster_Robert_MP4-RLF.zip_FILES/MP4-RLF/table1");
	}
}

