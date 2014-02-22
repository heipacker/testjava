package test.dlmu.guava;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Predicates.or;
import static com.google.common.base.Predicates.equalTo;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testPreconditions();
		testColletionFilter();
		testCollectionOrder();
		testSetAndMap();
	}
	
	static void testSetAndMap(){
		HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
		HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

		System.out.println("union:");
		print(Sets.union(setA, setB).iterator());
		
		System.out.println("difference:");
		print(Sets.difference(setA, setB).iterator());
		
		System.out.println("intersection:");
		print(Sets.intersection(setA, setB).iterator());
		
		HashMap<Integer, Integer> mapA = Maps.newHashMap();
		mapA.put(1, 2);
		mapA.put(2, 4);
		mapA.put(3, 5);
		HashMap<Integer, Integer> mapB = Maps.newHashMap();
		mapB.put(6, 2);
		mapB.put(2, 4);
		mapB.put(4, 5);
		
		MapDifference<Integer, Integer> differenceMap = Maps.difference(mapA, mapB);
		differenceMap.areEqual();
		Map<Integer, ValueDifference<Integer>> entriesDiffering = differenceMap.entriesDiffering();
		System.out.println("entriesDiffering:");
		print(entriesDiffering.entrySet());
		Map<Integer, Integer> entriesOnlyOnLeft = differenceMap.entriesOnlyOnLeft();
		System.out.println("entriesOnlyOnLeft:");
		print(entriesOnlyOnLeft.entrySet());
		Map<Integer, Integer> entriesOnlyOnRight = differenceMap.entriesOnlyOnRight();
		System.out.println("entriesOnlyOnRight:");
		print(entriesOnlyOnRight.entrySet());
		Map<Integer, Integer> entriesInCommon = differenceMap.entriesInCommon();
		System.out.println("entriesInCommon:");
		print(entriesInCommon.entrySet());
	}
	
	static void testPreconditions() {
		Preconditions.checkState(1 > 0);
		Preconditions.checkArgument(1 > 0);
		Preconditions.checkNotNull(new Object());
	}

	static void testColletionFilter() {
		List<String> names = Lists.newArrayList("Aleksander", "Jaran", "Integrasco", "Guava", "Java");

		Iterable<String> filtered = Iterables.filter(names, or(or(equalTo("Aleksander"), equalTo("Jaran")), lengthLessThan(5)));

		print(filtered);
	}

	static void testCollectionOrder() {
		
		Comparator<Person> byLastName = new Comparator<Person>() {
			
			public int compare(final Person p1, final Person p2) {
				return p1.getLastName().compareTo(p2.getLastName());
			}
		};

		Comparator<Person> byFirstName = new Comparator<Person>() {
			
			public int compare(final Person p1, final Person p2) {
				return p1.getFirstName().compareTo(p2.getFirstName());
			}
		};
		
		Person person = new Person();
		person.setFirstName("person1_first");
		person.setLastName("person1_last");

		List<Person> persons = Lists.newArrayList(person, new Person("person2_first", "person2_last"));

		List<Person> sortedCopy = Ordering.from(byLastName).compound(byFirstName).reverse().sortedCopy(persons);
		//直接扩展Comparator
		OrderingByLastName orderByLastName = new OrderingByLastName();
		
		sortedCopy = orderByLastName.compound(new OrderingByFirstName()).reverse().sortedCopy(persons);

		print(sortedCopy);
		
		System.out.println("--------------------------------------------------------------------");
		sortedCopy = Ordering.from(byLastName).sortedCopy(Iterables.filter(persons, withFirstName("Steve")));
		
		print(sortedCopy);

	}
	
	private static class EqualWithFirstNamePredicate implements Predicate<Person> {
		private final Person p;

		private EqualWithFirstNamePredicate(final Person p) {
			this.p = p;
		}

		public boolean apply(final Person p1) {
			return p.getFirstName().equals(p1.getFirstName());
		}
	}

	public static Predicate<Person> withFirstName(final String str) {
		return new EqualWithFirstNamePredicate(new Person(str, ""));
	}
	
	private static class OrderingByFirstName extends Ordering<Person>{

		@Override
		public int compare(final Person left, final Person right) {
			return left.getFirstName().compareTo(right.getFirstName());
		}
		
	}
	
	private static class OrderingByLastName extends Ordering<Person>{

		@Override
		public int compare(final Person left, final Person right) {
			return left.getFirstName().compareTo(right.getFirstName());
		}
		
	}
	
	static <T> void print(Iterable<T> iterable) {
		print(iterable.iterator());
	}
	
	static <T> void print(Set<T> list) {
		print(list.iterator());
	}
	
	static <T> void print(List<T> list) {
		print(list.iterator());
	}

	static <T> void print(Iterator<T> it) {
		// print the list
		while (it.hasNext()) {
			print(it.next());
		}
	}

	static <T> void print(T object) {
		// print the T
		if (object instanceof String || Integer.class.isAssignableFrom(object.getClass())) {
			System.out.println(object);
		} else if (object instanceof Person) {
			Person p = (Person) object;
			System.out.println("firstName:" + p.getFirstName() + " lastName:" + p.getLastName());
		} else if (object instanceof Map.Entry<?, ?>){
			Map.Entry<?, ?> p = (Map.Entry<?, ?>) object;
			System.out.println("key:" + p.getKey() + " value:" + p.getValue());
		}
	}
	private static class Person {

		private String firstName;

		private String lastName;

		public Person() {

		}

		public Person(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
	}

	private static class LengthLessThanPredicate implements Predicate<String> {
		private final int length;

		private LengthLessThanPredicate(final int length) {
			this.length = length;
		}

		public boolean apply(final String s) {
			return s.length() < length;
		}
	}

	public static Predicate<String> lengthLessThan(final int length) {
		return new LengthLessThanPredicate(length);
	}
}
