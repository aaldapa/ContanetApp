<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<h1 class="pagetitle"><bean:message key="familias.listado.titulo"/></h1>
<html:form action="/familiaRedirect" method="post">     
	
	<display:table class="formulario" name="sessionScope.listado-familias" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" defaultsort="1" defaultorder="ascending">
	    <display:column headerClass="cod" sortable="true">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idFamilia"/>' />
		</display:column>	
    	<display:column headerClass="top" titleKey="familias.listado.tabla.nombre" sortable="true">
    	<html:link page="/action/familiaRedirect" paramId="id" paramName="iddisplay" paramProperty="idFamilia">
			<bean:write name="iddisplay" property="nombre"/>
		</html:link>
    </display:column>
		<display:column property="notas" headerClass="top" titleKey="familias.listado.tabla.descripcion" />
	</display:table>


<br>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.delete"/>
		</html:submit>
	</div>
	
</html:form>