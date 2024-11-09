package models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User extends Person {

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = true) 
    private String picture;  

    // Many-to-One relationship with Location, referencing the specific "village" Location for the user
    @ManyToOne
    @JoinColumn(name = "village_id", nullable = true)
    private Location village;

    // One-to-Many relationship with Borrower, assuming "reader" in Borrower refers to User
    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Borrower> borrowers;

    // One-to-Many relationship with Membership, assuming "reader" in Membership refers to User
    @OneToMany(mappedBy = "reader", fetch = FetchType.LAZY)
    private List<Membership> memberships;

    // Constructors
    public User() {
        super();
    }

    // Parameterized constructor
    public User(String password, RoleType role, String userName, String picture, Location village) {
        super(); // Calls the Person constructor
        this.password = password;
        this.role = role;
        this.userName = userName;
        this.picture = picture;
        this.village = village;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
