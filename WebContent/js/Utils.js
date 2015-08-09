function mostrarElemento(element){
		if (Element.visible(element)) {
  			new Effect.SlideUp(element, { duration: 0.25 }); 	   			
  		} else {
  			new Effect.SlideDown(element, { duration: 0.25 });		
		}
}
// Muestra o oculta los elementos por clase dependiendo de los parametros enviados
// 1.- Si se pasa true se muestra todo, si paso false, Si no paso nada '' cambia el estilo  
function mostrarOcultarPorClass(clase,parametro){ 
medios = $$('.'+clase);
	medios.each( function(elemento, indice) {
		if ( parametro == ''){
			if (Element.visible(elemento)) {$(elemento).hide();
			} else {$(elemento).show();}
		
		}else if ( parametro == 'true'){
			$(elemento).show();
		}else{
			$(elemento).hide();
		}
		
	});
}

function soloNumerosSinCero(e){
	var key;
	var keychar;
	el = Event.element(e);

	if (window.event) {
	key = window.event.keyCode;
	} else if (e) {
	key = e.which;
	} else {
	return true;
	}
		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==Event.KEY_BACKSPACE) || (key==Event.KEY_TAB) || (key==Event.KEY_RETURN) || (key==Event.KEY_ESC)) {
			return true;
		} else if ( ('123456789').indexOf(keychar) > -1) {
			return true;
		} else {
			try { Event.stop(e); } catch(ce) { }
			try { e.returnValue = false; } catch(ce) { }
		return false;
		}
}

function soloNumeros(e){
	var key;
	var keychar;
	el = Event.element(e);

	if (window.event) {
	key = window.event.keyCode;
	} else if (e) {
	key = e.which;
	} else {
	return true;
	}
		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==Event.KEY_BACKSPACE) || (key==Event.KEY_TAB) || (key==Event.KEY_RETURN) || (key==Event.KEY_ESC)) {
			return true;
		} else if ( ('0123456789').indexOf(keychar) > -1) {
			return true;
		} else {
			try { Event.stop(e); } catch(ce) { }
			try { e.returnValue = false; } catch(ce) { }
		return false;
		}
}



function soloNumerosConComa(e){
	var key;
	var keychar;
	el = Event.element(e);

	if (window.event) {
	key = window.event.keyCode;
	} else if (e) {
	key = e.which;
	} else {
	return true;
	}
		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==Event.KEY_BACKSPACE) || (key==Event.KEY_TAB) || (key==Event.KEY_RETURN) || (key==Event.KEY_ESC)) {
			return true;
		} else if ((keychar == ',' && !($F(el.id).indexOf(',') != -1)) || (('0123456789').indexOf(keychar) > -1)) {
				
				// 1.- Validamos los dos decimales
				var donde_coma = $F(el.id).indexOf(',');
				if ($F(el.id).indexOf(',') != -1){
					var numeros_totales = $F(el.id).length;
						if ( (numeros_totales - donde_coma) > 2){
							try { Event.stop(e); } catch(ce) { }
							try { e.returnValue = false; } catch(ce) { }
							return false;
						}
				}
				// 2.- (Pendiente) Valido los 4 enteros y dos decimales
				
				
			return true;
		} else {
			try { Event.stop(e); } catch(ce) { }
			try { e.returnValue = false; } catch(ce) { }
			return false;
		}
}

function soloNumerosConComa1Decimal(e){
	var key;
	var keychar;
	el = Event.element(e);

	if (window.event) {
	key = window.event.keyCode;
	} else if (e) {
	key = e.which;
	} else {
	return true;
	}
		keychar = String.fromCharCode(key);
		// control keys
		if ((key==null) || (key==0) || (key==Event.KEY_BACKSPACE) || (key==Event.KEY_TAB) || (key==Event.KEY_RETURN) || (key==Event.KEY_ESC)) {
			return true;
		} else if ((keychar == ',' && !($F(el.id).indexOf(',') != -1)) || (('0123456789').indexOf(keychar) > -1)) {
				
				// 1.- Validamos 1 decimal
				var donde_coma = $F(el.id).indexOf(',');
				if ($F(el.id).indexOf(',') != -1){
					var numeros_totales = $F(el.id).length;
						if ( (numeros_totales - donde_coma) > 1){
							try { Event.stop(e); } catch(ce) { }
							try { e.returnValue = false; } catch(ce) { }
							return false;
						}
				}
				// 2.- (Pendiente) Valido los 4 enteros y 1 decimal
				
				
			return true;
		} else {
			try { Event.stop(e); } catch(ce) { }
			try { e.returnValue = false; } catch(ce) { }
			return false;
		}
}

Number.prototype._toFixed=Number.prototype.toFixed; //Preserves the current function
Number.prototype.toFixed=function(precision){
/* step 1 */ var a=this, pre=Math.pow(10,precision||0);
/* step 2 */ a*=pre; //currently number is 162295.499999
/* step 3 */ a = a._toFixed(2); //sets 2 more digits of precision creating 16230.00
/* step 4 */ a = Math.round(a);
/* step 5 */ a/=pre;
/* step 6 */ return a._toFixed(precision);
}
/*This last step corrects the number of digits from 162.3 ( which is what we get in
 step 5 to the corrected 162.30. Without it we would get 162.3 */


String.prototype.replaceAll = function(item,replacewith) {
var s = this;
while (s.indexOf(item) >= 0)
s = s.replace(item, replacewith);
return s;
}
// Reemplazos de String
String.prototype.trim = function() {
	  return this.replace(/^\s*|\s*$/g, '');
}
String.prototype.rtrim = function() {
	  return this.replace(/\s*$/g, '');
}
String.prototype.ltrim = function() {
  return this.replace(/^\s*/g, '');
}
String.prototype.trim = function() {
  return this.ltrim().rtrim();
}
//elimina cualquier etiqueta de código HTML de la cadena de texto.
String.prototype.stripTags = function() {
	  return this.replace(/<\/?[^>]+>/gi, '');
}