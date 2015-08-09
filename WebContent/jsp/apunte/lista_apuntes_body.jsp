<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%-- <script language='Javascript' src='<%=request.getContextPath()%>/js/calendario/popcalendar.js'></script> --%>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.selectboxes.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/jquery.ui.core.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/jquery.ui.widget.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/jquery.ui.position.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/jquery.ui.menu.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/jquery.ui.autocomplete.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/jquery_apuntes.js"></script>

<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/jquery.ui.all.css">

	<style>
	
	.ui-autocomplete-loading {
		background: white url('<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/images/ui-anim_basic_16x16.gif') right center no-repeat;
	}

	.ui-draggable, .ui-droppable {
		background-position: top;
	}
	
	.ui-autocomplete {
        max-height: 100px;
        overflow-y: auto;
        /* prevent horizontal scrollbar */
        overflow-x: hidden;
    }
	
	</style>

<!-- Defino variables para referenciar los textos de los botones-->

<html:form styleId="formulario" action="/apunteRedirect" method="post">
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>

	<h1 class="pagetitle">
		<bean:message key="apunte.listado.titulo"/>
	</h1>
	
	<div id="flotante" class="contactForm">
	
		<fieldset id="id_filtros">
			<legend><bean:message key="apunte.listado.leyenda"/></legend>
				<p id="mensaje"></p>
				<table  style="margin:0 0 0 10px;width: 650px;">
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.finicio" /></td>
						<td>
							<%-- 
							<input id="fechaInicio" name="fechaInicioStr"  type="text" size="8" value='<bean:write name="listadoApuntesForm" property="fechaInicioStr"/>' />
							<html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fechaInicio, 'dd/mm/yyyy');"/>
							 --%>
							<input id="id_fecha_inicio" name="fechaInicioStr"  type="text" size="8" value='<bean:write name="listadoApuntesForm" property="fechaInicioStr"/>' readonly="readonly"/>
							<button style="font-size:60%;">A</button>
							<button style="font-size:60%;">A</button>
							<button style="font-size:60%;">M</button>
							<button style="font-size:60%;">M</button>
							
						</td>
						<td style="font-size:75%;"><bean:message key="apunte.listado.fhasta" /></td>
						<td>
							<%-- 
							<input id="fechaFin" name="fechaFinStr" type="text" size="8"  value='<bean:write name="listadoApuntesForm" property="fechaFinStr"/>' readonly="readonly" />
							<html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fechaFin, 'dd/mm/yyyy');"/> 
							--%>
							<input id="id_fecha_fin" name="fechaFinStr"  type="text" size="8" value='<bean:write name="listadoApuntesForm" property="fechaFinStr" />' readonly="readonly"/>
						</td>
					</tr>
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
					<tr>
						<td style="font-size:75%;"><bean:message key="apunte.listado.tabla.concepto"/></td>
						<td style="font-size:75%;">
							<div class="ui-widget">
								<html:text styleId="id_concepto" property="concepto" maxlength="35" size="35" />
								<a id="id_buscar" href="javascript:"><html:img  page="/img/search-icon.png" width="15px" height="15px" align="top" titleKey="apunte.listado.titulo.buscar" style="border:none"/></a>
							</div>  
						</td>
						<td style="font-size:75%;" colspan="2">  </td>
					</tr>
				</table>
		</fieldset>
		
	</div>
	<div id="imgAbrir" style="float:right; width:125px; font-size:110%;display: none">
		<a href="javascript:mostrardiv();"><bean:message key="apunte.listado.titulo.imgArriba" /><html:img page="/img/fechaAbajo.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.titulo.imgArriba" /></a>
	</div>
	<div id="imgCerrar"style="float:right; width:125px; font-size:110%; ">
		<a href="javascript:cerrar();"><bean:message key="apunte.listado.titulo.imgAbajo" /><html:img page="/img/fechaArriba.gif" width="15px" height="15px" align="top" titleKey="apunte.listado.titulo.imgAbajo" style="border:none"/></a>
	</div>
	
	<bean:define id="lstApuntes" name="listadoApuntesForm" property="lstApuntesForm" />
	<bean:size id="size" name="lstApuntes"/>
	<div id="cargando" style="text-align: center; padding-top: 50px;">
		<p><img src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/images/cargando.gif" /></p>
	</div>
	<div id="id_tableContainer" class="tableContainer">
		
		<table id="id_tabla_resumen" class="resumen" border="0" style="font-size: 120%;background-color: #AFAFAF;color:white;">	</table>
		
		 <%-- 
		 <logic:notEmpty name="listadoApuntesForm" property="lstApuntesForm">
			
				<table id="id_tabla_resumen" class="resumen" border="0" style="font-size: 120%;background-color: #AFAFAF;color:white;">
					<tr>
						<td  style="text-align: right;width: 80px;"><bean:message key="apunte.listado.napuntes" />:</td>
						<td id="id_napuntes" style="padding-left:5px; width: 20px;">
							<bean:write name="size"/>
						</td>
						
						<td style="text-align: right;width: 80px;"><bean:message key="apunte.listado.ingreso" />:</td>
						<td id="id_ingresos" style="padding-left:5px; width: 110px;">
							<bean:write name="listadoApuntesForm" property="totalIngresos" format=",##0.00"/>&nbsp; &euro;
						</td>
						<td style="text-align: right;width: 55px;"><bean:message key="apunte.listado.gastos" />:</td>
						<td id="id_gastos" style="padding-left:5px;width: 110px;">
							<bean:write name="listadoApuntesForm" property="totalGastos" format=",##0.00"/>&nbsp; &euro;
						</td>
						<td style="text-align: right;width: 100px;font-weight: bold;"><bean:message key="apunte.listado.diferencia" />:</td>
						<td id="id_balance" style="padding-left:5px;width: 130px;font-weight: bold;">
							 <bean:write name="listadoApuntesForm" property="totalBalance" format=",##0.00"/>&nbsp; &euro;
						</td>
						
						<td style="background-color: white;"><a  href="javascript:abrirPdf();"><html:img page="/img/file-extension-pdf-icon.png" width="25px" height="25px" align="top" titleKey="abrir.pdf.titulo" style="border:none"/></a></td>
						<td style="background-color: white;"><a  href="javascript:abrirXls();"><html:img page="/img/file-extension-xls-icon.png" width="25px" height="25px" align="top" titleKey="abrir.xls.titulo" style="border:none"/></a></td>
					</tr>
				</table>
			
	</logic:notEmpty> 
	
		--%>	
		
	
		<table id="id_tabla_apuntes" class="scrollTable">
	    	<%-- 
	    	<thead class="fixedHeader">
	          	<tr>
	           		<th scope="col" class="cod"><input type="checkbox" name="all" id="all" onclick="checkAll();" /></th>
	           		<th scope="col" class="center" style="width: 60px;"><bean:message key="apunte.listado.tabla.fecha"/></th>
	           		<th scope="col" style="width: 190px;"><bean:message key="apunte.listado.tabla.concepto"/></th>
	           		<th scope="col" style="width: 100px;"><bean:message key="apunte.listado.tabla.grupo"/></th>
	           		<th scope="col" style="width: 100px;"><bean:message key="apunte.listado.tabla.clase"/></th>
	           		<th scope="col" class="center" style="width: 70px;" ><bean:message key="apunte.listado.tabla.importe"/></th>
	           		<th scope="col" class="center" style="width: 70px;"><bean:message key="apunte.listado.tabla.saldo"/></th>
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
						<td >
							<input type="checkbox" name="ids" value='<bean:write name="apunte" property="id"/>' />
						</td>
						
						<td style="<%=gasto_ingreso%>;width: 20px;">
							<bean:write name="apunte" property="fecha" formatKey="patron.fecha.corta"/>
						</td>
						<td style="<%=gasto_ingreso%>;width: 210px;">
							<bean:write name="apunte" property="conceptoAbreviado" />
						</td>
						<td  style="<%=gasto_ingreso%>;width: 100px;" >
							<bean:write name="apunte" property="nombreGrupo" />
						</td>
						<td  style="<%=gasto_ingreso%>;width: 100px;">
							<bean:write name="apunte" property="nombreClase"/>
						</td>
						<td class="right" style="<%=gasto_ingreso%>;width: 75px;" >
							<bean:write name="apunte" property="importe" format=",##0.00"/>&nbsp; &euro;
						</td>
						<td  class="right" style="<%=gasto_ingreso_saldo%>;width: 75px;" >
							<bean:write name="apunte" property="saldo" format=",##0.00"/>&nbsp; &euro;
						</td>
						
					</tr>
				</logic:iterate>
				</tbody>
				--%>
			</table>

			</div>
	<br>

	<div class="botonera">
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.new"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.update"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.delete"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.copy"/>
		</html:submit>
		<html:submit property="accion" styleClass="boton">
			<bean:message key="boton.organizar"/>
		</html:submit>
	</div>
	
