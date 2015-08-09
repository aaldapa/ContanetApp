<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>

<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxcore.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxchart.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxdata.js"></script>
<script language='Javascript'  src="<%=request.getContextPath()%>/scripts/jqwidgets/gettheme.js"></script>
<script language='Javascript'  src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxbuttons.js"></script>
<script language='Javascript'  src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxscrollbar.js"></script>
<script language='Javascript'  src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxlistbox.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jqwidgets/jqxdropdownlist.js"></script>
 
<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jqwidgets/styles/jqx.base.css" />
<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/jquery.ui.all.css">

<!-- Defino variables para referenciar los textos de los botones-->
<bean:define id="save">
	<bean:message key="boton.guardar" />
</bean:define>

<html:form  styleId="formulario" action="/volverSeleccionAnalisis" method="post">
<html:hidden property="id"/>
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>

	<h1 class="pagetitle">
		<bean:message key="grafica.formulario.titulo"/>
	</h1>
	
	<div class="contactForm">
	
	<fieldset id="id_filtros">
			<legend><bean:message key="grafica.formulario.leyenda"/></legend>
			<table  style="margin:0 0 0 10px;width: 650px;">
				
				<tr>
					<td style="font-size:75%;"><bean:message key="grafica.formulario.contabilidades" /></td>
					<td>
						<bean:define id="contabilidadesCollection" name="analisisForm" property="lstContabilidades" />
						<html:select styleId="id_combo_contabilidad" name="analisisForm" property="idContabilidad" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
							<logic:present name="analisisForm" property="lstContabilidades">
								<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
							</logic:present>
						</html:select>
					</td>
					<td style="font-size:75%;"><bean:message key="grafica.formulario.cuentas" /></td>
					<td>
					<logic:present name="analisisForm" property="lstCuentas">
						<bean:define id="cuentasCollection" name="analisisForm" property="lstCuentas" />
					</logic:present>
						<html:select styleId="id_combo_cuenta" name="analisisForm" property="idCuenta" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.cuenta" />
								<logic:present name="analisisForm" property="lstCuentas">
									<html:options collection="cuentasCollection"  property="idCuenta" labelProperty="notas"/>
								</logic:present>
						</html:select>
					</td>
				</tr>
				<tr>
					<td style="font-size:75%;"><bean:message key="grafica.formulario.anios" /></td>
					<td>
						<bean:define id="aniosCollection" name="analisisForm" property="lstAnios" />

						<html:select styleId="id_anio" name="analisisForm" property="anio" style="width:100px">
							<html:options collection="aniosCollection" property="id" labelProperty="label"/>
						</html:select>
					</td>
					<td style="font-size:75%;">
						<bean:message key="apunte.listado.grupos" />
					</td>
					<td>
						<bean:define id="gruposCollection" name="analisisForm" property="lstGrupos" />
					
						<html:select styleId="id_combo_grupo" name="analisisForm" property="idGrupo" style="width:180px" >
							<html:option  value="0" key="apunte.listado.seleccion.grupo" />
								<logic:present name="analisisForm" property="lstGrupos">
									<html:options collection="gruposCollection"  property="idGrupo" labelProperty="nombre"/>
								</logic:present>
						</html:select>
					</td>
				</tr>

			</table>
		</fieldset>
		
	</div>
	
		<div id='jqxChart' style="width:700px; height:400px; position: relative; left: 0px; top: 10px; margin-bottom: 10px;"></div>
	
		<div class="botonera">
			<html:submit property="accion" styleClass="boton">
				<bean:message key="boton.back"/> 
			</html:submit>
		</div>
	
</html:form>

<script type="text/javascript">
//jquery
var $j = jQuery.noConflict();
	
