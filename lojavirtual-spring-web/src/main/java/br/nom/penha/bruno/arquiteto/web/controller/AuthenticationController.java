package br.nom.penha.bruno.arquiteto.web.controller;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

/**
 * Managed Bean utilizado para integração entre JSF e Spring Security, de forma
 * a permitir a criação de páginas de login utilizando compontentes Faces.
 * 
 * Configurado como um Spring bean.
 *
 */
@Controller("authenticationController")
public class AuthenticationController {

	private final static Logger logger = Logger.getLogger(AuthenticationController.class);

	/**
	 * Método vinculado ao botão de submit da página de login. 
	 * 
	 * Faz um encaminhamento para a URL padrão do filtro de login do Spring, 
	 * que é "j_spring_security_check" e informa o FacesContext para abortar
	 * o restante do ciclo de vida através do método responseComplete().
	 * 
	 * @return null 
	 */
    public String login() {
    	try{
    	ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                 .getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(),
                (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
    	} catch (Exception e) {
    		logger.error("Erro de login", e);
    	}
        return null;
    }
}