</html:form>

<script type="text/javascript">
	function reload(){
		var accion="<%=request.getContextPath()%>/action/listarApuntes?idContabilidad="+document.getElementById("id_combo_contabilidad").value;
		document.getElementById('formulario').action=accion;
		document.getElementById('formulario').submit();
	}
	
	function abrirPdf(){
		var accion="<%=request.getContextPath()%>/action/listarApuntes?accion=pdf";
		document.getElementById('formulario').action=accion;
		document.getElementById('formulario').submit();
	}
	
	function abrirXls(){
		var accion="<%=request.getContextPath()%>/action/listarApuntes?accion=xls";
		document.getElementById('formulario').action=accion;
		document.getElementById('formulario').submit();
	}
	
	function mostrardiv() {
		div = document.getElementById("flotante");
		div.style.display = "";
		
		imgCerrar = document.getElementById("imgCerrar");
		imgCerrar.style.display="";
	
		imgAbrir= document.getElementById("imgAbrir");
		imgAbrir.style.display = "none";
	}

	function cerrar() {
		div = document.getElementById("flotante");
		div.style.display="none";

		imgCerrar = document.getElementById("imgCerrar");
		imgCerrar.style.display="none";
		
		imgAbrir= document.getElementById("imgAbrir");
		imgAbrir.style.display = "";
	}
	
    function checkAll() {
        var nodoCheck = document.getElementsByTagName("input");
        var varCheck = document.getElementById("all").checked;
        for (i=0; i<nodoCheck.length; i++){
            if (nodoCheck[i].type == "checkbox" && nodoCheck[i].name != "all" && nodoCheck[i].disabled == false) {
                nodoCheck[i].checked = varCheck;
            }
        }
    }
