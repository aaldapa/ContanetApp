<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>

<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/themes/base/jquery.ui.all.css">

<%-- <link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/css/calendarview.css"> --%>

<script language='Javascript' src='<%=request.getContextPath()%>/scripts/prototype/prototype.js'></script>
<%-- <script language='Javascript' src='<%=request.getContextPath()%>/js/calendario/popcalendar.js'></script> --%>

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

<html:html>

<html:form styleId="formulario" action="/contabilidadCuenta">



<html:hidden property="id"></html:hidden>
	<h1 class="pagetitle">
		<bean:message key="cuenta.formulario.titulo.Nuevo"/>
		&nbsp;<bean:write name="cuentaForm" property="accion"/>
	</h1>

	<div class="contactForm">
		<fieldset>
			<legend><bean:message key="cuenta.formulario.leyenda"/></legend>
			
			 <p><label class="left">
			 Apertura:
			 
			 </label>
				
			<input id="id_fecha" name="fechaAperturaStr"  type="text" size="8" value='<bean:write name="cuentaForm" property="fechaAperturaStr"/>' readonly="readonly"/>
				
			</p>
			
				
			
			<p>	
				<label class="left"><bean:message key="apunte.listado.contabilidades" /></label>
				<bean:define id="contabilidadesCollection" name="cuentaForm" property="lstContabilidades" />
				<html:select styleId="id_combo_contabilidad" name="cuentaForm" property="idContabilidad" style="width:180px">
					<logic:present name="cuentaForm" property="lstContabilidades">
						<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
					</logic:present>
				</html:select>
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
	
});
</script>