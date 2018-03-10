/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.nom.penha.bruno.arquiteto.comparator.ComparatorCategoriaDescricao;
import br.nom.penha.bruno.arquiteto.dao.CategoriaDAO;
import br.nom.penha.bruno.arquiteto.dao.ProdutoDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.model.Produto;
import br.nom.penha.bruno.arquiteto.model.ProdutoDigital;
import br.nom.penha.bruno.arquiteto.model.ProdutoMaterial;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

@Controller("produtoController")
public class ProdutoController {

    private final static Logger logger = Logger.getLogger(ProdutoController.class);
    private Produto produto = new ProdutoMaterial();
    private ListDataModel produtos;
    private List categorias = new ArrayList();
    private String tipo = "material";
    private List tipos;

    @Autowired
    ProdutoDAO produtoDAO;

    @Autowired
    CategoriaDAO categoriaDAO;

    public ProdutoDAO getProdutoDAO() {
        return produtoDAO;
    }

    public void setProdutoDAO(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.tipo = (produto instanceof ProdutoDigital) ? "digital" : "material";
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipos(List tipos) {
        this.tipos = tipos;
    }

    public List getTipos() {
        tipos = new ArrayList();
        tipos.add(new SelectItem("material", "material"));
        tipos.add(new SelectItem("digital", "digital"));
        return tipos;
    }

    public List getCategorias() {
        categorias = new ArrayList();
        try {
            List<Categoria> categoriasList = (List<Categoria>) categoriaDAO.findAll();
            Collections.sort(categoriasList, new ComparatorCategoriaDescricao());
            for (int i = 0; i < categoriasList.size(); i++) {
                Categoria categoria = (Categoria) categoriasList.get(i);
                SelectItem item
                        = new SelectItem(categoria, categoria.getDescricao());
                categorias.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return categorias;
    }

    public boolean isProdutoMaterial() {
        return produto instanceof ProdutoMaterial;
    }

    public void atualizarTipoProduto(ValueChangeEvent valueChangeEvent) {

        if (valueChangeEvent.getNewValue().equals("material")) {
            this.setProduto(new ProdutoMaterial(produto));
        } else {
            this.setProduto(new ProdutoDigital(produto));

        }
        logger.info("ValueChangeListener action executando...");

        FacesContext.getCurrentInstance().renderResponse();

    }

    /**
     * Retorna o último produto selecionado. Caso o atributo produto seja null
     * ele é inicializado para uma nova entidade
     *
     *
     * @return um produto
     */
    public Produto getProduto() {
        if (produto == null) {
            this.setProduto(new ProdutoMaterial());
        }
        return produto;
    }

    /**
     * inicializa o atributo produto para uma nova entidade
     *
     * @return "editarProduto"
     */
    public String setup() {
        this.setProduto(new ProdutoMaterial());
        return "editarProduto";
    }

    /**
     * método vinculado ao botão salvar do formulário de criação/edição de um
     * produto. Aciona o dao correspondente a produto
     *
     * @return null ou "sucesso"
     */
    public String salvar() {
        try {
            produtoDAO.save(produto);
            reset();
            JSFHelper.addSuccessMessage("produto foi salvo com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao salvar o produto";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return "sucesso";
    }

    /**
     * Método vinculado ao link de edição de um produto. Atualiza o atributo
     * produto com o produto selecionado e navega para a página de edição.
     *
     * @return editarProduto
     */
    public String verDetalhes() {
        this.setProduto((Produto) produtos.getRowData());
        return "editarProduto";
    }

    /**
     * Método vinculado ao link de remoção de um protótipo. Aciona o DAO
     * correspondente a produto
     *
     * @return null ou "sucesso"
     */
    public String remover() {
        try {
            this.setProduto((Produto) produtos.getRowData());
            produtoDAO.delete(produto);
            reset();
            JSFHelper.addSuccessMessage("O produto foi removido com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao remover o produto";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return "sucesso";
    }

    /**
     * joga os valores dos atributos para null
     */
    private void reset() {
        produtos = null;
        this.setProduto(null);
    }

    /**
     * retorna todos os produtos. Aciona o DAO correspondente a produto
     *
     * @return ListDataModel com todas os protótipos
     */
    public ListDataModel getProdutos() {
        try {
            if (produtos == null) {
                List<Produto> lista = (List<Produto>) produtoDAO.findAll();
                produtos = new ListDataModel(lista);
            }
        } catch (Exception e) {
            String msg = "Erro ao carregar produtos";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return new ListDataModel(new ArrayList());
        }
        return produtos;
    }
}
