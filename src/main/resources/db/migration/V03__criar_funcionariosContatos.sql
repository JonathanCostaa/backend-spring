CREATE TABLE funcionariosContatos(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	cargo VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
	telefone VARCHAR(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;