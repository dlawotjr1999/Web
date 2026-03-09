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

import com.example.demo.question.Question;
import com.example.demo.question.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class DemoApplicationTest {

    @Autowired
    private QuestionRepository questionRepository;

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
        Question q = this.questionRepository.findById(1)
            .orElseThrow(() -> new AssertionError("Question not found"));
        this.questionRepository.delete(q);
        assertEquals(1, this.questionRepository.count());
    }

}
