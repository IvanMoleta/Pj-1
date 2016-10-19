<nav class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<img class="logo" src="./imagem/bg_fundo_transparente.png" />
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<!-- <li><a href="sobre.jsp">Sobre</a></li> -->
				<c:choose>
					<c:when test="${(sessionScope.permissao != 4)}">
						<li><a href="agendamento.jsp" id="agendamento">Agendamento</a></li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${(sessionScope.permissao == 1) || (sessionScope.permissao == 3)}">
						<li class="dropdown"><a id="adm" class="dropdown-toggle" aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" href="#"> Gerenciamento <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="cancelamento.jsp">Cancelamento</a></li>
								<li><a href="plantonista.jsp">Plantonista</a></li>
								<li><a href="permissao.jsp">Permissões</a></li>
							</ul>
						</li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${(sessionScope.permissao == 1) || (sessionScope.permissao == 3)}">
						<li class="dropdown"><a class="dropdown-toggle" aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" href="#"> Relatório <span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="relatorio.jsp">Relatório Feedback</a></li>
								<li><a href="campanha.jsp">Relatório Campanha</a></li>
							</ul>
						</li>
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${(sessionScope.permissao == 3)}">
						<li><a href="questionario.jsp">Questionário</a></li>
					</c:when>
				</c:choose>
				<li><a href="sair.jsp">Sair</a></li>
			</ul>
		</div>
		<div class="saudacaoMenu" id="matricula" matricula="${sessionScope.matricula}"><b><i>Seja bem-vindo, ${sessionScope.nomeFuncionario}</i></b></div>
	</div>
</nav>