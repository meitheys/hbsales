package br.com.hbsis.usuario;

import org.apache.catalina.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;


/**
 * Classe resposável por receber as requisições externas ao sistema
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/usuarios")
public class UsuarioRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioRest.class);

	private final UsuarioService usuarioService;
	private final IUsuarioRepository iUsuarioRepository;

	@Autowired
	public UsuarioRest(UsuarioService usuarioService, IUsuarioRepository iUsuarioRepository) {
		this.usuarioService = usuarioService;
		this.iUsuarioRepository = iUsuarioRepository;
	}

	@PostMapping("/save")
	public UsuarioDTO save(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
		LOGGER.info("Recebendo solicitação de persistência de usuário...");
		LOGGER.debug("Payload: {}", usuarioDTO);

		return this.usuarioService.save(usuarioDTO);
	}

	@GetMapping("/{id}")
	public UsuarioDTO find(@PathVariable("id") Long id) {

		LOGGER.info("Recebendo find by ID... id: [{}]", id);

		return this.usuarioService.findById(id);
	}

	@GetMapping("/findUsuarios")
	public List<Usuario> findAll() {
		return this.iUsuarioRepository.findAll();
	}

	@PutMapping("/{id}")
	public UsuarioDTO udpate(@PathVariable("id") Long id, @RequestBody UsuarioDTO usuarioDTO) throws Exception {
		LOGGER.info("Recebendo Update para Usuário de ID: {}", id);
		LOGGER.debug("Payload: {}", usuarioDTO);

		return this.usuarioService.update(usuarioDTO, id);
	}

	@DeleteMapping("delete/{id}")
	public void delete(@PathVariable("id") Long id) {
		LOGGER.info("Recebendo Delete para Usuário de ID: {}", id);

		this.usuarioService.delete(id);
	}

	/* Don't deal with that ^^^ */

	/* Deal with what's on JWTController, that's the try of to inputting JWT in my Back-Front Application to connect this with the Angular and to do the Login of the application */
}
