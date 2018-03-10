package br.nom.penha.bruno.arquiteto.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Gerenciador de erros de autorização que faz o redirecionamento para 
 * uma página de erro configurada através dos estados de fluxo do 
 * Spring Web Flow. Seu uso é importante para o correto funcionamento da navegação
 * para páginas de erro de autorização.
 *
 * Também serve para ilustrar a utilização de verificação de habilitação
 * de nivel de severidade do Log4J antes de montar a mensagem. Essa é uma boa
 * prática para melhoria de desempenho de logging de mensagens.
 */
@Component("accessDeniedHandler")
public class GlobalAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger logger = Logger.getLogger(GlobalAccessDeniedHandler.class);
	
	/**
	 * método chamado para gerenciar a falha de autorização e redirecionar para o
	 * fluxo main estado accessDenied do Spring Web Flow
	 */
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException exception) throws IOException, ServletException {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		if(logger.isDebugEnabled()) {
			logger.debug("ContextPath = " + contextPath);
			logger.debug("ServletPath = " + servletPath);
		}
		response.sendRedirect(contextPath+servletPath+"/main?startup=accessDenied");
	}
}
