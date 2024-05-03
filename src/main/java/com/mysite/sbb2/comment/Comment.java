package com.mysite.sbb2.comment;

import com.mysite.sbb2.answer.Answer;
import com.mysite.sbb2.question.Question;
import com.mysite.sbb2.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Question question;
    @ManyToOne
    private Answer answer;
    @ManyToOne
    private SiteUser author;

    private LocalDateTime createDate;

    @ManyToMany
    Set<SiteUser> voter;

    private LocalDateTime modifyDate;
}
