package com.example.videoapp;

public class Movie {
	int idMovie;
	String ime;

	public Movie() {
		super();
	}

	public Movie(String ime) {
		super();
		this.ime = ime;
	}

	public Movie(int idMovie, String ime) {
		super();
		this.idMovie = idMovie;
		this.ime = ime;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}
}
