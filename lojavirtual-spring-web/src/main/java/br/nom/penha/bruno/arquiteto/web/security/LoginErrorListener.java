package br.nom.penha.bruno.arquiteto.web.security;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;

import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;


/**
 * Listener utilizado na integração entre JSF e Spring Security para permitir
 * a exibição de mensagens de falha de autenticação nas páginas JSF através dos
 * componentes <h:messages> 
 *
 */
@SuppressWarnings("serial")
public class LoginErrorListener implements PhaseListener {

	private static Logger logger = Logger.getLogger(LoginErrorListener.class);

	/**
	 * adiciona uma mensagem de falha de autenticaçao no contexto Faces de forma
	 * a permitir que seja exibida na página JSF
	 */
	public void beforePhase(PhaseEvent event) {
		Exception e = (Exception) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (e instanceof BadCredentialsException) {
			logger.error("Erro de autenticacao");
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			JSFHelper.addErrorMessage("Usuario ou senha invalidos.");
		} else if (e != null) {
			logger.error("Erro no login", e);
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap()
					.put(WebAttributes.AUTHENTICATION_EXCEPTION, null);
			JSFHelper.addErrorMessage(e.getMessage());
		}
	}

	/**
	 * configura a fase de notificação do listener como sendo a fase JSF
	 * RENDER_RESPONSE
	 */
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	public void afterPhase(final PhaseEvent arg0) {
	}
}
