$(document).ready(function(){
		$("#logoutLink").on("click", function(e){
			e.preventDefault(); /* stop the hyperlink default behaviour  */
			
			/* use  the form to  log out and navigate to the url path /logout  
			
			 NOTE: spring security requires a POST request form to logout in order to obtain the csrf token, 
			 		the csrf token is a hidden property in the form
			*/
			document.logoutForm.submit();
		});
		
		customizeDropDownMenu();
	});
	
	
function customizeDropDownMenu() {
	
	$(".dropdown > a").click(function() {
		location.href = this.href;
	});
	
}
	