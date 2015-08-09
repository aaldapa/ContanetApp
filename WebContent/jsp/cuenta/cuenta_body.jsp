<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%-- <script language='Javascript' src='<%=request.getContextPath()%>/js/calendario/popcalendar.js'></script> --%>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
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
<bean:define id="insert">
	<bean:message key="cuentas.listado.new" />
</bean:define>
<bean:define id="view">
	<bean:message key="cuentas.listado.view" />
</bean:define>
<bean:define id="update">
	<bean:message key="cuentas.listado.update" />
</bean:define>
<bean:define id="delete">
	<bean:message key="cuentas.listado.delete" />
</bean:define>
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define> 

<html:html>

<html:form styleId="formulario" action="/cuenta">
<html:hidden property="idUsuario" value="<%=idUsuario%>"/>
<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="cuenta.formulario.titulo.Nuevo"/>
		&nbsp;<bean:write name="cuentaForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<p>
			 	<label class="left">
			 		<bean:message key="cuenta.formulario.fapertura"/>
			 		<%-- <html:img page="/img/calendar.gif" width="15px" height="15px" align="top" style="border:none" titleKey="apunte.listado.calendario" onclick="popUpCalendar(this, formulario.fecha, 'dd/mm/yyyy');"/> --%>
			 	</label>
				<%-- <input id="fecha" name="fechaAperturaStr"  type="text" size="8" value='<bean:write name="cuentaForm" property="fechaAperturaStr"/>' readonly="readonly" />	 --%>
				<input id="id_fecha" name="fechaAperturaStr"  type="text" size="8" value='<bean:write name="cuentaForm" property="fechaAperturaStr"/>' readonly="readonly"/>
			</p>
			
			<p>	
				<label class="left"><bean:message key="cuenta.formulario.banco" /></label>
				
				<logic:present name="cuentaForm" property="lstBancos" >
				<bean:define id="bancosCollection" name="cuentaForm" property="lstBancos" />
				<html:select styleId="id_combo_banco" name="cuentaForm" property="idBanco" style="width:180px">
					<html:option  value="0" key="cuenta.formulario.seleccion.banco" />
					<logic:present name="cuentaForm" property="lstBancos">
						<html:options collection="bancosCollection"  property="idBanco" labelProperty="nombreBanco"/>
					</logic:present>
				</html:select>
				</logic:present>
			</p>
				<%-- 
				<div id='jqxWidget'></div>
				--%>
			
			<p>	
				<label class="left"><bean:message key="apunte.listado.contabilidades" /></label>
				
				<logic:present name="cuentaForm" property="lstContabilidades" >
				<bean:define id="contabilidadesCollection" name="cuentaForm" property="lstContabilidades" />
				<html:select styleId="id_combo_contabilidad" name="cuentaForm" property="idContabilidad" style="width:180px">
					<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
					<logic:present name="cuentaForm" property="lstContabilidades">
						<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
					</logic:present>
				</html:select>
				</logic:present>

			</p>
					
			 <p>
				<label class="left"><bean:message key="cuenta.formulario.dnititular"/></label>
				<html:text property="dniTitular" maxlength="12" size="10" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="cuenta.formulario.titular"/></label>
				<html:text property="titular" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="cuenta.formulario.dnititular"/>2</label>
				<html:text property="dniTitular2" maxlength="12" size="10" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="cuenta.formulario.titular2"/></label>
				<html:text property="titular2" maxlength="35" size="35" styleClass="field"/>
			</p>
			<p>
				<label class="left"><bean:message key="cuenta.formulario.saldo"/></label>
				<input type="text" id="id_importe" name="saldoInicialStr" maxlength="10" size="8"  value="<bean:write name="cuentaForm" property="saldoInicial" format=",##0.00" />" >	&euro;
			</p>
			<div style="margin-left: 114px">
				<p>
					<label ><bean:message key="cuenta.formulario.entidad"/></label>
					<label ><bean:message key="cuenta.formulario.sucursal"/></label>
					<label ><bean:message key="cuenta.formulario.dc"/></label>
					<label ><bean:message key="cuenta.formulario.numero"/></label>
				</p>
				<p>
					<html:text  property="entidad" maxlength="4" size="4"/>
					<html:text  property="sucursal" maxlength="4" size="4"/>
					<html:text  property="dc" maxlength="2" size="2" />
					<html:text  property="numCuenta" maxlength="10" size="10" />
				</p>
			</div>
			<p>
				<label class="left"><bean:message key="clase.formulario.descripcion" /></label>
				<html:textarea property="descripcion"	cols="30" rows="2" />
			</p>
		</fieldset>
	</div>
		
	<div class="botonera">
		<logic:notEqual name="cuentaForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="cuentaForm" property="accion" value="<%=insert%>">
					<bean:message key="cuentas.listado.new" />
				</logic:equal>
				<logic:equal name="cuentaForm" property="accion" value="<%=update%>">
					<bean:message key="cuentas.listado.update" />
				</logic:equal>
				<logic:equal name="cuentaForm" property="accion" value="<%=delete%>">
					<bean:message key="cuentas.listado.delete" />
				</logic:equal>
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">
		</html:cancel>
	</div>
	
	
</html:form>


</html:html>

<script type="text/javascript">
//jquery
var $j = jQuery.noConflict();
	
$j(document).ready(function (){
	
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
	
	 
    /*var theme = getTheme();
    var source = [
                   { html: "<div style='height: 30px; float: left;'><img style='float: left; margin-top: 2px; margin-right: 5px;' src='../../img/logo-kutxabank.gif'/><span style='float: left; font-size: 13px; font-family: Verdana Arial;'>Kutxabank</span></div>", title: 'Kutxabank' },
                   { html: "<div style='height: 30px; float: left;'><img style='float: left; margin-top: 2px; margin-right: 5px;' src='../../img/ing-logo.jpg'/><span style='float: left; font-size: 13px; font-family: Verdana Arial;'>ING Direct</span></div>", title: 'ING Direct' },
                   { html: "<div style='height: 30px; float: left;'><img style='float: left; margin-top: 2px; margin-right: 5px;' src='../../img/openbank.jpg'/><span style='float: left; font-size: 13px; font-family: Verdana Arial;'>Openbank</span></div>", title: 'Openbank' },
    ];
    // Create a jqxDropDownList
	$j("#jqxWidget").jqxDropDownList({ source: source,2: 0, width: '200', height: '25px', theme: theme });
     */
	
});
</script>
<script type="text/javascript">

	//Prototype
	if ( $('id_importe') != null){	Event.observe('id_importe', 'keypress', soloNumerosConComa);}

</script>
