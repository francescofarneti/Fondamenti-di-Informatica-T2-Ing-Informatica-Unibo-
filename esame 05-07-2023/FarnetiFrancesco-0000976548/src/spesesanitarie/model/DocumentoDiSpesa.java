package spesesanitarie.model;

import java.time.LocalDate;
import java.util.List;


public class DocumentoDiSpesa {
	private LocalDate data;
	private String emittente;
	private double importo;
	private List<VoceDiSpesa> items;
	
	public DocumentoDiSpesa(LocalDate data, String emittente, double importo, List<VoceDiSpesa> items) {
		verificaPrecondizioni(data, emittente, importo, items);
		verificaCongruenzaVoci(importo, items);
		this.data = data;
		this.emittente = emittente;
		this.importo = importo;
		this.items = items;
	}

	private void verificaPrecondizioni(LocalDate data, String emittente, double importo, List<VoceDiSpesa> items) {
		//
		// *** DA IMPLEMENTARE ***
		// Verifica che i riferimenti non siano nulli, che l'importo sia effettivamente un numero, e che la lista non sia vuota
		//
		if(data == null || emittente == null  || items == null) throw new IllegalArgumentException();
		if(Double.isNaN(importo))throw new IllegalArgumentException();
		if(importo<=0)throw new IllegalArgumentException();
		if(items.size()<1)throw new IllegalArgumentException();
	}

	private void verificaCongruenzaVoci(double importo, List<VoceDiSpesa> items) {
		//
		// *** DA IMPLEMENTARE ***
		// Verifica che il totale delle voci di spesa coincida (a meno di 1 cent) col totale del documento di spesa
		//
		Double somma = 0.0;
		for(VoceDiSpesa voce : items) {
			somma = somma + voce.getImporto();
		}
		//if(somma != importo)throw new IllegalArgumentException();
		if(Math.abs(somma-importo)>0.01)throw new IllegalArgumentException();
		//fatto così perchè nella persistenza sommando gli importi delle voci il double mi inserisce infinite cifre
	}

	public LocalDate getData() {
		return data;
	}

	public String getEmittente() {
		return emittente;
	}

	public double getImporto() {
		return importo;
	}

	public List<VoceDiSpesa> getVoci() {
		return items;
	}

	public boolean contieneVoce(Tipologia t) {
		return items.stream().anyMatch(voce -> voce.getTipologia()==t);
	}
	
	@Override
	public String toString() {
		//
		// *** DA IMPLEMENTARE ***
		// Deve emettere la stringa che sarà mostrata nella GUI nell'area di testo riassuntiva
		// E' richiesto che tale stringa sia adeguatamente formattata
		//
		String str = Formatters.itDateFormatter.format(data)+" "+emittente+" "+Formatters.itPriceFormatter.format(importo)+", di cui:\n";
		for(VoceDiSpesa voce : items) {
			str = str + voce.getTipologia().toString()+ "\t"+ Formatters.itPriceFormatter.format(voce.getImporto())+"\n";
		}
		return str;
	}
	
	
}
