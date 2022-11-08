package com.execute.protocol.admin.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEMP_CATEGORIES")
//@JsonIgnoreProperties({"event"})
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(unique = true)
    private String title;
    @Column
    private String description;
//    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY)
//    private Event event;
}
