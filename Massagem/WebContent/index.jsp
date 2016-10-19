<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>
		<link rel="stylesheet" href="./css/font-awesome.min.css">
		<script src="./js/validacaoLogin.js"></script>
		<script src="./js/mensagensToast.js"></script>
		<script src="./js/jquery.validate.min.js"></script>
	</head>

	<body>
		<c:if test="${sessionScope.error == 'ErroID'}">
			<script>errorMessage("Página não localizada.");</script>
		</c:if>
		<c:if test="${sessionScope.erroAutenticacao == 'erroAutenticacao'}">
			<script>errorMessage("Login/Senha incorretos.");</script>
		</c:if>
		<c:if test="${sessionScope.senhaDifinida == 'senhaDifinida'}">
			<script>noticeMessage("Senha definida com sucesso.");</script>
		</c:if>
		<c:if test="${sessionScope.erroDefinirSenha == 'erroDefinirSenha'}">
			<script>errorMessage("Erro ao definir nova senha. Tente novamente.");</script>
		</c:if>

		<div class="container">
			<div class="jumbotron">
				<form class="form-horizontal" id="form1" action="Autenticacao" method="post">
  					<fieldset>
						<legend style="text-align:center">Sistema de Agendamento de Massagem</legend>

						<div class="form-group">
							<label class="col-md-4 control-label" for="Email">Codigo Funcional</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-user fa-fw"></i>
									</span>
									<input id="matricula" name="matricula" type="text" placeholder="Codigo Funcional" class="form-control input-md">
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="Password">Senha</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-key fa-fw"></i>
									</span>
									<input id="password" name="password" type="password" placeholder="Senha" class="form-control input-md">
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="Submit"></label>
							<div class="col-md-4">
								<button id="Submit" class="btn btn-success" type="Submit">Entrar</button>
								<span style="margin-left:116px"><a href="cadastrar.jsp">Primeiro acesso?</a></span>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>