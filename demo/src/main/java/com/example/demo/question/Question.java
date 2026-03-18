package com.example.demo.question;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.answer.Answer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
    
    // Id Annotation : 식별자 필드. 엔티티의 필드를 기본키에 mapping
    @Id
    // GeneratedValue Annotation : 데이터를 저장할 때 해당 속성에 값을 일일이 입력하지 않아도 자동으로 1씩 증가하여 저장
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Column Annotation : 엔티티의 필드를 column에 매핑
    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // OneToMany Annotation : 1:N 관계를 나타냄. 여기서는 하나의 질문에 대해 여러 개의 답변을 가질 수 있음을 의미함
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;
}