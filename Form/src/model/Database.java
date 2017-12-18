package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//store people in the database
public class Database {

	private List<Person> people;
	private Connection con;
	
	//fields for "preferences"
	private int port;
	private String user;
	private String password;
	
	

	public Database() {
		people = new LinkedList<Person>();
	}
	
	//69
	public void configure(int port, String user, String password) throws Exception {
		
		this.port = port;
		this.user = user;
		this.password = password;
		
		if (con != null) {
			disconnect();
			connect();
		}
		
	}
	
	public void connect() throws Exception {
		if(con !=null) return;
		try { 
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}


		String url = "jdbc:mysql://localhost:3306/Swing";
		con = DriverManager.getConnection(url, "root", "Twist3r:1");

		System.out.println("Connected " + con);
	}

	public void disconnect() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println("Can`t close connection");
			}
		}
	}



	//38
	//save the people into the MySQL database
	public void save() throws SQLException {

		//sqlStatement
		String checkSsql = "Select count(*) as count from people where id=?";
		//prepared statement (tells the prepared statement to use the sqlStatement)
		PreparedStatement checkStmt = con.prepareStatement(checkSsql);

		String insertSql = "insert into people (id, name, age, employment_status, tax_id, us_citizen, gender, occupation) values(?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
		//updating sqlStatement
		String updateSql = "update people set name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? where id = ?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		
		for (Person person : people) {
			int id = person.getId();
			String name = person.getName();
			String occupation = person.getOccupation();
			AgeCategory age = person.getAgeCategory();
			EmploymentCategory emp = person.getEmpCat();
			String tax = person.getTaxId();
			boolean isUs = person.isUsCitizen();
			Gender gender = person.getGender();

			//pass the position of the wildcard (1), and replace it with id
			checkStmt.setInt(1, id);
			//execute the statement
			ResultSet checkResult = checkStmt.executeQuery();

			//move to the next rows
			checkResult.next();

			int count = checkResult.getInt(1);
			//39 inserting data to MySQL DB
			if(count == 0) {
				System.out.println("Inserting person with id " + id);
				
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, age.name());
				insertStatement.setString(col++, emp.name());
				insertStatement.setString(col++, tax);
				insertStatement.setBoolean(col++, isUs);
				insertStatement.setString(col++, gender.name());
				insertStatement.setString(col++, occupation);
				
				insertStatement.executeUpdate();
			}
			else {
				System.out.println("Updating person with ID " + id + " is " + count );
				
				int col = 1;
				
				updateStatement.setString(col++, name);
				updateStatement.setString(col++, age.name());
				updateStatement.setString(col++, emp.name());
				updateStatement.setString(col++, tax);
				updateStatement.setBoolean(col++, isUs);
				updateStatement.setString(col++, gender.name());
				updateStatement.setString(col++, occupation);
				updateStatement.setInt(col++, id);
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void load() throws SQLException {
		people.clear();
		
		String sql = "select id, name, age, employment_status, tax_id, us_citizen, gender, occupation from people order by name";
		Statement selectStatement = con.createStatement();
		 
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()) {
			//need to match the column name 
			int id = results.getInt("id");
			String name = results.getString("name");
			String age = results.getString("age");
			String emp = results.getString("employment_status");
			String tax = results.getString("tax_id");
			Boolean isUs = results.getBoolean("us_citizen");
			String gender = results.getString("gender");
			String occ = results.getString("occupation");
			
			//System.out.println(id);
			
			
			
			Person person = new Person(id, name, occ, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), tax, isUs, Gender.valueOf(gender));
			people.add(person);
			System.out.println(person);
		}
		
		
		results.close();
		selectStatement.close();
		
	}


	 
	
	public void addPerson(Person person) {
		people.add(person);
	}

	//32
	public void removePerson(int index) {
		people.remove(index);
	} 

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	//28
	//writing ArrayList of people to file
	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		//convert to an array first
		Person[] persons = people.toArray(new Person[people.size()]);
		oos.writeObject(persons);

		oos.close();
	}

	//load method

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();

			people.clear();
			//converted the persons array to a list and added to the list
			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//remember array (remembers the type)
		ois.close();
	}

}