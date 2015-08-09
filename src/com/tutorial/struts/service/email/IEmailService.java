package com.tutorial.struts.service.email;

import java.io.File;

import org.apache.struts.util.MessageResources;

public interface IEmailService {

	public Boolean enviarEmail(MessageResources recursos,String asunto, String texto, File file);
	
}
