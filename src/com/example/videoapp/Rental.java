package com.example.videoapp;

public class Rental {
	int member_id;
	int movie_id;
	String datum;

	public Rental() {
		super();
	}

	public Rental(int member_id, int movie_id, String datum) {
		super();
		this.member_id = member_id;
		this.movie_id = movie_id;
		this.datum = datum;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public int getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

}
