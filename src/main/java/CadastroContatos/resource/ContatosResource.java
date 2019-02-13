package CadastroContatos.resource;


import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import CadastroContatos.model.Contatos;
import CadastroContatos.repository.ContatosRepositoery;

@CrossOrigin("*")
@RestController
@RequestMapping("/Contatos")

public class ContatosResource {
	
	@Autowired
	private ContatosRepositoery  contatosRepository;
	
	@GetMapping
	public List<Contatos> Listar(){
		
		return contatosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Contatos> criar(@RequestBody Contatos contatos,HttpServletResponse response ){
		
		Contatos contatosSalvo = contatosRepository.save(contatos);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/id}")
		.buildAndExpand(contatosSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return  ResponseEntity.created(uri).body(contatosSalvo);
	}

	 @GetMapping("/{id}")
	 public Contatos buscarPeloId(@PathVariable Long id){
		 return contatosRepository.findOne(id);
	}
	 
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
	contatosRepository.delete(id);
	}
		
	@PutMapping("/{id}")
		public ResponseEntity<Contatos> atualizar(@PathVariable Long id, @Valid @RequestBody Contatos contatos){
		
		Contatos  contatosSalva = contatosRepository.findOne(id);
			
		if( contatosSalva == null){	
		throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(contatos, contatosSalva, "id");
	    contatosRepository.save(contatosSalva);
		return ResponseEntity.ok(contatosSalva);
				
		
	}

}
