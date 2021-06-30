package com.example.smssender.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sms_messages")
public class Sms {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_seq")
    @SequenceGenerator(name = "sms_seq", sequenceName = "sms_seq")
    private Long id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime dateTime;

    @Column
    private Boolean isSent;

    @Column
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Tag> tags;
}
