package models;

import java.util.UUID;
import javax.persistence.*;

@MappedSuperclass
public abstract class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "personId", columnDefinition = "BINARY(16)") 
	private UUID personId;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Enumerated(EnumType.STRING)
	private GenderType gender;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(name = "is_deleted", nullable = true)
	private boolean isDeleted = false;

	// Constructors
	public Person() {
	}

	public Person(UUID personId, String firstName, String lastName, GenderType gender, String phoneNumber) {
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}

	
	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
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

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
