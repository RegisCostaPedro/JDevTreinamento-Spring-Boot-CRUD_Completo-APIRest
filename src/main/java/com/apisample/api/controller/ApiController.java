package com.apisample.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.apisample.api.model.Usuario;
import com.apisample.api.repositories.UsuarioRepository;

@RestController
public class ApiController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @GetMapping(value = "/mostranome/{name}")
  @ResponseStatus(HttpStatus.OK)
  public String greetinText(@PathVariable String name) {
    return "Curso Spring Boot API: " + name + "!";
  }

 
 
  @GetMapping(value ="/olamundo/{nome}")
  @ResponseStatus(HttpStatus.OK)
  public String retornaOlaMundo(@PathVariable String nome) {
    Usuario usuario = new Usuario();
    usuario.setNome(nome);
    usuarioRepository.save(usuario); /*Grava o nome no banco de dados */
    return "Olá mundo! " + nome;
  }

  @GetMapping(value = "listatodos")/* Nosso primeiro método de API */
  @ResponseBody /*Retorna os dados para o corpo da Resposta*/
  public ResponseEntity<List<Usuario>> listUsuario(){
     List<Usuario> usuarios = usuarioRepository.findAll(); /*Executa a consulta no banco de dados */

     return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /*Retorna a lista em json */
  }

  @PostMapping(value = "salvar") /*Mapeia a URL */
  @ResponseBody /* Vai fazer a descrição da resposta */
  public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /* Recebe os dados para Salvar */
    Usuario user = usuarioRepository.save(usuario);
     return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
}

@DeleteMapping(value = "delete") /*Mapeia a URL */
@ResponseBody /* Vai fazer a descrição da resposta */
public ResponseEntity<String> delete(@RequestParam Long idUser){ /* Recebe os dados para deletar */
   usuarioRepository.deleteById(idUser);
   return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);
}


@GetMapping(value = "buscaruserid") /*Mapeia a URL */
@ResponseBody /* Vai fazer a descrição da resposta */
public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "idUser") Long idUser){ /* Recebe os dados para consultar */
  Usuario usuario = usuarioRepository.findById(idUser).get();
   return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
}

@PutMapping(value = "atualizar") /*Mapeia a URL */
@ResponseBody /* Vai fazer a descrição da resposta */
public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ /* Recebe os dados para consultar */
  if (usuario.getId() == null) {
    return new ResponseEntity<String>("Id do usuário não foi encontrado", HttpStatus.OK);
  }
  Usuario user = usuarioRepository.saveAndFlush(usuario);
   return new ResponseEntity<Usuario>(user, HttpStatus.OK);
}


@GetMapping(value = "buscarPorNome") /*Mapeia a URL */
@ResponseBody /* Vai fazer a descrição da resposta */
public ResponseEntity<List<Usuario>> buscarPorNome( @RequestParam(name = "name") String name){ /* Recebe os dados para consultar */
  
  List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

   return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
}
}




