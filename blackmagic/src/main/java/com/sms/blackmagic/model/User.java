package com.sms.blackmagic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="authority")
    private int authority;


    @Column(name="account_name")
    private String accountName;

    @Column(name="password")
    private String password;

    @Column(name="register_date")
    @CreationTimestamp
    private LocalDateTime registerDate;

    @Column(name="ip_address")
    private String ipAddress;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="contact")
    private String contact;

    @Column(name="note")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (this.authority) {
            case 2:
                return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
            case 1:
                return Collections.singleton(new SimpleGrantedAuthority("ROLE_MASTER"));
            case 0:
            default:
                return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return this.accountName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private Integer comId;

    public Integer getCompanyId() {
        return this.getCompany().getCompanyId();
    }

    public void setCompanyId(Integer companyId) {
        this.comId = companyId;
    }
}
