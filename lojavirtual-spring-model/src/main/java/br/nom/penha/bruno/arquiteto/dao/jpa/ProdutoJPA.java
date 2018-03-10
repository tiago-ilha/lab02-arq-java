package br.nom.penha.bruno.arquiteto.dao.jpa;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.nom.penha.bruno.arquiteto.dao.DAOException;
import br.nom.penha.bruno.arquiteto.dao.ProdutoDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.model.ProdutoDigital;

@Repository("produtoDAO")
public class ProdutoJPA extends DaoSupport<Produto, Integer>
        implements ProdutoDAO {

    @Transactional
    @Override
    public void save(Produto produto) {
        if (produto.getId() == null) {
            entityManager.persist(produto);
        } else {
            atualizaTipo(produto);
            entityManager.merge(produto);
        }
    }

    @Transactional
    @Override
    public void delete(Produto produto) {
        super.delete(produto);
    }

    public Collection<Produto> findByMarca(String marca) throws DAOException {
        String jpql = "select p from Produto p where marca like :marca";
        Map params = new HashMap();
        params.put("marca", marca + "%");
        return findByQuery(jpql, params);
    }

    public Collection<Produto> findByCategoria(Categoria categoria) throws DAOException {
        String jpql = "select p from Produto p where p.categoria = :categoria";
        Map params = new HashMap();
        params.put("categoria", categoria);
        return findByQuery(jpql, params);
    }

    public Collection<Produto> findProdutos(String nome, Integer idCategoria, String marca) throws DAOException {

        String conector = " where ";
        String jpql = "select p from Produto p";
        Map params = new HashMap();

        if (nome != null && !nome.equals("")) {
            jpql += conector + " p.nome like :nome ";
            params.put("nome", nome + "%");
            if (conector.trim().equals("where")) {
                conector = " and ";
            }
        }
        if (idCategoria != null && idCategoria.intValue() != 0) {
            jpql += conector + " p.categoria.id = :idCategoria ";
            params.put("idCategoria", idCategoria);
            if (conector.trim().equals("where")) {
                conector = " and ";
            }
        }
        if (marca != null && !marca.equals("")) {
            jpql += conector + " p.marca like :marca ";
            params.put("marca", marca + "%");
        }

        return findByQuery(jpql, params);
    }

    /**
     * Faz a atualização da coluna tipo_produto quando o tipo do produto é
     * alterado.
     *
     * Essa atualização é necessária ao trabalhar com herança, pois o JPA não
     * suporta uma edição com a troca da classe da entidade. Caso não seja feita
     * a atualização manual na coluna de discriminação da base de dados é feita
     * uma inserção (INSERT) em vez de atualização (UPDATE) gerando um novo
     * registro em vez de alterar o registro existente.
     *
     * Este código está implementado como exemplo deste tipo de operação, mas
     * cabe uma análise do negócio para validar se este tipo de alteração faz
     * sentido.
     *
     * @param produto
     */
    private void atualizaTipo(Produto produto) {
        Produto produtoNoBD = entityManager.find(Produto.class, produto.getId());
        if (produtoNoBD.getClass() != produto.getClass()) {
            String tipo = "M";
            if (produto instanceof ProdutoDigital) {
                tipo = "D";
            }
            Query updateQuery = entityManager.createNativeQuery("UPDATE produtos set tipo_produto = '" + tipo + "' WHERE id = " + produto.getId());
            updateQuery.executeUpdate();
            entityManager.flush();
            entityManager.clear();
        }
    }

}
