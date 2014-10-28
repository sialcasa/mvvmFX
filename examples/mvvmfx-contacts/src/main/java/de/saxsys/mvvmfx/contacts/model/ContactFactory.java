package de.saxsys.mvvmfx.contacts.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * A factory that produces contacts. This is used to create dummy data for tests and for the demo.
 */
public class ContactFactory {

	/**
	 * @return a contact with random generated demo content.
	 */
	public static Contact createRandomContact(){
		Contact contact = new Contact();

		contact.setFirstname(new Random().nextBoolean() ? getFemaleFirstname() : getMaleFirstname());
		contact.setLastname(getLastname());
		
		contact.setBirthday(getBirthday());
		
		contact.setEmailAddress(getEmailAddress(contact.getFirstname()));
		contact.setPhoneNumber(getPhoneNumber());
		contact.setMobileNumber(getPhoneNumber());
		
		return contact;
	}
	
	private static LocalDate getBirthday(){
		int year = (2014 - 50) + new Random().nextInt(50);
		
		int month = new Random().nextInt(12) + 1;
		
		int day = new Random().nextInt(27) + 1;

		return LocalDate.of(year,month,day);
	}

	private static String getPhoneNumber() {
		StringBuilder number = new StringBuilder();
		
		number.append("+49 ");
		
		for(int i=0 ; i<11 ; i++){
			number.append(getRandomNumber());
		}
		
		return number.toString();
	}

	private static String getEmailAddress(String firstname) {
		StringBuilder emailAddress = new StringBuilder();
		
		emailAddress.append(firstname);
		emailAddress.append(getRandomNumber());
		emailAddress.append(getRandomNumber());
		emailAddress.append("@");

		List<String> domains = Arrays.asList("example.com", "example.org", "mail.example.com");
		emailAddress.append(domains.get(new Random().nextInt(domains.size())));
		
		return emailAddress.toString();
	}

	private static String getLastname(){

		List<String> names = Arrays.asList("Smith", "Brown", "Lee","Johnson", "Williams", "Müller", "Schmidt", "Schneider");

		return names.get(new Random().nextInt(names.size()));
	}
	
	private static String getMaleFirstname(){

		List<String> names = Arrays.asList("Max", "Paul", "Leon", "Lucas", "Jonas", "Ben", "Tim", "David");

		return names.get(new Random().nextInt(names.size()));
	}
	
	private static String getFemaleFirstname(){

		List<String> names = Arrays.asList("Marie", "Julia", "Anne", "Laura", "Lisa", "Sarah", "Michelle", "Sophie");

		return names.get(new Random().nextInt(names.size()));
	}
	
	private static int getRandomNumber(){
		return new Random().nextInt(10);
	}

}
