package br.nom.penha.bruno.arquiteto.web.flow;

import org.apache.log4j.Logger;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.execution.FlowExecutionException;
import org.springframework.webflow.execution.FlowExecutionListenerAdapter;
import org.springframework.webflow.execution.FlowSession;
import org.springframework.webflow.execution.RequestContext;

/**
 * Classe listener que monitora a entrada e saída dos fluxos do Spring Web Flow 
 *
 */
public class SimpleSpringWebFlowMonitor extends FlowExecutionListenerAdapter {

    private static final Logger logger = Logger.getLogger(SimpleSpringWebFlowMonitor.class);

    /**
     * faz um logging de mensagem toda vez que um fluxo é encerrado com uma exceção
     */
    @Override
    public void exceptionThrown(RequestContext context, FlowExecutionException exception) {
    	if(logger.isInfoEnabled()) {
    		logger.info("Exceção lançada pelo flow :" + exception.getFlowId());
    	}
    }

    /**
     * faz um logging de mensagem toda vez que um novo fluxo é criado
     */
    @Override
    public void sessionCreating(RequestContext context, FlowDefinition definition) {
    	if(logger.isInfoEnabled()) {        
    		logger.info("Criando sessão para o flow: " + definition.getId());
    	}
    }

    /**
     * faz um logging de mensagem toda vez que um fluxo é encerrado
     */
    @Override
    public void sessionEnded(RequestContext context, FlowSession session, String outcome, AttributeMap output) {
    	if(logger.isInfoEnabled()) {    	
    		logger.info("Sessao finalizada para o flow: " + session.getDefinition().getId());
    	}
    }
	
	
}
