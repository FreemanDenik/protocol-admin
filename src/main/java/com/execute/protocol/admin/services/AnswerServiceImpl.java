package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.dto.AnswerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     * Т.е те записи которых нет в dto
     * Этот метод надо использовать до Mapper преобразовании
     *
     * @param answersDto
     * @param answers
     */
    public void deleteAnswersInDtoDifferent(Set<AnswerDto> answersDto, Set<Answer> answers) {
//        Set<Integer> dtoIds = answersDto.stream().mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
//        Set<Integer> ids = answers.stream().filter(w -> !dtoIds.contains(w.getId()))
//                .mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
        Set<Integer> categoryRemovesId = answers
                .stream()
                .mapToInt(w -> w.getId())
                .boxed()
                .filter(w ->
                        !answersDto.stream().mapToInt(e->e.getId())
                                .boxed().collect(Collectors.toSet()).contains(w))
                        .collect(Collectors.toSet());
        answerRepository.deleteAllById(categoryRemovesId);
    }

    public Set<Answer> getAnswers(int eventId) {
        return answerRepository.findByEventId(eventId);
    }
}
