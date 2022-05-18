package com.example.news.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nameType, color;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "type")
    private List<News> newsList;

    public Type(String nameType, String color, List<News> newsList) {
        this.nameType = nameType;
        this.color = color;
        this.newsList = newsList;
    }

    public String getNameType() {
        return nameType;
    }
}
