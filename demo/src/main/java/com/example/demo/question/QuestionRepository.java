package com.example.demo.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // 인터페이스명은 참조 객체의 필드명과 동일해야 함
    // 즉 findBySubjectAndContent를 사용하려면 Question 클래스에 subject와 content 필드가 있어야 함
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
}
