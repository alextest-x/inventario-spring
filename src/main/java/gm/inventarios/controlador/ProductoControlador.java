package gm.inventarios.controlador;

import gm.inventarios.excepcion.RecursoNoEncontradoExcepcion;
import gm.inventarios.modelo.Producto;
import gm.inventarios.servicio.ProductoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
  //http://localhost:8080/inventario-app //contextpath
@RequestMapping("inventario-app")
@CrossOrigin(value = "http://localhost:4200")  //recibe las peticiones del frontend
public class ProductoControlador {

private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

   //inyectamos del servicio al controlador
    @Autowired
    private ProductoServicio productoServicio;

    //http://localhost:8080/inventario-app/productos
    @GetMapping("/productos")
    public List<Producto> obtenerProductos(){
    List<Producto> productos= this.productoServicio.listarProductos();

    logger.info("Productos obtenidos");
    productos.forEach((producto -> logger.info(producto.toString())));
    return productos;
    }


    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto){
     logger.info("Producto agregar: " + producto);
     return this.productoServicio.guardarProducto(producto);
    }


    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id){
     //buscar en la base de datos
      Producto producto = this.productoServicio.buscarProductoPorId(id);
      if(producto != null)
      return ResponseEntity.ok(producto);
      else
       throw new RecursoNoEncontradoExcepcion("No se encontro el id ");
    }


    //metodo que actualiza un producto ya existente por id del producto
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(

        @PathVariable int id,   //obtiene el parametro del id por PathVariable
        @RequestBody Producto productoRecibido){ //se recibe la informacionque viene del formulario

        //se vueve a buscar el id a la bd y recuperamos de nuevo la informacion
        //con set recibimos el producto que estamos recibiendo productoRecibido.getDescripcion
        //asi se actualiza la informacion del formulario y estamos actualizando sobre el producto que recuperamos de la BD
        Producto producto = this.productoServicio.buscarProductoPorId(id);

        //si no encontramos registro al actualizar no haya error
        if(producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);

        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setExistencia(productoRecibido.getExistencia());
        this.productoServicio.guardarProducto(producto); //guarda el objeto de tipo producto con los nuevos valores
        // retornamos un  ok y el objeto de producto que se ha actualizado la BD
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("/productos/{id}")
    //responde con una cadena de eliminado y un valor boleano true o false
    //se usa un Map<> para responder dos valores
    //recibimos un parametro de tipo id con @PathVariable
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id){

        //aseguramos que existe el producto en la Bd
        Producto producto = productoServicio.buscarProductoPorId(id);

        //para que no haya error cuando no hay id ya se haya eliminado
        if(producto == null)
            throw new RecursoNoEncontradoExcepcion("No se encontro el id: " + id);

        //por medio del objeto producto (producto.getIdProducto()); obtenemos el id para eliminar el producto
        this.productoServicio.eliminarProductoPorId(producto.getIdProducto());
        //con map procesamos la respuesta para que sea dos valores
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado ", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);

    }

}



