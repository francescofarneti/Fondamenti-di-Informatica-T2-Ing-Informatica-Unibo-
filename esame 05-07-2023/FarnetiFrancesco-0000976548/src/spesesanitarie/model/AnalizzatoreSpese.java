package spesesanitarie.model;

import java.util.ArrayList;
import java.util.List;



public class AnalizzatoreSpese {

	private List<DocumentoDiSpesa> listaSpese;

	public AnalizzatoreSpese(List<DocumentoDiSpesa> listaSpese) {
		if (listaSpese==null || listaSpese.isEmpty()) throw new IllegalArgumentException("la lista spese non può essere nulla né vuota");
		this.listaSpese = listaSpese;
	}

	public List<DocumentoDiSpesa> getListaSpese() {
		return listaSpese;
	}

	@Override
	public String toString() {
		return "AnalizzatoreSpese [listaSpese=" + listaSpese + "]";
	}
	
	public double somma(Tipologia t){
		// 
		// ***** DA IMPLEMENTARE ***** 
		//
		double somma = 0;
		for(DocumentoDiSpesa documento : listaSpese) {
			for(VoceDiSpesa voce : documento.getVoci()) {
				if(voce.getTipologia().equals(t)) {
					somma = somma + voce.getImporto();
				}
			}
		}
		return somma;
	}
	
	public List<DocumentoDiSpesa> filtraPer(Tipologia t){
		// 
		// ***** DA IMPLEMENTARE ***** 
		//
		List<DocumentoDiSpesa> result= new ArrayList<>();
		
		for(DocumentoDiSpesa documento : listaSpese) {
			for(VoceDiSpesa voce : documento.getVoci()) {
				if(voce.getTipologia().equals(t)) {
					result.add(documento);
					break;
				}
			}
		}
		
		return result;
	}

}
