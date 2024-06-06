package spesesanitarie.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import spesesanitarie.model.Formatters;
import spesesanitarie.model.DocumentoDiSpesa;
import spesesanitarie.model.Tipologia;
import spesesanitarie.model.VoceDiSpesa;

	/*
		03/01/2022;Farmacia;€ 25,71
		FC;€ 4,98
		FC;€ 8,04
		FC;€ 4,41
		FC;€ 8,28
		18/01/2022;Farmacia;€ 16,65
		FC;€ 16,65
		24/01/2022;Farmacia;€ 3,00
		TK;€ 3,00
		25/01/2022;Dentista;€ 300,00
		LP;€ 300,00
		...
	*/

public class MySpeseReader implements SpeseReader {

	@Override
	public List<DocumentoDiSpesa> leggiSpese(Reader rdr) throws IOException {
		//
		// ***** DA IMPLEMENTARE *****
		// Si suggerisce di delegare a un apposito metodo privato leggiVoci la lettura delle voci di spese
		//
		List<DocumentoDiSpesa> result = new ArrayList<>();
		BufferedReader reader = new BufferedReader(rdr);
		String riga;
		while((riga = reader.readLine()) != null) {
			String []items = riga.split(";");
			LocalDate date;
			try {
				date = LocalDate.parse(items[0].trim(), Formatters.itDateFormatter);
			} catch (Exception e) {
				throw new BadFileFormatException("errore parse date");
			}
			
			String emittente = items[1].trim();
			
			int n;
			try {
				n = Integer.parseInt(items[2].trim());
			} catch (Exception e) {
				throw new BadFileFormatException("errore parse n");
			}
			if(n<0)throw new BadFileFormatException("errore parse n");
			
			double importo;
			try {
				importo = Formatters.itPriceFormatter.parse(items[3].trim()).doubleValue();
				//importo = Double.parseDouble(items[3].replace("€", "").trim().replace(",", ".").trim());
			} catch (Exception e) {
				throw new BadFileFormatException("errore parse importo");
			}
			
			List<VoceDiSpesa> lista = leggiVoci(n,reader);
			DocumentoDiSpesa documento = new DocumentoDiSpesa(date,emittente,importo,lista);
			result.add(documento);
		}
		return result;
	}


	// Metodo privato ausiliario
	//
	private List<VoceDiSpesa> leggiVoci(int nItems, BufferedReader reader) throws IOException {
		//
		// ***** DA IMPLEMENTARE *****
		//
		List<VoceDiSpesa> list = new ArrayList<>();
		int count = 0;
		while(count < nItems) {
			String riga = reader.readLine();
			if(riga==null)throw new BadFileFormatException("");
			String [] items = riga.split(";");
			Tipologia tipologia;
			try {
				tipologia = Tipologia.valueOf(items[0].toUpperCase().trim());
			} catch (Exception e) {
				throw new BadFileFormatException("errore tipologia");
			}
			Double importo;
			try {
				importo = Formatters.itPriceFormatter.parse(items[1].trim()).doubleValue();
				//importo = Double.parseDouble(items[1].replace("€", "").trim().replace(",", ".").trim());
			} catch (Exception e) {
				throw new BadFileFormatException("errore importo"+ items[1].replace("€", "").trim().replace(",", ".").trim()+"--");
			}
			VoceDiSpesa voce = new VoceDiSpesa(tipologia,importo);
			list.add(voce);
			count++;
		}
		return list;
	}

}