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
    /**
     * Опубликована ли карта
     */
    @Column
    private boolean publication;
    /**
     * Является ли карта дочерней
     */
    @Column
    private boolean child;
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
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    //Если orphanRemoval=true, то при удалении комментария из списка комментариев топика, комментарий удаляется из базы
    //Если orphanRemoval=false, то при удалении комментария из списка, в базе комментарий остается.  Просто его внешний ключ (comment.topic_id) обнуляется, и  больше комментарий не ссылается на топик.
    @OrderBy("id ASC")
    private Set<Answer> answers = new LinkedHashSet<>();

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setEvent(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setEvent(null);
    }

    public void syncAnswers() {
        answers.forEach(w -> w.setEvent(this));
    }
}
