<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="contabilidades.listado.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="contabilidades.listado.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="contabilidades.listado.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="contabilidades.listado.delete" />
</bean:define>

<html:html>

<html:form action="/contabilidad">

<html:hidden property="id"></html:hidden>
<html:hidden property="idUsuario"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="contabilidades.formulario.titulo.ver"/>
		&nbsp;<bean:write name="contabilidadForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="contabilidades.formulario.leyenda"/></legend>
			<p>
				<label class="left"><bean:message key="contabilidades.formulario.nombre"/></label>
				<html:text property="nombre" maxlength="35" size="35" styleClass="field"/>
				<bean:message key="contabilidades.formulario.predeterminada" />
				<logic:equal name="contabilidadForm" property="predeterminada" value="S">
					<input type="checkbox" name="predeterminada" value="S" checked="checked"/>
				</logic:equal>
				<logic:notEqual name="contabilidadForm" property="predeterminada" value="S">
					<input type="checkbox" name="predeterminada" value="S"/>
				</logic:notEqual>
			</p>
			<p>
				<label class="left"><bean:message key="contabilidades.formulario.descripcion" /></label>
				<html:textarea property="notas"	cols="30" rows="2" />
			</p>
		
		</fieldset>
		
		<!-- Listado de usuarios -->
	
		<fieldset>
				<legend><bean:message key="contabilidades.formulario.leyenda.usuarios"/></legend>
				<bean:define id="lstFamiliares" name="contabilidadForm" property="lstUsuarios" />
				<bean:define id="lstIdsContabilidadesUsuario" name="contabilidadForm" property="lstIdsContabilidadesUsuario" />
				
				<logic:iterate id="item" name="lstFamiliares" indexId="contador">
					<bean:define id="checkeado" value="" />
					<logic:iterate id="idsContaUser" name="lstIdsContabilidadesUsuario">
						
						<logic:equal name="item" property="idUsuario" value="<%=idsContaUser.toString()%>">
							<bean:define id="checkeado" value="checked" />
						</logic:equal>
						
					</logic:iterate>
					<!-- Si se trata de un alta de contabilidad  -->
					<logic:empty name="contabilidadForm" property="id">
						<!-- Aparece seleccionado por defecto el usuario que esta dando el alta -->
						<bean:define id="id_usuario" name="<%=Constantes.LOGIN%>" property="usuario.idUsuario"/>
						
						<logic:equal name="item" property="idUsuario" value="<%=id_usuario.toString()%>">				
							<bean:define id="checkeado" value="checked" />
						</logic:equal>
					</logic:empty>
					<p>
						<logic:equal name="checkeado" value="checked">
							<input type="checkbox" name="idsUsuariosSelected" value='<bean:write name="item" property="idUsuario"/>' checked="checked"/>
						</logic:equal>
						<logic:notEqual name="checkeado" value="checked">
							<input type="checkbox" name="idsUsuariosSelected" value='<bean:write name="item" property="idUsuario"/>'/>	
						</logic:notEqual>
						<bean:write name="item" property="nombreCompleto"/>
					</p>
				</logic:iterate>
				
		</fieldset>
		
		
	</div>
	<div class="botonera">
		<logic:notEqual name="contabilidadForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="contabilidadForm" property="accion" value="<%=insert%>">
					<bean:message key="contabilidades.listado.new" />
				</logic:equal>
				<logic:equal name="contabilidadForm" property="accion" value="<%=update%>">
					<bean:message key="contabilidades.listado.update" />
				</logic:equal>
				<logic:equal name="contabilidadForm" property="accion" value="<%=delete%>">
					<bean:message key="contabilidades.listado.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


</html:html>