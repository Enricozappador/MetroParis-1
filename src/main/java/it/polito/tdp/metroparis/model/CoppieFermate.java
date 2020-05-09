package it.polito.tdp.metroparis.model;

public class CoppieFermate {
	
	private Fermata fp; 
	private Fermata fa;
	public CoppieFermate(Fermata fp, Fermata fa) {
		super();
		this.fp = fp;
		this.fa = fa;
	}
	public Fermata getFp() {
		return fp;
	}
	public void setFp(Fermata fp) {
		this.fp = fp;
	}
	public Fermata getFa() {
		return fa;
	}
	public void setFa(Fermata fa) {
		this.fa = fa;
	} 

}
