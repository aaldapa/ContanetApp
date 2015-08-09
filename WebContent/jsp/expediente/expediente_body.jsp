<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@page import="java.lang.Integer"%>
<script language='Javascript' src='<%=request.getContextPath()%>/scripts/jquery/jquery.js'></script>

<h1 class="pagetitle"><bean:message key="perfil.titulo"/></h1>
<div class="contactForm">

	<fieldset>
			<legend><bean:message key="label.leyend"/></legend>
			<%--
			<div style="margin:0 0 2.0em 0; line-height:1.5em; font-size:120%;">
				
				<div style="float:left; width:75px; margin:0 0 0 60px; padding:2px; font-size:120%;">
					<bean:message key="label.perfil"/>
				</div>
				<div style="float:left; width:200px; padding:2px; font-size:120%;">
					 <bean:write name="LOGIN" property="usuario.perfil.nombre"/> 
				</div>
				<div style="float:left; width:100px; margin:0 0 0 10px; padding:2px; font-size:120%;">
					<bean:message key="label.familia"/>
				</div>
				<div style="float:left; width:200px; padding:2px; font-size:120%;">
					<logic:present name="LOGIN" property="usuario.familia">
						<bean:write name="LOGIN" property="usuario.familia.nombre"/>
					</logic:present>
				</div>
			</div>	
		
			<div style="margin:0 0 2.0em 0; line-height:1.5em; font-size:120%;">
				<div style="float:left; width:75px; margin:0 0 0 60px; padding:2px; font-size:120%;">
					<bean:message key="label.firstName"/> 
				</div>
				<div style="float:left; width:200px; padding:2px; font-size:120%;">
					<bean:write name="LOGIN" property="usuario.nombre"/>
				</div>
				<div style="float:left; width:100px; margin:0 0 0 10px; padding:2px; font-size:120%;">
					<bean:message key="label.lastName"/> 
				</div>
				<div style="float:left; width:200px; padding:2px; font-size:120%;">
					<bean:write name="LOGIN" property="usuario.apellidos"/>
				</div>
			</div>
				--%>
			<div style="margin:0 0 1.0em 0; line-height:1.5em; font-size:120%;">
				<div style="float:left; width:75px; margin:0 0 0 60px; padding:2px; font-size:120%;">
					<bean:message key="label.email"/>
				</div>
				<div style="float:left; width:200px; padding:2px; font-size:120%;">
					<bean:write name="LOGIN" property="usuario.email"/>
				</div>
				<div style="float:left; width:100px; margin:0 0 0 10px; padding:2px; font-size:120%;">
					<bean:message key="label.saldo"/>
				</div>
				<logic:lessThan name="expedienteForm" property="balanceActual" value="0">
					<div style="float:left; width:175px; padding:2px; font-size:140%;color:red;">
						<bean:write name="expedienteForm" property="balanceActual" format=",##0.00"/>&nbsp; &euro;
					</div>
				</logic:lessThan>
				<logic:greaterEqual name="expedienteForm" property="balanceActual" value="0">
					<div style="float:left; width:175px; padding:2px; font-size:140%;">
						<span id="id_balance_actual"><bean:write name="expedienteForm" property="balanceActual" format=",##0.00"/> </span>&euro;
						
					</div>
				</logic:greaterEqual>
			</div>
			
		</fieldset>
</div>

