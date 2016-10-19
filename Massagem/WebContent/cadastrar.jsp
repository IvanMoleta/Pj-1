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
		<c:if test="${sessionScope.errorPrimeiroAcesso == 'ErroPrimeiroAcesso'}">
			<script>noticeMessage("É seu primeiro acesso. Cadastre uma nova senha para acessar o sistema.");</script>
		</c:if>
		<c:if test="${sessionScope.senhaJaAlterada == 'senhaJaAlterada'}">
			<script>noticeMessage("Sua senha já foi alterada previamente.");</script>
		</c:if>
		<div class="container">
			<div class="jumbotron">
				<form class="form-horizontal" id="form1" action="Cadastramento" method="post">
  					<fieldset>
						<legend style="text-align:center">Sistema de Agendamento de Massagem</legend>

						<div class="form-group">
							<label class="col-md-4 control-label" for="Email">Codigo Funcional</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-user fa-fw"></i>
									</span>
									<input id="matricula" name="matricula" value="${sessionScope.matricula}" type="text" placeholder="Codigo Funcional" class="form-control input-md">
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
							<label class="col-md-4 control-label" for="ConfirmPassword">Confirme Senha</label>
							<div class="col-md-3">
								<div class="input-group">
									<span class="input-group-addon">
										<i class="fa fa-key fa-fw"></i>
									</span>
									<input id="password_again" name="password_again" type="password" placeholder="Digite a mesma senha" class="form-control input-md">
								</div>
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="Submit"></label>
							<div class="col-md-4">
								<button id="Submit" class="btn btn-success" type="Submit">Entrar</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</body>
</html>