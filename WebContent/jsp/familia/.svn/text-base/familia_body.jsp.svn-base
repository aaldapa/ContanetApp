<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="boton.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="boton.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="boton.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="boton.delete" />
</bean:define>

<html:html>

<html:form action="/familia">

<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="familias.formulario.titulo.ver"/>
		&nbsp;<bean:write name="familiaForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="familias.formulario.leyenda"/></legend>
			<p>
				<label class="left"><bean:message key="familias.formulario.nombre"/></label>
				<html:text property="nombre" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="familias.formulario.descripcion" /></label>
				<html:textarea property="notas"	cols="30" rows="2" />
			</p>
		
		</fieldset>
	</div>
	<div class="botonera">
		<logic:notEqual name="familiaForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="familiaForm" property="accion" value="<%=insert%>">
					<bean:message key="boton.new" />
				</logic:equal>
				<logic:equal name="familiaForm" property="accion" value="<%=update%>">
					<bean:message key="boton.update" />
				</logic:equal>
				<logic:equal name="familiaForm" property="accion" value="<%=delete%>">
					<bean:message key="boton.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


</html:html>