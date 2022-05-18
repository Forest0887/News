package com.example.news.controllers;

import com.example.news.entity.News;
import com.example.news.entity.Type;
import com.example.news.repos.NewsRepository;
import com.example.news.repos.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private TypesRepository typesRepository;

    @GetMapping("/home") // вывод списка новостей
    public String home(Model model) {
        Iterable<News> news = newsRepository.findAll();
        model.addAttribute("news", news);
        return "news";
    }

    @PostMapping("/home/addNews") // добавление в бд новости
    public String homeNewsAdd(@RequestParam String name, @RequestParam String short_description,
                              @RequestParam String long_description, @RequestParam Long typeId, Model model) {
        Optional<Type> type = typesRepository.findById(typeId);
        News news;
        if (type.isPresent()) {
            news = new News(name, short_description, long_description, type.get());
        } else {
            return homeAdd(model);
        }
        newsRepository.save(news);
        return "redirect:/home";
    }

    @GetMapping("/home/add") // вывод странички добавления новости
    public String homeAdd(Model model) {
        List<Type> types = typesRepository.findAll();
        model.addAttribute("types", types);
        return "news-add";
    }

    @GetMapping("/home/{id}") // полное описание новости
    public String newsDetail(@PathVariable(value = "id") Long id, Model model){
        if (!newsRepository.existsById(id)){
            return "redirect:/home";
        }
        Optional<News> news =  newsRepository.findById(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res :: add);
        model.addAttribute("news", res);
        return "news-details";
    }

    @GetMapping("/home/{id}/edit") // меню редактирования новости
    public String  newsEdit(@PathVariable(value = "id") Long id, Model model){
        if (!newsRepository.existsById(id)){
            return "redirect:/home";
        }
        Optional<News> news =  newsRepository.findById(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res :: add);
        model.addAttribute("news", res);
        List<Type> types = typesRepository.findAll();
        model.addAttribute("type", types);
        return "news-edit";
    }

    @PostMapping("/home/{id}/edit") // добавление отредактированной новости в бд
    public String homeNewsUpdate(@PathVariable(value = "id") Long id, @RequestParam String name, @RequestParam String short_description,
                              @RequestParam String long_description, @RequestParam Long typeId, Model model){
        News news = newsRepository.findById(id).orElseThrow();
        news.setName(name);
        news.setShort_description(short_description);
        news.setLong_description(long_description);
        Optional<Type> type = typesRepository.findById(typeId);
        if (type.isPresent()) {
            news.setType(type.get());
        } else {
            return homeAdd(model);
        }
        newsRepository.save(news);
        return "redirect:/home";
    }

    @PostMapping("/home/{id}/remove") // удаление
    public String homeNewsDelete(@PathVariable(value = "id") Long id, Model model){
        News news = newsRepository.findById(id).orElseThrow();
        newsRepository.delete(news);
        return "redirect:/home";
    }

}