package com.knowu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "library_books")
public class LibraryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String author;
    private String category;
    private Integer totalCopies;
    private Integer availableCopies;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;

    public enum BookStatus { AVAILABLE, BORROWED, LOST }

    public LibraryBook() {}

    public Long getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public Integer getTotalCopies() { return totalCopies; }
    public Integer getAvailableCopies() { return availableCopies; }
    public BookStatus getStatus() { return status; }

    public void setIsbn(String v) { this.isbn = v; }
    public void setTitle(String v) { this.title = v; }
    public void setAuthor(String v) { this.author = v; }
    public void setCategory(String v) { this.category = v; }
    public void setTotalCopies(Integer v) { this.totalCopies = v; }
    public void setAvailableCopies(Integer v) { this.availableCopies = v; }
    public void setStatus(BookStatus v) { this.status = v; }
}
