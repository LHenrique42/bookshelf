package com.henrique.bookshelf.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.henrique.bookshelf.model.Category;
import com.henrique.bookshelf.repository.BookRepository;
import com.henrique.bookshelf.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController()
@RequestMapping(value="/api/category")
@Api(value="API REST Bookshelf")
@CrossOrigin(origins = "*")
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value = "Lista categorias")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "The request has succeeded."),
    @ApiResponse(code = 404, message = "Category not found."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }

    @ApiOperation(value = "Procura categoria")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "The category entry was created."),
    @ApiResponse(code = 400, message = "Invalid parameters."),
    @ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getById(@PathVariable(value="id") long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (!optionalCategory.isPresent()) {
                logger.warn("Category not find: id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(optionalCategory.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Cadastra nova categoria de livros")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
    @ApiResponse(code = 404, message = "Category not found."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Category> create(@Valid @RequestBody Category categoryRequest) {
        logger.info("Registering category: {}", categoryRequest.toString());
        try {
            Category category = categoryRepository.save(categoryRequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(category.getName()).toUri();
            return ResponseEntity.created(location).body(category);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Deleta uma categoria")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
    @ApiResponse(code = 400, message = "Invalid parameters."),
    @ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Category> delete(@PathVariable(value="id") long id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if (!optionalCategory.isPresent()) {
                logger.warn("Category not find: id: {}", id);
                return ResponseEntity.notFound().build();
            }
            deleteCategoryInTransaction(optionalCategory.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @Transactional
    public void deleteCategoryInTransaction(Category category) {
        bookRepository.deleteByCategoryId(category.getId());
        categoryRepository.delete(category);
    }

    @ApiOperation(value = "Atualiza um autor")
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Category> update(@PathVariable(value="id") long id, 
        @Valid @RequestBody Category categoryRequest) {

        try {
            categoryRepository.save(categoryRequest);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
