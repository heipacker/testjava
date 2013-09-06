package test.dlmu.collection.hashmap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Person implements Iterable<Person> {
	public Person(String fn, String ln, int a, Person... kids) {
		this.firstName = fn;
		this.lastName = ln;
		this.age = a;
		for (Person kid : kids)
			children.add(kid);
	}

	public void setFirstName(String value) {
		this.firstName = value;
	}

	public void setLastName(String value) {
		this.lastName = value;
	}

	public void setAge(int value) {
		this.age = value;
	}

	public int hashCode() {
		return firstName.hashCode() & lastName.hashCode() & age;
	}

	private String firstName;
	private String lastName;
	private int age;
	private List<Person> children = new ArrayList<Person>();

	@Override
	public Iterator<Person> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

}