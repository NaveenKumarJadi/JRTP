package com.naveen.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.naveen.entity.Book;
import com.naveen.service.BookService;

@RestController
public class BookController {

	@Autowired
	private BookService bookService;

	// Insert
	@PostMapping("/book")
	public ResponseEntity<String> createBook(@RequestBody Book book) {
		String msg = bookService.upsertBook(book);
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}

	// Retrieving Particular Book By ID
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
		Book book = bookService.getById(bookId);
		return new ResponseEntity<Book>(book, HttpStatus.OK);
	}

	// Retrieving All Books
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allBooks = bookService.getAllBooks();
		return new ResponseEntity<List<Book>>(allBooks, HttpStatus.OK);
	}

	// Update
	@PutMapping("/book")
	public ResponseEntity<String> updateBook(@RequestBody Book book) {
		String msg = bookService.upsertBook(book);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	// Delete
	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable Integer bookId){
		String deletebook = bookService.deleteById(bookId);
		return new ResponseEntity<>(deletebook, HttpStatus.OK);
	}
}
