function testAjax()
{
	return $.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "23"
		}
	});
}
function displayData(x)
{
	x.success(function(json)
	{
		var temp = "";
		for (var i = 0; i < json.length; i++)
		{
			//console.log(json);
			for (var j = 0; j < json[i].length; j+=2)
			{
				 //alert(json[i][j]);
				if (j == 0 || j == 1)
				{
					$('#pergunta').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
				}
				else
				{
					$('#pergunta').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
					$('#pergunta').append('<input id="pergunta_'+json[i][j]+'" type="radio" name="pergunta" value="'+json[i][j]+'">');
				}
			}
		}
		//$("#qdadePerguntas1").val(1);

	});
}

var promise = testAjax();
displayData(promise);

/*$(document).ready(function()
{
	$('input[type="radio"]').on('change', function() {
	    if ($('input[name="pergunta"]').is(":checked")){
	      $('div').show();
	    } else {
	      $('div').hide();
	    }
	  });
	var resposta = "";

	$("#enviar").click(function(event)
	{
		for (var i = 0; i < $("#qdadePerguntas1").val(); i++)
		{
			resposta = $("#pergunta_"+(i)).val();
		}
 		$.ajax({
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id         : "22",
				arrayDados : arrayDados
			},
			success : function(json)
			{
				if (json == "sucesso")
				{
					successMessage('Avaliação respondida com sucesso. Obrigado!');
					//window.location = "/Massagem/agendamento.jsp";
				}
				else
				{
					errorMessage('Erro ao efetuar gravação dos dados. Por favor, tente novamente.');
				}
			}
		});
	});
});*/