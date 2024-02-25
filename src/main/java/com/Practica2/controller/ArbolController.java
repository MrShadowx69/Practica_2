
package com.Practica2.controller;
import com.example.demo.model.Arbol;
import com.example.demo.repository.ArbolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/arboles")
public class ArbolController {

    @Autowired
    private ArbolRepository arbolRepository;

    @GetMapping("")
    public String listarArboles(Model model) {
        model.addAttribute("arboles", arbolRepository.findAll());
        return "index";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("arbol", new Arbol());
        return "agregarArbol";
    }

    @PostMapping("/nuevo")
    public String guardarArbol(@ModelAttribute Arbol arbol) {
        arbolRepository.save(arbol);
        return "redirect:/arboles";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditar(@PathVariable int id, Model model) {
        Arbol arbol = arbolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Árbol no encontrado"));
        model.addAttribute("arbol", arbol);
        return "editarArbol";
    }

    @PostMapping("/{id}/editar")
    public String actualizarArbol(@PathVariable int id, @ModelAttribute Arbol arbolActualizado) {
        Arbol arbol = arbolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Árbol no encontrado"));
        arbol.setNombreComun(arbolActualizado.getNombreComun());
        arbol.setRutaImagen(arbolActualizado.getRutaImagen());
        arbol.setTipoFlor(arbolActualizado.getTipoFlor());
        arbol.setDurezaMadera(arbolActualizado.getDurezaMadera());
        arbol.setTipoFrutos(arbolActualizado.getTipoFrutos());
        arbolRepository.save(arbol);
        return "redirect:/arboles";
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarArbol(@PathVariable int id) {
        Arbol arbol = arbolRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Árbol no encontrado"));
        arbolRepository.delete(arbol);
        return "redirect:/arboles";
    }
}