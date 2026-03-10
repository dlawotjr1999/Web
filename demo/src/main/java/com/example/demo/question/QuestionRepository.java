package com.example.demo.question;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // 인터페이스명은 참조 객체의 필드명과 동일해야 함
    // 즉 findBySubjectAndContent를 사용하려면 Question 클래스에 subject와 content 필드가 있어야 함
    // 예를 들어, subject와 content 필드가 있을 때, findBySubjectAndContent는 가능하지만 findBySubjectsAndContents는 불가능
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
}
