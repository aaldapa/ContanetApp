<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>

<h1 class="pagetitle"><bean:message key="usuarios.listado.titulo"/></h1>
<html:form action="/usuarioRedirect" method="post">     

	<display:table class="formulario" name="sessionScope.listado-usuarios" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" defaultsort="1" defaultorder="ascending">
	    <display:column headerClass="cod" >
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idUsuario"/>' />
		</display:column>	
    		
    	<display:column headerClass="top" titleKey="usuarios.listado.tabla.nombre" sortable="true">
	    	<html:link page="/action/usuarioRedirect" paramId="id" paramName="iddisplay" paramProperty="idUsuario" >
				<bean:write name="iddisplay" property="nombre"/>
			</html:link>
    	</display:column>
    	<display:column headerClass="top" titleKey="usuarios.listado.tabla.apellidos">
			<html:link page="/action/usuarioRedirect" paramId="id" paramName="iddisplay" paramProperty="idUsuario">
				<bean:write name="iddisplay" property="apellidos"/>
			</html:link>
    	</display:column>
    	
		<display:column property="perfil.nombre" headerClass="top" titleKey="usuarios.listado.tabla.perfil" />
		<display:column headerClass="top" titleKey="usuarios.listado.tabla.familia">
			<logic:notEmpty name="iddisplay" property="familia">
				<%-- <bean:write name="iddisplay" property="familia.nombre"/>--%>
				<bean:define id="familia" name="iddisplay" property="familia"/>
				<bean:write name="familia" property="nombre"/>
			</logic:notEmpty>
		</display:column>
	</display:table>
<br>

	<div class="botonera">
		<logic:equal name="LOGIN" property="usuario.perfil.idPerfil" value="1">
			<html:submit property="accion" styleClass="boton">
				<bean:message key="usuarios.listado.new"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="usuarios.listado.update"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="usuarios.listado.delete"/>
			</html:submit>
		</logic:equal>
	</div>
	
</html:form>