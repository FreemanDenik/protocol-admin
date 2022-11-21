package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
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

    public Set<Answer> getAnswers(int eventId) {
        return answerRepository.findByEventId(eventId);
    }
}
