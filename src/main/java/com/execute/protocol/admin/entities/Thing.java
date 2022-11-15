package com.execute.protocol.admin.entities;

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
@Table(name = "THINGS")
public class Thing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
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
