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

import CadastroContatos.model.Funcionarios;
import CadastroContatos.repository.FuncionariosRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/Funcionarios")

public class FuncionariosResource {

	@Autowired
	private FuncionariosRepository funcionariosRepository;

	@GetMapping
	public List<Funcionarios> Listar() {

		return funcionariosRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Funcionarios> criar(@RequestBody Funcionarios funcionarios, HttpServletResponse response) {

		Funcionarios funcionariosSalvo = funcionariosRepository.save(funcionarios);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(funcionariosSalvo.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());

		return ResponseEntity.created(uri).body(funcionariosSalvo);
	}

	@GetMapping("/{id}")
	public Funcionarios buscarPeloId(@PathVariable Long id) {
		return funcionariosRepository.findOne(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {

		funcionariosRepository.delete(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Funcionarios> atualizar(@PathVariable Long id,
			@Valid @RequestBody Funcionarios funcionarios) {

		Funcionarios funcionariosSalva = funcionariosRepository.findOne(id);

		if (funcionariosSalva == null) {

			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(funcionarios, funcionariosSalva, "id");
		funcionariosRepository.save(funcionariosSalva);
		return ResponseEntity.ok(funcionariosSalva);

	}

}
