package com.mysite.sbb2.comment;

import com.mysite.sbb2.answer.Answer;
import com.mysite.sbb2.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByAnswer(Answer answer, Pageable pageable);
}
