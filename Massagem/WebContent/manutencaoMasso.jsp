<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>

		<script src="./js/pt-brCalendar.js"></script>
		<script src="./js/manutencaoMasso.js"></script>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>
		<%@ include file="menu.jsp" %>		

		<div class="container">
			<div class="jumbotron">
				<div class="form-group">
					<select id="carregaMasso" class="botaoMasso" ></select>
					<select id="carregaLocal" class="botaoMasso" ></select>		
					<input type="button" id="trocaLocal" value="Alterar local"/>
					<div id="montaCheck"></div>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="jumbotron">
				<div class="form-group">
					<div class="container">
					</div>
				</div>
			</div>
		</div>
	</body>
</html>