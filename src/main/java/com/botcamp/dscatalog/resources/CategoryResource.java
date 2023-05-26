package com.botcamp.dscatalog.resources;

import com.botcamp.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> listaDeCategorias = new ArrayList<>();
        listaDeCategorias.add(new Category(1l, "Books"));
        listaDeCategorias.add(new Category(1l, "Eletronics"));
        return ResponseEntity.ok().body(listaDeCategorias);
    }
}
