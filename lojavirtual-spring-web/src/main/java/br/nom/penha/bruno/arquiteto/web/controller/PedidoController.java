package br.nom.penha.bruno.arquiteto.web.controller;

import java.util.Collections;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.nom.penha.bruno.arquiteto.comparator.ComparatorPedidoClienteNumero;
import br.nom.penha.bruno.arquiteto.dao.PedidoDAO;
import br.nom.penha.bruno.arquiteto.model.Pagamento;
import br.nom.penha.bruno.arquiteto.model.PagamentoBoleto;
import br.nom.penha.bruno.arquiteto.model.Pedido;

@Controller("pedidoController")
public class PedidoController {

    private ListDataModel pedidos;
    private Pedido pedido;
    
    @Autowired
    PedidoDAO pedidoDAO;

    public PedidoDAO getPedidoDAO() {
        return pedidoDAO;
    }

    public void setPedidoDAO(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }
    
    

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public boolean isBoleto() {
        Pagamento pagamento = pedido.getPagamento();
        if (pagamento == null) {
            return false;
        } else {
            return pagamento instanceof PagamentoBoleto;
        }
    }

    public void setPedidos(ListDataModel pedidos) {
        this.pedidos = pedidos;
    }

    public ListDataModel getPedidos() {
        try {
            List<Pedido> pedidosList = (List<Pedido>) pedidoDAO.findAll();
            if (pedidosList != null && pedidosList.size() > 0) {
                Collections.sort(pedidosList, new ComparatorPedidoClienteNumero());
            }
            pedidos = new ListDataModel(pedidosList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public String verDetalhes() {
        pedido = (Pedido) pedidos.getRowData();
        return "verPedido";
    }
}
