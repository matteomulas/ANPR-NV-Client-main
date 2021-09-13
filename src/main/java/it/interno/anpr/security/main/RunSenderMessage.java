package it.interno.anpr.security.main;

import java.io.File;

import it.interno.anpr.activator.DispatchHandler;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.config.WSTypeHandler;
import it.interno.anpr.config.EnvironmentHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * Main per l'esecuzione del Client, viene invocato il servizio di Test
 * Connessione
 * 
 * @author gferraro
 *
 */

public class RunSenderMessage {
	private static Log LOGGER = LogFactory.getLog(RunSenderMessage.class);
    private static String Result = "";
	public static void main(String[] args) {

		// LOGGER.info("------------------------------------------------------------------------------------");

		try {
			// ConfigSSL.trustAll();

			System.setProperty("javax.net.xssl.trustStore", "keystore/cacerts");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

			ParamHandler param = new ParamHandler();

			// LOGGER.info("ARGS: " + args.length);
			// for(int i = 0; i < args.length; i++){
			// 	LOGGER.info("ARGS i>>>>: " + args[i]);
			// }

			if (args.length != 5) {
			//  	LOGGER.info("Usage: <ENV> <WS> <FILE_PROPS> <TEXT_REQ> <SAVE_RES>");
			//	LOGGER.info("<ENV> = TEST, PRE, FREE, PROD");
			//	LOGGER.info("<WS> = TESTCONN, WS1001, WS1002, ..., WS3002, ...");
            //  //LOGGER.info("<FILE_PROPS> = Path of properties file");
			//	LOGGER.info("<TEXT_PROPS> = text of properties defined as array of key=value");
            //  //LOGGER.info("<FILE_REQ> = Path of request file");
			//	LOGGER.info("<TEXT_REQ> = request in xml");
			//	LOGGER.info("<SAVE_RES> = Path of saved file contained as attached pdf in the response");
				System.exit(-5);
            //    return Result;
			} else {
				// LOGGER.info("trustStore: " + System.getProperty("javax.net.xssl.trustStore"));
				// LOGGER.info("trustStorePassword: " + System.getProperty("javax.net.xssl.trustStore"));
				if (!EnvironmentHandler.isValid(args[0])) {
					// LOGGER.info("<ENV> Invalid. Admitted values: TEST, PRE, FREE, PROD");
					 System.exit(-4);
					//return Result;
				}

				param.setEnvironment(args[0]);

				WSTypeHandler wsHandler = new WSTypeHandler();
				if (!wsHandler.isValid(args[1])) {
					// LOGGER.info("<WS> Invalid. Admitted values: TESTCONN, WS1001, WS1002, ... WS3002....");
					 System.exit(-3);
					//return Result;
				}
				wsHandler.setWSFamily(args[1]);
				param.setWsType(wsHandler);

				// File f = new File(args[2]);
				// if (!f.exists()) {
				// 	LOGGER.info("<FILE_PROPS> The file does not exist");
				// 	System.exit(-1);
				// }
				// param.setFileProperties(args[2]);
				if (args[2] == null || args[2].length() < 50) {
					// LOGGER.info("<TEXT_PROPS> can not be empty or it is too short");
					 System.exit(-2);	
					//return Result;
				}
				param.setPropertiesKeyStoreText(args[2]);

				// f = new File(args[3]);
				// if (!f.exists()) {
				// 	LOGGER.info("<FILE_REQ> The file does not exist");
				// 	System.exit(-1);
				// }
				// param.setFileRequest(args[3]);
				if (args[3] == null || args[3].length() < 1) {
					// LOGGER.info("<TEXT_REQ> can not be empty");
					 System.exit(-1);	
					//return Result;
				}

				param.setRequestText("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:Richiesta6001 xmlns:ns2=\"http://sogei.it/ANPR/6001certificazione\">" + args[3]);
				//param.setRequestText(args[3]);
				//LOGGER.info("****XML REQ>>>>>>  " + "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns2:Richiesta6001 xmlns:ns2=\"http://sogei.it/ANPR/6001certificazione\">" + args[3]);
				param.setResponseSavedFile(args[4] + ".pdf");
			}

			LOGGER.info("Invoke " + args[1]);
			DispatchHandler dispatch = new DispatchHandler(param);
			// long startTime = System.currentTimeMillis();
			//dispatch.execute();
			Result = dispatch.getStringResponse();
			LOGGER.info("**" + Result);
            //LOGGER.info("Time spent by service <" + (System.currentTimeMillis() - startTime) + "msec>");
			//LOGGER.info("Done and saved on " + args[4] + ".pdf");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-6);
			//return Result;
		}

	}

}
