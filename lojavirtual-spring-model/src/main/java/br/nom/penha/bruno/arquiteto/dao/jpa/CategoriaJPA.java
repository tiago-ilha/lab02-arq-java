package br.nom.penha.bruno.arquiteto.dao.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.nom.penha.bruno.arquiteto.dao.CategoriaDAO;
import br.nom.penha.bruno.arquiteto.model.Categoria;

@Repository("categoriaDAO")
public class CategoriaJPA extends DaoSupport<Categoria,Integer>
        implements CategoriaDAO {
    
    @Transactional
    @Override
    public void save(Categoria categoria) {
        super.save(categoria);
    }
    
    @Transactional
    @Override
    public void delete(Categoria categoria) {
        super.delete(categoria);
    }
    
}
