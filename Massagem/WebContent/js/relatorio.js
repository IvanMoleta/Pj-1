$(document).ready(function()
{
	//Monta combo massoterapeuta
	$.ajax(
	{
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{ 
			id : "7" 
		},
		success : function(json)
		{
			var options = "<option value='' selected>Escolha...</option>";
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';
			});

			$("#idMassoterapeuta").html(options);
		}
	});
	
	$("#inicio").datepicker(
	{
		dateFormat: 'dd/mm/yy',
	});
	
	$("#fim").datepicker(
	{
		dateFormat: 'dd/mm/yy',
	});
	
	$("#enviar").click(function() 
	{
		$.ajax(
		{
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id				  : "20",
				idMassoterapeuta  : $("#idMassoterapeuta").val(),
				inicio            : $('#inicio').val(),
				fim               : $('#fim').val()
			},
			success : function(json)
			{
				$(document).ready(function()
				{
					$('#tabelaDados').DataTable(
					{
						dom: 'Bfrtip',
				        buttons: ['copy', 'excel', 'pdf', 'print'], //'csv',
						destroy: true,
						data: json,
						columns:
						[
							{ title: "Data" },
							{ title: "Funcionário" },
							{ title: "Massoterapeuta" },
							{ title: "Pergunta" },
							{ title: "Pontuação" },
							{ title: "Observações" }
	                    ]
				   });
			   });
			}
		});
		$('#mostrarTabelaDados').show();
	});
});