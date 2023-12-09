package gm.inventarios.servicio;

import gm.inventarios.modelo.Producto;
import gm.inventarios.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoServicio implements IProductoServicio {

    //inyectamos con @Autowired para comunicarnos con el repositorio
    @Autowired
    private ProductoRepositorio productorepositorio;



//implementado los metodos

    @Override
    public List<Producto> listarProductos() {
        return this.productorepositorio.findAll();
    }

    @Override
    public Producto buscarProductoPorId(Integer idProducto) {

        //buscar producto por id idProducto regresa un objeto tipo optional
        Producto producto = this.productorepositorio.findById(idProducto).orElse(null);
         //ponemos .orElse(null) prsino lo encuentra
        return producto;

    }

    @Override
    public Producto guardarProducto(Producto producto) {
        this.productorepositorio.save(producto);

        return producto;
    }

    @Override
    public void eliminarProductoPorId(Integer idProducto) {
        this.productorepositorio.deleteById(idProducto);

    }
}
