package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.CategoryDto;

import java.util.List;
import java.util.Set;

public interface AnswerService {
    void saveAll(List<Answer> answers);
    void remove(List<Integer> answers);
    void deleteAnswersInDtoDifferent(List<AnswerDto> answersDto, List<Answer> answers);
    List<Answer> getAnswers(int eventId);
}
