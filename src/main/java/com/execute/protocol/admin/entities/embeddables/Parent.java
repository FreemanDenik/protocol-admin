package com.execute.protocol.admin.entities.embeddables;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Parent {
    /**
     * Главный родительское событие (с которого начинается сюжет)
     */
    @Column(name = "parent_event")
    private int parentEvent;
    /**
     * Не посредственно родительское событие
     */
    @Column(name = "own_parent")
    private int ownEvent;
    /**
     * Ответ, который приводит к этому событию
     */
    @Column(name = "own_answer")
    private int ownAnswer;
}
