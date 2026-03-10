package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.answer.Answer;
import com.example.demo.answer.AnswerRepository;
import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class DemoApplicationTest {

    // Autowired Annotation : Spring이 대신 객체를 생성하고 주입
    // 즉 new로 객체를 생성하지 않아도 자동으로 의존성을 주입함
    // * Bean : spring 컨테이너가 생성, 의존성 주입, 생명주기를 관리하는 java 객체(Component, Controller, Service, Repository)
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void clear() {
        questionRepository.deleteAll();
    }

    // Insert Test
    @Test
    void testJPA() {
        Question q1 = new Question();
        q1.setSubject("What is SBB?");
        q1.setContent("I want to know SBB");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);

        Question q2 = new Question();
        q2.setSubject("This is SBB model Question");
        q2.setContent("Is id is generated Automatically?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  
    }

    // SELECT ALL Test
    @Test
    void testJPA2() {
        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("What is SBB?", q.getSubject());
    }

    // SELECT by id Test
    @Test
    void testJpa3() {
        Optional<Question> oq = this.questionRepository.findById(1);
        if(oq.isPresent()) {
            Question q = oq.get();
            assertEquals("What is SBB?", q.getSubject());
        }
    }

    // SELECT by conditions Test
    // findBySubject is not built-in function.
    @Test
    void testJpa4() {
        Question q = this.questionRepository.findBySubject("What is SBB?");
        assertEquals(1, q.getId());
    }

    @Test
    void testJpa5() {
        Question q = this.questionRepository.findBySubjectAndContent(
            "What is SBB?", "I want to know SBB");
        assertEquals(1, q.getId());    
    }

    // Update Test
    @Test
    void testJpa6() {
        // 방식1 : 있음을 확인한 뒤 꺼내는 방식
        Optional<Question> oq = this.questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("modified content");
        this.questionRepository.save(q);
    }

    // Delete Test
    @Test
    void testJpa7() {
        assertEquals(2, this.questionRepository.count());

        // 방식2 : 있으면 바로 꺼내고, 없으면 예외를 던져 테스트 실패
        // 1번 id에 해당하는 question을 가져오지 못할 경우 에러 발생
        Question q = this.questionRepository.findById(1)
            .orElseThrow(() -> new AssertionError("Question not found"));
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

    // Create Answer Test
    @Test
    void testJpa8() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("Yes. It is generated automatically.");
        a.setQuestion(q);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }

    // SELECT Answer Test
    @Test
    void testJpa9() {
        Optional<Answer> oa = this.answerRepository.findById(2);
        assertTrue(oa.isPresent());
        Answer a = oa.get();
        assertEquals(2, a.getQuestion().getId());
    }

    // Transactional Annotation : 메서드가 종료될 때까지 DB 세션이 끊기지 않음
    @Transactional
    @Test
    void TestJpa10() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("Yes. It is generated automatically.", answerList.get(0).getContent());
    }
}
