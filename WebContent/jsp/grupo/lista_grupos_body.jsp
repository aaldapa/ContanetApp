<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<h1 class="pagetitle"><bean:message key="grupos.listado.titulo"/></h1>
<html:form action="/grupoRedirect" method="post">     

	<display:table class="formulario" name="sessionScope.listado-grupos" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false">
	    
	    <display:column headerClass="cod" sortable="false">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idGrupo"/>' />
		</display:column>	
	    
	    <display:column headerClass="top" titleKey="grupos.listado.tabla.nombre" sortable="true">
	    	<html:link page="/action/grupoRedirect" paramId="id" paramName="iddisplay" paramProperty="idGrupo">
				<bean:write name="iddisplay" property="nombre"/>
			</html:link>
	    </display:column>
	    <display:column property="notas" headerClass="top" titleKey="grupos.listado.tabla.notas" />
	</display:table>
	
	
	<br>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="grupos.listado.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="grupos.listado.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="grupos.listado.delete"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="grupos.listado.clases"/>
		</html:submit>
	</div>
	
</html:form>