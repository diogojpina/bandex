package br.usp.ime.bandex;

public class Restaurante {
	private int id;
	private String name;
	private String address;
	private String tel;
	private Menu[] menu;
	
	
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

	public Menu[] getMenu() {
		return menu;
	}

	public void setMenu(Menu[] menu) {
		this.menu = menu;
	}	
}
