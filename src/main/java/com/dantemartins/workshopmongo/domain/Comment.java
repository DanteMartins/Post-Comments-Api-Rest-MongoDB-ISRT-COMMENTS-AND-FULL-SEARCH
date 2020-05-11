package com.dantemartins.workshopmongo.domain;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="comment")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String    id;
	private String  text;
	private Date    date;
		
	public Comment() {
	}

	public Comment(String id,String text, Date date) {
		super();
		this.id   = id;
		this.text = text;
		this.date = date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}