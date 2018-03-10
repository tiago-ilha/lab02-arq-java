package br.nom.penha.bruno.arquiteto.web.flow;

import org.springframework.stereotype.Component;
import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.engine.TargetStateResolver;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.support.DefaultTargetStateResolver;
import org.springframework.webflow.execution.FlowExecutionException;


/**
 * Tratador de exceções de fluxo padrão que provoca uma transição para a página
 * principal do aplicativo quando ocorre uma exceção de fluxo 
 *
 */
@Component("globalFlowExceptionHandler")
public class GlobalFlowExceptionHandler implements FlowExecutionExceptionHandler {

	/**
	 * permite selecionar quais exceções desejamos tratar. No caso qualquer 
	 * subclasse de Exception, mas poderiamos ter selecionado exceções mais 
	 * específicas.
	 */
    public boolean canHandle(FlowExecutionException exception) {
        return exception.getCause().getClass().isAssignableFrom(Exception.class);
    }

    /**
     * provoca uma transição para a página principal do aplicativo quando ocorre
     * uma exceção
     */
    public void handle(FlowExecutionException exception, RequestControlContext context) {
        TargetStateResolver resolver = new DefaultTargetStateResolver("home");
        Transition transition = new Transition(resolver);
        context.execute(transition);
    }
}
