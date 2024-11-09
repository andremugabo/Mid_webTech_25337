package models;

import javax.persistence.*;
import java.util.UUID;
import java.util.Date;

@Entity
@Table(name = "Borrower")
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "borrowerId", columnDefinition = "BINARY(16)")  
    private UUID borrowerId;

    @ManyToOne(cascade = CascadeType.ALL)  
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)  
    private Date dueDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date pickupDate;

    @Column(nullable = true)
    private Integer fine = 0; 

    @Column(nullable = true)
    private Integer lateChargeFees = 0; 

    @ManyToOne(cascade = CascadeType.ALL)  
    @JoinColumn(name = "reader_id", nullable = false)
    private User reader;
    
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date return_date;
    
    @Column(name = "isDeleted")
    private boolean isDeleted;

    
    public boolean isDeleted() {
		return isDeleted;
	}


	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}


	public Borrower() {}

    
    public Borrower(Book book, Date dueDate, Date pickupDate, User reader) {
        this.book = book;
        this.dueDate = dueDate;
        this.pickupDate = pickupDate;
        this.reader = reader;
    }

    
    public Date getReturn_date() {
		return return_date;
	}


	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}


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
