<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.js'></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="save">
	<bean:message key="boton.guardar" />
</bean:define>

<html:form  styleId="formulario" action="/ApuntePorDefecto" method="post">
<html:hidden property="id"/>
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>

	<h1 class="pagetitle">
		<bean:message key="apuntepd.formulario.titulo"/>
	</h1>
	
	<div class="contactForm">
	
	<fieldset id="id_filtros">
			<legend><bean:message key="apuntepd.formulario.leyenda"/></legend>
			<table  style="margin:0 0 0 10px;width: 650px;">
				<%-- 
				<tr>
					<td colspan="2"></td>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.tipo" /></td>
					<td>
						<html:select styleId="id_combo_tipo" name="apuntePorDefectoForm" property="tipoApunte" style="width:100px">
							<html:option  value="INGRESO" key="apunte.formulario.tipo.ingreso" />
							<html:option  value="GASTO" key="apunte.formulario.tipo.gasto" />
						</html:select>
					</td>
				</tr>
				--%>
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.listado.contabilidades" /></td>
					<td>
						<bean:define id="contabilidadesCollection" name="apuntePorDefectoForm" property="lstContabilidades" />
						<html:select styleId="id_combo_contabilidad" name="apuntePorDefectoForm" property="idContabilidad" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
							<logic:present name="apuntePorDefectoForm" property="lstContabilidades">
								<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
							</logic:present>
						</html:select>
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.listado.cuentas" /></td>
					<td>
					<logic:present name="apuntePorDefectoForm" property="lstCuentas">
						<bean:define id="cuentasCollection" name="apuntePorDefectoForm" property="lstCuentas" />
					</logic:present>
						<html:select styleId="id_combo_cuenta" name="apuntePorDefectoForm" property="idCuenta" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.cuenta" />
								<logic:present name="apuntePorDefectoForm" property="lstCuentas">
									<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
								</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.listado.grupos" /></td>
					<td>
						<bean:define id="gruposCollection" name="apuntePorDefectoForm" property="lstGrupos" />
					
						<html:select styleId="id_combo_grupo" name="apuntePorDefectoForm" property="idGrupo" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.grupo" />
								<logic:present name="apuntePorDefectoForm" property="lstGrupos">
									<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
								</logic:present>
						</html:select>
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.listado.clases" /></td>
					<td>
						<bean:define id="clasesCollection" name="apuntePorDefectoForm" property="lstClases" />
				 	
						<html:select styleId="id_combo_clase" name="apuntePorDefectoForm" property="idClase" style="width:180px">
							<html:option  value="0" key="apunte.listado.seleccion.clase" />
								<logic:present name="apuntePorDefectoForm" property="lstClases">
									<html:options collection="clasesCollection"  property="idClase" labelProperty="nombre"/>
								</logic:present>
						</html:select>
					</td>
				</tr>

			</table>
		</fieldset>
		
	</div>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton"><bean:message key="boton.guardar"/></html:submit>
	</div>

</html:form>
<script type="text/javascript">
//jquery
var $j = jQuery.noConflict();
	
$j(document).ready(function (){
	var url_destino = "<%=request.getContextPath()%>/action/recargarCombo";
	
	$j('#id_combo_contabilidad').change(function (event){
	
		var dataenvio ='idContabilidad='+$j('#id_combo_contabilidad').val();
		var path=url_destino+'?'+dataenvio;
				
		$j.getJSON(path,
			function(data){  
			//Elimino todos los options del combos
			$j('#id_combo_cuenta').removeOption(/./);
			//Añado en primer lugar en la opcion 0 el valor del properties 
			$j('#id_combo_cuenta').addOption('0', '<bean:message key="apunte.listado.seleccion.cuenta"/>');
			//Cargo el combo recorriendo los valores del json
			$j.each(data.lstCuentas, function(i,cuenta){
					$j('#id_combo_cuenta').addOption(cuenta.id, cuenta.label);
			    });
			//Dejo seleccionada la opcion 0
			$j('#id_combo_cuenta').selectOptions('0');
		});		
	});
	
	$j('#id_combo_grupo').change(function (event){
		
		var dataenvio ='idGrupo='+$j('#id_combo_grupo').val();
		var path=url_destino+'?'+dataenvio;
				
		$j.getJSON(path,
			function(data){  
			//Elimino todos los options del combos
			$j('#id_combo_clase').removeOption(/./);
			//Añado en primer lugar en la opcion 0 el valor del properties 
			$j('#id_combo_clase').addOption('0', '<bean:message key="apunte.listado.seleccion.clase"/>');
			//Cargo el combo recorriendo los valores del json
			$j.each(data.lstClases, function(i,clase){
					$j('#id_combo_clase').addOption(clase.id, clase.label);
			    });
			//Dejo seleccionada la opcion 0
			$j('#id_combo_clase').selectOptions('0');
		});		
	});
	
});
</script>