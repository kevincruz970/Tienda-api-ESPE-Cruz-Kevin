package com.espe.tienda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.espe.tienda.exception.ProductoInvalidoException;
import com.espe.tienda.exception.ProductoNoEncontradoException;
import com.espe.tienda.model.Producto;

@Service
public class ProductoService {

    private final Map<Long, Producto> productos = new ConcurrentSkipListMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    public ProductoService() {
        registrar(new Producto(null, "Buso con logo ESPE", 20.00, 10));
        registrar(new Producto(null, "Uniforme completo ESPE", 150.50, 40));
        registrar(new Producto(null, "Taza con logo ESPE", 10.00, 15));
    }

    public List<Producto> listar() {
        return new ArrayList<>(productos.values());
    }

    public Producto buscarPorId(Long id) {
        Producto producto = productos.get(id);
        if (producto == null) {
            throw new ProductoNoEncontradoException(id);
        }
        return producto;
    }

    public Producto registrar(Producto producto) {
        validar(producto);
        producto.setId(secuencia.incrementAndGet());
        productos.put(producto.getId(), producto);
        return producto;
    }

    public void eliminar(Long id) {
        if (productos.remove(id) == null) {
            throw new ProductoNoEncontradoException(id);
        }
    }

    private void validar(Producto producto) {
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio del producto debe ser mayor a cero");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new ProductoInvalidoException("El stock del producto no puede ser negativo");
        }
    }
}