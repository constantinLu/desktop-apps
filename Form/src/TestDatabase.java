import java.sql.SQLException;

import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class TestDatabase {

	public static void main(String[] args) {
		System.out.println("Running Database test");
		
		
		Database db = new Database();
		
		try {
			db.connect();
			System.out.println("Connected to the database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.addPerson(new Person("Joe", "lion tamer", AgeCategory.adult, EmploymentCategory.employed, "777", true, Gender.male));
		db.addPerson(new Person("Sue", "policeman", AgeCategory.senior, EmploymentCategory.selfEmployed, null, true, Gender.female));
		
		
		//testing the save method from Database
		try {
			db.save();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//testing the load method from Database
		try {
			db.load();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.disconnect();
	}
	
	

}
