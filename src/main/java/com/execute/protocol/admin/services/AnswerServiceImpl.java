package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.dto.AnswerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;


    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void saveAll(List<Answer> answers) {
        answerRepository.saveAll(answers);
    }

    public void remove(List<Integer> answers) {
        answerRepository.deleteAllById(answers);
    }

    /**
     * Удаляет записи answers исходя из разницы между answersDto и answers
     * @param answersDto
     * @param answers
     */

    public void deleteAnswersInDtoDifferent(List<AnswerDto> answersDto, List<Answer> answers) {
        List<Integer> dtoIds = answersDto.stream().mapToInt(w -> w.getId()).boxed().toList();
        List<Integer> Ids = answers.stream().filter(w -> !dtoIds.contains(w.getId()))
                .mapToInt(w -> w.getId()).boxed().toList();

        answerRepository.deleteAllById(Ids);
    }

    public List<Answer> getAnswers(int eventId) {
        return answerRepository.findByEventId(eventId);
    }
}
