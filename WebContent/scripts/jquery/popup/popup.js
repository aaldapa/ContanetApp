var $j = jQuery.noConflict();
var modalWindow = {  
     parent:"body",  
     windowId:null,  
     content:null,  
     width:null,  
     height:null,  
     close:function()  
     {  
		$j(".modal-window").remove();  
		$j(".modal-overlay").remove();  
    },  
     open:function()  
     { 
         var modal = "";  
         modal += "<div class=\"modal-overlay\"></div>";  
         modal += "<div id=\"" + this.windowId + "\" class=\"modal-window\" style=\"width:" + this.width + "px; height:" + this.height + "px; margin-top:-" + (this.height / 2) + "px; margin-left:-" + (this.width / 2) + "px;\">";  
         modal += this.content;  
         modal += "</div>";      
 
         $j(this.parent).append(modal);  
   
         $j(".modal-window").append("<a class=\"close-window\"></a>");  
         $j(".close-window").click(function(){modalWindow.close();});  
         $j(".modal-overlay").click(function(){modalWindow.close();});  
     }  
 };  
 
var openMyModal = function(source)  
{  modalWindow.windowId = "myModal";  
   modalWindow.width = 680;  
   modalWindow.height = 465;  
   modalWindow.content = "<iframe width='680' height='465' frameborder='0' scrolling='no' allowtransparency='true' src='" + source + "'></iframe>";  
   modalWindow.open();  
};  