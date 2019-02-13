package CadastroContatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import CadastroContatos.model.Contatos;

public interface ContatosRepositoery extends JpaRepository<Contatos, Long>{

}
