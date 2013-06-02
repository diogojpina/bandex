package br.usp.ime.bandex;


public class Menu {
	private String created;
	private String updated;
	private int id;
	private int kcal;
	private String date;
	private int periodo;
	private String options;
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPeriodo() {
		return periodo;
	}
	
	public String getPeriodoName() {
		if (this.periodo == 2)
			return "Almoço";
		else
			return "Jantar";
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}
	
	
}
