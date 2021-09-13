package it.sogei.ws.client;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import it.interno.anpr.activator.DispatchHandler;
import it.interno.anpr.config.ConfigHandler;
import it.interno.anpr.config.EnvironmentHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.security.ssl.ConfigSSL;
import it.interno.anpr.ws._6001certificazione.Risposta6001;

public class ConnessioneFreeTest {
	@BeforeClass
	public static void setTrustStore() throws Exception {
		System.setProperty("javax.net.ssl.trustStore", "keystore/cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	}

	@Before
	public void resetConfig() {
		ConfigHandler.reload();
	}

	// @Test
	// public void test1_ConnessioneOnFree() throws Exception {
	// ParamHandler param = new ParamHandler();
	// param.setEnvironment(EnvironmentHandler.FREE);
	// WSTypeHandler wsHandler = new WSTypeHandler();
	// wsHandler.setWSFamily(WSTypeHandler.TESTCONN);
	// param.setWsType(wsHandler);
	// param.setFileRequest("request/TestConn/testConn_FREE.req");
	// DispatchHandler dispatch = new DispatchHandler(param);
	// assert (dispatch.execute());
	// System.out.print("-------------------------------------- Fine
	// test1_ConnessioneOnFree ");
	// }

	// @Test
	// public void test4_6001OnFree() throws Exception {
	// ParamHandler param = new ParamHandler();
	// param.setEnvironment(EnvironmentHandler.FREE);
	// WSTypeHandler wsHandler = new WSTypeHandler();
	// wsHandler.setWSFamily(WSTypeHandler.WS6001);
	// param.setWsType(wsHandler);
	// param.setFileRequest("request/6001/6001_888231_000009.req");
	// DispatchHandler dispatch = new DispatchHandler(param);
	// assert(dispatch.execute());
	// }

	@Test
	public void test4_6001OnFree() throws Exception {
		String fileRequest = "request/6001/6001_888231_000009.req";
		String fileOutput = "pdf/6001_888231_000009.req";
		String fileProperties = "config/FREE_Keystore.properties";
		String requestText = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
		"<ns2:Richiesta6001 xmlns:ns2=\"http://sogei.it/ANPR/6001certificazione\">" +
			"<testataRichiesta>" +
				"<idOperazioneComune>000030</idOperazioneComune>" +
				"<codMittente>888231</codMittente>" +
				"<codDestinatario>ANPR02</codDestinatario>" +
				"<operazioneRichiesta>6001</operazioneRichiesta>" +
				"<dataOraRichiesta>2021-02-17T10:20:11.543+02:00</dataOraRichiesta>" +
				"<tipoOperazione>C</tipoOperazione>" +
				"<tipoInvio>FREE</tipoInvio>" +
				"<dataDefinizionePratica>2021-04-14</dataDefinizionePratica>" +
			"</testataRichiesta>" +
			"<listaTipiCertificati>" +
				"<tipoCertificato>1</tipoCertificato>" +
			"</listaTipiCertificati>" +
			"<soggettoIntestatario>" +
				"<generalita>" +
					"<cognome>PITZALIS</cognome>" +
					"<nome>ROBERTA</nome>" +
				"</generalita>" +
			"</soggettoIntestatario>" +
			"<datiControllo>" +
				"<cognomeUffAnagrafe>PIPPO</cognomeUffAnagrafe>" +
				"<nomeUffAnagrafe>PAPERINO</nomeUffAnagrafe>" +
				"<tipoRichiedente>2</tipoRichiedente>" +
				"<tipoRichiesta>1</tipoRichiesta>" +
				"<paEstera>0</paEstera>" +
				"<emissione>2</emissione>" +
				"<bollo>99</bollo>" +
				"<bolloVirtuale>0</bolloVirtuale>" +
				"<diritti>1</diritti>" +
			"</datiControllo>" +
		"</ns2:Richiesta6001>";
		String propertiesText = "org.apache.ws.security.crypto.merlin.keystore.file=keystore/FREE/888231-CO-0000.p12\n"+
		"org.apache.ws.security.crypto.merlin.keystore.password=E0F8606F\n"+
		"org.apache.ws.security.crypto.merlin.keystore.type=pkcs12\n"+
		"org.apache.ws.security.crypto.merlin.keystore.alias=888231-CO-0000\n"+
		"ID_OPERATORE=USERSFREETEST231\n"+
		"ID_SEDE=888231\n"+
		"ID_POSTAZIONE=888231-CO-0000\n"+
		"ID_APPLICAZIONE=12345\n"+
		"ID_TRANSAZIONE=99999\n"+
		"PATH_KEYSTORE_POSTAZIONE=keystore/FREE/888231-CO-0000.p12\n"+
		"PASS_KEYSTORE_POSTAZIONE=E0F8606F\n"+
		"ALIAS_KEYSTORE_POSTAZIONE=888231-CO-0000\n"+
		"URL_WS=https://wsfree.anpr.interno.it";
		ParamHandler param = new ParamHandler();
		param.setEnvironment(EnvironmentHandler.FREE);
		WSTypeHandler wsHandler = new WSTypeHandler();
		wsHandler.setWSFamily(WSTypeHandler.WS6001);
		param.setWsType(wsHandler);
		// param.setFileProperties(fileProperties);
		param.setPropertiesKeyStoreText(propertiesText);
		// param.setFileRequest(fileRequest);
		param.setRequestText(requestText);
		param.setResponseSavedFile(fileOutput + ".pdf");
		DispatchHandler dispatch = new DispatchHandler(param);
		assert (dispatch.execute());

		// Risposta6001 resp = dispatch.getResponse();
		// System.out.print("Fine test4_6001OnFree: " + resp);
	}
}
