<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display" %>
 
<h1 class="pagetitle"><bean:message key="depositos.listado.titulo"/></h1>
<html:form styleId="fomulario" action="/depositoRedirect" method="post">
<bean:define id="idUsuario" name="LOGIN" property="usuario.idUsuario" />
<display:table class="formulario" name="sessionScope.listado-depositos" pagesize="<%=Constantes.LISTA_CORTA%>" id="iddisplay" keepStatus="false" defaultsort="1" defaultorder="ascending">
    
    <display:column headerClass="cod" sortable="true">
		<logic:equal name="iddisplay" property="idCreador.idUsuario" value="<%=idUsuario.toString()%>">
			<input type="checkbox" id="ids" name="ids" value='<bean:write name="iddisplay" property="idDeposito"/>' />
		</logic:equal>
	</display:column>	
    <display:column headerClass="fecha" titleKey="depositos.listado.tabla.fecha.apertura" class="center">
   		<bean:write name="iddisplay" property="fechaApertura" formatKey="patron.fecha.corta"/>
    </display:column>
    <display:column headerClass="top" titleKey="depositos.listado.tabla.nombreDeposito" >
		<html:link page="/action/depositoRedirect" paramId="id" paramName="iddisplay" paramProperty="idDeposito" >
			<bean:write name="iddisplay" property="nombreDeposito"/>
		</html:link>
    </display:column>
    <display:column headerClass="default" titleKey="depositos.listado.tabla.importe" class="right">
    	<bean:write name="iddisplay" property="importe" format=",##0.00"/>&nbsp; &euro;
    </display:column>
    <display:column headerClass="corto" titleKey="depositos.listado.tabla.rentabilidad" class="right">
		<bean:write name="iddisplay"  property="rentabilidad" format=",##0.00"/>&nbsp;%
    </display:column>
    <display:column headerClass="fecha" style="align-text:center" titleKey="depositos.listado.tabla.fecha.vencimiento" class="center">
		<bean:write name="iddisplay" property="fechaVencimiento" formatKey="patron.fecha.corta"/>
    </display:column>
</display:table>

<br>

	<div class="botonera">
	
		<html:submit property="accion" styleClass="boton">
			<bean:message key="depositos.listado.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="depositos.listado.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="depositos.listado.delete"/>
		</html:submit>
	
	</div>

</html:form>