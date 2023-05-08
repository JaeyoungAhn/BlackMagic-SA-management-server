package com.sms.blackmagic.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@ToString
@Getter
@Setter
@Builder
//@Table(name="company")
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "company_name", nullable = false, length = 50)
    private String companyName;

//    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<User> users = new ArrayList<>();

}

