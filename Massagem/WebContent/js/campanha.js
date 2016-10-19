$(document).ready(function()
{

	$("#enviar").click(function() 
	{
		$.ajax(
		{
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id	: "28",
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
							{ title: "Pergunta" },
							{ title: "Massoterapeuta" },
							{ title: "Resposta" }
	                    ]
				   });
			   });
			}
		});
		$('#mostrarTabelaDados').show();
	});
});