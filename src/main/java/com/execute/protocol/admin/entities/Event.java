package com.execute.protocol.admin.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEMP_EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column
    private boolean useOnce;
    @Column
    private int category;
//    @Column
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category oCategory;
    @Column
    @NotNull
    @NotBlank
    private String eventText;
    @Column(name = "create_time")
    @NotNull
    private LocalDateTime createTime;
    @Column(name = "update_time")
    @NotNull
    private LocalDateTime updateTime;
    @Column
    @NotNull
    @OneToMany(
            mappedBy = "event",
            fetch = FetchType.LAZY,
            targetEntity = Answer.class,
            orphanRemoval = true,
            cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Answer> answers;
}
