/*function testAjax()
{
	return $.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "21"
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
			for (var j = 0; j < json[i].length; j+=2)
			{
				//alert(json[i][j]);
				if (j == 0 || j == 1)
				{
					$('#categorias').append('<fieldset><legend><i>'+json[i][j+1]+'</i></legend>');
				}
				else
				{
					$('#categorias').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
					$('#categorias').append('<input id="star_'+json[i][j]+'" value="1" type="number" class="rating" min=0 max=5 step=1 data-size="sm" style="display:none">');
				}
			}
			$('#categorias').append('</fieldset><br />');
		}
		$("#qdadePerguntas").val(9);

		$('input').ratemate();
	});
}

var promise = testAjax();
displayData(promise);
*/
//Por favor, você identifica a Quick Massagem como um benefício diferencial oferecido pela nossa empresa?
$(document).ready(function()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "23"
		},
		success:function(json)
		{
			var temp = "";
			for (var i = 0; i < json.length; i++)
			{
				for (var j = 0; j < json[i].length; j+=2)
				{
					if (j == 0 || j == 1)
					{
						$('#pergunta2').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
					}
					else
					{
						$('#pergunta2').append('<input checked style="float:left" id="resposta_'+json[i][j]+'" type="radio" name="resposta3" value="'+json[i][j]+'">');
						$('#pergunta2').append('<div class="fonteResposta">' + json[i][j+1] + '</div>');
					}
				}
			}
		}
	});
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "24"
		},
		success:function(json)
		{
			var temp = "";
			for (var i = 0; i < json.length; i++)
			{
				for (var j = 0; j < json[i].length; j+=2)
				{
					if (j == 0 || j == 1)
					{
						$('#pergunta').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
					}
					else
					{
						$('#pergunta').append('<input checked style="float:left" id="resposta_'+json[i][j]+'" type="radio" name="resposta2" value="'+json[i][j]+'">');
						$('#pergunta').append('<div class="fonteResposta">' + json[i][j+1] + '</div>');
					}
				}
			}
		}
	});
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "25"
		},
		success:function(json)
		{
			var temp = "";
			for (var i = 0; i < json.length; i++)
			{
				for (var j = 0; j < json[i].length; j+=2)
				{
					if (j == 0 || j == 1)
					{
						$('#pergunta1').append('<div class="fontePergunta">' + json[i][j+1] + '</div>');
					}
					else
					{
						$('#pergunta1').append('<input style="float:left" id="resposta_'+json[i][j]+'" type="checkbox" class="opcaoselect" name="resposta1" value="'+json[i][j]+'">');
						$('#pergunta1').append('<div class="fonteResposta">' + json[i][j+1] + '</div>');
					}
				}
			}
		}
	});
	var arrayDados = [];

	$("#enviar").click(function(event)
	{	
		var respostasCheckbox = $("input[name=resposta1]:checked").map(function()
		{    
	      return $(this).val();   
	    });
		var arrayRespostas = respostasCheckbox.get();
		//Pergunta 3
		var resposta31 = $("input[name=resposta31]:checked").val();
 		//Pergunta 4
 		var resposta41 = $("input[name=resposta41]:checked").val();
 		//Pergunta 5 		
 		var resposta51 = $("input[name=resposta51]:checked").val();
 		//AV - Pergunta 1
		var resposta71 = $("input[name=resposta71]:checked").val();
		var resposta72 = $("input[name=resposta72]:checked").val();
		var resposta73 = $("input[name=resposta73]:checked").val();
		var resposta74 = $("input[name=resposta74]:checked").val();
		var resposta75 = $("input[name=resposta75]:checked").val();
 		var arrayRespostas1 = [resposta71,resposta72,resposta73,resposta74,resposta75];
 		//AV - Pergunta 2
		var resposta81 = $("input[name=resposta81]:checked").val();
		var resposta82 = $("input[name=resposta82]:checked").val();
		var resposta83 = $("input[name=resposta83]:checked").val();
		var resposta84 = $("input[name=resposta84]:checked").val();
		var resposta85 = $("input[name=resposta85]:checked").val();
 		var arrayRespostas2 = [resposta81,resposta82,resposta83,resposta84,resposta85];
 		//AV - Pergunta 3
		var resposta91 = $("input[name=resposta91]:checked").val();
		var resposta92 = $("input[name=resposta92]:checked").val();
		var resposta93 = $("input[name=resposta93]:checked").val();
		var resposta94 = $("input[name=resposta94]:checked").val();
		var resposta95 = $("input[name=resposta95]:checked").val();
 		var arrayRespostasAV3 = [resposta91,resposta92,resposta93,resposta94,resposta95];
 		//AV - Pergunta 4
		var resposta10 = $("input[name=resposta10]:checked").val();
		var resposta11 = $("input[name=resposta12]:checked").val();
		var resposta12 = $("input[name=resposta13]:checked").val();
		var resposta13 = $("input[name=resposta14]:checked").val();
		var resposta14 = $("input[name=resposta15]:checked").val();
 		var arrayRespostas4 = [resposta10,resposta12,resposta13,resposta14,resposta15];
 		//AV - Pergunta 5
		var resposta15 = $("input[name=resposta15]:checked").val();
		var resposta16 = $("input[name=resposta16]:checked").val();
		var resposta17 = $("input[name=resposta17]:checked").val();
		var resposta18 = $("input[name=resposta18]:checked").val();
		var resposta19 = $("input[name=resposta19]:checked").val();
 		var arrayRespostas5 = [resposta15,resposta16,resposta17,resposta18,resposta19];
 		//AV - Pergunta 6
		var resposta20 = $("input[name=resposta20]:checked").val();
		var resposta21 = $("input[name=resposta21]:checked").val();
		var resposta22 = $("input[name=resposta22]:checked").val();
		var resposta23 = $("input[name=resposta23]:checked").val();
		var resposta24 = $("input[name=resposta24]:checked").val();
 		var arrayRespostas6 = [resposta20,resposta21,resposta22,resposta23,resposta24];
 		var obs = $('#obs').val();
		if((arrayRespostas == "") || (obs == ""))
		{
			errorMessage('Por favor responda as peguntas.');
		}
		else
		{
			$.ajax({
				url : 'Campanha',
				type: "post",
				dataType: "json",
				data:
				{
					id               : "1",
					resposta3        : $("input[name=resposta3]:checked").val(),
					resposta2        : $("input[name=resposta2]:checked").val(),
					arrayRespostas   : arrayRespostas,
					resposta31       : resposta31,
					resposta41       : resposta41,
					resposta51       : resposta51,
					arrayRespostas1  : arrayRespostas1,
					arrayRespostas2  : arrayRespostas2,
					arrayRespostasAV3: arrayRespostasAV3,
					arrayRespostas4  : arrayRespostas4,
					arrayRespostas5  : arrayRespostas5,
					arrayRespostas6  : arrayRespostas6,
					obs              : obs
				},
				success : function(json)
				{
					if (json == "sucesso")
					{
						successMessage('Questionário respondido com sucesso. Obrigado!');
						setTimeout(function(){
							window.location = "/Massagem/agendamento.jsp";
						}, 1000);
					}
					else if (erro = "validaQuestionario")
					{
						errorMessage('Questionário já foi respondido.');
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
