package it.interno.anpr.security.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.io.OutputStream;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.ws.tipodato.TipoErroriAnomalia;
import it.interno.anpr.ws._6001anprservice.AnprPortType6001;
import it.interno.anpr.ws._6001anprservice.AnprService6001;
import it.interno.anpr.ws._6001certificazione.Richiesta6001;
import it.interno.anpr.ws._6001certificazione.Risposta6001;
import it.interno.anpr.ws.vocabolario6001certificazione.TipoTestataRisposta;

import org.apache.cxf.helpers.IOUtils;

/**
 * Gestore per l'invocazione del servizio Test Connessione
 * 
 * @author gferraro
 *
 */
public class Op6001Handler extends RequestHandler implements IMessageHandler<Risposta6001> {
	private static Log LOGGER = LogFactory.getLog(Op6001Handler.class);

	public Op6001Handler(ParamHandler param) {
		super(param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.sogei.security.message.IMessageHandler#sendRequest()
	 */
	public Risposta6001 sendRequest() {
		AnprService6001 port = new AnprService6001();
		Risposta6001 rispostaEmissione;

		try {
			AnprPortType6001 wsAnprPort = port.getAnprServicePort6001();
			setClient(wsAnprPort);
			//InputStream xmlSource = new FileInputStream(param.getFileRequest());
InputStream xmlSource = new ByteArrayInputStream(param.getRequestText().getBytes(StandardCharsets.UTF_8));
			Richiesta6001 request = (Richiesta6001) jaxbXMLToObject(xmlSource, Richiesta6001.class);

			LOGGER.info("WS6001 invoke...");
			rispostaEmissione = wsAnprPort.emissioneCertificato(request);
			LOGGER.info("WS6001 response..." + rispostaEmissione);
		} catch (Exception e) {
			LOGGER.error("Exception nell'invio richiesta", e);
			throw new RuntimeException(e);
		}
		if (rispostaEmissione.getRisposta6001OK() != null) {
			// try (FileWriter writer = new FileWriter(param.getResponseSavedFile())) {
			try {
				DataHandler dh = rispostaEmissione.getRisposta6001OK().getFile().getCertificatoPdf();
				InputStream is = dh.getInputStream();
				// LOGGER.info("param.getResponseSavedFile()..." + param.getResponseSavedFile());
				OutputStream os = new FileOutputStream(new File(param.getResponseSavedFile()));
				IOUtils.copyAndCloseInput(is, os);
				os.flush();
				os.close();
				String resp = "{" + getStringTestataRisposta(rispostaEmissione.getRisposta6001OK().getTestataRisposta()) + "}";
				//LOGGER.info("Correct response \n £ " + resp + " £");
				return rispostaEmissione;
			} catch (IOException e) {
				LOGGER.error("Exception saving response on file", e);
				throw new RuntimeException(e);
			}
		} else {
			// String s = getStringErrori(rispostaEmissione.getRisposta6001KO().getListaErrori().getErrore()) + 
			// " || Testata risposta: " + getStringTestataRisposta(rispostaEmissione.getRisposta6001KO().getTestataRisposta());
			String s = "{" + getStringTestataRisposta(rispostaEmissione.getRisposta6001KO().getTestataRisposta())
			+ getStringErrori(rispostaEmissione.getRisposta6001KO().getListaErrori().getErrore()) + "}" ;
			//LOGGER.error("Invalid response \n £ " + s + " £ ");
			return rispostaEmissione;
			//throw new RuntimeException(s);
		}

	}
	

	private String getStringErrori(List<TipoErroriAnomalia> errori) {
		if (errori == null || errori.isEmpty()) {
			return "(niente errori)";
		}
		else {
			String s = "";
			for(int i=0; i < errori.size(); i++) {
				TipoErroriAnomalia element = errori.get(i);
			    s +=  " Errore " + (i +1)+  " - CampoErroreAnomalia: " + element.getCampoErroreAnomalia() 
			    	+ " CodiceErroreAnomalia: " + element.getCodiceErroreAnomalia() 
			    	+ " OggettoErroreAnomalia: " + element.getOggettoErroreAnomalia() 
			    	+ " TestoErroreAnomalia: " + element.getTestoErroreAnomalia()
			    	+ " TipoErroreAnomalia: " + element.getTipoErroreAnomalia()
			    	+ " ValoreErroreAnomalia: " + element.getValoreErroreAnomalia();
			}
			return s;
		}
	}

	
	private String getStringTestataRisposta(TipoTestataRisposta risposta) {
		if (risposta == null) {
			return "(risposta nulla)";
		}
		else {
		return "Cod.Destinatario: " + risposta.getCodDestinatario() 
		+ " Cod.Mittente: " + risposta.getCodMittente()
		+ " EsitoOperazione: " + risposta.getEsitoOperazione()
		+ " IdOperazioneANPR: " + risposta.getIdOperazioneANPR()
		+ " IdOperazioneComune: " + risposta.getIdOperazioneComune()
		+ " OperazioneRichiesta: " + risposta.getOperazioneRichiesta()
		+ " ProtocolloComune: " + risposta.getProtocolloComune()
		+ " DataDecorrenza: " + risposta.getDataDecorrenza()
		+ " DataOraRichiesta: " + risposta.getDataOraRichiesta()
		+ " DataProtocolloComune: " + risposta.getDataProtocolloComune();
		}
		
	}

	public boolean isResponseValid(Risposta6001 resp) {
		if ((resp.getRisposta6001OK() != null) || (resp.getRisposta6001KO() != null))
			return true;
		else
			return false;
	}
}
