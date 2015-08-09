<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
 
<h1 class="pagetitle"><bean:message key="cuentas.listado.titulo"/>: <bean:write name="listadoCuentasForm" property="contabilidad.nombreContabilidad"/> </h1>
<html:form styleId="fomulario" action="/contabilidadCuentaRedirect" method="post">
<html:hidden property="contabilidad.idContabilidad"></html:hidden>  
<bean:define id="idUsuario" name="LOGIN" property="usuario.idUsuario" />
<display:table class="formulario" name="sessionScope.listado-cuentas" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" defaultsort="1" defaultorder="ascending">
    
    <display:column headerClass="cod" sortable="true">
		<logic:equal name="iddisplay" property="creador.idUsuario" value="<%=idUsuario.toString()%>">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idCuenta"/>' />
		</logic:equal>
	</display:column>	
    <display:column headerClass="top" titleKey="cuentas.listado.tabla.dni.titular" >
		<html:link page="/action/contabilidadCuentaRedirect" paramId="id" paramName="iddisplay" paramProperty="idCuenta" >
			<bean:write name="iddisplay" property="notas"/>
		</html:link>
    </display:column>
    <display:column headerClass="top" titleKey="cuentas.listado.tabla.titular">
    <bean:write name="iddisplay" property="titular"/>-<bean:write name="iddisplay" property="titular2"/>
    </display:column>
    <display:column headerClass="top" titleKey="cuentas.listado.tabla.numero">
		<a href="<%=request.getContextPath()%>/action/contabilidadCuentaRedirect?id=<bean:write name="iddisplay" property="idCuenta"/>">
			<bean:write name="iddisplay" property="entidad"/>
			<bean:write name="iddisplay" property="sucursal"/>
			<bean:write name="iddisplay" property="dc"/>
			<bean:write name="iddisplay" property="numCuenta"/>
		</a>
    </display:column>
</display:table>

<br>

	<div class="botonera">
	
		<html:submit property="accion" styleClass="boton">
			<bean:message key="cuentas.listado.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="cuentas.listado.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="cuentas.listado.delete"/>
		</html:submit>
	
	</div>

</html:form>