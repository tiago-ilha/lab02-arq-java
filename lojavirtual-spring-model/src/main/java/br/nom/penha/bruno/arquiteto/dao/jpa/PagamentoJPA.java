package br.nom.penha.bruno.arquiteto.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.nom.penha.bruno.arquiteto.dao.PagamentoDAO;
import br.nom.penha.bruno.arquiteto.model.Pagamento;

@Repository("pagamentoDAO")
public class PagamentoJPA extends DaoSupport<Pagamento, Integer>
        implements PagamentoDAO {

    @Transactional
    @Override
    public void save(Pagamento pagamento) {
        super.save(pagamento);
    }

    @Transactional
    @Override
    public void delete(Pagamento pagamento) {
        super.delete(pagamento);
    }
}
