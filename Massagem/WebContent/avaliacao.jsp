<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>

		<link rel="stylesheet" href="./css/avaliacao.css" media="all" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" href="./css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
		<script src="./js/star-rating.js" type="text/javascript"></script>
		<script src="./js/avaliacao.js" type="text/javascript"></script>

		<script src="./js/raphael-min.js"></script>
		<script src="./js/jquery.ratemate.js"></script> 
		
	</head>
		
	<body style="background-color: #eee">
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>

		<c:set value="${sessionScope.matricula}" var="matricula" scope="session"></c:set>

		<%-- <c:out value="${sessionScope.idAgendamento}"></c:out> --%>

		<%@ include file="menu.jsp" %>

		<div class="container">
			<div id="categorias"></div>
				<div class="form-group">
					<label class="control-label">Qual o nível geral de satisfação da sua última massagem?*</label>
					<div class="col">
						<input id="star" max="5" min="0" step="1" type="number" value="0" style="display: none">
					</div>
				</div>
			<div class="form-group">
			<label class="control-label">Existe alguma sugestão/crítica ou elogio que você gostaria de compartilhar conosco?(opcional)</label>
				<div class="col">
					<textarea id="obs" name="obs" style='width: 328px; height: 81px;'></textarea>
				</div>
			</div>
			<div class="form-group">
			<label class="control-label">Você compareceu a sua última massagem?</label>
				<div class="col">
					<input type="radio" checked name="compareceu" value="sim">Sim<br>
 					<input type="radio" name="compareceu" value="não">Não
				</div>
			</div>
		<input id="enviar" type="button" value="Enviar" />
		</div>
		<input type="hidden" id="qdadePerguntas" value="" />
	</body>
</html>