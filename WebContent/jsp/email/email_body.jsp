<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:form styleId="form" action="/envio" enctype="multipart/form-data">
        

	<h1 class="pagetitle">
		<bean:message key="email.formulario.titulo"/>
		&nbsp;
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="email.formulario.leyenda"/></legend>
			
			<p>
				<label class="left"><bean:message key="email.formulario.asunto"/></label>
				<html:text property="asunto" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="email.formulario.texto" /></label>
				<html:textarea property="texto"	cols="30" rows="2" />
			</p>
				
			<p>
				<label class="left"><bean:message key="email.formulario.adjunto" /></label>
				<html:file property="file" />
			</p>
	
		</fieldset>
	</div>
	<div class="botonera">
		<html:submit  property="accion" styleClass="boton">
			<bean:message key="email.formulario.enviar"/>
		</html:submit>
	</div>
	
	
</html:form>
