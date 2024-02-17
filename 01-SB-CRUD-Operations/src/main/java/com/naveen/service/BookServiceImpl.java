package com.naveen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.naveen.entity.Book;
import com.naveen.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	// upsert (insert / update based on Primary Key)
	@Override
	public String upsertBook(Book book) {
		bookRepository.save(book);
		return "Updated Successfully";
	}

	@Override
	public Book getById(Integer bookId) {
		Optional<Book> findById = bookRepository.findById(bookId);

		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public String deleteById(Integer bookId) {
		bookRepository.deleteById(bookId);
		return "Deleted Successfully";
	}

}
