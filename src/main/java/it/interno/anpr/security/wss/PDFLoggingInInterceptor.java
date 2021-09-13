package it.interno.anpr.security.wss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.message.Message;

import it.interno.anpr.util.Utils;

public class PDFLoggingInInterceptor extends LoggingInInterceptor {

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.apache.cxf.interceptor.Interceptor#handleMessage(org.apache.cxf.message.Message)
	 */
	@Override
	public void handleMessage(Message message) throws Fault {
		try {
			System.out.println("Esecuzione PDF - InInterceptor...");
			String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);
			String encoding = (String) message.get(Message.ENCODING);
			String ct = (String) message.get(Message.CONTENT_TYPE);
			final LoggingMessage buffer = new LoggingMessage("", id);
			InputStream is = message.getContent(InputStream.class);
			if (is != null) {
				logInputStream(message, is, buffer, encoding, ct);
				PrintWriter writer1 = null;
				writer1 = new PrintWriter(new File("file.pdf"));
				writer1.write(buffer.getPayload().toString());
				writer1.flush();
				writer1.close();

				// FileOutputStream os = new FileOutputStream("file.pdf");
				// os.write(buffer.getPayload().toString().getBytes());
				// os.flush();
				// os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new Fault(e);
		}

		/*
		 * try { SOAPMessage soapMsg = message.getContent(SOAPMessage.class); if
		 * (soapMsg == null) { } else { FileOutputStream os = new FileOutputStream
		 * ("message_in.xml"); soapMsg.writeTo(os); os.flush(); os.close ( ); } }
		 */
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}