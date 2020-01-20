package br.com.hbsis.usuario;

import org.apache.commons.lang.StringUtils;
import org.passay.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.*;

/**
 * Classe responsável pelo processamento da regra de negócio
 */
@Service
public class UsuarioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

	private final IUsuarioRepository iUsuarioRepository;
    private static final int TAMANHO_MINIMO = 6;
    private static final int TAMANHO_MAXIMO = 20;
    private static final int QUANTIDADE_DIGITOS = 4;
    private static final int QUANTIDADE_LETRAS = 2;

	public UsuarioService(IUsuarioRepository iUsuarioRepository) {
		this.iUsuarioRepository = iUsuarioRepository;
	}

	public UsuarioDTO save(UsuarioDTO usuarioDTO) throws Exception {

		this.validate(usuarioDTO);

		LOGGER.info("Salvando usuário");
		LOGGER.debug("Usuario: {}", usuarioDTO);

		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioDTO.getLogin());
		usuario.setSenha(usuarioDTO.getSenha());
		usuario.setUuid(UUID.randomUUID().toString());

		usuario = this.iUsuarioRepository.save(usuario);

		return UsuarioDTO.of(usuario);
	}

	//Checar senha
    private List<String> checkPassword(String senha) throws Exception{
	    if (StringUtils.isBlank(senha)){
	        throw new Exception("Favor valide a senha");
        }

        //Coloca uma regra de tamanho para a senha
        LengthRule lr = new LengthRule(TAMANHO_MINIMO, TAMANHO_MAXIMO);

        //Não permite espaços
        WhitespaceRule wr = new WhitespaceRule();

        //Obriga uso de caracteres letras
        AlphabeticalCharacterRule ac = new AlphabeticalCharacterRule (QUANTIDADE_LETRAS);

        //Obriga uso de números
        DigitCharacterRule dc = new DigitCharacterRule(QUANTIDADE_DIGITOS);

        //Obriga uso de caracter especial
        SpecialCharacterRule nac = new SpecialCharacterRule ();

        //Obriga uso de caracter maíusculo
        UppercaseCharacterRule uc = new UppercaseCharacterRule();

        List<Rule> ruleList = new ArrayList<Rule>();
        ruleList.add(lr);
        ruleList.add(wr);
        ruleList.add(ac);
        ruleList.add(dc);
        ruleList.add(nac);
        ruleList.add(uc);

        Properties props = new Properties();
        props.load(new FileInputStream("./src/main/resources/message.properties"));
        MessageResolver resolver = new PropertiesMessageResolver(props);

        PasswordValidator validator = new PasswordValidator(resolver, ruleList);
        PasswordData passwordData = new PasswordData(new String(senha));

        RuleResult result = validator.validate(passwordData);
        if (!result.isValid()) {
            return validator.getMessages(result);
        }
        return null;
    }

	private void validate(UsuarioDTO usuarioDTO) throws Exception {
		LOGGER.info("Validando Usuario");

		String senha = usuarioDTO.getSenha();

		this.checkPassword(senha);

		if (usuarioDTO == null) {
			throw new IllegalArgumentException("UsuarioDTO não deve ser nulo");
		}

		if (StringUtils.isEmpty(usuarioDTO.getSenha())) {
			throw new IllegalArgumentException("Senha não deve ser nula/vazia");
		}

		if (StringUtils.isEmpty(usuarioDTO.getLogin())) {
			throw new IllegalArgumentException("Login não deve ser nulo/vazio");
		}

		if(usuarioDTO.getLogin().length() < 2) {
			throw new IllegalArgumentException("Favor inserir um nome valido");
		}

		if(usuarioDTO.getSenha().length() <= 5){
			throw new IllegalArgumentException("Senha muito fraca!");
		}

	}

	public UsuarioDTO findById(Long id) {
		Optional<Usuario> usuarioOptional = this.iUsuarioRepository.findById(id);

		if (usuarioOptional.isPresent()) {
			return UsuarioDTO.of(usuarioOptional.get());
		}

		throw new IllegalArgumentException(String.format("ID %s não existe", id));
	}

	public UsuarioDTO update(UsuarioDTO usuarioDTO, Long id) throws Exception {
		this.validate(usuarioDTO);
		Optional<Usuario> usuarioExistenteOptional = this.iUsuarioRepository.findById(id);

		if (usuarioExistenteOptional.isPresent()) {
			Usuario usuarioExistente = usuarioExistenteOptional.get();

			LOGGER.info("Atualizando usuário... id: [{}]", usuarioExistente.getId());
			LOGGER.debug("Payload: {}", usuarioDTO);
			LOGGER.debug("Usuario Existente: {}", usuarioExistente);

			usuarioExistente.setLogin(usuarioDTO.getLogin());
			usuarioExistente.setSenha(usuarioDTO.getSenha());

			usuarioExistente = this.iUsuarioRepository.save(usuarioExistente);

			return UsuarioDTO.of(usuarioExistente);
		}


		throw new IllegalArgumentException(String.format("ID %s não existe", id));
	}

	public Optional<Usuario> existsUsuario(String login, String senha){
		Optional<Usuario> userOptional = iUsuarioRepository.findByLoginAndSenha(login, senha);

		if(userOptional.isPresent()){
			return userOptional;
		}
		throw new IllegalArgumentException("Usuario/Senha incorreto!");
	}

	public void delete(Long id) {
		LOGGER.info("Executando delete para usuário de ID: [{}]", id);

		this.iUsuarioRepository.deleteById(id);
	}

	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return (UserDetails) iUsuarioRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Usuário não foi encontrado com esse login - " + login));
	}

}
