package com.example.accessingdatamysql;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
@RequestMapping(path="/mascotas") 
public class MainController {
  @Autowired 
  private UserRepository userRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;


  @PostMapping(path="/add") 
  public @ResponseBody String addNewUser (@RequestParam String nombre, 
      @RequestParam String raza, @RequestParam String propietario) {
    
    User n = new User();
    n.setName(nombre);
    n.setRaza(raza);
    n.setPropietario(propietario);
    userRepository.save(n);
    return "Saved";
  }

  @GetMapping(path="/all")
  public @ResponseBody Iterable<User> getAllUsers() {
    
    return userRepository.findAll();
  }

  @PutMapping(path="/edit")
  public @ResponseBody String editUser(@RequestParam String nombre,
      @RequestParam Integer id, @RequestParam String raza, @RequestParam String propietario) {
    User n = userRepository.findById(id).orElse(null);
    if (n != null) {
      n.setName(nombre);
      n.setRaza(raza);
      n.setPropietario(propietario);
      userRepository.save(n);
      return "Edited";
    } else {
      return "Error";
    }
  }
    
  @GetMapping(path="/ver/{id}")
  public @ResponseBody User getUser(@PathVariable Integer id) {
    return userRepository.findById(id).get();
  }

  @DeleteMapping(path="/del")
  public @ResponseBody String editUser(@RequestParam Integer id) {
    User n = new User();
    n.setId(id);
    userRepository.delete(n);
    return "Deleted";
  }

  @GetMapping(path="/get/report")
  public @ResponseBody List<Map<String, Object>> getReport(){
    String sql = "SELECT CONCAT(name, raza, propietario) as MIS_USUARIOS_CON_SUS_MASCOTAS FROM user;";                           
    List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
    return users;

  }


}