package com.mysite.sbb2.answer;

import com.mysite.sbb2.DataNotFormatException;
import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFormatException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }

    public Page<Answer> getAnswerPage(Question question, int page, String sort) {
        if (sort.equals("createDate")) {
            List<Sort.Order> sorts = new ArrayList<>();
            sorts.add(Sort.Order.desc("createDate"));
            Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
            return this.answerRepository.findAllByQuestion(question, pageable);
        } else {
            Pageable pageable = PageRequest.of(page, 10);
            if (sort.equals("voter"))
                return this.answerRepository.findByQuestionSortedByVoterSize(question, pageable);

            return this.answerRepository.findAllByQuestion(question, pageable);
        }

    }
}
