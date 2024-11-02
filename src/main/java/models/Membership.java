package models;

import javax.persistence.*;

import util.UUIDToBinaryConverter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Membership")
public class Membership {

	@Id
	@GeneratedValue
	@Column(name = "membershipId", columnDefinition = "BINARY(16)") 
	private UUID membershipId;

	private Date expiringTime;

	@Column(nullable = false)
	private String membershipCode;

	@Column(nullable = false)
	private Date registrationDate;

	@Enumerated(EnumType.STRING)
	private Status membershipStatus;

	@ManyToOne
	@JoinColumn(name = "reader_id")
	private User reader;

	@ManyToOne
	@JoinColumn(name = "membership_type_id")
	private MembershipType membershipType;

	public Membership() {
		super();
	}

	public enum Status {
		APPROVED, REJECTED, PENDING
	}

	public UUID getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(UUID membershipId) {
		this.membershipId = membershipId;
	}

	public Date getExpiringTime() {
		return expiringTime;
	}

	public void setExpiringTime(Date expiringTime) {
		this.expiringTime = expiringTime;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Status getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(Status membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public User getReader() {
		return reader;
	}

	public void setReader(User reader) {
		this.reader = reader;
	}

	public MembershipType getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}

}
