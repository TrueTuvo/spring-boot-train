package com.severyn.zabara.spring.springboottrain.controllers;

import com.severyn.zabara.spring.springboottrain.models.Article;
import com.severyn.zabara.spring.springboottrain.repo.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Article article = new Article(title, anons, full_text);
        articleRepository.save(article);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Article article = articleRepository.findById(id).orElseThrow();
        article.setTitle(title);
        article.setAnons(anons);
        article.setFullText(full_text);

        articleRepository.save(article);
        return "redirect:/blog";
    }


    @GetMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Article> article = articleRepository.findById(id);
        List<Article> articles = new ArrayList<>();
        article.ifPresent(articles::add);
        for(Article ar: articles){
            ar.setViews(ar.getViews()+1);
            articleRepository.save(ar);
        }
        model.addAttribute("articles", articles);

        return "blog-edit";
    }


    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if(!articleRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Article> article = articleRepository.findById(id);
        List<Article> articles = new ArrayList<>();
        article.ifPresent(articles::add);
        for(Article ar: articles){
            ar.setViews(ar.getViews()+1);
            articleRepository.save(ar);
        }

        model.addAttribute("articles", articles);
        return "blog-details";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Article article = articleRepository.findById(id).orElseThrow();
        articleRepository.delete(article);

        return "redirect:/blog";
    }


}
