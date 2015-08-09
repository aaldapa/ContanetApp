<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@page import="com.tutorial.struts.forms.apunte.ApunteForm"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%-- <script language='Javascript' src='<%=request.getContextPath()%>/js/calendario/popcalendar.js'></script>--%>
<script language='Javascript' src='<%=request.getContextPath()%>/js/Utils.js'></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/prototype/prototype.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>

<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/jquery.ui.all.css">

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="insert">
	<bean:message key="boton.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="boton.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="boton.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="boton.delete" />
</bean:define>
<bean:define id="copy">
	<bean:message key="boton.copy" />
</bean:define>


<html:form  styleId="formulario" action="/apunte" method="post">
<html:hidden property="id"/>
<html:hidden property="accion"/>
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>
	<h1 class="pagetitle">
		<bean:message key="apunte.listado.titulo"/>
		&nbsp;<bean:write name="apunteForm" property="accion"/>
	</h1>
	
	<div class="contactForm">
	<!-- 	Inicio octubre_12 -->
	<html:hidden name="apunteForm" property="seleccionAnterior.idContabilidad"/>
	<html:hidden name="apunteForm" property="seleccionAnterior.idCuenta"/>
	<html:hidden name="apunteForm" property="seleccionAnterior.idClase"/>
	<html:hidden name="apunteForm" property="seleccionAnterior.idGrupo"/>
	<html:hidden name="apunteForm" property="seleccionAnterior.fechaInicioStr"/>
	<html:hidden name="apunteForm" property="seleccionAnterior.fechaFinStr"/>
	<!-- 	Fin octubre_12 -->
		<fieldset id="id_filtros">
			<legend><bean:message key="apunte.formulario.leyenda"/></legend>
			<table  style="margin:0 0 0 10px;width: 650px;">
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.fecha" /></td>
					<td>
						<%-- 
						<input id="fecha" name="fechaStr"  type="text" size="8" value='<bean:write name="apunteForm" property="fechaStr"/>' readonly="readonly" />
						<html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fecha, 'dd/mm/yyyy');"/>
						--%>
						<input id="id_fecha" name="fechaStr"  type="text" size="8" value='<bean:write name="apunteForm" property="fechaStr"/>' readonly="readonly"/>
						
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.tipo" /></td>
					<td>
						<html:select styleId="id_combo_tipo" name="apunteForm" property="tipoApunte" style="width:100px">
							<html:option  value="INGRESO" key="apunte.formulario.tipo.ingreso" />
							<html:option  value="GASTO" key="apunte.formulario.tipo.gasto" />
						</html:select>
					</td>
					<td style="font-size:75%;">
					<html:img page="/img/icono_info.png" width="15px" height="15px" align="top"  titleKey="apunte.formulario.traspaso.info"/>
					<bean:message key="apunte.formulario.traspaso" />
						<logic:equal name="apunteForm" property="traspaso" value="S">
							<input type="checkbox" name="traspaso" value="S" checked="checked"/>
						</logic:equal>
						<logic:notEqual name="apunteForm" property="traspaso" value="S">
							<input type="checkbox" name="traspaso" value="S"/>
						</logic:notEqual>
					
					</td>
					
				</tr>
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.listado.contabilidades" /></td>
					<td>
						<bean:define id="contabilidadesCollection" name="apunteForm" property="lstContabilidades" />
						<html:select styleId="id_combo_contabilidad" name="apunteForm" property="idContabilidad" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
							<logic:present name="apunteForm" property="lstContabilidades">
								<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
							</logic:present>
						</html:select>
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.listado.cuentas" /></td>
					<td colspan="2">
						<bean:define id="cuentasCollection" name="apunteForm" property="lstCuentas" />
					
						<html:select styleId="id_combo_cuenta" name="apunteForm" property="idCuenta" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.cuenta" />
								<logic:present name="apunteForm" property="lstCuentas">
									<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
								</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.listado.grupos" /></td>
					<td>
						<bean:define id="gruposCollection" name="apunteForm" property="lstGrupos" />
					
						<html:select styleId="id_combo_grupo" name="apunteForm" property="idGrupo" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.grupo" />
								<logic:present name="apunteForm" property="lstGrupos">
									<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
								</logic:present>
						</html:select>
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.listado.clases" /></td>
					<td colspan="2">
						<bean:define id="clasesCollection" name="apunteForm" property="lstClases" />
				 	
						<html:select styleId="id_combo_clase" name="apunteForm" property="idClase" style="width:180px">
							<html:option  value="0" key="apunte.listado.seleccion.clase" />
								<logic:present name="apunteForm" property="lstClases">
									<html:options collection="clasesCollection"  property="idClase" labelProperty="nombre"/>
								</logic:present>
						</html:select>
					</td>
				</tr>

				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.importe"/></td>
					<td style="font-size:75%;">
						<input type="text" maxlength="9" size="9" id="id_importe" name="importeStr" style="text-align:center;" value='<bean:write name="apunteForm" property="importe" format=",##0.00"/>' >&nbsp; &euro;
					</td>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.concepto"/></td>
					<td colspan="2">
						<html:text name="apunteForm" property="concepto" maxlength="35" size="35" />
					</td>
					
				</tr>
				<tr>
					<td style="font-size:75%;"><bean:message key="apunte.formulario.descripcion"/></td>
					<td colspan="4" style="font-size:75%;">
					<html:textarea name="apunteForm" property="notas" rows="2" />
				</tr>
			</table>
		</fieldset>
		
	</div>

	<div class="botonera">
		<logic:notEqual name="apunteForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="apunteForm" property="accion" value="<%=insert%>">
					<bean:message key="boton.new" />
				</logic:equal>
				<logic:equal name="apunteForm" property="accion" value="<%=update%>">
					<bean:message key="boton.update" />
				</logic:equal>
				<logic:equal name="apunteForm" property="accion" value="<%=delete%>">
					<bean:message key="boton.delete" />
				</logic:equal>
				<logic:equal name="apunteForm" property="accion" value="<%=copy%>">
					<bean:message key="boton.new" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


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
	
	/************CALENDARIO****************/	
	//Configuro el calendario en español
	$j.datepicker.setDefaults($j.datepicker.regional['es']);
	//Calendario inicio
	$j( "#id_fecha" ).datepicker({
	      changeMonth: true,
          changeYear: true,
          showOn: "both",
				buttonImage: '<%=request.getContextPath()%>/img/calendar.gif',
        		buttonImageOnly: true,
        	 	buttonText:'<bean:message key="apunte.listado.calendario" />',
        		showOtherMonths: true,
        		selectOtherMonths: true,
        		showWeek: false,
        		firstDay: 1,
        		showButtonPanel: true
    });
	
});
</script>

<script type="text/javascript">
<%-- 
/**
Comento la funcion de reload utilizada con el evento onchange=reload(); desde los combos
para recargar la pagina porque ahora utilizo llamadas ajax para cargar los combos
*/
function reload(){
		document.getElementById('formulario').action="<%=request.getContextPath()%>/action/recargaApunte";
		document.getElementById('formulario').submit();
	}
--%>
	//Prototype
	if ( $('id_importe') != null){	Event.observe('id_importe', 'keypress', soloNumerosConComa);}

</script>
