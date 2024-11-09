package models;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Shelf")
public class Shelf {

	@Id
	@GeneratedValue
	@Column(name = "shelfId", columnDefinition = "BINARY(16)")
	private UUID shelfId;

	private int availableStock;

	@Column(nullable = false)
	private String bookCategory;

	private int borrowedNumber;

	private int initialStock;

	@Column(name = "shelfCode", nullable = true, unique = true)
	private String shelfCode;

	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

	@OneToMany(mappedBy = "shelf")
	private List<Book> books;

	public Shelf() {
		super();
	}

	public UUID getShelfId() {
		return shelfId;
	}

	public void setShelfId(UUID shelfId) {
		this.shelfId = shelfId;
	}

	public int getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(int availableStock) {
		this.availableStock = availableStock;
	}

	public String getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}

	public int getBorrowedNumber() {
		return borrowedNumber;
	}

	public void setBorrowedNumber(int borrowedNumber) {
		this.borrowedNumber = borrowedNumber;
	}

	public int getInitialStock() {
		return initialStock;
	}
	
	public String getShelfCode() {
        return shelfCode;
    }

    public void setShelfCode(String shelfCode) {
        this.shelfCode = shelfCode;
    }

	public void setInitialStock(int initialStock) {
		this.initialStock = initialStock;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
