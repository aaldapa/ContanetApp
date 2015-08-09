<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%-- <script language='Javascript' src='<%=request.getContextPath()%>/js/calendario/popcalendar.js'></script> --%>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>

<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/jquery.ui.all.css">
<!-- Defino variables para referenciar los textos de los botones-->

<html:form styleId="formulario" action="/crearInforme" method="post">
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>

	<h1 class="pagetitle">
		<bean:message key="informe.formulario.titulo"/>
	</h1>
	
	<div class="contactForm">
	<fieldset>
			<legend><bean:message key="informe.formulario.leyenda"/></legend>
				
				<table  style="margin:0 0 0 10px;width: 650px;">
					<tr>
						<td style="font-size:75%;"><bean:message key="informe.listado.tipo"/></td>
						<td style="font-size:75%;"><bean:message key="informe.listado.grafica"/> <html:radio property="detallado" value="false"/></td>
						<td style="font-size:75%;"><bean:message key="informe.listado.detalle"/> <html:radio property="detallado" value="true"/>  </td>
						<td style="height:30px;"></td>
					</tr>
										
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.finicio" /></td>
						<td>
							<%-- <input id="fechaInicio" name="fechaInicioStr"  type="text" size="8" value='<bean:write name="informeForm" property="fechaInicioStr"/>' readonly="readonly" />
							<html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fechaInicio, 'dd/mm/yyyy');"/> --%>
							<input id="id_fecha_inicio" name="fechaInicioStr"  type="text" size="8" value='<bean:write name="informeForm" property="fechaInicioStr" />' readonly="readonly"/>
							<button style="font-size:60%;">A</button>
							<button style="font-size:60%;">A</button>
							<button style="font-size:60%;">M</button>
							<button style="font-size:60%;">M</button>
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.fhasta" /></td>
						<td>
							<%-- <input id="fechaFin" name="fechaFinStr" type="text" size="8"  value='<bean:write name="informeForm" property="fechaFinStr"/>' readonly="readonly" />
							<html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fechaFin, 'dd/mm/yyyy');"/> --%>
							<input id="id_fecha_fin" name="fechaFinStr"  type="text" size="8" value='<bean:write name="informeForm" property="fechaFinStr" />' readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.contabilidades" /></td>
						<td>
							<bean:define id="contabilidadesCollection" name="informeForm" property="lstContabilidades" />
							<html:select styleId="id_combo_contabilidad" name="informeForm" property="idContabilidad" style="width:180px" onchange="reload()">
								<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
								<logic:present name="informeForm" property="lstContabilidades">
									<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
								</logic:present>
							</html:select>
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.cuentas" /></td>
						<td>
							<bean:define id="cuentasCollection" name="informeForm" property="lstCuentas" />
					
							<html:select styleId="id_combo_cuenta" name="informeForm" property="idCuenta" style="width:180px" onchange="reload()">
								<html:option  value="0" key="apunte.listado.seleccion.cuenta" />
									<logic:present name="informeForm" property="lstCuentas">
										<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
									</logic:present>
							</html:select>
						</td>
					</tr>
					
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.grupos" /></td>
						<td>
							<bean:define id="gruposCollection" name="informeForm" property="lstGrupos" />
					
							<html:select styleId="id_combo_grupo" name="informeForm" property="idGrupo" style="width:180px" onchange="reload()">
								<html:option  value="0" key="apunte.listado.seleccion.grupo" />
									<logic:present name="informeForm" property="lstGrupos">
										<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
									</logic:present>
							</html:select>
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.clases" /></td>
						<td>
							<bean:define id="clasesCollection" name="informeForm" property="lstClases" />
				 	
							<html:select styleId="id_combo_clase" name="informeForm" property="idClase" style="width:180px" onchange="reload()">
								<html:option  value="0" key="apunte.listado.seleccion.clase" />
									<logic:present name="informeForm" property="lstClases">
										<html:options collection="clasesCollection"  property="idClase" labelProperty="nombre"/>
									</logic:present>
							</html:select>
						</td>
						
					</tr>
					
					
				</table>
		</fieldset>
	</div>
<br>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.informe"/>
		</html:submit>
	</div>

</html:form>

<script type="text/javascript">
<%-- 	function reload(){
		var accion="<%=request.getContextPath()%>/action/informe";
		document.getElementById('formulario').action=accion;
		document.getElementById('formulario').submit();
	} --%>
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
	
	/************CALENDARIO****************/	
	//Configuro el calendario en español
	$j.datepicker.setDefaults($j.datepicker.regional['es']);
	//Calendario inicio
	$j( "#id_fecha_inicio" ).datepicker({
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
	
	//Calendario fin
	$j( "#id_fecha_fin" ).datepicker({
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
	
	/******************* BOTONERA SELECCION DE FECHAS ***********************************/
	$j( "button:first" )
            .button({
            	icons: {
            		 secondary: "ui-icon-triangle-2-e-w"
            	}
			}).click(function( event ) {
               event.preventDefault();
               var f = new Date();
               $j("#id_fecha_inicio").val('01/01/'+f.getFullYear());
               $j("#id_fecha_fin").val('31/12/'+f.getFullYear());
               var path=url_destino+'?'+recogerParametros();
              
               $j.getJSON(path,
     				function(data){
     					cargarTablaResumen(data);
     			});
            })
            .next().button({
                icons: {
                    secondary: "ui-icon-triangle-1-w"
                }
            }).click(function( event ) {
            	event.preventDefault();
                var f = new Date();
                $j("#id_fecha_inicio").val('01/01/'+(f.getFullYear()-1));
                $j("#id_fecha_fin").val('31/12/'+(f.getFullYear()-1));
                var path=url_destino+'?'+recogerParametros();
               
                $j.getJSON(path,
      				function(data){
      					cargarTablaResumen(data);
      			});
            })
            .next().button({
                icons: {
                    secondary: "ui-icon-triangle-2-e-w"
                }
            }).click(function( event ) {
               event.preventDefault();
               var f = new Date();
               var mes=f.getMonth()+1;
               //calculo del ultimo dia del mes
               f.setTime(f.getTime() + ((32 - f.getDate()) * 86400000) );
               f.setTime(f.getTime() - (f.getDate() * 86400000) );
                              
               if (mes<10)
            	   mes='0'+mes;              
               $j("#id_fecha_inicio").val('01/'+mes+'/'+f.getFullYear());
               $j("#id_fecha_fin").val(f.getDate()+'/'+mes+'/'+f.getFullYear());
               var path=url_destino+'?'+recogerParametros();
              
               $j.getJSON(path,
     				function(data){
     					cargarTablaResumen(data);
     			});
            })
            .next().button({
                icons: {
                    secondary: "ui-icon-triangle-1-w"
                }
            }).click(function( event ) {
               event.preventDefault();
               var factual= new Date();
               var f =  new Date(factual.getFullYear(), (factual.getMonth()-1),factual.getDate(), 0, 0, 0, 0);
               
               var mes=f.getMonth()+1;
               //calculo del ultimo dia del mes
               f.setTime(f.getTime() + ((32 - f.getDate()) * 86400000) );
               f.setTime(f.getTime() - (f.getDate() * 86400000) );
                              
               if (mes<10)
            	   mes='0'+mes;     
               
               $j("#id_fecha_inicio").val('01/'+mes+'/'+f.getFullYear());
               $j("#id_fecha_fin").val(f.getDate()+'/'+mes+'/'+f.getFullYear());
               var path=url_destino+'?'+recogerParametros();
              
               $j.getJSON(path,
     				function(data){
     					cargarTablaResumen(data);
     			});
            });
	
});
</script>