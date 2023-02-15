package kz.redmadbot.auction.entity;

import javax.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends Abstract_entity{
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "ownerUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Announcement> ownersAnnouncements;

    @OneToMany(mappedBy = "buyerUser", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Announcement> wantToBuyAnnouncments;

    public User() {
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


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Announcement> getOwnersAnnouncements() {
        return ownersAnnouncements;
    }

    public void setOwnersAnnouncements(List<Announcement> ownersAnnouncements) {
        this.ownersAnnouncements = ownersAnnouncements;
    }

    public List<Announcement> getWantToBuyAnnouncments() {
        return wantToBuyAnnouncments;
    }

    public void setWantToBuyAnnouncments(List<Announcement> wantToBuyAnnouncments) {
        this.wantToBuyAnnouncments = wantToBuyAnnouncments;
    }
}
