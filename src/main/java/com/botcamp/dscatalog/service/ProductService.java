package com.botcamp.dscatalog.service;

import com.botcamp.dscatalog.dto.ProductDTO;
import com.botcamp.dscatalog.entities.Product;
import com.botcamp.dscatalog.repositories.ProductRepository;
import com.botcamp.dscatalog.service.exceptions.DatabaseException;
import com.botcamp.dscatalog.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository ProductRepository;

    public ProductDTO findById(Long id) {
        return new ProductDTO(findProductById(id));
    }

    public ProductDTO findByIdWithCategories(Long id) {
        Product product = findProductById(id);
        return new ProductDTO(product, product.getCategories());
    }

    public Product findProductById(Long id){
         Product product = ProductRepository.findById(id).
                 orElseThrow(() -> new ResourceNotFoundException("Id not found " + id));
         return product;
    }
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> lista = ProductRepository.findAll(pageRequest);
        return lista.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO save(ProductDTO dto) {
        Product obj = new Product();
        //obj.setName(dto.getName());
        return new ProductDTO(ProductRepository.save(obj));
    }

    @Transactional(readOnly = true)
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            //Instancia um novo objeto, porém não irá acessar o banco de dados
            Product obj = ProductRepository.getReferenceById(id);
            //obj.setName(dto.getName());
            return new ProductDTO(ProductRepository.save(obj));
        } catch (EntityNotFoundException e) {
            throw  new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try{
            Product product = findProductById(id);
            ProductRepository.delete(product);
        }
        catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation");
        }
    }
}
