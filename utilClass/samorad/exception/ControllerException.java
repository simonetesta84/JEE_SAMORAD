package samorad.exception;

import samorad.util.common.Constant;
import samorad.util.common.Message;

public class ControllerException extends Exception {
	
	private String messageException;
	private String tipoMsg;
	
	public ControllerException(String messageException, String tipoMsg) {
		super();
		this.tipoMsg = tipoMsg;
		this.messageException = messageException;
	}

	public void getMessageExceptionVideo() {
		switch (tipoMsg) {
			case Constant.FrontEnd.TipoMsg.INFO:
				Message.addInfo(messageException);
			break;
			case Constant.FrontEnd.TipoMsg.WARM:
				Message.addWarn(messageException);	
			break;			
			case Constant.FrontEnd.TipoMsg.ERROR:
				Message.addError(messageException);
			break;
			case Constant.FrontEnd.TipoMsg.FATAL_ERROR:
				Message.addFatal(messageException);
			break;
		}
	}
	
	public String getgetMessageExceptionLog(){
		return messageException;
	}
	
}