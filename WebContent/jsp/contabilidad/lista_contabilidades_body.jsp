<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<h1 class="pagetitle"><bean:message key="contabilidades.listado.titulo"/></h1>
<html:form action="/contabilidadRedirect" method="post">
	<bean:define id="idUsuario" name="LOGIN" property="usuario.idUsuario" />
	<display:table class="formulario" name="sessionScope.listado-contabilidades" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" defaultsort="1" defaultorder="ascending">
	    <display:column headerClass="cod" >
			<logic:equal name="iddisplay" property="creador.idUsuario" value="<%=idUsuario.toString()%>">
				<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idContabilidad"/>' />
			</logic:equal>
		</display:column>	
    		
    	<display:column headerClass="top" titleKey="contabilidades.listado.tabla.nombre" sortable="true">
	    	<html:link page="/action/contabilidadRedirect" paramId="id" paramName="iddisplay" paramProperty="idContabilidad" >
				<bean:write name="iddisplay" property="nombreContabilidad"/>
			</html:link>
    	</display:column>
    	  	
		<display:column property="notas" headerClass="top" titleKey="contabilidades.listado.tabla.descripcion"/>
		<display:column headerClass="default" titleKey="contabilidades.listado.tabla.predeterminada" >
			<logic:equal name="iddisplay" property="predeterminada" value="S">
				<bean:message key="global.si"/>
			</logic:equal>
			<logic:equal name="iddisplay" property="predeterminada" value="N">
				<bean:message key="global.no"/>
			</logic:equal>
		</display:column>
	</display:table>

<br>
<!-- Si el usuario no tiene perfil de administrador no se mostrara la botonera -->
	<%-- <logic:equal name="LOGIN" property="usuario.perfil.idPerfil" value="1"> --%>
		<div class="botonera">
			<html:submit property="accion" styleClass="boton">
				<bean:message key="contabilidades.listado.new"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="contabilidades.listado.update"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="contabilidades.listado.delete"/>
			</html:submit>
			
			<html:submit property="accion" styleClass="boton">
				<bean:message key="contabilidades.listado.cuentas"/>
			</html:submit>
			
		</div>
	<%-- </logic:equal> --%>
	
</html:form>