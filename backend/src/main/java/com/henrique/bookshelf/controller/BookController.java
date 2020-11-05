package com.henrique.bookshelf.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.henrique.bookshelf.model.Author;
import com.henrique.bookshelf.model.Book;
import com.henrique.bookshelf.model.Category;
import com.henrique.bookshelf.repository.AuthorRepository;
import com.henrique.bookshelf.repository.BookRepository;
import com.henrique.bookshelf.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value="/api/book")
@Api(value="API REST Bookshelf")
@CrossOrigin(origins = "*")
public class BookController {

    private static final Logger logger = LoggerFactory
        .getLogger(BookController.class);
    
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookController(CategoryRepository categoryRepository, 
        BookRepository bookRepository,
        AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @ApiOperation(value = "Lista livros")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @ApiOperation(value = "Procura livro único")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "The request has succeeded."),
			@ApiResponse(code = 404, message = "Book not found."),
			@ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getById(@PathVariable(value="id") long id) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (!optionalBook.isPresent()) {
                logger.warn("Book not find: id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(optionalBook.get());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ApiOperation(value = "Cadastra novo livro")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "The book registry was created."),
			@ApiResponse(code = 400, message = "Invalid parameters."),
			@ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
			@ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Book> create(@Valid @RequestBody Book bookRequest) {
        logger.info("Registering book: [ {} ]", bookRequest.toString());

        try {
            Author author = bookRequest.getAuthor();
            Optional<Author> optionalAuthor = authorRepository
                .findById(author.getId());
            if (!optionalAuthor.isPresent()) {
                logger.warn("Author not find: {}", author.toString());
                return ResponseEntity.badRequest().build();
            }
    
            Category category = bookRequest.getCategory();
            Optional<Category> optionalCategory = categoryRepository
                .findByName(category.getName());
            if (!optionalCategory.isPresent()) {
                logger.warn("Category not find: id: {}", category.toString());
                return ResponseEntity.badRequest().build();
            }
    
            bookRequest.setAuthor(bookRequest.getAuthor());
            bookRequest.setCategory(bookRequest.getCategory());
    
            bookRepository.save(bookRequest);
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookRequest.getId()).toUri();
                return ResponseEntity.created(location).body(bookRequest);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ApiOperation(value = "Deleta um livro")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
			@ApiResponse(code = 404, message = "Book not found."),
			@ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Book> delete(@PathVariable(value="id") long id) {

        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (!optionalBook.isPresent()) {
                logger.warn("Book not find: {}", optionalBook.toString());
                return ResponseEntity.notFound().build();
            }
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Atualiza um livro")
	@ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
			@ApiResponse(code = 400, message = "Invalid parameters."),
			@ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
			@ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> update(@PathVariable(value="id") long id, 
        @Valid @RequestBody Book bookRequest) {

        try{
            Author author = bookRequest.getAuthor();
            Optional<Author> optionalAuthor = authorRepository
                .findById(author.getId());
            if (!optionalAuthor.isPresent()) {
                logger.warn("Author not find: {}", author.toString());
                return ResponseEntity.badRequest().build();
            }
    
            Optional<Book> optionalBook = bookRepository
                .findById(bookRequest.getId());
            if (!optionalBook.isPresent()) {
                logger.warn("Book not find: {}", bookRequest.toString());
                return ResponseEntity.badRequest().build();
            }
    
            Category category = bookRequest.getCategory();
            Optional<Category> optionalCategory = categoryRepository
                .findByName(category.getName());
            if (!optionalCategory.isPresent()) {
                logger.warn("Category not find: id: {}", category.toString());
                return ResponseEntity.badRequest().build();
            }
    
            bookRequest.setAuthor(bookRequest.getAuthor());
            bookRequest.setCategory(bookRequest.getCategory());
            bookRequest.setId(optionalBook.get().getId());
            bookRepository.save(bookRequest);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
