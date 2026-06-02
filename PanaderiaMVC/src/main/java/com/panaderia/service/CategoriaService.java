package com.panaderia.service;

import com.panaderia.model.Categoria;
import com.panaderia.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // LISTAR
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    // GUARDAR
    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // BUSCAR POR ID
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
