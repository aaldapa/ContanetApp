<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="clases.listado.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="clases.listado.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="clases.listado.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="clases.listado.delete" />
</bean:define>

<html:html>

<html:form action="/clase">

<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="clase.formulario.titulo.Nuevo"/>
		&nbsp;<bean:write name="claseForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="clase.formulario.leyenda"/></legend>
			
			
			<p>
				<label class="left"><bean:message key="clase.formulario.grupo"/></label>
				<!-- Guardo el listado de grupos en una variable para referenciarla mas tarde en el 
				collections del option porque no puedo o no se acceder al listado de forma directa 
				con algo asi como clasesListForm.lstGrupos -->
				<bean:define id="gruposCollection" name="claseForm" property="lstGrupos" />
			
				<html:select styleId="id_combo" name="claseForm" property="idGrupo">
					<html:option  value="0" key="clase.formulario.seleccion.grupo" />
					<logic:present name="claseForm" property="lstGrupos">
						<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
					</logic:present>
				</html:select>
			</p>
			
			<p>
				<label class="left"><bean:message key="clase.formulario.nombre"/></label>
				<html:text property="nombre" maxlength="35" size="35" styleClass="field"/>
			</p>
			
			<p>
				<label class="left"><bean:message key="clase.formulario.descripcion" /></label>
				<html:textarea property="descripcion"	cols="30" rows="2" />
			</p>
		</fieldset>
	
	</div>
	<div class="botonera">
		<logic:notEqual name="claseForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="claseForm" property="accion" value="<%=insert%>">
					<bean:message key="clases.listado.new" />
				</logic:equal>
				<logic:equal name="claseForm" property="accion" value="<%=update%>">
					<bean:message key="clases.listado.update" />
				</logic:equal>
				<logic:equal name="claseForm" property="accion" value="<%=delete%>">
					<bean:message key="clases.listado.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>
</html:html>