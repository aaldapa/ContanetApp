<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html:html>
<html:form action="/Email">
<logic:equal name="resultadoEnvio" value="true">
	<bean:message key="email.correcto"/>
 	<bean:message key="mail.smtp.user"/>
</logic:equal>
<logic:notEqual name="resultadoEnvio" value="true">
	<bean:message key="email.error"/>
</logic:notEqual>


<div class="botonera">
		<html:cancel  styleClass="boton" >
			<bean:message key="email.formulario.volver"/>
		</html:cancel>
	</div>
</html:form>
</html:html>