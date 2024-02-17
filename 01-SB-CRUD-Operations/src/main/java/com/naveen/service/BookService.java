package com.naveen.service;

import java.util.List;

import com.naveen.entity.Book;

public interface BookService {

	public String upsertBook(Book book); //For Insert and Update
	
	public Book getById(Integer bookId);
	
	public List<Book> getAllBooks();
	
	public String deleteById(Integer bookId);

}
