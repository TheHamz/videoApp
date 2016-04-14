package com.example.videoapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity {
	SQLiteDatabase dbx;
	DatabaseHandler db = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		postaviKartice();
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// UPISIVANJE MEMBERA
	public void upisiMembera(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		EditText edt1 = (EditText) findViewById(R.id.editText1);
		EditText edt2 = (EditText) findViewById(R.id.editText3);
		if (edt1.getText().toString().trim().equals("") && edt2.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Unesite ime i prezime!", Toast.LENGTH_LONG).show();
		} else {
			db.addMember(new Member(edt1.getText().toString(), edt2.getText().toString()));
			edt1.setText("");
			edt2.setText("");
			Toast.makeText(getApplicationContext(), "Upisan je novi èlan!", Toast.LENGTH_LONG).show();
		}
	}

	// DOHVAÆANJE ODREÐENOG MEMBERA
	public void oneMember(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		TextView txt = (TextView) findViewById(R.id.textView67);
		EditText edt34 = (EditText) findViewById(R.id.editText32);
		if (edt34.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Unesite broj èlana!", Toast.LENGTH_LONG).show();
		} else {
			String s = edt34.getText().toString();
			int l = Integer.parseInt(s);
			Member member = db.getSingleMember(l);
			String korisnik = "Ime: " + member.get_name() + "\nAdresa: " + member.get_adress();
			txt.setText(korisnik);
		}
	}

	// DELETE MEMBER
	public void delMember(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		EditText txt = (EditText) findViewById(R.id.editText149);
		if (txt.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Unesite broj èlana!", Toast.LENGTH_LONG).show();
		} else {
			String memNum = txt.getText().toString();
			int xyz = Integer.parseInt(memNum);
			db.deleteMember(xyz);
			Toast.makeText(getApplicationContext(), "Obrisan je member: " + xyz + "!", Toast.LENGTH_LONG).show();
			txt.setText("");
		}
	}

	// BROJ MEMBERA
	public void memberCount(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		long tzy = db.getMemberCount();
		Toast.makeText(getApplicationContext(), String.valueOf(tzy), Toast.LENGTH_LONG).show();

	}

	// ISPIS SVIH MEMBERA U LISTU I ON CLICK
	public void allMember(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		ArrayList<String> list = new ArrayList<String>();
		final ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setTitle("Èlan");
				adb.setMessage("" + lv.getItemAtPosition(position));
				adb.setPositiveButton("Ok", null);
				adb.show();
			}
		});

		List<Member> kontakti = new ArrayList<Member>();

		kontakti = db.getAllMembers();

		if (kontakti != null) {
			for (Member k : kontakti) {
				list.add("Broj: " + k.get_id() + "\n" + "Ime: " + k.get_name() + "\n" + "Adresa: " + k.get_adress());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

			lv.setAdapter(arrayAdapter);
		} else {
			Toast.makeText(getApplicationContext(), "Nema pronadjenih èlanova", Toast.LENGTH_LONG).show();
		}
	}

	// UPISIVANJE RENTAL-a
	public void upisiRental(View view) {
		DatabaseHandler db = new DatabaseHandler(this);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		String datumerino = dateFormat.format(date);

		EditText edt14 = (EditText) findViewById(R.id.editText12rental);
		EditText edt26 = (EditText) findViewById(R.id.editText13rental);
		if (edt14.getText().toString().trim().equals("") && edt26.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Unesite posudbu!", Toast.LENGTH_LONG).show();
		} else {
			db.addRental(new Rental(Integer.parseInt(edt14.getText().toString()), Integer.parseInt(edt26.getText().toString()), datumerino));
			edt14.setText("");
			edt26.setText("");
			Toast.makeText(getApplicationContext(), "Upisan je novi RENTAL!", Toast.LENGTH_LONG).show();
		}
	}

	// ISPIS SVIH RENTAL-a U LISTU I ON CLICK
	public void allRentals(View view) {
		EditText edtt = (EditText) findViewById(R.id.editText1rental);
		final DatabaseHandler db = new DatabaseHandler(this);
		ArrayList<String> list = new ArrayList<String>();
		if (edtt.getText().toString().equals("")) {
			Toast.makeText(getApplicationContext(), "Niste unijeli broj èlana!", Toast.LENGTH_LONG).show();
		} else {
			final ListView lv = (ListView) findViewById(R.id.listView1);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
					adb.setTitle("Rental:");

					String xyz = lv.getItemAtPosition(position).toString();
					final String zg = patternMatcher(xyz); // movie
					final String zgx = patternMatch(xyz); // member

					int memberinjo = Integer.parseInt(zgx);
					Member member = db.getSingleMember(memberinjo);
					String pearlJam = member.get_name();

					int moveirno = Integer.parseInt(zg);
					Movie movie = db.getSingleMovie(moveirno);
					String eddieVedder = movie.getIme();

					adb.setMessage("Èlan: " + pearlJam + "\nFilm: " + eddieVedder);

					Toast.makeText(getApplicationContext(), pearlJam + "--" + eddieVedder, Toast.LENGTH_LONG).show();
					adb.setNegativeButton("BRIŠI POSUDBU!", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							db.deleteRental(Integer.parseInt(zgx), Integer.parseInt(zg));
							Toast.makeText(getApplicationContext(), "OBRISANO", Toast.LENGTH_LONG).show();
						}
					});
					adb.setPositiveButton("OK", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

						}
					});
					adb.show();
				}
			});

			List<Rental> kontakti = new ArrayList<Rental>();
			EditText edtr = (EditText) findViewById(R.id.editText1rental);
			int memID = Integer.parseInt(edtr.getText().toString());
			kontakti = db.getAllRentals(memID);

			if (kontakti != null) {
				for (Rental k : kontakti) {
					list.add("Broj: " + k.getMember_id() + "\n" + "Posuðeni film: " + k.getMovie_id() + "\n" + "Datum: " + k.getDatum());
				}
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

				lv.setAdapter(arrayAdapter);
			}
		}
	}

	// UPISIVANJE FILMA
	public void upisiFilm(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		EditText etrd = (EditText) findViewById(R.id.addMovieTxt);
		if (etrd.getText().toString().trim().equals("")) {
			Toast.makeText(getApplicationContext(), "Unesite film", Toast.LENGTH_LONG).show();
		} else {
			db.addMovie(new Movie(etrd.getText().toString()));
			etrd.setText("");
			Toast.makeText(getApplicationContext(), "USPJEŠNO", Toast.LENGTH_LONG).show();

		}
	}

	// ISPIS SVIH FILMOVA U LISTU I ON CLICK
	public void allMovies(View view) {
		DatabaseHandler db = new DatabaseHandler(this);
		ArrayList<String> list = new ArrayList<String>();
		final ListView lv = (ListView) findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
				adb.setTitle("Filmovi:");
				adb.setMessage(""+lv.getItemAtPosition(position));
				adb.setPositiveButton("Ok", null);
				adb.show();
			}
		});

		List<Movie> film = new ArrayList<Movie>();
		film = db.getAllMovies();

		if (film != null) {
			for (Movie k : film) {
				list.add("Broj: " + k.getIdMovie() + "\n" + "Naziv filma: " + k.getIme());
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

			lv.setAdapter(arrayAdapter);
		}
	}

	// POSTAVLJANJE KARTICA I TABOVA //Zamijeniti sa swiperinjo
	private void postaviKartice() {
		ActionBar at = getActionBar();
		at.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		at.setDisplayShowTitleEnabled(false);
		at.setDisplayShowHomeEnabled(false);
		Tab kartica = at.newTab().setText("Upis // Ispis").setTabListener(new TabListener<Fragment1>(this, "frag1", Fragment1.class));
		at.addTab(kartica);
		kartica = at.newTab().setText("Posuðivanje // Vraæanje").setTabListener(new TabListener<Fragment2>(this, "frag2", Fragment2.class));
		at.addTab(kartica);
		kartica = at.newTab().setText("Baza filmova").setTabListener(new TabListener<Fragment3>(this, "frag3", Fragment3.class));
		at.addTab(kartica);

	}

	public String patternMatcher(String xyz) {
		String foundNumber = "";
		Pattern pattern = Pattern.compile("(?<=Posuðeni film: )([0-9]+)");
		Matcher matcher = pattern.matcher(xyz);
		if (matcher.find()) {
			foundNumber = matcher.group();
		}
		return foundNumber;
	}

	public String patternMatch(String zxy) {
		String foundNumberTwo = "";
		Pattern pattern = Pattern.compile("(?<=Broj: )([0-9]+)");
		Matcher matcher = pattern.matcher(zxy);
		if (matcher.find()) {
			foundNumberTwo = matcher.group();
		}
		return foundNumberTwo;
	}

	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment fragment;
		private final Activity aktivnost;
		private final String oznaka;
		private final Class<T> klasa;

		public TabListener(Activity aktivnost, String oznaka, Class<T> klasa) {
			this.aktivnost = aktivnost;
			this.oznaka = oznaka;
			this.klasa = klasa;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (fragment == null) {
				fragment = Fragment.instantiate(aktivnost, klasa.getName());
				ft.add(android.R.id.content, fragment);
			} else {
				ft.attach(fragment);
			}
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (fragment != null) {
				ft.detach(fragment);
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}

}