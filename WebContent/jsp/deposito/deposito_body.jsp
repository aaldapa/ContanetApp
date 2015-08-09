<%@page import="com.tutorial.struts.utils.Constantes"%>
<%@page import="com.tutorial.struts.forms.apunte.ApunteForm"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script language='Javascript' src='<%=request.getContextPath()%>/js/Utils.js'></script>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/prototype/prototype.js'></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-1.8.2.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/js/jquery-ui-1.9.0.custom.js"></script>
<script language='Javascript' src="<%=request.getContextPath()%>/scripts/jquery/ui/development-bundle/ui/i18n/jquery.ui.datepicker-es.js"></script>

<link rel="stylesheet" type="text/css" media="screen,projection,print" href="<%=request.getContextPath()%>/scripts/jqwidgets/styles/jqx.base.css" />
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
<bean:define id="idUsuario">
<bean:write name="LOGIN" property="usuario.idUsuario"/>
</bean:define>
 
<html:html>

<html:form  styleId="formulario" action="/deposito" method="post">
<html:hidden property="id"/>
<html:hidden property="accion"/>

<html:hidden property="idUsuario" value="<%=idUsuario%>"/>
	<h1 class="pagetitle">
		<bean:message key="deposito.formulario.titulo"/>
		&nbsp;<bean:write name="depositoForm" property="accion"/>
	</h1>
	
	<div class="contactForm">

		<fieldset id="id_filtros">
			<legend><bean:message key="deposito.formulario.leyenda"/></legend>
			
			<table  style="margin:0 0 0 10px;width: 650px;">
				
				<tr>
					<td style="font-size:75%;"><bean:message key="deposito.formulario.banco" /></td>
					<td colspan="2">
						<logic:present name="depositoForm" property="lstBancos" >
							<bean:define id="bancosCollection" name="depositoForm" property="lstBancos" />
							<html:select styleId="id_combo_banco" name="depositoForm" property="idBanco" style="width:180px">
								<html:option  value="0" key="cuenta.formulario.seleccion.banco" />
								<logic:present name="depositoForm" property="lstBancos">
									<html:options collection="bancosCollection"  property="idBanco" labelProperty="nombreBanco"/>
								</logic:present>
							</html:select>
						</logic:present>
					</td>
					<td style="font-size:75%;"><bean:message key="deposito.formulario.contabilidad" /></td>
					<td colspan="2">
						<logic:present name="depositoForm" property="lstContabilidades" >
							<bean:define id="contabilidadesCollection" name="depositoForm" property="lstContabilidades" />
							<html:select styleId="id_combo_contabilidad" name="depositoForm" property="idContabilidad" style="width:180px">
								<html:option  value="0" key="apunte.listado.seleccion.contabilidad" />
								<logic:present name="depositoForm" property="lstContabilidades">
									<html:options collection="contabilidadesCollection"  property="idContabilidad" labelProperty="nombreContabilidad"/>
								</logic:present>
							</html:select>
						</logic:present>
					</td>
				</tr>
				
				<tr>
					<td style="font-size:75%;"><bean:message key="deposito.formulario.fapertura" /></td>
					<td colspan="2"><input id="id_fecha_apertura" name="fechaAperturaStr"  type="text" size="8" value='<bean:write name="depositoForm" property="fechaAperturaStr"/>' readonly="readonly"/></td>
					<td style="font-size:75%;"><bean:message key="deposito.formulario.fvencimiento" /></td>
					<td ><input id="id_fecha_vencimiento" name="fechaVencimientoStr"  type="text" size="8" value='<bean:write name="depositoForm" property="fechaVencimientoStr"/>' readonly="readonly"/></td>
				</tr>
				
				<tr style="font-size:75%;">
					<td ><bean:message key="deposito.formulario.ndeposito" /></td>
					<td colspan="2"><html:text  property="numeroDeposito" maxlength="20" size="20"/></td>
					<td ><bean:message key="deposito.formulario.nombre" /></td>
					<td colspan="2"><html:text property="nombreDeposito" size="30" /></td>
				</tr>
			
				<tr style="font-size:75%;">
					<td ><bean:message key="deposito.formulario.titular" /></td>
					<td colspan="2"><html:text property="titular" size="30"/></td>
					<td><bean:message key="deposito.formulario.cotitular" /></td>
					<td colspan="2"><html:text property="cotitular" size="30" /></td>
				</tr>
				
				<tr style="font-size:75%;">
					<td ><bean:message key="deposito.formulario.importe" /></td>
					<td><input type="text" id="id_importe" name="importeStr" maxlength="10" size="8"  style="text-align: right;" value="<bean:write name="depositoForm" property="importe" format=",##0.00" />" >	&euro;</td>
					<td  style="text-align: center;"><bean:message key="deposito.formulario.rentabilidad" /> </td>
					<td><input type="text" id="id_rentabilidad" name="rentabilidadStr" maxlength="5" size="3" style="text-align: right;" value="<bean:write name="depositoForm" property="rentabilidad" format=",##0.00" />" >%</td>
					<td  style="text-align: right;"><bean:message key="deposito.formulario.cancelacion" /></td>
					<td style="text-align: center;"><input type="text" id="id_cancelacion" name="rentabilidadCancelacionStr" maxlength="5" size="3" style="text-align: right;" value="<bean:write name="depositoForm" property="rentabilidadCancelacion" format=",##0.00" />" >%</td>
				</tr>
				
				<tr style="font-size:75%;">
					<td ><bean:message key="deposito.formulario.descripcion" /></td>
					<td colspan="5"><html:textarea property="descripcion"	cols="60" rows="2" /></td>
				</tr>
				
			
			</table>
			
			
		</fieldset>
		
	</div>

	<div class="botonera">
		<logic:notEqual name="depositoForm" property="accion" value="<%=view%>">
			<html:submit  property="accion" styleClass="boton">
				<logic:equal name="depositoForm" property="accion" value="<%=insert%>">
					<bean:message key="boton.new" />
				</logic:equal>
				<logic:equal name="depositoForm" property="accion" value="<%=update%>">
					<bean:message key="boton.update" />
				</logic:equal>
				<logic:equal name="depositoForm" property="accion" value="<%=delete%>">
					<bean:message key="boton.delete" />
				</logic:equal>
				
			</html:submit>
		</logic:notEqual>
		<html:cancel styleClass="boton">Volver
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
	//Calendario apertura
	$j( "#id_fecha_apertura" ).datepicker({
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
	//Calendario apertura
	$j( "#id_fecha_vencimiento" ).datepicker({
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

	//Prototype
	if ( $('id_ndeposito') != null){	Event.observe('id_ndeposito', 'keypress', soloNumerosConComa);}
	if ( $('id_importe') != null){	Event.observe('id_importe', 'keypress', soloNumerosConComa);}
	if ( $('id_rentabilidad') != null){	Event.observe('id_rentabilidad', 'keypress', soloNumerosConComa);}
	if ( $('id_cancelacion') != null){	Event.observe('id_cancelacion', 'keypress', soloNumerosConComa);}

</script>
