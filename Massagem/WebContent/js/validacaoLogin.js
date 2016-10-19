$(document).ready(function()
{
	jQuery.validator.setDefaults(
	{
		//debug: true,
		success: "valid"
	});
	
	$('#form1').validate(
	{
		rules:
		{
			matricula:
			{
				required:true
			},
			password:
			{
				required:true,
				minlength:5,
				maxlength:15
			},
		    password_again:
		    {
		    	equalTo:"#password"
            }
		},
		highlight: function(element)
		{
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element)
		{
			$(element).closest('.form-group').removeClass('has-error');
		},
		errorElement: 'span',
		errorClass: 'help-block',
		errorPlacement: function(error, element)
		{
			if(element.parent('.input-group').length)
			{
				error.insertAfter(element.parent());
			}
			else
			{
				error.insertAfter(element);
			}
		}
	})
});