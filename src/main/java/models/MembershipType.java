package models;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "MembershipType")
public class MembershipType {

	@Id
	@GeneratedValue
	@Column(name = "mumbershipTypeId", columnDefinition = "BINARY(16)") 
	private UUID membershipTypeId;

	private int maxBooks;

	@Column(nullable = false)
	private String membershipName;

	private int price;

	@OneToMany(mappedBy = "membershipType")
	private List<Membership> memberships;

	public MembershipType() {
		super();
	}

	public UUID getMembershipTypeId() {
		return membershipTypeId;
	}

	public void setMembershipTypeId(UUID membershipTypeId) {
		this.membershipTypeId = membershipTypeId;
	}

	public int getMaxBooks() {
		return maxBooks;
	}

	public void setMaxBooks(int maxBooks) {
		this.maxBooks = maxBooks;
	}

	public String getMembershipName() {
		return membershipName;
	}

	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Membership> getMemberships() {
		return memberships;
	}

	public void setMemberships(List<Membership> memberships) {
		this.memberships = memberships;
	}

}
