/**
 * 
 */
package it.interno.anpr.testconn.gui;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import it.interno.anpr.ws._6001certificazione.Risposta6001;
import it.interno.anpr.config.ParamHandler;
import it.interno.anpr.security.message.Op6001Handler;


public class SendButtonHandler {

	public static String handle( ParamHandler param ) throws Exception {
		String result = null;
		try {
			Op6001Handler handler = new Op6001Handler( param );
			Risposta6001 risposta = handler.sendRequest();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			JAXBContext jaxbContext = JAXBContext.newInstance(Risposta6001.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal( risposta, buffer );
			result = buffer.toString();
		} catch (Exception e) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream( baos, true );
			e.printStackTrace( ps );
			ps.flush();
			result = baos.toString();
		}
		return result;
	}
	
}
