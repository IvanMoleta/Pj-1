$(document).ready(function()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "18" },
		success : function(json)
		{
			if (json != 0)
			{
				//Redireciona para responder avaliação
				window.location = "/Massagem/avaliacao.jsp";
			}
			else
			{
				getDadosAgendamento();
			}
		}
	});
});

function getDadosAgendamento()
{
	//Montar a combo de locais
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "1" },
		success : function(json)
		{
			var options = "";
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';
			});

			$("#local").html(options);
			atualizaTabela();
		}
	});

	//Quando combo local é alterada
	$("#local").change(function ()
	{
		atualizaTabela();
	});

	//Quando alterar a data...
	$("#datepicker").change(function ()
	{
		atualizaTabela();
	});

	//Quando o checkbox é alterada
	$("input[type=radio]").change(function ()
	{
		atualizaTabela();
	});
	//Passar para o próximo dia
	$("#botaonext").click(function ()
	{		
        var dias = 7;
        var dataAtual = new Date();            
        var previsao = new Date();

        previsao.setDate(dataAtual.getDate() + dias);  		
        n = previsao.getDate()  +"/" + (previsao.getMonth() + 1)+ "/" + previsao.getFullYear();
	
		var dateTypeVar = $('#datepicker').datepicker('getDate');
		var dataFormatoBR = $.datepicker.formatDate('dd/mm/yy', dateTypeVar);
		
		var dt = $.datepicker.formatDate('mm/dd/yy', new Date(n))

		if(dataFormatoBR < dt)
		{
			proximoDia();
			atualizaTabela();
		}

	});

	//Voltar dia
	$("#botaovoltar").click(function ()
	{
		var d = new Date();
		dataHora = (d.toLocaleString());
		var dataAtual = dataHora.split(" ");
		var da = dataAtual[0];
		
		var dateTypeVar = $('#datepicker').datepicker('getDate');
		var dataFormatoBR = $.datepicker.formatDate('dd/mm/yy', dateTypeVar);
		
		if(da < dataFormatoBR)
		{
			voltarDia();
			atualizaTabela();
		}
	});
}

function atualizaTabela()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data:
		{
			id : "2",
			cmbLocal : $("#local").val(),
			ckbTurnoM : $("#ckbTurnoM").prop("checked"),
			ckbTurnoT : $("#ckbTurnoT").prop("checked"),
			data : $("#datepicker").val()
		},
		success : function(json)
		{
			if (json == "erro")
			{
				errorMessage('Erro: dados não localizados.<br/>Por favor, refaça a pesquisa.');
				$("#tabelaDados").hide();
			}
			else if (json == "temPlantonista")		
			{		
			//	errorMessage('Nesse horário é realizado apenas atendimento de plantonista.');
				$("#tabelaDados").hide();
			}
			else
			{
				$("#tabelaDados").show();
				var temp = "";
				var arrayMassoterapeuta = [];

				//Cabecalho... só passar pela primeira linha do retorno
				for (var i = 0; i < 1; i++)
				{
					for (var j = 0; j < json[0].length; j++)
					{
						nome = json[i][j].split("_");

						if (j == 0)
						{
							temp += "<th style='width: 4%;'><center>"+nome[0]+"<center></th>";
						}
						else
						{
							temp += "<th style='width: 32%;'><center>"+nome[0]+"<center></th>";
						}
						
						if (j > 0)
						{
							arrayMassoterapeuta[j-1] = nome[1];
							//arrayMassoterapeuta[j-1] = json[i][j];
						}
					}
				}

				$("#cabecalhoTabela").html(temp);
				temp = "";

				var horario;

				//Preenche corpo da tabela
				for (var i = 1; i < json.length; i++)
				{
					temp += "<tr>";
					for (var j = 0; j < json[0].length; j++)
					{
						dados = json[i][j].split("_");

						//Monta culuna de horários
						if (j == 0)
						{
							horario = json[i][j];
							temp += "<td id="+j+">"+dados[1]+"</td>";
						}
						//Monta coluna de agendamentos
						else
						{
							dados2 = horario.split("_");
							//temp += "<td id="+horario+ "_" + arrayMassoterapeuta[j-1]+">"+json[i][j]+"</td>";
							temp += "<td id="+dados2[0]+ "_" + arrayMassoterapeuta[j-1]+">"+json[i][j]+"</td>";
						}
					}
					temp += "</tr>";
				}

				$("#corpoTabela").html(temp);
			}
		}
	});
}

