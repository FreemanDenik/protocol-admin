package com.execute.protocol.admin.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "TEMP_ANSWERS")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @NotNull
    @Column(name = "use_once")
    private boolean useOnce;
    @EqualsAndHashCode.Include
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
    // не давать название addThinks mupstruct пропускает такие поля
    @Column(name = "give_things")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "TEMP_GIVE_THINGS")

    private Set<Integer> giveThings;
    @Column(name = "if_things")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "TEMP_IF_THINGS")
    private Set<Integer> ifThings;
    @Column
    private int link;
    @Column(name = "open_categories")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "TEMP_OPEN_CATEGORIES")
    private Set<Integer> openCategories;
    @Column(name = "close_categories")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "TEMP_CLOSE_CATEGORIES")
    private Set<Integer> closeCategories;
    //optional = false,  Если установлено значение false, то всегда должна существовать ненулевая связь.

    // @JoinColumnработает с физической моделью, т. е. с тем, как вещи фактически расположены в хранилище данных (базе данных). Указание nullable = falseсделает столбец БД необнуляемым.
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private Event event;


}
