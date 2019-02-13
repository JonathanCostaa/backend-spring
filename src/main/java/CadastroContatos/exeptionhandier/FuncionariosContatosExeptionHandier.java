package CadastroContatos.exeptionhandier ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class FuncionariosContatosExeptionHandier extends ResponseEntityExceptionHandler  {
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String mensagemUsuario= messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvdor= ex.getCause().toString();
		return handleExceptionInternal(ex, new Erro(mensagemUsuario,mensagemDesenvolvdor), headers, status, request);

	}
	
	public static class Erro{
		String mensagemUsuario;
		String mensagemDesenvolvdor;
		
		public Erro(String mensagemUsuario, String mensagemDesenvolvdor) {
			super();
			this.mensagemUsuario = mensagemUsuario;
			this.mensagemDesenvolvdor = mensagemDesenvolvdor;
		}
	}

}
