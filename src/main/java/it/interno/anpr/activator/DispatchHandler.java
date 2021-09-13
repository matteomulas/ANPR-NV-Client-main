package it.interno.anpr.activator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.security.message.Op6001Handler;
import it.interno.anpr.security.message.TestConnHandler;
import it.interno.anpr.ws.tipodato.TipoErroriAnomalia;
import it.interno.anpr.ws._6001anprservice.AnprPortType6001;
import it.interno.anpr.ws._6001anprservice.AnprService6001;
import it.interno.anpr.ws._6001certificazione.Richiesta6001;
import it.interno.anpr.ws._6001certificazione.Risposta6001;
import it.interno.anpr.ws.vocabolario6001certificazione.TipoListaErrori;
import it.interno.anpr.ws.vocabolario6001certificazione.TipoTestataRisposta;
import it.interno.anpr.ws.tipodato.TipoErroriAnomalia;

public class DispatchHandler {

	private ParamHandler param;
	private static Log LOGGER = LogFactory.getLog(ConfigHandler.class);

	public DispatchHandler(ParamHandler param) {
		this.param = param;
	}
	
	public boolean execute() {
		switch (param.getWsType().getWSFamily()) {
			case WSTypeHandler.TESTCONN:
				TestConnHandler servizioHandler = new TestConnHandler(param);
				return servizioHandler.isResponseValid(servizioHandler.sendRequest());
			case WSTypeHandler.WS6001:
				Op6001Handler servizio6001 = new Op6001Handler(param);
				return servizio6001.isResponseValid(servizio6001.sendRequest());
			default:
				LOGGER.error("Operation <"+param.getWsType().getWSFamily()+"> not managed");
				return false;
		}
	}

    public String getStringResponse() {
		String respString;
		Op6001Handler servizio6001 = new Op6001Handler(param);
		Risposta6001 rispostaEmissione = servizio6001.sendRequest();
		if (rispostaEmissione.getRisposta6001OK() != null) {
			respString = "OK{" + getStringTestataRisposta(rispostaEmissione.getRisposta6001OK().getTestataRisposta()) 
				+ getStringErroriOK(rispostaEmissione.getRisposta6001OK().getListaErrori()) + "}" ;
		}else{
			respString = "KO{" + getStringTestataRisposta(rispostaEmissione.getRisposta6001KO().getTestataRisposta())
			+ getStringErrori(rispostaEmissione.getRisposta6001KO().getListaErrori().getErrore()) + "}" ;
		}
		return respString;
	}



    private String getStringErroriOK(List<TipoListaErrori> errori){
		if (errori == null || errori.isEmpty()) {
			return ",\"Errori\":[]";
		}else{
			String s = ",\"Errori\":[";
			for(int i = 0; i< errori.size(); i++) {
				List<TipoErroriAnomalia> erroriAnomalia = errori.get(i).getErrore();
				for(int j = 0; j < erroriAnomalia.size(); j++){
					TipoErroriAnomalia element = erroriAnomalia.get(j);
					s +=  "{" 
				    + "\"CampoErroreAnomalia\":\""   + element.getCampoErroreAnomalia() + "\"," 
			    	+ "\"CodiceErroreAnomalia\":\""  + element.getCodiceErroreAnomalia() + "\","
			    	+ "\"OggettoErroreAnomalia\":\"" + element.getOggettoErroreAnomalia() + "\","
			    	+ "\"TestoErroreAnomalia\":\""   + element.getTestoErroreAnomalia() + "\","
			    	+ "\"TipoErroreAnomalia\":\""    + element.getTipoErroreAnomalia() + "\","
			    	+ "\"ValoreErroreAnomalia\":\""  + element.getValoreErroreAnomalia() + "\"}";
				}
			}
			s = s + "]";
			return s;
		}
	}
    
    private String getStringErrori(List<TipoErroriAnomalia> errori) {
		if (errori == null || errori.isEmpty()) {
			return ",\"Errori\":[]";
		}
		else {
			String s =  ",\"Errori\":[";
			for(int i=0; i < errori.size(); i++) {
				TipoErroriAnomalia element = errori.get(i);
			    // s +=  " Errore " + (i +1)+  " - CampoErroreAnomalia: " + element.getCampoErroreAnomalia() 
			    // 	+ " CodiceErroreAnomalia: " + element.getCodiceErroreAnomalia() 
			    // 	+ " OggettoErroreAnomalia: " + element.getOggettoErroreAnomalia() 
			    // 	+ " TestoErroreAnomalia: " + element.getTestoErroreAnomalia()
			    // 	+ " TipoErroreAnomalia: " + element.getTipoErroreAnomalia()
			    // 	+ " ValoreErroreAnomalia: " + element.getValoreErroreAnomalia();
				s += "{" 
				    + "\"CampoErroreAnomalia\":\""   + element.getCampoErroreAnomalia() + "\"," 
			    	+ "\"CodiceErroreAnomalia\":\""   + element.getCodiceErroreAnomalia() + "\"," 
			    	+ "\"OggettoErroreAnomalia\":\""  + element.getOggettoErroreAnomalia() + "\"," 
			    	+ "\"TestoErroreAnomalia\":\""    + element.getTestoErroreAnomalia() + "\"," 
			    	+ "\"TipoErroreAnomalia\":\""    + element.getTipoErroreAnomalia() + "\"," 
			    	+ "\"ValoreErroreAnomalia\":\""  + element.getValoreErroreAnomalia() + "\"}";
			}
			s = s + "]";
			return s;
		}
	}

	
	private String getStringTestataRisposta(TipoTestataRisposta risposta) {
		if (risposta == null) {
			return "(risposta nulla)";
		}
		else {
		// return "Cod.Destinatario: " + risposta.getCodDestinatario() 
		// + " Cod.Mittente: " + risposta.getCodMittente()
		// + " EsitoOperazione: " + risposta.getEsitoOperazione()
		// + " IdOperazioneANPR: " + risposta.getIdOperazioneANPR()
		// + " IdOperazioneComune: " + risposta.getIdOperazioneComune()
		// + " OperazioneRichiesta: " + risposta.getOperazioneRichiesta()
		// + " ProtocolloComune: " + risposta.getProtocolloComune()
		// + " DataDecorrenza: " + risposta.getDataDecorrenza()
		// + " DataOraRichiesta: " + risposta.getDataOraRichiesta()
		// + " DataProtocolloComune: " + risposta.getDataProtocolloComune();
		return   
		  "\"TestataRisposta\":{\"Cod.Destinatario\":\"" + risposta.getCodDestinatario() + "\","  
		+ "\"Cod.Mittente\":\""         + risposta.getCodMittente() + "\"," 
		+ "\"EsitoOperazione\":\""      + risposta.getEsitoOperazione() + "\"," 
		+ "\"IdOperazioneANPR\":\""     + risposta.getIdOperazioneANPR() + "\"," 
		+ "\"IdOperazioneComune\":\""   + risposta.getIdOperazioneComune() + "\"," 
		+ "\"OperazioneRichiesta\":\""  + risposta.getOperazioneRichiesta()+ "\"," 
		+ "\"ProtocolloComune\":\""    + risposta.getProtocolloComune() + "\"," 
		+ "\"DataDecorrenza\":\""       + risposta.getDataDecorrenza() + "\"," 
		+ "\"DataOraRichiesta\":\""     + risposta.getDataOraRichiesta() + "\"," 
		+ "\"DataProtocolloComune\":\"" + risposta.getDataProtocolloComune() + "\"}";
		}
		
	} 

	
}
