package com.example.videoapp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "memberDB";

	private static final String TABLE_MEMBERS = "membersTB";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_ADRESS = "adress";

	private static final String TABLE_MOVIES = "movieTB";
	private static final String KEY_ID_MOVIE = "id_movie";
	private static final String KEY_NAME_MOVIE = "name_movie";

	private static final String RENTAL_TABLE = "rentTB";
	private static final String MEMBER_ID = "memberID";
	private static final String MOVIE_ID = "movieID";
	private static final String RENTAL_DATE_ID = "datum";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_MEMBER_TABLE = "CREATE TABLE " + TABLE_MEMBERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_ADRESS + " TEXT" + ")";
		String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_MOVIES + "(" + KEY_ID_MOVIE + " INTEGER PRIMARY KEY," + KEY_NAME_MOVIE + " TEXT" + ")";
		String CREATE_RENT_TABLE = "CREATE TABLE " + RENTAL_TABLE + "(" + MEMBER_ID + " INTEGER," + MOVIE_ID + " INTEGER," + RENTAL_DATE_ID + " TEXT" + ")";

		db.execSQL(CREATE_MOVIE_TABLE);
		db.execSQL(CREATE_MEMBER_TABLE);
		db.execSQL(CREATE_RENT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
		db.execSQL("DROP TABLE IF EXISTS " + RENTAL_TABLE);
		onCreate(db);
	}

	// ADD NEW RENTAL
	public void addRental(Rental rental) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MEMBER_ID, rental.getMember_id());
		values.put(MOVIE_ID, rental.getMovie_id());
		values.put(RENTAL_DATE_ID, rental.getDatum());
		db.insert(RENTAL_TABLE, null, values);
		db.close();
	}

	// GET ALL RENTALS
	public List<Rental> getAllRentals(int memID) {
		List<Rental> rentalList = new ArrayList<Rental>();
		String selectQuery = "SELECT * FROM " + RENTAL_TABLE + " WHERE " + MEMBER_ID + "=" + memID;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Rental rental = new Rental();
				rental.setMember_id(Integer.parseInt(cursor.getString(0)));
				rental.setMovie_id(Integer.parseInt(cursor.getString(1)));
				rental.setDatum(cursor.getString(2));
				rentalList.add(rental);
			} while (cursor.moveToNext());
			return rentalList;
		} else
			return null;

	}

	// GET ALL MOVIES
	public List<Movie> getAllMovies() {
		List<Movie> movieList = new ArrayList<Movie>();
		String selectQuery = "SELECT * FROM " + TABLE_MOVIES;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Movie movie = new Movie();
				movie.setIdMovie(Integer.parseInt(cursor.getString(0)));
				movie.setIme(cursor.getString(1));
				movieList.add(movie);
			} while (cursor.moveToNext());
			return movieList;
		} else
			return null;
	}

	// GET ALL MEMBERS
	public List<Member> getAllMembers() {
		List<Member> memberList = new ArrayList<Member>();
		String selectQuery = "SELECT * FROM " + TABLE_MEMBERS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Member member = new Member();
				member.set_id(Integer.parseInt(cursor.getString(0)));
				member.set_name(cursor.getString(1));
				member.set_adress(cursor.getString(2));
				memberList.add(member);
			} while (cursor.moveToNext());
			return memberList;
		} else
			return null;
	}

	// GET SINGLE MEMBER
	public Member getSingleMember(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MEMBERS, new String[] { KEY_ID, KEY_NAME, KEY_ADRESS }, KEY_ID + "=" + id, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Member contact = new Member(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
		return contact;
	}

	// GET SINGLE MOVIE
	public Movie getSingleMovie(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_ID_MOVIE, KEY_NAME_MOVIE }, KEY_ID_MOVIE + "=" + id, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Movie movie = new Movie(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
		return movie;
	}

	// ADD NEW MEMBER
	public void addMember(Member member) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, member.get_name());
		values.put(KEY_ADRESS, member.get_adress());
		db.insert(TABLE_MEMBERS, null, values);
		db.close();
	}

	// ADD NEW MOVIE
	public void addMovie(Movie movie) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME_MOVIE, movie.getIme());
		db.insert(TABLE_MOVIES, null, values);
		db.close();
	}

	// DELETE MEMBER
	public void deleteMember(int member_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_MEMBERS, KEY_ID + " = ?", new String[] { String.valueOf(member_id) });
	}

	// DELETE RENTAL
	public void deleteRental(int slon, int konj) {
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteRental = "DELETE FROM " + RENTAL_TABLE + " WHERE " + MEMBER_ID + "=" + slon + " AND " + MOVIE_ID + "=" + konj + ";";
		db.execSQL(deleteRental);

	}

	// GET NUMBER OF MEMBERS
	public long getMemberCount() {
		return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_MEMBERS, null, null);
	}

	// GET NUMBER OF MOVIES
	public long getMovieCount() {
		return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_MOVIES, null, null);
	}

	// ADD NEW RENTAL
	public void rent(Rental rental) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, rental.getMember_id());
		values.put(KEY_ADRESS, rental.getMovie_id());
		values.put(RENTAL_DATE_ID, rental.getDatum());
		db.insert(RENTAL_TABLE, null, values);
		db.close();
	}
	
}
