package com.example.demo.answer;

import java.time.LocalDateTime;

import com.example.demo.question.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    // ManyToOne Annotation : N:1 관계를 나타냄. 여기서는 여러 개의 답변들이 하나의 질문에 대응함을 의미함
    @ManyToOne
    private Question question;
}