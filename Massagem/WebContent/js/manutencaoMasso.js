$(document).ready(function()
{	 
	$("#carregaLocal").hide(); 	
	$("#montaCheck").hide(); 
	
	carregaMasso();
	
	idMasso = $("#carregaMasso").val();
	
	$("#carregaMasso").change(function() 
	{
		if (idMasso == 0)
		{
			$("#carregaLocal").hide(); 	
		}
		else
		{
			carregaLocal();
		};
	});
	

	$("#trocaLocal").click(function() 
	{
		montaCheck();
		trocaLocal();
	});
});

function carregaMasso()
{
	$.ajax(
	{
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "28" },
		success : function(json)
		{
			var options = "";
			
			options = '<option value="0">Selecione</option>';
					
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';			
			});
					
			$("#carregaMasso").html(options);
		}		
	});	
}

function carregaLocal()
{
	$.ajax(
	{
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "29", idMasso: $("#carregaMasso").val() },
		success : function(json)
		{
			var options = "";
							
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';
			});

			$("#carregaLocal").html(options);	
		}
	});	
	$("#carregaLocal").show();
}

function montaCheck()
{
	if( idMasso != 0 )
	{
		$.ajax(
		{
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data: { id : "1" },
			success : function(json)
			{
				var temp = "";
				
				$.each(json, function(key, value)
				{
					temp += "<tr>";		
					temp += "<td><input type='checkbox' value='"+key+ "'/></td><td>"+value+"</td>";
					temp += "</tr>";
				});
				
				$("#montaCheck").html(temp);
				$("#montaCheck").toggle(); 	
			}
		});
	}
}

function trocaLocal()
{
	$(document).on('change', '#montaCheck input[type=checkbox]' , function()
	{
		if(this.checked)
		{
			$.ajax({
				url : 'Processamento',
				type: "post",
				dataType: "json",
				data: { id : "30", idMasso: $("#carregaMasso").val(), idLocal: this.value  },
				success : function(json)
				{
					if (json == "sucesso")
					{
						successMessage('Local alterado com sucesso');
						$("#montaCheck").hide(); 	
						carregaLocal();
					}
					else if (json == "erro")
					{
						errorMessage('Erro ao enviar os dados. Favor tente novamente mais tarde.');
						montaCheck()
					}
					else if(json == "mesmoLocal")
					{
						errorMessage('Funcionário já atua neste local.');
						montaCheck();
					}
				}
			});
		}
	});
}