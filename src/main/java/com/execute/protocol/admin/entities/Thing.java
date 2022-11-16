package com.execute.protocol.admin.entities;

import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.enums.EmTarget;
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
@Table(name = "TEMP_THINGS")
public class Thing implements FastFiner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column
    private boolean publication;
    @Column
    @EqualsAndHashCode.Include
    private String title;
    @Column
    private String description;
    @Column
    private EmTarget target;  // GOLD     REPUTATION  ANSWER
    @Column
    private int count; // +20       -30         23
}
