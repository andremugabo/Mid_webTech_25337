package models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Person {

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(nullable = false)
    private String userName;

    // Many-to-One relationship with Location, referencing the specific "village" Location for the user
    @ManyToOne
    @JoinColumn(name = "village_id")
    private Location village;

    // One-to-Many relationship with Borrower, assuming "reader" in Borrower refers to User
    @OneToMany(mappedBy = "reader")
    private List<Borrower> borrowers;

    // One-to-Many relationship with Membership, assuming "reader" in Membership refers to User
    @OneToMany(mappedBy = "reader")
    private List<Membership> memberships;

    // Constructors
    public User() {
        super();
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getVillage() {
        return village;
    }

    public void setVillage(Location village) {
        this.village = village;
    }

    public List<Borrower> getBorrowers() {
        return borrowers;
    }

    public void setBorrowers(List<Borrower> borrowers) {
        this.borrowers = borrowers;
    }

    public List<Membership> getMemberships() {
        return memberships;
    }

    public void setMemberships(List<Membership> memberships) {
        this.memberships = memberships;
    }
}