</script>
<script type="text/javascript">
var $j = jQuery.noConflict();


$j(document).ready(function (){
	
	$j("#cargando").on("ajaxStart", function(){
	    $j(this).show(); // this hace referencia a la div con la imagen.
	    $j('#id_tableContainer').hide();
	}).on("ajaxStop", function(){
	    $j(this).hide();
	    $j('#id_tableContainer').show();
	});
	
	var url_destino = "<%=request.getContextPath()%>/action/recargarApunteJson";
	
	/****** AUTOCOMPLETE DEL CAMPO CONCEPTO       ******/
	var pathautocomplete="<%=request.getContextPath()%>/action/autocompletarConceptos";
	$j( "#id_concepto" ).autocomplete({
		source: function( request, response ) {
          			$j.getJSON(pathautocomplete+'?'+recogerParametros()+'&term='+request.term,
          					function(data){
          			  			response($j.map(data.lstConceptos, function (item) {
                         			 return {
			                       		//Indicamos el Valor
			                               value: item.concepto,
			                               //el Label si lo desean
			                              // label: item.Nombre,
			                               //y el ID
			                               label: item.concepto
                           				}
                      		 }))
          				})
     				},
     			
       
       /* Otra forma de hacer la llamada ajax
        //Los datos, que son invocado mediante JQuery ajax
        source: function (request, response) {
        	$j.ajax({
            //Ruta donde buscar los datos
                url: pathautocomplete+'?'+recogerParametros(),
                //Parametros con sus valores a pasar a la url
                data: { term: $j("#id_concepto").val() },
                //Tipo de Dato devuelto
                dataType: "json",
                //Si todo esta ok, construye los datos a mostrar en el textbox
                success: function (data) {
                    response($j.map(data.lstConceptos, function (item) {
                       return {
                    		//Indicamos el Valor
                            value: item.id,
                            //el Label si lo desean
                           // label: item.Nombre,
                            //y el ID
                            label: item.concepto
                        }
                    }))
                }
            })
        },*/
		minLength: 0,
		
		//Cuando seleccione un valor muestra su resultado
        select: function (event, ui) {
        	
        	$j.getJSON(url_destino+'?'+recogerParametros()+'&term='+ui.item.value,
        			function(data){ 
        				cargarTablaResumen(data);
    		});
        }
		
	});
	
	/*************BOTON DE BUSQUEDA JUNTO AL CAMPO DE CONCEPTO*/
	//Boton buscar
	$j( "#id_buscar").click(function(){
		$j.getJSON(url_destino+'?'+recogerParametros(),
				function(data){ 
					cargarTablaResumen(data);
		});
	});	
	
	
	/******** LLAMADA A LA CLASE QUE NOS DEVUELVE UN JSON CON LOS DATOS DE LA TABLA APUNTES */
	
	//Cuando carga la pagina hacemos la llamada por si tenemos que cargar la tabla con apuntes
	$j(window).load(function () {
		var path=url_destino+'?'+recogerParametros();
		
		$j.getJSON(path,
			function(data){
			cargarTablaResumen(data);
		});
	});
	
			
	/******* RECARGA DE COMBOS ************************************************************/
	$j('#id_combo_contabilidad').change(function (event){
		var path=url_destino+'?'+recogerParametros();
		
		$j.getJSON(path,
			function(data){ 
			
			//Elimino todos los options del combos
			$j('#id_combo_cuenta').removeOption(/./);
			//Añado en primer lugar en la opcion 0 el valor del properties 
			$j('#id_combo_cuenta').addOption('0', '<bean:message key="apunte.listado.seleccion.cuenta"/>');
			
			//Cargo el combo recorriendo los valores del json
			if (data.lstCuentas!=undefined){
				$j.each(data.lstCuentas, function(i,cuenta){
					$j('#id_combo_cuenta').addOption(cuenta.id, cuenta.label);
			    });
			}
			
			cargarTablaResumen(data);
			
			//Dejo seleccionada la opcion 0
			$j('#id_combo_cuenta').selectOptions('0');
		});		
	});
		
	$j('#id_combo_grupo').change(function (event){
		var path=url_destino+'?'+recogerParametros();
		$j.getJSON(path,
			function(data){  
			
			//Elimino todos los options del combos
			$j('#id_combo_clase').removeOption(/./);
			//Añado en primer lugar en la opcion 0 el valor del properties 
			$j('#id_combo_clase').addOption('0', '<bean:message key="apunte.listado.seleccion.clase"/>');
			//Cargo el combo recorriendo los valores del json
			
			if (data.lstClases!=undefined){
				$j.each(data.lstClases, function(i,clase){
						$j('#id_combo_clase').addOption(clase.id, clase.label);
				    });
			}
			cargarTablaResumen(data);
					
			//Dejo seleccionada la opcion 0
			$j('#id_combo_clase').selectOptions('0');
		});
	});
	
	$j('#id_combo_cuenta').change(function (event){
		var path=url_destino+'?'+recogerParametros();
		$j.getJSON(path,
			function(data){  
				cargarTablaResumen(data); 
		});
	});
	
	
	$j('#id_combo_clase').change(function (event){
		var path=url_destino+'?'+recogerParametros();
		$j.getJSON(path,
			function(data){
				cargarTablaResumen(data);
		});
	});
	/******************* CALENDARIOS *************************************************/
	
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
        		showButtonPanel: true,
        		onSelect: function(textoFecha, objDatepicker){
        			var path=url_destino+'?'+recogerParametros();
        			$j.getJSON(path,
        				function(data){
        					cargarTablaResumen(data);
        			});
				}
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
      		showButtonPanel: true,
      		onSelect: function(textoFecha, objDatepicker){
      			var path=url_destino+'?'+recogerParametros();
      			$j.getJSON(path,
      				function(data){
      					cargarTablaResumen(data);
      			});
			}
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
               var factual= new Date();
               var f =  new Date(factual.getFullYear(), (factual.getMonth()),factual.getDate(), 0, 0, 0, 0);
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
	
	/******************* FUNCIONES COMUNES *******************************************/
	
	function recogerParametros(){
		var paramFechaIni='fechaInicio='+$j('#id_fecha_inicio').val();
		var paramFechaFin='&fechaFin='+$j('#id_fecha_fin').val();
		var paramContabilidad ='&idContabilidad='+$j('#id_combo_contabilidad').val();
		var paramCuenta ='&idCuenta='+$j('#id_combo_cuenta').val();
		var paramGrupo ='&idGrupo='+$j('#id_combo_grupo').val();
		var paramClase ='&idClase='+$j('#id_combo_clase').val();
		var paramConcepto ='&idConcepto='+$j('#id_concepto').val();
		var parametros=paramFechaIni+paramFechaFin+paramContabilidad+paramCuenta+paramGrupo+paramClase+paramConcepto;
		
		return parametros;
	} 
	
	function cargarTablaResumen(data) {
		
		var	rows_resumen=
				'<tr id="id_rows_resumen">'+
					'<td  style="text-align: right;width: 80px;"><bean:message key="apunte.listado.napuntes" />:</td>'+
					'<td id="id_napuntes" style="padding-left:5px; width: 20px;">'+	data.resumen.numApuntes+'</td>'+
					'<td style="text-align: right;width: 80px;"><bean:message key="apunte.listado.ingreso" />:</td>'+
					'<td id="id_ingresos" style="padding-left:5px; width: 110px;">'+data.resumen.ingresos+'&nbsp; &euro;</td>'+
					'<td style="text-align: right;width: 55px;"><bean:message key="apunte.listado.gastos" />:</td>'+
					'<td id="id_gastos" style="padding-left:5px;width: 110px;">'+data.resumen.gastos+'&nbsp; &euro;</td>'+
					'<td style="text-align: right;width: 80px;font-weight: bold;"><bean:message key="apunte.listado.diferencia" />:</td>'+
					'<td id="id_balance" style="padding-left:5px;width: 100px;font-weight: bold;">'+data.resumen.balance+'&nbsp; &euro;</td>'+
					'<td style="background-color: white;"><a  href="javascript:abrirPdf();"><html:img page="/img/file-extension-pdf-icon.png" width="25px" height="25px" align="top" titleKey="abrir.pdf.titulo" style="border:none"/></a></td>'+
					'<td style="background-color: white;"><a  href="javascript:abrirXls();"><html:img page="/img/file-extension-xls-icon.png" width="25px" height="25px" align="top" titleKey="abrir.xls.titulo" style="border:none"/></a></td>'+
			'</tr>';
			
		var	rows_apuntes_cabecera=
				'<thead id="id_rows_apuntes_cabecera" class="fixedHeader">'+
          			'<tr>'+
           				'<th scope="col" class="cod"><input type="checkbox" name="all" id="all" onclick="checkAll();" /></th>'+
           				'<th scope="col" class="center" style="width: 60px;"><bean:message key="apunte.listado.tabla.fecha"/></th>'+
           				'<th scope="col" style="width: 170px;"><bean:message key="apunte.listado.tabla.concepto"/></th>'+
		           		'<th scope="col" style="width: 100px;"><bean:message key="apunte.listado.tabla.grupo"/></th>'+
		           		'<th scope="col" style="width: 100px;"><bean:message key="apunte.listado.tabla.clase"/></th>'+
		           		'<th scope="col" class="center" style="width: 80px;" ><bean:message key="apunte.listado.tabla.importe"/></th>'+
		           		'<th scope="col" class="center" style="width: 80px;"><bean:message key="apunte.listado.tabla.saldo"/></th>'+
		           	'</tr>'+
       			'</thead>'+
				'<tbody id="id_body_apuntes" class="scrollContent">';
			
			if($j('#id_rows_resumen').length>0){
				//alert('elimino rows resumen');
				$j('#id_rows_resumen').remove();
				if($j('#id_rows_apuntes_cabecera').length>0){
					//alert('elimino rows apuntes');
					$j('#id_rows_apuntes_cabecera').remove();
					$j('#id_body_apuntes').remove();
				}
			}
			if(data.resumen.numApuntes!=undefined){
				//alert('inserto rows resumen');
				$j('#id_tabla_resumen').append(rows_resumen);
			}
			//alert('fin resumen');
			
			var rows_apuntes="";
			
			$j.each(data.lstApuntes, function(i,apunte){
					var trcolor='impar';
					var tdcolorapunte='';
					var tdcolorsaldo='';
					if (i%2==0)	trcolor='par';
					if (apunte.tipoApunte=='GASTO')tdcolorapunte='color:red';
					if (apunte.saldoStr<0)tdcolorsaldo='color:red';
					
				
				rows_apuntes=rows_apuntes+'<tr class="'+trcolor+'">'+
					'<td >'+
						'<input type="checkbox" name="ids" value='+apunte.id+' />'+
					'</td>'+
					'<td style="width: 20px;'+tdcolorapunte+'">'+
						apunte.fechaStr+
					'</td>'+
					'<td style="width: 210px;'+tdcolorapunte+'">'+
						apunte.concepto+
					'</td>'+
					'<td  style="width: 100px;'+tdcolorapunte+'" >'+
						apunte.nombreGrupo+
					'</td>'+
					'<td  style="width: 100px;'+tdcolorapunte+'">'+
						apunte.nombreClase+
					'</td>'+
					'<td class="right" style="width: 75px;'+tdcolorapunte+'" >'+
						apunte.importeStr+ '&nbsp; &euro;'+
					'</td>'+
					'<td  class="right" style="width: 75px;'+tdcolorsaldo+'" >'+
						apunte.saldoStr+ '&nbsp; &euro;'+
					'</td>'+
				'</tr>';
				
			    });
				+				
			'</tbody>';

			if(data.lstApuntes.length>0){
				//alert('inserto rows apuntes');
				$j('#id_tabla_apuntes').append(rows_apuntes_cabecera);
				$j('#id_tabla_apuntes').append(rows_apuntes);
			}
	}
	
});
</script>