$(document).on('dblclick', '#tabelaDados td', function(event)
{
	var idTd = event.target.id;

	if ($(event.target).text() == "")
	{
		var c = null;

		$.ajax({
			url : 'GetDadosSession',
			type: "post",
			dataType: "json",
			success : function(json)
			{
				nome = json.split("_");
				c = confirm("Confirma seus dados?\n\nMatrícula: " + nome[0] + "\nNome: " + nome[1]);

				if (c)
				{
					//Chamar Servlet para gravar dados
					$.ajax({
						url : 'Processamento',
						type: "post",
						dataType: "json",
						data:
						{
							id : "4",
							idTd : idTd,
							matricula : nome[0],
							data : $("#datepicker").val()
						},
						success : function(json)
						{
							if (json.match(/regraAgendamento/))
					        {
					        	var quebra = json.split("_");
					        	var dataHora = quebra[1];

					        	errorMessage('Já existe massagem marcada no dia '+ dataHora);
					        }
					        else if (json == "sucesso")
							{
								successMessage('Dados gravados com sucesso.');
							}
							else if (json == "regraHorario")
							{
								errorMessage('Não é permitido agendamento para um horário anterior ao horário atual.');
							}
							else if (json == "regraFirebug")
							{
								errorMessage('Já existe marcação nesse horário.');
							}

							atualizaTabela();
						}
					});
				}
			}
		});
	}
});

$(document).on(
{
	mouseenter: function (e)
	{
		var array = $("#"+e.target.id).attr('id').split("_");	
		
		var sessao = $("#mv").val();
		var perm = $("#perm").val();
		var funcionario = new Array();
		funcionario = $(e.target).text().split(" ");
		var matFun = funcionario[0];
		
		if( sessao == matFun || perm == 3) 
		{			
			//Se célula da tabela de agendamento não estiver vazia
			if (($(e.target).text() != "") && ($(e.target).text().length > 5) && ($(e.target).text() != "Indisponivel"))
			{
				$.ajax(
				{
					url : 'Processamento',
					type: "post",
					dataType: "json",
					data:
					{
						id : "19",
						idHorario : array[0],
						date : $("#datepicker").val()
					},
						success : function(json)
						{
							if (json == "sucesso")
							{
								$("#" + e.target.id).append("<i id='remove' class='fa fa-times fa-fw' style='color:red; cursor: pointer;'></i>");
							}
						}
				});
			}
		}
	},
	mouseleave: function (e)
	{
		$("#remove").remove();
	}
}, "#tabelaDados td");

$(document).on('click', '#remove', function(event)
{
	var dados = $("#"+event.target.id).parent().attr('id').split("_");

	c = confirm("Deseja cancelar seu agendamento?");
	//var idTd = event.target.id;

	if (c)
	{
		$.ajax({
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data:
			{
				id : "12",
				idHorario : dados[0],
				matriculaMassoterapeuta : dados[1],
				data : $("#datepicker").val()
			},
			success : function(json)
			{
				if (json == "sucesso")
				{
					successMessage('Cancelamento efetuado com sucesso.');
				}
				else if (json == "erro")
				{
					errorMessage('Não foi possível efetuar o cancelamento.');
				}
				atualizaTabela();
			}
		});
	}
});

function proximoDia()
{
	var dateTypeVar = $('#datepicker').datepicker('getDate');
	var dataFormatoAmericano = $.datepicker.formatDate('yy/mm/dd', dateTypeVar);

	var data = new Date(dataFormatoAmericano);
	var dia = data.getDay();

	//Incrementa de 1 em 1 dia. Caso seja uma sexta-feira (dia 5), incrementa 3 dias, ou seja, segunda-feira.
	var incrementa = 1;
	if (dia == 5)
	{
		incrementa = 3;
	}

	var date2 = $('#datepicker').datepicker('getDate', '+'+incrementa+'d');
	date2.setDate(date2.getDate()+incrementa);
	$('#datepicker').datepicker('setDate', date2);
}

function voltarDia()
{
	var dateTypeVar = $('#datepicker').datepicker('getDate');
	var dataFormatoAmericano = $.datepicker.formatDate('yy/mm/dd', dateTypeVar);

	var data = new Date(dataFormatoAmericano);
	var dia = data.getDay();

	//Decrementa de -1 em -1 dia. Caso seja uma segunda-feira (dia 1), decrementa -3 dias, ou seja, sexta-feira.
	var decrementa = -1;
	if (dia == 1)
	{
		decrementa = -3;
	}

	var date2 = $('#datepicker').datepicker('getDate', '+'+decrementa+'d');
	date2.setDate(date2.getDate()+decrementa);
	$('#datepicker').datepicker('setDate', date2);
}