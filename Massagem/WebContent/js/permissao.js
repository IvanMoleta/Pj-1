$(document).ready(function()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{ 
			id : "22"
		},
		success : function(json)
		{
			var options = "";
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';
			});

			$("#cmbFuncionario").html(options);
		}
	});
	$("#btadicionar").click(function() 
	{
		var permissao = $("#permissao").val();
		var idFuncionario = $("#cmbFuncionario").val();
		$.ajax(
		{
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id			  : "26",
				permissao     : permissao,
				idFuncionario : idFuncionario
			},
			success : function(json)
			{
				if(json = "sucesso")
				{
					successMessage('Permissão adicionada!');
				}
			}
		});
	});
});