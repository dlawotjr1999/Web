package com.example.demo.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// RequestMapping Annotation : URL값으로 mapping 조건 부여
@RequestMapping("/question")
// 
@RequiredArgsConstructor

@Controller
public class QuestionController {
    
    private final QuestionService questionService;
    
    // GetMapping Annotation : URL과의 mapping 담당. 서버의 리소스 조회(HTTP의 GET Method)
    @GetMapping("list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping(value = "detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuesetion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // PostMapping Annotation : 서버에 리소스 등록(HTTP의 POST Method)
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "question_form";
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }

}
