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
import java.util.Optional;

@Controller
public class TypeController {

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping("/home/type") // вывод списка типов
    public String homeType(Model model) {
        Iterable<Type> type = typesRepository.findAll();
        model.addAttribute("type", type);
        return "type";
    }

    @PostMapping("/home/addType") // запись в бд типа
    public String homeTypeAdd(@RequestParam String nameType, @RequestParam String color, Model model){
        Type type = new Type(nameType, color, new ArrayList<>());
        typesRepository.save(type);
        return homeType(model);
    }

    @GetMapping("/home/addTypes") // переход на страничку добавления типа
    public String homeAddType(Model model) {
        return "type-add";
    }

    @GetMapping("/home/type/{id}/edit") // меню редактирования типа
    public String  typeEdit(@PathVariable(value = "id") long id, Model model){
        if (!typesRepository.existsById(id)){
            return homeType(model);
        }
        Optional<Type> type =  typesRepository.findById(id);
        ArrayList<Type> res = new ArrayList<>();
        type.ifPresent(res :: add);
        model.addAttribute("type", res);
        return "type-edit";
    }

    @PostMapping("/home/type/{id}/edit") // добавление отредактированного типа новости в бд
    public String homeTypeUpdate(@PathVariable(value = "id") long id, @RequestParam String nameType,
                                 @RequestParam String color, Model model) {
        Type type = typesRepository.findById(id).orElseThrow();
        type.setNameType(nameType);
        type.setColor(color);
        typesRepository.save(type);
        return homeType(model);
    }

    @PostMapping("/home/type/{id}/remove") // удаление
    public String homeTypeDelete(@PathVariable(value = "id") long id, Model model){
        Type type = typesRepository.findById(id).orElseThrow();
        typesRepository.delete(type);
        return homeType(model);
    }

    @GetMapping("/home/type/{id}/type_selection") // выборка новостей определенного типа
    public String homeTypeSelection(@PathVariable(value = "id") long id, Model model){
        Type type = typesRepository.findById(id).orElseThrow();
        Iterable<News> news = type.getNewsList();
        model.addAttribute("news", news);
        return "select-type";
    }

}
