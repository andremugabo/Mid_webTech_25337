package models;

import javax.persistence.*;

import util.UUIDToBinaryConverter;

import java.util.UUID;
import java.util.Date;

@Entity
@Table(name = "Borrower")
public class Borrower {

	@Id
    @GeneratedValue
    @Column(name = "borrowerId", columnDefinition = "BINARY(16)")  
    private UUID borrowerId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private Date pickupDate;

    @Column(nullable = true)
    private Integer fine;

    @Column(nullable = true)
    private Integer lateChargeFees;

    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;

	

	public UUID getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(UUID borrowerId) {
		this.borrowerId = borrowerId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Integer getFine() {
		return fine;
	}

	public void setFine(Integer fine) {
		this.fine = fine;
	}

	public Integer getLateChargeFees() {
		return lateChargeFees;
	}

	public void setLateChargeFees(Integer lateChargeFees) {
		this.lateChargeFees = lateChargeFees;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

    
}