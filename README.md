# ANPR Novisercice Client Java

Il progetto è una versione modificare dell'originale progetto disponibile su https://github.com/italia/anpr-client-example

Il codice è stato modificato per renderlo una libreria in grado di poter essere utilizzata da riga di comando da altri software ed, in futuro, eventualmente essere integrata in un'applicazione RESTful interamente sviluppata in Java.

## Installazioni

- Java 1.8 (https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)

  Impostare la variabile d'ambiente `JAVA_HOME`.

  Su **mac**, aggiungere JAVA_HOME in `~/.zshenv` o `~/.zshrc` per java 1.8 con il seguente testo:  
  `export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_291.jdk/Contents/Home"`

- maven (https://maven.apache.org/download.cgi)

  Aggiungere il path di maven la variabile d'ambiente `PATH`.

  Su **mac**, in `/etc/paths` aggiungere un testo simile al seguente: `/Users/mytree/develop/tools/apache-maven-3.8.1/bin`.
  Verificare se maven funziona con `mvn -v`.

## Shortlist utili comandi Maven

| comando     | descrizione                                                                                                                                                       |
| ----------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| mvn test    | lancia i test, in questo caso quanto contenuto in `it.sogei.ws.client.ConnessioneFreeTest`, ed esegue gli stessi passaggi del main file del comando di esecuzione |
| mvn compile | compila il codice e lo mette in target/                                                                                                                           |
| mvn package | crea il jar e lo mette in target/                                                                                                                                 |
| mvn clean   | cancella target/ e tutto ciò che ciò che contiene, a volte bisogna eseguirlo più volte                                                                            |

# Run del jar: tag v0.4

- Creazione jar: `mvn package`

- La cartella da cui fare il run deve avere la sottocartella `target/`

- Assicurarsi che esistano le cartelle dove salvare i file pdf (parametro <SAVE_RES>).

- Run
  `java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar <ENV> <WS> <TEXT_PROPS> <TEXT_REQ> <SAVE_RES>`

| Parametro  | Obbligatorio | Tipo    | Descrizione                                                                                                             | Possibili valori                                                                                                                          |
| ---------- | ------------ | ------- | ----------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| ENV        | Si           | Stringa | Ambiente su cui sono eseguite le richieste di ANPR                                                                      | VAL, TEST, PRE, FREE, PROD. Vedi valori nella classe `it.interno.anpr.config.EnvironmentHandler`                                          |
| WS         | Si           | Stringa | WebService SOAP invocato                                                                                                | TESTCONN, WS1001, ..., WS2001, ..., WS3001, ..., WS4001, ..., WS5001, ... Vedi valori nella classe `it.interno.anpr.config.WSTypeHandler` |
| TEXT_PROPS | Si           | Stringa | Testo con le properties del comune sul modello del file `config/FREE_Keystore.properties`                               | Lista di key=value                                                                                                                        |
| TEXT_REQ   | Si           | Stringa | Testo xml della richiesta da inviare come contenuto nei file dentro la cartella request/                                | Testo XML                                                                                                                                 |
| SAVE_RES   | Si           | Stringa | Path con nome del file ma senza ".pdf" dove sarà salvato il certificato ottenuto in risposta. **Il path deve esistere** |                                                                                                                                           |

### Esempio di comandi con xml che genera errore nella richiesta:

```
java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar FREE WS6001 'org.apache.ws.security.crypto.merlin.keystore.file=keystore/FREE/888231-CO-0000.p12
org.apache.ws.security.crypto.merlin.keystore.password=E0F8606F
org.apache.ws.security.crypto.merlin.keystore.type=pkcs12
org.apache.ws.security.crypto.merlin.keystore.alias=888231-CO-0000
ID_OPERATORE=USERSFREETEST231
ID_SEDE=888231
ID_POSTAZIONE=888231-CO-0000
ID_APPLICAZIONE=12345
ID_TRANSAZIONE=99999
PATH_KEYSTORE_POSTAZIONE=keystore/FREE/888231-CO-0000.p12
PASS_KEYSTORE_POSTAZIONE=E0F8606F
ALIAS_KEYSTORE_POSTAZIONE=888231-CO-0000
URL_WS=https://wsfree.anpr.interno.it' '<?xml version="1.0" encoding="UTF-8"?>
<ns2:Richiesta6001 xmlns:ns2="http://sogei.it/ANPR/6001certificazione">
	<testataRichiesta>
		<idOperazioneComune>000006</idOperazioneComune>
		<codMittente>888231</codMittente>
		<codDestinatario>ANPR02</codDestinatario>
		<operazioneRichiesta>6001</operazioneRichiesta>
		<dataOraRichiesta>2021-02-17T10:20:11.543+02:00</dataOraRichiesta>
		<tipoOperazione>C</tipoOperazione>
		<tipoInvio>FREE</tipoInvio>
		<dataDefinizionePratica>2021-04-14</dataDefinizionePratica>
	</testataRichiesta>
	<listaTipiCertificati>
		<tipoCertificato>1</tipoCertificato>
	</listaTipiCertificati>
	<soggettoIntestatario>
		<generalita>
			<cognome>PITZALIS</cognome>
			<nome>ROBERTA</nome>
		</generalita>
	</soggettoIntestatario>
	<datiControllo>
		<cognomeUffAnagrafe>PIPPO</cognomeUffAnagrafe>
		<nomeUffAnagrafe>PAPERINO</nomeUffAnagrafe>
		<tipoRichiedente>2</tipoRichiedente>
		<tipoRichiesta>1</tipoRichiesta>
		<paEstera>0</paEstera>
		<emissione>2</emissione>
		<bollo>99</bollo>
		<bolloVirtuale>2</bolloVirtuale>
		<diritti>0</diritti>
	</datiControllo>
</ns2:Richiesta6001>' pdf/v4error
```

### Esempio di comandi:

```
java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar FREE WS6001 'org.apache.ws.security.crypto.merlin.keystore.file=keystore/FREE/888231-CO-0000.p12
org.apache.ws.security.crypto.merlin.keystore.password=E0F8606F
org.apache.ws.security.crypto.merlin.keystore.type=pkcs12
org.apache.ws.security.crypto.merlin.keystore.alias=888231-CO-0000
ID_OPERATORE=USERSFREETEST231
ID_SEDE=888231
ID_POSTAZIONE=888231-CO-0000
ID_APPLICAZIONE=12345
ID_TRANSAZIONE=99999
PATH_KEYSTORE_POSTAZIONE=keystore/FREE/888231-CO-0000.p12
PASS_KEYSTORE_POSTAZIONE=E0F8606F
ALIAS_KEYSTORE_POSTAZIONE=888231-CO-0000
URL_WS=https://wsfree.anpr.interno.it' '<?xml version="1.0" encoding="UTF-8"?>
<ns2:Richiesta6001 xmlns:ns2="http://sogei.it/ANPR/6001certificazione">
	<testataRichiesta>
		<idOperazioneComune>000009</idOperazioneComune>
		<codMittente>888231</codMittente>
		<codDestinatario>ANPR02</codDestinatario>
		<operazioneRichiesta>6001</operazioneRichiesta>
		<dataOraRichiesta>2021-02-17T10:20:11.543+02:00</dataOraRichiesta>
		<tipoOperazione>C</tipoOperazione>
		<tipoInvio>FREE</tipoInvio>
		<dataDefinizionePratica>2021-04-14</dataDefinizionePratica>
	</testataRichiesta>
	<listaTipiCertificati>
		<tipoCertificato>1</tipoCertificato>
	</listaTipiCertificati>
	<soggettoIntestatario>
		<generalita>
			<cognome>PITZALIS</cognome>
			<nome>ROBERTA</nome>
		</generalita>
	</soggettoIntestatario>
	<datiControllo>
		<cognomeUffAnagrafe>PIPPO</cognomeUffAnagrafe>
		<nomeUffAnagrafe>PAPERINO</nomeUffAnagrafe>
		<tipoRichiedente>2</tipoRichiedente>
		<tipoRichiesta>1</tipoRichiesta>
		<paEstera>0</paEstera>
		<emissione>2</emissione>
		<bollo>99</bollo>
		<bolloVirtuale>0</bolloVirtuale>
		<diritti>1</diritti>
	</datiControllo>
</ns2:Richiesta6001>' pdf/v4ok
```

# Run del jar: tag v0.3

- Creazione jar: `mvn package`

- La cartella da cui fare il run deve avere le sottocartelle `keystore/` e `target/`

- Assicurarsi che esistano le cartelle dove salvare i file pdf (parametro <SAVE_RES>), quella con i file di properties dei comuni (parametro <FILE_PROPS>).

- Run
  `java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar <ENV> <WS> <FILE_PROPS> <TEXT_REQ> <SAVE_RES>`

| Parametro  | Obbligatorio | Tipo    | Descrizione                                                                                                             | Possibili valori                                                                                                      |
| ---------- | ------------ | ------- | ----------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| ENV        | Si           | Stringa | Ambiente su cui sono eseguite le richieste di ANPR                                                                      | VAL, TEST, PRE, FREE, PROD. Vedi `it.interno.anpr.config.EnvironmentHandler`                                          |
| WS         | Si           | Stringa | WebService SOAP invocato                                                                                                | TESTCONN, WS1001, ..., WS2001, ..., WS3001, ..., WS4001, ..., WS5001, ... Vedi `it.interno.anpr.config.WSTypeHandler` |
| FILE_PROPS | Si           | Stringa | Path con file .properties con le property del comune.                                                                   |                                                                                                                       |
| TEXT_REQ   | Si           | Stringa | Testo della richiesta da inviare come contenuto nei file dentro la cartella request/                                    |                                                                                                                       |
| SAVE_RES   | Si           | Stringa | Path con nome del file ma senza ".pdf" dove sarà salvato il certificato ottenuto in risposta. **Il path deve esistere** |                                                                                                                       |

### Esempio di comandi con xml che genera errore nella richiesta:

```
java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar FREE WS6001 config/FREE_Keystore.properties '<?xml version="1.0" encoding="UTF-8"?>
<ns2:Richiesta6001 xmlns:ns2="http://sogei.it/ANPR/6001certificazione">
	<testataRichiesta>
		<idOperazioneComune>000006</idOperazioneComune>
		<codMittente>888231</codMittente>
		<codDestinatario>ANPR02</codDestinatario>
		<operazioneRichiesta>6001</operazioneRichiesta>
		<dataOraRichiesta>2021-02-17T10:20:11.543+02:00</dataOraRichiesta>
		<tipoOperazione>C</tipoOperazione>
		<tipoInvio>FREE</tipoInvio>
		<dataDefinizionePratica>2021-04-14</dataDefinizionePratica>
	</testataRichiesta>
	<listaTipiCertificati>
		<tipoCertificato>1</tipoCertificato>
	</listaTipiCertificati>
	<soggettoIntestatario>
		<generalita>
			<cognome>PITZALIS</cognome>
			<nome>ROBERTA</nome>
		</generalita>
	</soggettoIntestatario>
	<datiControllo>
		<cognomeUffAnagrafe>PIPPO</cognomeUffAnagrafe>
		<nomeUffAnagrafe>PAPERINO</nomeUffAnagrafe>
		<tipoRichiedente>2</tipoRichiedente>
		<tipoRichiesta>1</tipoRichiesta>
		<paEstera>0</paEstera>
		<emissione>2</emissione>
		<bollo>99</bollo>
		<bolloVirtuale>2</bolloVirtuale>
		<diritti>0</diritti>
	</datiControllo>
</ns2:Richiesta6001>' pdf/v3error
```

### Esempio di comandi:

```
java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar FREE WS6001 config/FREE_Keystore.properties '<?xml version="1.0" encoding="UTF-8"?>
<ns2:Richiesta6001 xmlns:ns2="http://sogei.it/ANPR/6001certificazione">
	<testataRichiesta>
		<idOperazioneComune>000009</idOperazioneComune>
		<codMittente>888231</codMittente>
		<codDestinatario>ANPR02</codDestinatario>
		<operazioneRichiesta>6001</operazioneRichiesta>
		<dataOraRichiesta>2021-02-17T10:20:11.543+02:00</dataOraRichiesta>
		<tipoOperazione>C</tipoOperazione>
		<tipoInvio>FREE</tipoInvio>
		<dataDefinizionePratica>2021-04-14</dataDefinizionePratica>
	</testataRichiesta>
	<listaTipiCertificati>
		<tipoCertificato>1</tipoCertificato>
	</listaTipiCertificati>
	<soggettoIntestatario>
		<generalita>
			<cognome>PITZALIS</cognome>
			<nome>ROBERTA</nome>
		</generalita>
	</soggettoIntestatario>
	<datiControllo>
		<cognomeUffAnagrafe>PIPPO</cognomeUffAnagrafe>
		<nomeUffAnagrafe>PAPERINO</nomeUffAnagrafe>
		<tipoRichiedente>2</tipoRichiedente>
		<tipoRichiesta>1</tipoRichiesta>
		<paEstera>0</paEstera>
		<emissione>2</emissione>
		<bollo>99</bollo>
		<bolloVirtuale>0</bolloVirtuale>
		<diritti>1</diritti>
	</datiControllo>
</ns2:Richiesta6001>' pdf/v3ok
```

# Run del jar: tag v0.2

- Creazione jar: `mvn package`

- La cartella da cui fare il run deve avere le sottocartelle `keystore/` e `target/`

- Assicurarsi che esistano le cartelle dove salvare i file pdf (parametro <SAVE_RES>), quella con i file di properties dei comuni (parametro <FILE_PROPS>) e quella con le richieste (parametro <FILE_REQ>).

- Run
  `java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar <ENV> <WS> <FILE_PROPS> <FILE_REQ> <SAVE_RES>`

| Parametro  | Obbligatorio | Tipo    | Descrizione                                                                                                             | Possibili valori                                                                                                      |
| ---------- | ------------ | ------- | ----------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------- |
| ENV        | Si           | Stringa | Ambiente su cui sono eseguite le richieste di ANPR                                                                      | VAL, TEST, PRE, FREE, PROD. Vedi `it.interno.anpr.config.EnvironmentHandler`                                          |
| WS         | Si           | Stringa | WebService SOAP invocato                                                                                                | TESTCONN, WS1001, ..., WS2001, ..., WS3001, ..., WS4001, ..., WS5001, ... Vedi `it.interno.anpr.config.WSTypeHandler` |
| FILE_PROPS | Si           | Stringa | Path con file .properties con le property del comune.                                                                   |                                                                                                                       |
| FILE_REQ   | Si           | Stringa | Path con file contenente la richiesta da inviare.                                                                       |                                                                                                                       |
| SAVE_RES   | Si           | Stringa | Path con nome del file ma senza ".pdf" dove sarà salvato il certificato ottenuto in risposta. **Il path deve esistere** |                                                                                                                       |

Esempio di comandi:

`java -Djavax.net.ssl.trustStore="keystore/cacerts" -Djavax.net.ssl.trustStorePassword="changeit" -jar target/tool-testconn-1.3.0-spring-boot.jar FREE WS6001 config/FREE_Keystore.properties request/6001/6001_888231_000006.req pdf/aaa06`

---

---

# Il seguente testo è quello del README originale del progetto

---

---

[![Partecipa sul canale #anpr](https://img.shields.io/badge/Slack%20channel-%23anpr-blue.svg)](https://developersitalia.slack.com/messages/C7A8NS7RQ)
[![Ricevi un invito a Slack](https://slack.developers.italia.it/badge.svg)](https://slack.developers.italia.it/)

# Client Java di esempio per il test di connessione ad ANPR

Per l'installazione e l'esecuzione del test di connessione, **dopo aver inserito le credenziali**, eseguire

```
mvn clean install
```

(questo comando esegue tutte le operazioni, compreso l'esecuzione del test di connessione)

**Nota**: è necessario utilizzare un ambiente Java8.
Si può utilizzare la utility `jenv` per impostare il proprio ambiente di sviluppo.
In questo caso, una volta attivata la versione 1.8, richiamare maven con

```
jenv exec mvn clean install
```

Una classe di test della connessione si trova nella cartella `src/test`

# Impostare le credenziali

Per rendere il client utilizzabile, è importante impostare, al primo utilizzo, i certificati con le credenziali di test.
Per personalizzare il certificato bisogna:

- Modificare `src/main/resources/config/FREE_Keystore.properties` inserendo i riferimenti al proprio ceritficato, al codice della postazione e al PIN del certificato.
- Aggiungere il certificato nella cartella `keystore/FREE`

Per ottenere delle credenziali di test, si prega di compilare il modulo all'indirizzo https://anpr-test.bobuild.com/request.

# Accesso all'applicazione web

Una volta ricevute le credenziali, si potrà accedere all'applicazione web di test per visualizzare e svolgere operazioni con i dati caricati.

Per fare ciò è necessario importare il certificato ricevuto (sbloccandolo con il rispettivo PIN) nel proprio sistema operativo o nel proprio browser. Dopodiché sarà possibile accedere a https://dpfree.anpr.interno.it/combas con nome utente e password ricevute via mail.

# Link utili

- [Pagina di ANPR su Developers Italia](https://developers.italia.it/it/anpr)
- [Documentazione di ANPR](http://docs.anpr.it)

# Commandi utili

-
- java -cp tool-testconn-1.3.0.jar it.interno.anpr.security.main.RunSenderMessage <env> <WS...> <Path req>
- copia il jar bouncycastle in C:\Program Files\Java\jre1.8.0_281
- copia il jar common-logging in C:\Program Files\Java\jre1.8.0_281