$j(document).ready(function (){
	
	
	/******* RECARGA DE COMBOS ************************************************************/
	var url_recargaCombos = "<%=request.getContextPath()%>/action/recargarCombo";
	
	$j('#id_combo_contabilidad').change(function (event){
	
		var dataenvio ='idContabilidad='+$j('#id_combo_contabilidad').val();
		var path=url_recargaCombos+'?'+dataenvio;
				
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
		var dataenvio ="";
		cargarGrafica(dataenvio); 
	});
	
	$j('#id_combo_cuenta').change(function (event){
		var dataenvio =recogerParametros();
		cargarGrafica(dataenvio); 
	});
	
	$j('#id_anio').change(function (event){
		var dataenvio =recogerParametros();
		cargarGrafica(dataenvio); 
	});
	
	$j('#id_combo_grupo').change(function (event){
		var dataenvio =recogerParametros();
		cargarGrafica(dataenvio); 
	});
	
	/************ CONSTRUCCION GRAFICA ********************/
	
	// prepare chart data as an array	
	var url_grafica = "<%=request.getContextPath()%>/action/recargarAnalisis";
	var dataenvio =recogerParametros();
	var path=url_grafica+'?'+dataenvio;
	
	var source = {  datafields: [{ name: 'id' },{ name: 'clase' },{ name: 'anio' },{ name: 'ingresos' },{ name: 'gastos' },{ name: 'diferencias' }],
		datatype: "json",
		url: path
	};
    
    var dataAdapter = new $j.jqx.dataAdapter(source, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error);} });
    // prepare jqxChart settings
    var settings = {
		title: "Gráfica de gastos e ingresos",
        description: "Agrupación por clases",
        showLegend: true,
        enableAnimations: true,
        padding: { left: 5, top: 5, right: 5, bottom: 5 },
        titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
        source: dataAdapter,
        
        categoryAxis:
            {
        		dataField: 'clase',
                textRotationAngle: 90,
                //unitInterval: 1,
                showGridLines: true,
                //gridLinesInterval: 3,
               //gridLinesColor: '#888888',
                axisSize: 'auto'
            },
        colorScheme: 'scheme01',
        seriesGroups:
            [
                {
                    type: 'column',
                    orientation: 'horizontal',
                    //columnsGapPercent: 50,
                    toolTipFormatSettings: {sufix:' euros', decimalSeparator:',',thousandsSeparator: '.'},
                    
                    //seriesGapPercent: 5,
                   	formatSettings: { decimalSeparator:',',thousandsSeparator: '.'},	
                    valueAxis:
                    { 
                    	//axisSize: 'auto',
                    	orientation:'horizontal',
                    	flip:false,
                    	textRotationAngle: 0,
                    	unitInterval: 1000,
                        displayValueAxis: true,
                        useGradient: true,
                        //minValue:0,
                        formatSettings: { decimalSeparator:',',thousandsSeparator: '.'},
                        description: 'Cantidades en euros'
                    },
                    series: [
                             //{ dataField: 'ingresos', displayText: 'Ingresos'} ,
                             //{ dataField: 'gastos', displayText: 'Gastos'} ,
                             { dataField: 'diferencias', displayText: 'Balance', showLabels: true} 
                        ]
                }
            ]
    };
     // setup the chart
    $j('#jqxChart').jqxChart(settings);
    
/******************* FUNCIONES COMUNES *******************************************/
	
	function recogerParametros(){
		var paramAnio='anio='+$j('#id_anio').val();
		var paramTipo='&tipoGrafica=clases';
		var paramGrupo='&idGrupo='+$j('#id_combo_grupo').val();
		var paramContabilidad ='&idContabilidad='+$j('#id_combo_contabilidad').val();
		var paramCuenta ='&idCuenta='+$j('#id_combo_cuenta').val();
		var parametros=paramAnio+paramTipo+paramGrupo+paramContabilidad+paramCuenta;
		
		return parametros;
	} 
    
    function cargarGrafica(dataenvio){
    	var url_destino = "<%=request.getContextPath()%>/action/recargarAnalisis";
    	var path=url_destino+'?'+dataenvio;
    	
    	var source = { datafields: [{ name: 'id' },{ name: 'clase' },{ name: 'anio' },{ name: 'ingresos' },{ name: 'gastos' },{ name: 'diferencias' }],
    			datatype: "json",
    			url: path 
    		};
    	var dataAdapter = new $j.jqx.dataAdapter(source);
        $j('#jqxChart').jqxChart({ source: dataAdapter });
    }
    
});
</script>