package com.botcamp.dscatalog.service;

import com.botcamp.dscatalog.dto.CategoryDTO;
import com.botcamp.dscatalog.entities.Category;
import com.botcamp.dscatalog.repositories.CategoryRepository;
import com.botcamp.dscatalog.service.exceptions.DatabaseException;
import com.botcamp.dscatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO findById(Long id) {
        return new CategoryDTO(findCategoryById(id));
    }

    public Category findCategoryById(Long id){
         Category category = categoryRepository.findById(id).
                 orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
         return category;
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

    @Transactional
    public void deleteById(Long id) {
        try{
            Category category = findCategoryById(id);
            categoryRepository.delete(category);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }
}
