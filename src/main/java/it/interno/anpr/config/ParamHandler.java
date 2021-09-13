package it.interno.anpr.config;

public class ParamHandler {

	private String dataRequest;
	private String fileRequest;
	private String requestText;

	private WSTypeHandler wsType;
	private String responseSavedFile;

	public String getResponseSavedFile() {
		return responseSavedFile;
	}
	public void setResponseSavedFile(String responseSavedFile) {
		this.responseSavedFile = responseSavedFile;
	}
	
	public String getDataRequest() {
		return dataRequest;
	}
	public void setDataRequest(String dataRequest) {
		this.dataRequest = dataRequest;
	}

	public String getFileRequest() {
		return fileRequest;
	}
	public void setFileRequest(String fileRequest) {
		this.fileRequest = fileRequest;
	}

	public String getFileProperties() {
		return ConfigHandler.getPathFileKeystore();
	}
	public void setFileProperties(String fileProperties) {
		ConfigHandler.setPathFileKeystore(fileProperties);
		// System.out.println("ConfigHandler.getPathFileKeystore "+ ConfigHandler.getPathFileKeystore());
	}
	
	public String getEnvironment() {
		return EnvironmentHandler.getEnv();
	}
	
	public void setEnvironment(String environment) {
		EnvironmentHandler.setEnv(environment);
	}
	
	public WSTypeHandler getWsType() {
		return wsType;
	}
	
	public void setWsType(WSTypeHandler wsType) {
		this.wsType = wsType;
	}
	
	public String getRequestText() {
		return requestText;
	}
	
	public void setRequestText(String requestText) {
		this.requestText = requestText;
	}
	public String getPropertiesText() {
		return ConfigHandler.getPropertiesKeyStoreText();
	}
	public void setPropertiesKeyStoreText(String propertiesText) {
		ConfigHandler.setPropertiesKeyStoreText(propertiesText);
	}
}
