package com.evolent.application.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class Contact {

	public enum ContactStatus {
		ACTIVE, INACTIVE;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank(message = "First Name is mandatory")
	@Pattern(regexp="^[a-zA-Z\\s]*$", message="Invalid Input")
	@Column(name = "firstName")
	private String firstName;
	@NotBlank(message = "Last Name is mandatory")
	@Pattern(regexp="^[a-zA-Z\\s]*$", message="Invalid Input")
	@Column(name = "lastName")
	private String lastName;
	@NotBlank(message = "Email is mandatory")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Please enter valid email ID")
	@Column(name = "email")
	private String email;
	@Pattern(regexp = "^[6-9]\\d{9}$", message="Invalid Phone Number")
	@Column(name = "Phone_Number")
	private String phoneNumber;
	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	private ContactStatus status;
	
	public Contact(String firstName, String lastName, String email, String phoneNumber, ContactStatus status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}
	
	public Contact() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public ContactStatus getStatus() {
		return status;
	}
	public void setStatus(ContactStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Contact :: First Name = "+firstName + ", Last Name = " + lastName + ", Email = "+ email + ", Phone Number = "+ phoneNumber + 
				",Status = "+status;
	}
}
