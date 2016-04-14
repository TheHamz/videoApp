package com.example.videoapp;

public class Member {
	int _id;
	String _name;
	String _adress;

	public Member() {

	}

	public Member(int _id, String _name, String _adress) {
		super();
		this._id = _id;
		this._name = _name;
		this._adress = _adress;
	}

	public Member(String _name, String _adress) {
		super();
		this._name = _name;
		this._adress = _adress;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_adress() {
		return _adress;
	}

	public void set_adress(String _adress) {
		this._adress = _adress;
	}

}
