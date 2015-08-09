<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<h1 class="pagetitle">
	<bean:message key="grafica.formulario.titulo"/>
</h1>


<html:form  action="/viewAnalisis" method="post">
	<div class="contactForm">
		<fieldset id="id_filtros">
			<legend><bean:message key="grafica.seleccion.leyenda"/></legend>
				<table  style="margin:0 0 0 35px;width: 650px;">
					<tr>
						<td style="width: 20px;"><html:radio property="tipoGrafica" value="meses"/></td>
						<td style="font-size:75%;"><bean:message key="grafica.seleccion.meses"/></td>
					</tr>
					<tr>
						<td style="width: 20px;"><html:radio property="tipoGrafica" value="grupos"/></td>
						<td style="font-size:75%;"><bean:message key="grafica.seleccion.grupos"/></td>
					</tr>
					<tr>
						<td style="width: 20px;"><html:radio property="tipoGrafica" value="clases"/></td>
						<td style="font-size:75%;"><bean:message key="grafica.seleccion.clases"/></td>
					</tr>	
				</table>
		</fieldset>
	</div>		
	
	
	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.aceptar"/> 
		</html:submit>
	</div>
</html:form>

		