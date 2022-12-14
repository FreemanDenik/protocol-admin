package com.execute.protocol.admin.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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
            fetch = FetchType.EAGER,
            targetEntity = Answer.class,
            orphanRemoval = true,
            cascade= {CascadeType.ALL})
    private List<Answer> answers;
}
