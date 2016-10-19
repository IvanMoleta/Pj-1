$(document).ready(function()
{
	$('input').ratemate({ width: 200, height: 40 });
	
	$("#enviar").click(function(event)
	{	
		var score = $("#star").val();
		if(score == 0)
		{
			errorMessage('Por favor preencha o item obrigatório.');
		}
		else
		{
		var obs = $('#obs').val();
		var comparecimento = $("input[name=compareceu]:checked").val();
		$.ajax({
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id             : "17",
				score          : score,
				obs            : obs,
				comparecimento : comparecimento
			},
			success : function(json)
			{
				if (json == "sucesso")
				{
					successMessage('Avaliação respondida com sucesso. Obrigado!');
					setTimeout(function(){
						window.location = "/Massagem/agendamento.jsp";
					}, 1000);
				}
				else
				{
					errorMessage('Erro ao efetuar gravação dos dados. Por favor, tente novamente.');
				}
			}
		});
		}
	});
});