<html:form styleId="formulario" action="/viewExpediente">
	<%int numfilas= 1;%>
	<table id="id_tabla" class="formulario">
         <thead>
         	<tr>
         		<th class="top" width="160px"><bean:message key="label.tabla.contabilidades"/></th>
         		<th class="top" colspan="1" width="300px"><bean:message key="label.tabla.cuentas"/></th>
         		<th class="top"><bean:message key="label.tabla.saldo"/></th>
			</tr>
		</thead>
	       	<tbody>	
	       		<logic:iterate id="contabilidadBean" name="expedienteForm" property="lstContabilidadBeans" indexId="cont">
					<tr style="background-color: rgb(200,200,200)">
						
						<td colspan="2" style="font-weight: bold;"><bean:write name="contabilidadBean" property="contabilidad.nombreContabilidad"/> </td>
						<td style="font-weight: bold;">
							<span id="id_balance_contabilidad_<%=cont.intValue()+1%>"><bean:write name="contabilidadBean" property="balanceContabilidad" format=",##0.00"/></span> &nbsp; &euro;</td>
						<logic:iterate id="cuenta" name="contabilidadBean" property="lstCuentas" indexId="contador">
							<%numfilas++;%>
							<tr class='<%=numfilas%2==0? "even":"odd"%>'>
								<td style="text-align: right;"><html:img page="/img/cuentas.png" titleKey="label.imagen.cuenta"  width="20px" height="20px" align="top"/></td>
								<logic:lessThan name="cuenta" property="balance" value="0">
									<td  style="color:red;">
								</logic:lessThan>
								<logic:greaterEqual name="cuenta" property="balance" value="0">
									<td>
								</logic:greaterEqual>
									<bean:define id="checkeado" value="" />
									<logic:iterate id="idsCuentas" name="expedienteForm" property="lstIdsCuentasSelect">
										<logic:equal name="cuenta" property="idCuenta" value="<%=idsCuentas.toString()%>">
											<bean:define id="checkeado" value="checked" />
										</logic:equal>
									</logic:iterate>
												
									<logic:equal name="checkeado" value="checked">
										<input type="checkbox" name="lstIdsCuentasSelect" value='<bean:write name="cuenta" property="idCuenta"/>'  checked="checked" />
									</logic:equal>
									<logic:notEqual name="checkeado" value="checked">
										<input type="checkbox" name="lstIdsCuentasSelect" value='<bean:write name="cuenta" property="idCuenta"/>' />	
									</logic:notEqual>
																	
										&nbsp; <bean:write name="cuenta" property="notas"/>
								</td>
									<logic:lessThan name="cuenta" property="balance" value="0">
										<td style="text-align: right;color:red;">
									</logic:lessThan>
									<logic:greaterEqual name="cuenta" property="balance" value="0">
										<td style="text-align: right;">
									</logic:greaterEqual>
									<bean:write name="cuenta" property="balance" format=",##0.00"/>&nbsp; &euro;
								</td>
							<tr> 
							
						</logic:iterate>
					
						<logic:iterate id="deposito"  name="contabilidadBean" property="lstDepositos" indexId="contador2">
							<%numfilas++;%>				
							<tr class='<%=numfilas%2==0? "even":"odd"%>'>
								<td style="text-align: right;"><html:img page="/img/depositos.png" titleKey="label.imagen.deposito"   width="20px" height="20px" align="top"/></td>
								<logic:lessThan name="deposito" property="importe" value="0">
									<td  style="color:red;">
								</logic:lessThan>
								<logic:greaterEqual name="deposito" property="importe" value="0">
									<td>
								</logic:greaterEqual>
									<bean:define id="checkeado" value="" />
									<logic:iterate id="idsDepositos" name="expedienteForm" property="lstIdsDepositosSelect">
										<logic:equal name="deposito" property="idDeposito" value="<%=idsDepositos.toString()%>">
											<bean:define id="checkeado" value="checked" />
										</logic:equal>
									</logic:iterate>
												
									<logic:equal name="checkeado" value="checked">
										<input type="checkbox" name="lstIdsDepositosSelect" value='<bean:write name="deposito" property="idDeposito"/>'  checked="checked" />
									</logic:equal>
									<logic:notEqual name="checkeado" value="checked">
										<input type="checkbox" name="lstIdsDepositosSelect" value='<bean:write name="deposito" property="idDeposito"/>' />
									</logic:notEqual>

									&nbsp; <bean:write name="deposito" property="nombreDeposito"/>
								</td>
									<logic:lessThan name="deposito" property="importe" value="0">
										<td style="text-align: right;color:red;">
									</logic:lessThan>
									<logic:greaterEqual name="deposito" property="importe" value="0">
										<td style="text-align: right;">
									</logic:greaterEqual>
									<bean:write name="deposito" property="importe" format=",##0.00"/>&nbsp; &euro;
								</td>
							<tr> 
						</logic:iterate>
					</tr>			
				</logic:iterate>
		</tbody>
	</table>

</html:form>


	<script type="text/javascript">
	var $j = jQuery.noConflict();
	var url_destino = "<%=request.getContextPath()%>/action/recargarExpediente";
	$j(document).ready(function (){
		
		$j('#id_tabla :checkbox').change(function (event){
						
			var selectedItems = "";
			//cargo a la variable con la estructura necesaria para un set : selectedItems [0]=81&selectedItems [1]=12
			//$j('input:checkbox:checked').each(function(index){
			$j(':checked[name=lstIdsCuentasSelect]').each(function(index){	 
				if (index>0)
					selectedItems =selectedItems +'&';
				selectedItems =selectedItems +'lstIdsCuentasSelect['+index+']='+$j(this).val();
			});
			if (selectedItems.length>0)
				selectedItems =selectedItems +'&';
			$j(':checked[name=lstIdsDepositosSelect]').each(function(index){	 
				if (index>0)
					selectedItems =selectedItems +'&';
				selectedItems =selectedItems +'lstIdsDepositosSelect['+index+']='+$j(this).val();
			});
			var dataenvio =selectedItems;
			var path=url_destino+'?'+dataenvio;
			//alert(path);
			
			$j.getJSON(path,
				function(data){        
					$j.each(data.listaObjetos, function(i,balance){
						if (i>0)
				    		 $j('#id_balance_contabilidad_'+i).text(balance);
						else
				    		 $j('#id_balance_actual').text(balance);
				    });   	
			});		
		});
	});
	
	
</script>