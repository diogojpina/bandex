package br.usp.ime.bandex;

import java.util.ArrayList;
import java.util.List;

public class Restaurante {
	private int id;
	private String name;
	private String address;
	private String tel;
	private List<Menu> menus;
	
	public Restaurante() {
		this.menus = new ArrayList<Menu>();
	}
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}


	public void addMenu(Menu menu) {
		this.menus.add(menu);		
	}	
	
	public List<Menu> getMenuList() {
		return this.menus;
	}
}
