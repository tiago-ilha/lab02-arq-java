package br.nom.penha.bruno.arquiteto.dao.jpa;

import java.util.Collection;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.model.Cliente;
import br.nom.penha.bruno.arquiteto.model.Pedido;
import br.nom.penha.bruno.arquiteto.util.FormatHelper;

@Repository("pedidoDAO")
public class PedidoJPA extends DaoSupport<Pedido, Long>
        implements PedidoDAO {

    @Transactional
    @Override
    public void save(Pedido pedido) {
        super.save(pedido);
    }

    @Transactional
    @Override
    public void delete(Pedido pedido) {
        super.delete(pedido);
    }
    
    
    public Collection<Pedido> findAll() throws DAOException {
        String jpql = "select p from Pedido p left join fetch p.cliente left join fetch p.pagamento";
        return findByQuery(jpql);
    }

    public Collection<Pedido> findByCliente(Cliente cliente) throws DAOException {
        String jpql = "select p from Pedido p where p.cliente = :cliente";
        Map params = new HashMap();
        params.put("cliente", cliente);
        return findByQuery(jpql, params);
    }

    public Collection<Pedido> findByPeriodo(String strDataInicio, String strDataFinal) throws DAOException {
        String jpql = "select p from Pedido p where ";
        Map params = new HashMap();
        boolean dataInicio = false;
        try {
            if (strDataInicio != null && !(strDataInicio.equals(""))) {
                Date dataInicial = FormatHelper.getInstance().parseSimpleDate(strDataInicio + " 00:00");
                jpql += " data >= :dataInicio";
                params.put("dataInicio", dataInicial);
                dataInicio = true;
            }
            if (strDataFinal != null && !(strDataFinal.equals(""))) {
                Date dataFinal = FormatHelper.getInstance().parseSimpleDate(strDataFinal + " 23:59");
                if (dataInicio) {
                    jpql += " and ";
                }
                jpql += " data <= :dataFinal";
                params.put("dataFinal", dataFinal);
            }
        } catch (ParseException pe) {
            throw new DAOException("Formato de data invalido " + strDataInicio + " ou " + strDataFinal, pe);
        }
        return findByQuery(jpql,params);
    }

}

