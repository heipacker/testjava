package test.dlmu.hibernate.model;

import java.util.Date;

public class Event {

	private String title;
	
	private Date date;
	
	public Event(String title, Date date) {
		this.title = title;
		this.date = date;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public Date getDate() {
		return date;
	}

	
}
