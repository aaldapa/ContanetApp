<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>


<h1 class="pagetitle"><bean:message key="clases.listado.titulo"/>: <bean:write name="listadoClasesForm" property="grupo.nombre"/> </h1>
<html:form styleId="fomulario" action="/grupoClaseRedirect" method="post">     
<html:hidden property="grupo.idGrupo"></html:hidden>
<html:hidden property="grupo.nombre"></html:hidden>
  
	<display:table class="formulario" name="sessionScope.listado-clases" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" >
		<display:column headerClass="cod">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idClase"/>' />
		</display:column>	
	    <display:column headerClass="top" titleKey="clases.listado.tabla.nombre">
			<html:link page="/action/grupoClaseRedirect" paramId="id" paramName="iddisplay" paramProperty="idClase" >
				<bean:write name="iddisplay" property="nombre"/>
			</html:link>
	    </display:column>
	    <display:column headerClass="top" titleKey="clases.listado.tabla.descripcion">
	    	<bean:write name="iddisplay" property="notas"/>
	    </display:column>

	</display:table>

<br>

	<div class="botonera">
		<!-- Si no tiene perfil de administrador no se muestra la botonera (comentado) -->
		<%-- <logic:equal name="LOGIN" property="usuario.perfil.idPerfil" value="1">--%>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="clases.listado.new"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="clases.listado.update"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="clases.listado.delete"/>
			</html:submit>
			<html:submit property="accion" styleClass="boton">
				<bean:message key="boton.back"/>
			</html:submit>
		<%-- </logic:equal> --%>
	</div>

</html:form>

<script type="text/javascript">
function reload(){
	var accion="<%=request.getContextPath()%>/action/listarClases?idGrupo="+document.getElementById("id_combo").value;
	document.getElementById('fomulario').action=accion;
	document.getElementById('fomulario').submit();
}
</script>