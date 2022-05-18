package com.example.news.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String short_description;
    private String long_description;

    @JoinColumn(name = "type_id")
    @ManyToOne
    private Type type;

    public News(String name, String short_description, String long_description, Type type) {
        this.name = name;
        this.short_description = short_description;
        this.long_description = long_description;
        this.type = type;
    }



}
