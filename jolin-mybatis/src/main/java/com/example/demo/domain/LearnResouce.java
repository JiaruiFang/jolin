package com.example.demo.domain;

public class LearnResouce {

	private Long id;
	private String author;
    private String title;
    private String url;
	public LearnResouce() {
		super();
	}
	public LearnResouce(Long id, String author, String title, String url) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.url = url;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
