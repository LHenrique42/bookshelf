package com.henrique.bookshelf.controller;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.henrique.bookshelf.model.Author;
import com.henrique.bookshelf.repository.AuthorRepository;
import com.henrique.bookshelf.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping(value="/api/author")
@Api(value="API REST Bookshelf - Author Controller")
@CrossOrigin(origins = "*")
public class AuthorController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value = "Lista autores")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @ApiOperation(value = "Procura autor")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "The request has succeeded."),
    @ApiResponse(code = 404, message = "Author not found."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Author> getById(@PathVariable(value="id") long id) {
        try {
            Optional<Author> optionalAuthor = authorRepository.findById(id);
            if (!optionalAuthor.isPresent()) {
                logger.warn("Author not founded: id: {}", id);
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(optionalAuthor.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Cadastra novo autor")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "The author registry was created."),
    @ApiResponse(code = 400, message = "Invalid parameters."),
    @ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Author> create(@Valid @RequestBody Author authorRequest) {
        logger.info("Registering author: {}", authorRequest.toString());
        try {
            Author author = authorRepository.save(authorRequest);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(author.getId()).toUri();
            return ResponseEntity.created(location).body(author);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @ApiOperation(value = "Deleta um autor")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
    @ApiResponse(code = 404, message = "Author not found."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Author> delete(@PathVariable(value="id") long id) {
        try {
            Optional<Author> optionalAuthor = authorRepository.findById(id);
            if (!optionalAuthor.isPresent()) {
                logger.warn("Author not find: id: {}", id);
                return ResponseEntity.notFound().build();
            }
            deleteAuthorInTransaction(optionalAuthor.get());
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.unprocessableEntity().build();
    }

    @Transactional
    public void deleteAuthorInTransaction(Author author) {
        bookRepository.deleteByAuthorId(author.getId());
        authorRepository.delete(author);
    }

    @ApiOperation(value = "Atualiza um autor")
    @ApiResponses(value = { @ApiResponse(code = 204, message = "The request has succeeded."),
    @ApiResponse(code = 400, message = "Invalid parameters."),
    @ApiResponse(code = 403, message = "The server understood the request, but it is refusing to fulfill it."),
    @ApiResponse(code = 500, message = "The server failed to fulfill an apparently valid request.") })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Author> update(@PathVariable(value="id") long id, 
        @Valid @RequestBody Author authorRequest) {

        try {
            Optional<Author> optionalAuthor = authorRepository.findById(id);
            if (!optionalAuthor.isPresent()) {
                logger.warn("Author not founded: {}", authorRequest.toString());
                return ResponseEntity.notFound().build();
            }
            authorRequest.setId(id);
            authorRepository.save(authorRequest);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.unprocessableEntity().build();
    }
}
