<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="roles.listado.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="roles.listado.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="roles.listado.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="roles.listado.delete" />
</bean:define>

<html:html>

<html:form action="/rol">

<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="roles.formulario.titulo.ver"/>
		&nbsp;<bean:write name="rolForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="roles.formulario.leyenda"/></legend>
			<p>
				<label class="left"><bean:message key="roles.formulario.nombre"/></label>
				<html:text property="nombre" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="roles.formulario.descripcion" /></label>
				<html:textarea property="descripcion"	cols="30" rows="2" />
			</p>
		
		</fieldset>
	</div>
	<div class="botonera">
		<logic:notEqual name="rolForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="rolForm" property="accion" value="<%=insert%>">
					<bean:message key="roles.listado.new" />
				</logic:equal>
				<logic:equal name="rolForm" property="accion" value="<%=update%>">
					<bean:message key="roles.listado.update" />
				</logic:equal>
				<logic:equal name="rolForm" property="accion" value="<%=delete%>">
					<bean:message key="roles.listado.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


</html:html>