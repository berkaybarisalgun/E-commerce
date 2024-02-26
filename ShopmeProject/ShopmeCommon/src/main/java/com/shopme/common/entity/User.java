package com.shopme.common.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128,nullable = false,unique = true)
    private String email;

    @Column(length = 64,nullable = false)
    private String password;

    @Column(name="first_name",length = 45,nullable = false)
    private String firstName;

    @Column(name="last_name",length = 45,nullable = false)
    private String lastName;
    @Column(length = 64)
    private String photos;

    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER) // we used many to many because For example, in an e-commerce application, a user may need to have both the "Seller" and "Editor" roles. In this case, the ManyToMany relationship allows a user to have multiple roles and the same role to be shared by multiple users.
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name = "user_id"), //defines foreign key
            inverseJoinColumns = @JoinColumn(name = "role_id ")//check section 07 page 18 you will get it.
    )
    private Set<Role> roles=new HashSet<>();

    public User() {
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhotos() {
        return photos;
    }

    public Boolean getEnabled(){
        return enabled;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }

    @Override
    public String toString() { //The toString() method returns textual representation of a object. It will be invoked by System.out.print() method and also by Thymeleaf's print value expression. In most cases, we use toString() for testing/debugging purpose.
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roles=" + roles +
                '}';
    }
    @Transient
    public String getPhotosImagePath(){
        if(id==null || photos==null) return "/images/default-user.png";
        return "/user-photos/"+this.id+"/"+this.photos;
    }
    @Transient
    public String getFullName(){
        return firstName+" "+lastName;
    }

}
