<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<h1 class="pagetitle"><bean:message key="roles.listado.titulo"/></h1>
<html:form action="/rolRedirect" method="post">     

	<display:table class="formulario" name="sessionScope.listado-roles" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false">
	    
	    <display:column headerClass="cod" sortable="false">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idPerfil"/>' />
		</display:column>	
	    
	    <display:column headerClass="top" titleKey="roles.listado.tabla.nombre" sortable="true">
	    	<html:link page="/action/rolRedirect" paramId="id" paramName="iddisplay" paramProperty="idPerfil">
				<bean:write name="iddisplay" property="nombre"/>
			</html:link>
	    </display:column>
	    <display:column property="descripcion" headerClass="top" titleKey="roles.listado.tabla.descripcion" />
	</display:table>


<br>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="roles.listado.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="roles.listado.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="roles.listado.delete"/>
		</html:submit>
	</div>
	
</html:form>