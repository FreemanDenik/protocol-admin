package com.execute.protocol.admin.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
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
@Table(name = "TEMP_EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column
    private boolean publication;
    @Column
    private boolean useOnce;
    @Column
    private int category;
    @Column
    @NotNull
    @NotBlank
    @EqualsAndHashCode.Include
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
            cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @OrderBy( "id ASC" )
    private Set<Answer> answers = new LinkedHashSet<>();
//    public Set<Answer> getAnswers(){
//        return answers.stream().sorted(Comparator.comparingInt(Answer::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
//    }
    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setEvent(this);
    }
    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setEvent(null);
    }
    public void syncAnswers(){
        answers.forEach(w->w.setEvent(this));
    }
}
