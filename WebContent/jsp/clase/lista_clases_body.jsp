<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


<h1 class="pagetitle"><bean:message key="clases.listado.titulo"/></h1>
<html:form styleId="fomulario" action="/claseRedirect" method="post">     


	<logic:present name="listado-clases">
	
	<p>
		<label class="left"><bean:message key="clases.listado.seleccion.grupo"/></label>
		
		<!-- Guardo el listado de grupos en una variable para referenciarla mas tarde en el 
		collections del option porque no puedo o no se acceder al listado de forma directa 
		con algo asi como clasesListForm.lstGrupos -->
		<bean:define id="gruposCollection" name="listadoClasesForm" property="lstGrupos" />
			
		<html:select styleId="id_combo" name="listadoClasesForm" property="idGrupo" onchange="reload()">
			<html:option  value="0" key="clases.listado.seleccion.grupo.todas" />
				<logic:present name="listadoClasesForm" property="lstGrupos">
					<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
				</logic:present>
		</html:select>
		</p>
	
		<table class="formulario">
            <tbody>
            	<tr>
            		<th scope="col" class="cod"></th>
            		<th scope="col" class="top" ><bean:message key="clases.listado.tabla.nombre"/></th>
            		<th scope="col" class="top"><bean:message key="clases.listado.tabla.grupo"/></th></tr>
          
			<logic:iterate id="clase" name="listado-clases" indexId="contador">
				<tr  class='<%=contador.intValue()%2==0? "odd":"even"%>'>
					<td>
						<input type="checkbox" name="ids" value='<bean:write name="clase" property="idClase"/>' />
					</td>
					<td>
						<html:link page="/action/claseRedirect" paramId="id" paramName="clase" paramProperty="idClase">
							<bean:write name="clase" property="nombre"/>
						</html:link>					
					</td>
					<td>
						<bean:write name="clase" property="grupo.nombre"/>
					</td>
				</tr>
			</logic:iterate>
			</tbody>
		</table>
	</logic:present>


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