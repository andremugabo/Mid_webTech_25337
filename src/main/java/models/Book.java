package models;

import javax.persistence.*;

import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "Book")
public class Book {

	 @Id
	    @GeneratedValue
	    private UUID bookId;

	    @Enumerated(EnumType.STRING)
	    private BookStatus bookStatus;

	    @Column(nullable = false)
	    private String image;

	    private int edition;

	    @Column(nullable = false)
	    private String isbnCode;

	    private Date publicationYear;

	    @Column(nullable = false)
	    private String publisherName;

	    @Column(nullable = false)
	    private String title;

	    @ManyToOne
	    @JoinColumn(name = "shelf_id")
	    private Shelf shelf;
	    
	    

		public Book() {
			super();
		}

		public UUID getBookId() {
			return bookId;
		}

		public void setBookId(UUID bookId) {
			this.bookId = bookId;
		}

		public BookStatus getBookStatus() {
			return bookStatus;
		}

		public void setBookStatus(BookStatus bookStatus) {
			this.bookStatus = bookStatus;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public int getEdition() {
			return edition;
		}

		public void setEdition(int edition) {
			this.edition = edition;
		}

		public String getIsbnCode() {
			return isbnCode;
		}

		public void setIsbnCode(String isbnCode) {
			this.isbnCode = isbnCode;
		}

		public Date getPublicationYear() {
			return publicationYear;
		}

		public void setPublicationYear(Date publicationYear) {
			this.publicationYear = publicationYear;
		}

		public String getPublisherName() {
			return publisherName;
		}

		public void setPublisherName(String publisherName) {
			this.publisherName = publisherName;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Shelf getShelf() {
			return shelf;
		}

		public void setShelf(Shelf shelf) {
			this.shelf = shelf;
		}

	

    
}
