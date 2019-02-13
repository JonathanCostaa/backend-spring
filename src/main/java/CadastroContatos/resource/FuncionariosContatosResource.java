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

import CadastroContatos.model.FuncionariosContatos;
import CadastroContatos.repository.FuncionariosContatosRepository;


@CrossOrigin("*")
@RestController
@RequestMapping("/FuncionariosContatos")

public class FuncionariosContatosResource {
	
	@Autowired
	private FuncionariosContatosRepository  funcionariosContatosRepository;
	
	
	@GetMapping
	public List<FuncionariosContatos> Listar(){
		
		return funcionariosContatosRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<FuncionariosContatos> criar(@RequestBody FuncionariosContatos funcionariosContatos,HttpServletResponse response ){
		
	 FuncionariosContatos funcionariosContatosSalvo = funcionariosContatosRepository.save(funcionariosContatos);
	 
	 URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
			 .buildAndExpand(funcionariosContatosSalvo.getId()).toUri();
	 response.setHeader("Location", uri.toASCIIString());
	 
	 return  ResponseEntity.created(uri).body(funcionariosContatosSalvo);
	}
	
	 @GetMapping("/{id}")
	 public FuncionariosContatos buscarPeloId(@PathVariable Long id){
		 return funcionariosContatosRepository.findOne(id);
	}
	 
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id){
		
		funcionariosContatosRepository.delete(id);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<FuncionariosContatos> atualizar(@PathVariable Long id, @Valid @RequestBody FuncionariosContatos funcionariosContatos){
	
		FuncionariosContatos funcContatosSalvo = funcionariosContatosRepository.findOne(id);
		
		if( funcContatosSalvo== null){
			
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(funcionariosContatos  , funcContatosSalvo, "id");
		funcionariosContatosRepository.save(funcContatosSalvo);
		return ResponseEntity.ok(funcContatosSalvo);
			
	
	}
	
	
}
