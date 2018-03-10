/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.nom.penha.bruno.arquiteto.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.nom.penha.bruno.arquiteto.dao.CategoriaDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;
import br.nom.penha.bruno.arquiteto.web.util.JSFHelper;

@Controller("categoriaController")
public class CategoriaController {

    private final static Logger logger = Logger.getLogger(CategoriaController.class);
    private Categoria categoria = new Categoria();
    private ListDataModel categorias;
    
    @Autowired
    CategoriaDAO categoriaDAO;

    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }
    
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    
    /**
     * Retorna a última categoria selecionado. Caso o atributo
     * categoria seja null ele é inicializado para uma nova entidade
     * 
     * 
     * @return uma categoria
     */
    public Categoria getCategoria() {
        if (categoria == null) {
            categoria = new Categoria();
        }
        return categoria;
    }

    /**
     * inicializa o atributo categoria para uma nova entidade
     * 
     * @return "editarCategoria"
     */
    public String setup() {
        categoria = new Categoria();
        return "editarCategoria";
    }

    /** 
     * método vinculado ao botão salvar do formulário de criação/edição de
     * categoria. Aciona o dao correspondente a categoria
     * 
     * @return null ou "sucesso"
     */
    public String salvar() {
        try {
            categoriaDAO.save(categoria);
            reset();
            JSFHelper.addSuccessMessage("categoria foi salva com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao salvar a categoria";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return null;
        }
        return "sucesso";
    }

    /**
     * Método vinculado ao link de edição de uma categoria. Atualiza
     * o atributo categoria com a categoria selecionado e navega para a página
     * de edição.
     * 
     * @return editarCategoria
     */
    public String verDetalhes() {
        categoria = (Categoria) categorias.getRowData();
        return "editarCategoria";
    }

    /**
     * Método vinculado ao link de remoção de uma categoria. Aciona 
     * o DAO correspondente a categoria
     * 
     * @return null ou "sucesso"
     */
    public String remover() {
        try {
            categoria = (Categoria) categorias.getRowData();
            categoriaDAO.delete(categoria);
            reset();
            JSFHelper.addSuccessMessage("A categoria foi removida com sucesso");
        } catch (Exception e) {
            String msg = "Erro ao remover a categoria";
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
        categorias = null;
        categoria = null;
    }

    /**
     * retorna todos os categorias. Aciona o DAO correspondente a categoria
     * 
     * @return ListDataModel com todas os protótipos
     */
    public ListDataModel getCategorias() {
        try {
            if (categorias == null) {
                List<Categoria> lista = (List<Categoria>) categoriaDAO.findAll();
                categorias = new ListDataModel(lista);
            }
        } catch (Exception e) {
            String msg = "Erro ao carregar categorias";
            logger.error(msg, e);
            JSFHelper.addErrorMessage(msg);
            return new ListDataModel(new ArrayList());
        }
        return categorias;
    }
}
