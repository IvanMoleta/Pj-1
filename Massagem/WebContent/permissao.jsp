<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>
		<script src="./js/permissao.js"></script>
	</head>

	<body style="background-color: #eee">
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>

		<%@ include file="menu.jsp" %>
		<div class="container">
			<div class="jumbotron">
				<div class="col-sm-4">
					<label>Nível de Permissão </label>
					<select id="permissao">
						<option value="2">Padrão</option>
						<option value="3">RH</option>
					</select>
				</div>
				<div class="form-group">
				<label>Funcionário </label>
				<select id="cmbFuncionario" class="cmbFuncionario"></select>
				<input id="btadicionar" type="submit" class="btadicionar" value="Adicionar"/>
				</div>
			</div>
		</div>
	</body>
</html>