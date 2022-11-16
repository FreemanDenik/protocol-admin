package com.execute.protocol.admin.entities;

import com.execute.protocol.admin.interfaces.FastFiner;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TEMP_CATEGORIES")
//@JsonIgnoreProperties({"event"})
public class Category implements FastFiner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column
    private boolean publication;
    @Column(unique = true)
    @EqualsAndHashCode.Include
    private String title;
    @Column
    private String description;

}
