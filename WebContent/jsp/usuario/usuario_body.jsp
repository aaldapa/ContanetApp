<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="usuarios.listado.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="usuarios.listado.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="usuarios.listado.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="usuarios.listado.delete" />
</bean:define>

<html:html>

<html:form action="/usuario">

<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="usuario.formulario.titulo.Nuevo"/>
		&nbsp;<bean:write name="usuarioForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="usuario.formulario.leyenda"/></legend>
			<p>
				<label class="left"><bean:message key="usuario.formulario.perfil"/></label>
				<%--<html:text property="idPerfil" maxlength="35" size="35" styleClass="field"/> --%>
				<!-- Guardo el listado de roles en una variable para referenciarla mas tarde en el 
				collections del option porque no puedo o no se acceder al listado de forma directa 
				con algo asi como usuarioForm.lstRoles -->
				<bean:define id="rolesCollection" name="usuarioForm" property="lstRoles" />
			
				<html:select name="usuarioForm" property="idPerfil">
					<html:option  value="0" key="usuario.formulario.seleccion.rol" />
						<logic:present name="usuarioForm" property="lstRoles">
							<html:options collection="rolesCollection"  property="idPerfil" labelProperty="nombre"/>
						</logic:present>
				</html:select>
			</p>
			<p>
				<label class="left"><bean:message key="usuario.formulario.familia"/></label>
				<bean:define id="familiasCollection" name="usuarioForm" property="lstFamilia" />
			
				<html:select name="usuarioForm" property="idFamilia">
					<html:option  value="0" key="usuario.formulario.seleccion.familia" />
						<logic:present name="usuarioForm" property="lstFamilia">
							<html:options collection="familiasCollection"  property="idFamilia" labelProperty="nombre"/>
						</logic:present>
				</html:select>
			</p>
			<p>
				<label class="left"><bean:message key="usuario.formulario.nombre"/></label>
				<html:text property="nombre" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="usuario.formulario.apellido"/></label>
				<html:text property="apellidos" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="usuario.formulario.email"/></label>
				<html:text property="email" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="usuario.formulario.password"/></label>
				 <html:password property="password" maxlength="35" size="35" styleClass="field"/>
			</p>
			
				
		</fieldset>
	
	
	<!-- Listado de contabilidades -->
	
		<fieldset>
				<legend><bean:message key="usuario.formulario.leyenda.contabilidades"/></legend>
		
			<%-- <logic:present  name="usuarioForm" property="lstContabilidades">--%>
				<bean:define id="lstContabilidades" name="usuarioForm" property="lstContabilidades" />
				<bean:define id="lstIdsContabilidadesUsuario" name="usuarioForm" property="lstIdsContabilidadesUsuario" />
				
				<!-- Recorro la lista de contabilidades de la base de datos
				 y dentro de ella las contabilidades del usuario. Cuando el codigo de una y otra son iguales
				 muestro un check checkeado sino, lo muestro sin seleccionar  -->
				<logic:iterate id="item" name="lstContabilidades" indexId="contador">
					<bean:define id="checkeado" value="" />
					<logic:iterate id="idsContaUser" name="lstIdsContabilidadesUsuario">
						
						<logic:equal name="item" property="idContabilidad" value="<%=idsContaUser.toString()%>">
							<bean:define id="checkeado" value="checked" />
						</logic:equal>
						
					</logic:iterate>
					<p>
						<logic:equal name="checkeado" value="checked">
							<input type="checkbox" name="idsContabilidadesSelected" value='<bean:write name="item" property="idContabilidad"/>' checked="checked"/>
								
						</logic:equal>
						<logic:notEqual name="checkeado" value="checked">
							<input type="checkbox" name="idsContabilidadesSelected" value='<bean:write name="item" property="idContabilidad"/>'/>	
						</logic:notEqual>
						<bean:write name="item" property="nombreContabilidad"/>
					</p>
				</logic:iterate>
					
			<%--</logic:present>--%>
		</fieldset>
	</div>
	<div class="botonera">
		<logic:notEqual name="usuarioForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="usuarioForm" property="accion" value="<%=insert%>">
					<bean:message key="usuarios.listado.new" />
				</logic:equal>
				<logic:equal name="usuarioForm" property="accion" value="<%=update%>">
					<bean:message key="usuarios.listado.update" />
				</logic:equal>
				<logic:equal name="usuarioForm" property="accion" value="<%=delete%>">
					<bean:message key="usuarios.listado.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


</html:html>