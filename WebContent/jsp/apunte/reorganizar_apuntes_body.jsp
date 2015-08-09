<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>
	
<!-- Defino variables para referenciar los textos de los botones-->

<html:form styleId="formulario" action="/recargarReorganizarApuntes" method="post">
<%-- <html:hidden property="accion"/>--%>
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>
	<h1 class="pagetitle">
		<bean:message key="apunte.organizar.titulo"/>
	</h1>
	
	<div id="flotante" class="contactForm">
		<fieldset id="id_filtros">
			<legend><bean:message key="apunte.listado.leyenda"/></legend>
				<table  style="margin:0 0 0 10px;width: 650px;">
					
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.contabilidades" /></td>
						<td>
							<bean:define id="contabilidadesCollection" name="listadoApuntesForm" property="lstContabilidades" />
							<html:select styleId="id_combo_contabilidad" name="listadoApuntesForm" property="idContabilidad" style="width:180px">
								<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
								<logic:present name="listadoApuntesForm" property="lstContabilidades">
									<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
								</logic:present>
							</html:select>
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.cuentas" /></td>
						<td>
							<bean:define id="cuentasCollection" name="listadoApuntesForm" property="lstCuentas" />
					
							<html:select styleId="id_combo_cuenta" name="listadoApuntesForm" property="idCuenta" style="width:180px">
								<html:option  value="0" key="apunte.listado.seleccion.cuenta" />
									<logic:present name="listadoApuntesForm" property="lstCuentas">
										<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
									</logic:present>
							</html:select>
						</td>
					</tr>
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.grupos" /></td>
						<td>
							<bean:define id="gruposCollection" name="listadoApuntesForm" property="lstGrupos" />
					
							<html:select styleId="id_combo_grupo" name="listadoApuntesForm" property="idGrupo" style="width:180px">
								<html:option  value="0" key="apunte.listado.seleccion.grupo" />
									<logic:present name="listadoApuntesForm" property="lstGrupos">
										<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
									</logic:present>
							</html:select>
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.clases" /></td>
						<td>
							<bean:define id="clasesCollection" name="listadoApuntesForm" property="lstClases" />
				 	
							<html:select styleId="id_combo_clase" name="listadoApuntesForm" property="idClase" style="width:180px">
								<html:option  value="0" key="apunte.listado.seleccion.clase" />
									<logic:present name="listadoApuntesForm" property="lstClases">
										<html:options collection="clasesCollection"  property="idClase" labelProperty="nombre"/>
									</logic:present>
							</html:select>
						</td>
					</tr>
				</table>
		</fieldset>
	</div>
	
	<bean:define id="lstApuntes" name="listadoApuntesForm" property="lstApuntesFormReorganizar" />
	<div class="tableContainer">
		<table class="scrollTable">
	    	<thead class="fixedHeader">
	          	<tr>
	           		<th scope="col" class="center" style="width: 65px;"><bean:message key="apunte.listado.tabla.fecha"/></th>
	           		<th scope="col" style="width: 250px;"><bean:message key="apunte.listado.tabla.concepto"/></th>
	           		<th scope="col" style="width: 120px;"><bean:message key="apunte.listado.tabla.grupo"/></th>
	           		<th scope="col" style="width: 120px;"><bean:message key="apunte.listado.tabla.clase"/></th>
	           		<th scope="col" class="center" style="width: 75px;" ><bean:message key="apunte.listado.tabla.importe"/></th>
	       	</thead>
			<tbody class="scrollContent">	
				<logic:iterate id="apunte" name="lstApuntes" indexId="contador">
					<bean:define id="gasto_ingreso" value=""/>
					<logic:equal name="apunte" property="tipoApunte" value="GASTO">
						<bean:define id="gasto_ingreso" value="color:red;"/>
					</logic:equal>
					<bean:define id="gasto_ingreso_saldo" value=""/>
					<logic:lessThan name="apunte" property="saldo" value="0">
						<bean:define id="gasto_ingreso_saldo" value="color:red;"/>
					</logic:lessThan>
					
					<tr  class='<%=contador.intValue()%2==0? "par":"impar"%>'>
						
						<td style="<%=gasto_ingreso%>">
							<input type="hidden" name="ids" value='<bean:write name="apunte" property="id"/>' />
							<bean:write name="apunte" property="fecha" formatKey="patron.fecha.corta"/>
						</td>
						<td style="<%=gasto_ingreso%>;width: 250px;">
							<bean:write name="apunte" property="concepto"/>
						</td>
						<td  style="<%=gasto_ingreso%>;width: 120px;" >
							<bean:write name="apunte" property="nombreGrupo" />
						</td>
						<td  style="<%=gasto_ingreso%>;width: 120px;">
							<bean:write name="apunte" property="nombreClase"/>
						</td>
						<td class="right" style="<%=gasto_ingreso%>;width: 65px;" >
							<bean:write name="apunte" property="importe" format=",##0.00"/>&nbsp; &euro;
						</td>						
					</tr>
				</logic:iterate>
				</tbody>
			</table>
			</div>
	<br>
	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.organizar"/>
		</html:submit>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
</html:form>
<script type="text/javascript">
	<%--
	function reload(){
		var accion="<%=request.getContextPath()%>/action/recargarReorganizarApuntes";
		document.getElementById('formulario').action=accion;
		document.getElementById('formulario').submit();
	}
	--%>	
</script>

<script type="text/javascript">
//jquery
var $j = jQuery.noConflict();
	
$j(document).ready(function (){
	
	/******* RECARGA DE COMBOS ************************************************************/
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
