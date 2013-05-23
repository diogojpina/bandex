package br.usp.ime.bandex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RestauranteDatabaseHandler extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 3;
	 
    private static final String DATABASE_NAME = "retauranteRead";	
    
    private static final String TABLE_RESTAURANTE = "restaurante";
    private static final String TABLE_MENU = "menu";
    
    
    
    private static final String KEY_ID = "id";
    private static final String RESTAURANTE_NAME = "name";
    private static final String RESTAURANTE_ADDRESS = "address";
    private static final String RESTAURANTE_TEL = "tel";
    
    private static final String MENU_RESTAURANTE_ID = "restaurante_id";
    private static final String MENU_DAY = "day";
    private static final String MENU_KCAL = "kcal";
    private static final String MENU_PERIODO = "periodo";
    private static final String MENU_OPTIONS = "options";
    
    
	
	public RestauranteDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String strsql = "CREATE TABLE " + TABLE_RESTAURANTE + "(" + KEY_ID
                + " INTEGER PRIMARY KEY,"  + RESTAURANTE_NAME + " TEXT, "
				+ RESTAURANTE_ADDRESS + " TEXT," + RESTAURANTE_TEL + " TEXT)";
		db.execSQL(strsql);
		
		strsql = "CREATE TABLE " + TABLE_MENU + "(" + KEY_ID + " INTEGER PRIMARY KEY,"  
				+ MENU_RESTAURANTE_ID + " INT, " + MENU_DAY + " TEXT," + MENU_KCAL + " INT, "
				+ MENU_PERIODO + " INT," + MENU_OPTIONS + " TEXT)";		
		db.execSQL(strsql);
	}
	
	@Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);     
        onCreate(db);		
	}
	
	public long addRestaurante(Restaurante restaurante) {
		SQLiteDatabase db;
		
		ContentValues values = new ContentValues();
		values.put(RESTAURANTE_NAME, restaurante.getName());
		values.put(RESTAURANTE_ADDRESS, restaurante.getAddress());
		values.put(RESTAURANTE_TEL, restaurante.getTel());		
		
		long id;
		if (this.getRestaurante(restaurante.getId()) == null) {
			System.out.println("Insere");
			db = this.getWritableDatabase();
			values.put(KEY_ID, restaurante.getId());
			id = db.insert(TABLE_RESTAURANTE, null, values);
		}
		else {
			System.out.println("Atualiza");
			db = this.getWritableDatabase();
			int num = db.update(TABLE_RESTAURANTE, values, KEY_ID + "=?", new String[] {Integer.toString(restaurante.getId())});
			
			if (num == 0) {
				System.out.println("erro");
			}
			
			id = restaurante.getId();
		}
		
		
		for (int i=0; i < restaurante.getMenuList().size(); i++) {
    		this.addMenu(restaurante.getMenuList().get(i)); 
		}
		
		
		db.close();

		return id;
	}
	
	private long addMenu(br.usp.ime.bandex.Menu menu) {
		SQLiteDatabase db;
		
		ContentValues values = new ContentValues();
		values.put(MENU_DAY, menu.getDay().toString());
		values.put(MENU_KCAL, menu.getKcal());
		values.put(MENU_PERIODO, menu.getPeriodo());
		values.put(MENU_OPTIONS, menu.getOptions());
		
		
		
		long id = 0;
		if (this.getMenu(menu.getId()) == null) {
			System.out.println("Atualiza");
			db = this.getWritableDatabase();
			values.put(KEY_ID, menu.getId());
			id = db.insert(TABLE_MENU, null, values);
		}
		else {
			System.out.println("Insere");
			db = this.getWritableDatabase();
			
			int num = db.update(TABLE_MENU, values, KEY_ID + "=?", new String[] {Integer.toString(menu.getId())});
			
			if (num == 0) {
				System.out.println("erro");
			}			
		}
		
		
		
		return id;		
	}
	
	public Restaurante getRestaurante(int id) {
		SQLiteDatabase db = this.getReadableDatabase();		
		
		Cursor cursor = db.query(TABLE_RESTAURANTE, new String[] {KEY_ID, RESTAURANTE_NAME, RESTAURANTE_ADDRESS, RESTAURANTE_TEL},
				 		KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		
		
		if (cursor.getCount() == 0)
			return null;
		
		cursor.moveToFirst();		
		
		Restaurante restaurante = new Restaurante();		
		restaurante.setId(cursor.getInt(0));
		restaurante.setName(cursor.getString(1));
		restaurante.setAddress(cursor.getString(2));
		restaurante.setTel(cursor.getString(3));
		
		
		db.close();
		
		return restaurante;
	}
	
	public br.usp.ime.bandex.Menu getMenu(int id) {
		SQLiteDatabase db = this.getReadableDatabase();		
		
		Cursor cursor = db.query(TABLE_MENU, new String[] {KEY_ID, MENU_RESTAURANTE_ID, MENU_DAY, MENU_KCAL, MENU_PERIODO, MENU_OPTIONS},
				 		KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
				
		if (cursor.getCount() == 0)
			return null;
		
		cursor.moveToFirst();
		
		br.usp.ime.bandex.Menu menu = new br.usp.ime.bandex.Menu();
		
		db.close();
		
		return menu;		
	}
	
	public void deleteMenuByRestauranteId(int id) {
		
	}
}
