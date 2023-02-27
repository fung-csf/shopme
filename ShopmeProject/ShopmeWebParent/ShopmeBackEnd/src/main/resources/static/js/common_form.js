$(document).ready(function() {
		$("#buttonCancel").on("click", function(){
			window.location = moduleURL;
		});
		
		/* display uploaded image in image tag, ensure file size is not more than 1MB */
		$("#fileImage").change(function() {
			fileSize = this.files[0].size;
			
			//more than 1 MEGABYTE = 1024 * 1024 = 1048576 bytes
			//prevent the form from being submitted to the server
			if(fileSize > 1048576) {
				this.setCustomValidity("you must chose an image  less than 1MB!")
				this.reportValidity();
				
			}else {
				this.setCustomValidity("");
				showImageThumbnail(this);
			}
			
		});
		
	});
	
	function showImageThumbnail(fileInput){
		
		var file = fileInput.files[0];
		var reader = new FileReader();
		
		reader.onload = function(e){
			$("#thumbnail").attr("src", e.target.result);
		};
		
		reader.readAsDataURL(file);
	}