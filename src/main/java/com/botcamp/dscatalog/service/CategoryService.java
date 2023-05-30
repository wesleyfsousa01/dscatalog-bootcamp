package com.botcamp.dscatalog.service;

import com.botcamp.dscatalog.dto.CategoryDTO;
import com.botcamp.dscatalog.entities.Category;
import com.botcamp.dscatalog.repositories.CategoryRepository;
import com.botcamp.dscatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return new CategoryDTO((categoryRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Objeto não econtrado"))));
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> lista = categoryRepository.findAll();
        return lista.stream().map(x -> new CategoryDTO(x)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO save(CategoryDTO dto) {
        Category obj = new Category();
        obj.setName(dto.getName());
        return new CategoryDTO(categoryRepository.save(obj));
    }

    @Transactional(readOnly = true)
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            //Instancia um novo objeto, porém não irá acessar o banco de dados
            Category obj = categoryRepository.getReferenceById(id);
            obj.setName(dto.getName());
            return new CategoryDTO(categoryRepository.save(obj));
        } catch (EntityNotFoundException e) {
            throw  new ResourceNotFoundException("Id not found" + id);
        }
    }
}
