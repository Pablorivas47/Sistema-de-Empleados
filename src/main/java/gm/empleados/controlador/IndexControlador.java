package gm.empleados.controlador;

import gm.empleados.modelo.Empleado;
import gm.empleados.servicio.EmpleadoServicio;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.slf4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller

public class IndexControlador {

    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);

    @Autowired
    EmpleadoServicio  empleadoServicio;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String iniciar(ModelMap modelMap){
        List<Empleado> empleados = empleadoServicio.listarEmpleados();
        empleados.forEach(empleado -> logger.info(empleado.toString()));
        modelMap.put("empleados", empleados);
        return "index"; // index.jsp
    }

    @RequestMapping(value="/agregar", method = RequestMethod.GET)
    public String mostrarAgregar(){
        return "agregar";
    }

    @RequestMapping(value="/agregar", method = RequestMethod.POST)
    public String agregar(@ModelAttribute("empleadoForma") Empleado empleado, HttpServletRequest request) {
        logger.info("Empleado a agregar: " + empleado.toString());
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/"; // redirige al path "/"
    }

    @RequestMapping(value="/editar", method = RequestMethod.GET)
    public String mostrarEditar(@RequestParam int idEmpleado, ModelMap modelMap) {
        Empleado empleado = empleadoServicio.buscarEmpleadoPorId(idEmpleado);
        logger.info("Empleado a editar: " + empleado.toString());
        modelMap.put("empleado", empleado);
        return "editar"; //mostrar editar.jsp
    }

    @RequestMapping(value="/editar", method = RequestMethod.POST)
    public String editar(@ModelAttribute("empleadoForma") Empleado empleado) {
        logger.info("Empleado a guardar: " + empleado.toString());
        empleadoServicio.guardarEmpleado(empleado);
        return "redirect:/";
    }
    @RequestMapping(value="/eliminar", method = RequestMethod.GET)
    public String eliminar(@RequestParam int idEmpleado) {
        logger.info("Empleado a eliminar: " + idEmpleado);
        empleadoServicio.eliminarEmpleado(idEmpleado);
        return "redirect:/"; //
    }

}
