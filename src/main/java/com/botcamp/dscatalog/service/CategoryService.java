package com.botcamp.dscatalog.service;

import com.botcamp.dscatalog.dto.CategoryDTO;
import com.botcamp.dscatalog.entities.Category;
import com.botcamp.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> lista = categoryRepository.findAll();
        return  lista.stream().map(x -> new CategoryDTO(x)).toList();
    }
}
