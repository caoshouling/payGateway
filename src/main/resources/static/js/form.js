$(function() {
	   var ContextPath = '/payGateway';
    
       
       $("#user-vercode").on("click",function() {
    	   $.ajax({
				url : ContextPath +'/auth/vcode',
				type: 'get',
				success : function(data, status, xhr) {
					$('#user-vercode').attr('src', ContextPath +'/auth/vcode');
				},
			});
       });
        
       $("#logoutButton").on("click",function() {
    	   $.ajax({
    		   url : ContextPath+"/logout",
    		   type: 'get',
    		   success : function(data, status, xhr) {
    			   window.location.href=ContextPath+"/auth/loginPage" ;
    		   },
    	   });
       });
       
});