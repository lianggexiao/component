package qing.jsr107;

import java.io.Serializable;

public class Person implements Serializable{

	private  String ssn;
    private  String firstName;
    private  String lastName;

    public Person(String ssn, String firstName, String lastName)
    {
      this.ssn = ssn;
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getSsn() {
        return ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" + "ssn=" + ssn + ", firstName=" + firstName + ", lastName=" + lastName + '}';
    }
    
}
