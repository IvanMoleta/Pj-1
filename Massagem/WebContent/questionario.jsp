<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html"%>
		<link rel="stylesheet" href="./css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
		
		<script src="./js/star-rating.js" type="text/javascript"></script>
		<script src="./js/questionario.js"></script>
		<script src="./js/raphael-min.js"></script>
		<script src="./js/jquery.ratemate.js"></script> 
		<style>
			table
			{
			    width: 100%;
			}
			td, th
			{
				/* border: 1px solid; */
			    text-align: left;
			    padding: 8px;
			}
		</style>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>
		<%@ include file="menu.jsp" %>
		
		<div class="container">
			<div class="row">
				<fieldset class="fsStylerel">
					<div><h1><i>Campanha</i></h1></div>
					<hr>
					<!-- <div id="categorias"></div>
					<hr> -->
					<div id="pergunta" ></div>
					<hr>
					<div id="pergunta1"></div>
					<hr>
					<label>Por favor, avalie a Estrutura para realização das massagens(Cadeira, Maca, Privacidade da Sala, 
					Limpeza e Matériais descartáveis).</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Ruim</th>
					    <th>Bom</th>
					    <th>Ótimo</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						<td>Estrutura</td>
						<td><input type="radio" checked name="resposta31" value="14"> </td>
						<td><input type="radio" name="resposta31" value="15"> </td>
						<td><input type="radio" name="resposta31" value="16"> </td>
						<td><input type="radio" name="resposta31" value="13"> </td>
					  </tr>
					</table>
					<hr>
					<label>Quanto o Evento Magic Day realizado durante a Semana Equilibrium, qual é a sua opinião?</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Ruim</th>
					    <th>Bom</th>
					    <th>Ótimo</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Magic Day</td>
						<td><input type="radio" checked name="resposta41" value="14"> </td>
						<td><input type="radio" name="resposta41" value="15"> </td>
						<td><input type="radio" name="resposta41" value="16"> </td>
						<td><input type="radio" name="resposta41" value="13"> </td>
					  </tr>
					 </table>
					 <hr>
					 <label>Por favor, avalie o benefício de Quick Massagem como um todo.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Ruim</th>
					    <th>Bom</th>
					    <th>Ótimo</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Quick Massage</td>
						<td><input type="radio" checked name="resposta51" value="14"> </td>
						<td><input type="radio" name="resposta51" value="15"> </td>
						<td><input type="radio" name="resposta51" value="16"> </td>
						<td><input type="radio" name="resposta51" value="13"> </td>
					  </tr>
					 </table>
					 <hr>
					<div id="pergunta2"></div>
					<hr>
					<label >Pontualidade - Começa e termina no tempo correto.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Sim</th>
					    <th>Não</th>
					    <th>Às vezes</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta71" value="14"> </td>
						<td><input type="radio" name="resposta71" value="15"> </td>
						<td><input type="radio" name="resposta71" value="17"> </td>
						<td><input type="radio" name="resposta71" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta72" value="14"> </td>
						<td><input type="radio" name="resposta72" value="15"> </td>
						<td><input type="radio" name="resposta72" value="17"> </td>
						<td><input type="radio" name="resposta72" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta73" value="14"> </td>
						<td><input type="radio" name="resposta73" value="15"> </td>
						<td><input type="radio" name="resposta73" value="17"> </td>
						<td><input type="radio" name="resposta73" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta74" value="14"> </td>
						<td><input type="radio" name="resposta74" value="15"> </td>
						<td><input type="radio" name="resposta74" value="17"> </td>
						<td><input type="radio" name="resposta74" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta75" value="14"> </td>
						<td><input type="radio" name="resposta75" value="15"> </td>
						<td><input type="radio" name="resposta75" value="17"> </td>
						<td><input type="radio" name="resposta75" value="13"> </td>
					  </tr>
					</table>
					<hr>
					<label >Cordialidade / Simpatia - O Massoterapeuta é cortês, gentil e atencioso.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Sim</th>
					    <th>Não</th>
					    <th>Às vezes</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta81" value="14"> </td>
						<td><input type="radio" name="resposta81" value="15"> </td>
						<td><input type="radio" name="resposta81" value="17"> </td>
						<td><input type="radio" name="resposta81" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta82" value="14"> </td>
						<td><input type="radio" name="resposta82" value="15"> </td>
						<td><input type="radio" name="resposta82" value="17"> </td>
						<td><input type="radio" name="resposta82" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta83" value="14"> </td>
						<td><input type="radio" name="resposta83" value="15"> </td>
						<td><input type="radio" name="resposta83" value="17"> </td>
						<td><input type="radio" name="resposta83" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta84" value="14"> </td>
						<td><input type="radio" name="resposta84" value="15"> </td>
						<td><input type="radio" name="resposta84" value="17"> </td>
						<td><input type="radio" name="resposta84" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta85" value="14"> </td>
						<td><input type="radio" name="resposta85" value="15"> </td>
						<td><input type="radio" name="resposta85" value="17"> </td>
						<td><input type="radio" name="resposta85" value="13"> </td>
					  </tr>
					</table>
					<hr>
					<label >Atenção personalizada: pergunta que tipo de massagem você necessita, pergunta se apresenta 
					alguma dor específica antes de iniciar a massagem.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Sim</th>
					    <th>Não</th>
					    <th>Às vezes</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta91" value="14"> </td>
						<td><input type="radio" name="resposta91" value="15"> </td>
						<td><input type="radio" name="resposta91" value="17"> </td>
						<td><input type="radio" name="resposta91" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta92" value="14"> </td>
						<td><input type="radio" name="resposta92" value="15"> </td>
						<td><input type="radio" name="resposta92" value="17"> </td>
						<td><input type="radio" name="resposta92" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta93" value="14"> </td>
						<td><input type="radio" name="resposta93" value="15"> </td>
						<td><input type="radio" name="resposta93" value="17"> </td>
						<td><input type="radio" name="resposta93" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta94" value="14"> </td>
						<td><input type="radio" name="resposta94" value="15"> </td>
						<td><input type="radio" name="resposta94" value="17"> </td>
						<td><input type="radio" name="resposta94" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta95" value="14"> </td>
						<td><input type="radio" name="resposta95" value="15"> </td>
						<td><input type="radio" name="resposta95" value="17"> </td>
						<td><input type="radio" name="resposta95" value="13"> </td>
					  </tr>
					</table>
					<hr>
					<label>Apresentação pessoal, jaleco, higiene do massoterapeuta.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Ruim</th>
					    <th>Bom</th>
					    <th>Ótimo</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta10" value="14"> </td>
						<td><input type="radio" name="resposta10" value="15"> </td>
						<td><input type="radio" name="resposta10" value="16"> </td>
						<td><input type="radio" name="resposta10" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta11" value="14"> </td>
						<td><input type="radio" name="resposta11" value="15"> </td>
						<td><input type="radio" name="resposta11" value="16"> </td>
						<td><input type="radio" name="resposta11" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta12" value="14"> </td>
						<td><input type="radio" name="resposta12" value="15"> </td>
						<td><input type="radio" name="resposta12" value="16"> </td>
						<td><input type="radio" name="resposta12" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta13" value="14"> </td>
						<td><input type="radio" name="resposta13" value="15"> </td>
						<td><input type="radio" name="resposta13" value="16"> </td>
						<td><input type="radio" name="resposta13" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta14" value="14"> </td>
						<td><input type="radio" name="resposta14" value="15"> </td>
						<td><input type="radio" name="resposta14" value="16"> </td>
						<td><input type="radio" name="resposta14" value="13"> </td>
					  </tr>
					</table>
					<hr>
					<label>Qualidade da massagem: técnica utilizada, toque, resultado.</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Ruim</th>
					    <th>Bom</th>
					    <th>Ótimo</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta15" value="14"> </td>
						<td><input type="radio" name="resposta15" value="15"> </td>
						<td><input type="radio" name="resposta15" value="16"> </td>
						<td><input type="radio" name="resposta15" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta16" value="14"> </td>
						<td><input type="radio" name="resposta16" value="15"> </td>
						<td><input type="radio" name="resposta16" value="16"> </td>
						<td><input type="radio" name="resposta16" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta17" value="14"> </td>
						<td><input type="radio" name="resposta17" value="15"> </td>
						<td><input type="radio" name="resposta17" value="16"> </td>
						<td><input type="radio" name="resposta17" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta18" value="14"> </td>
						<td><input type="radio" name="resposta18" value="15"> </td>
						<td><input type="radio" name="resposta18" value="16"> </td>
						<td><input type="radio" name="resposta18" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta19" value="14"> </td>
						<td><input type="radio" name="resposta19" value="15"> </td>
						<td><input type="radio" name="resposta19" value="16"> </td>
						<td><input type="radio" name="resposta19" value="13"> </td>
					  </tr>
					</table>
						<hr>
					<label >Você identificou se o massoterapeuta domina a técnica de massagem utilizada, 
					ou seja, se foi seguida uma sequência técnica para realização da massagem?</label>
					<table>
					  <tr>
					    <th> </th>
					    <th>Sim</th>
					    <th>Não</th>
					    <th>Às vezes</th>
					    <th>Não tenho opinião</th>
					  </tr>
					  <tr>
						 
						<td>Ademir</td>
						<td><input type="radio" checked name="resposta20" value="14"> </td>
						<td><input type="radio" name="resposta20" value="15"> </td>
						<td><input type="radio" name="resposta20" value="17"> </td>
						<td><input type="radio" name="resposta20" value="13"> </td>
					  </tr>
					  <tr>
						<td>Alaor</td>
						<td><input type="radio" checked name="resposta21" value="14"> </td>
						<td><input type="radio" name="resposta21" value="15"> </td>
						<td><input type="radio" name="resposta21" value="17"> </td>
						<td><input type="radio" name="resposta21" value="13"> </td>
					  </tr>
					  <tr>
						<td>Márcio</td>
						<td><input type="radio" checked name="resposta22" value="14"> </td>
						<td><input type="radio" name="resposta22" value="15"> </td>
						<td><input type="radio" name="resposta22" value="17"> </td>
						<td><input type="radio" name="resposta22" value="13"> </td>
					  </tr>
					  <tr>
						<td>Sandra</td>
						<td><input type="radio" checked name="resposta23" value="14"> </td>
						<td><input type="radio" name="resposta23" value="15"> </td>
						<td><input type="radio" name="resposta23" value="17"> </td>
						<td><input type="radio" name="resposta23" value="13"> </td>
					  </tr>
					  <tr>
						<td>Valdinei</td>
						<td><input type="radio" checked name="resposta24" value="14"> </td>
						<td><input type="radio" name="resposta24" value="15"> </td>
						<td><input type="radio" name="resposta24" value="17"> </td>
						<td><input type="radio" name="resposta24" value="13"> </td>
					  </tr>
					</table>
				</fieldset>
				<hr>
				<label >Complemente a pesquisa deixando sua observação, sugestão:</label>
				<div >
					<textarea id="obs" name="obs" style='width: 542px; height: 82px;'></textarea>
				</div>
				<hr>
				<input id="enviar" type="button" value="Enviar"/>
				<hr>
			</div>
		</div>
	</body>
</html>