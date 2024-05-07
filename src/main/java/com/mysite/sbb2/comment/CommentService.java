package com.mysite.sbb2.comment;

import com.mysite.sbb2.DataNotFormatException;
import com.mysite.sbb2.answer.Answer;
import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Question question, Answer answer, String content, SiteUser siteUser) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setQuestion(question);
        comment.setAnswer(answer);
        comment.setAuthor(siteUser);
        this.commentRepository.save(comment);
        return comment;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFormatException("comment not found");
        }
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void vote(Comment comment, SiteUser siteUser) {
        comment.getVoter().add(siteUser);
        this.commentRepository.save(comment);
    }

    public Page<Comment> getCommentPage(Answer answer, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return this.commentRepository.findAllByAnswer(answer, pageable);
    }
}
