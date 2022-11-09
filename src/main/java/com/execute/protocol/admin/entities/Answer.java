package com.execute.protocol.admin.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TEMP_ANSWERS")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @NotNull
    @Column(name = "use_once")
    private boolean useOnce;
    @Column(name = "answer_text", nullable = false)
    private String answerText;
    @Column
    private byte gold;
    @Column
    private byte reputation;
    @Column
    private byte influence;
    @Column
    private byte shadow;
    @Column
    private byte luck;
    @Column
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TEMP_SPECIALS")
    private Set<Integer> specials;
    @Column
    private int link;
    @Column(name = "open_categories")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TEMP_OPEN_CATEGORIES")
    private Set<Integer> openCategories;
    @Column(name = "close_categories")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "TEMP_CLOSE_CATEGORIES")
    private Set<Integer> closeCategories;
    @ManyToOne (optional=false, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;

}
