package br.com.pjc.controllers.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pjc.controllers.services.AutenticacaoService;
import br.com.pjc.model.valuesobjects.AccessTokenResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import util.oauth2.OauthGrantType;
import util.validator.MD5HashValidator;


@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoRestController {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	

	private AutenticacaoService getAutenticacaoService() {
		return autenticacaoService;
	}
	
	@ApiOperation("Autentica um usuário com as informações fornecidas.")
	@RequestMapping(path = "/autenticar", method = RequestMethod.POST)
	public @ResponseBody AccessTokenResponse autenticarUsuario(
		HttpServletRequest request,
		@ApiParam("Tipo de autenticação") @RequestParam(name="grant_type",defaultValue="password",required=true) OauthGrantType grantType,
		@ApiParam("E-mail do usuário") @RequestParam(name="username",required=true) String username,
		@ApiParam("Senha") @RequestParam(name="password",required=true) String password ) {
			if( new MD5HashValidator().isValid(password, null)){
	   	     	AccessTokenResponse retorno = getAutenticacaoService().autenticarUsuario( request.getRemoteAddr(), username, password );
			    return retorno;
			} else {
				//throw new GenericApiException( SenhaIncorretaException.ExceptionCode.SENHA_INCORRETA );
			}
			
			return null;
	
	}
	
	@ApiOperation("Verifica a autenticidade de um token.")
	@RequestMapping(path = "/token/validar", method = RequestMethod.GET)
	public @ResponseBody AccessTokenResponse validar(
		HttpServletRequest request,
		@ApiParam("Token JWT") @RequestParam(name="token",required=true) String token ){
			token = token.substring("Bearer".length()).trim();
			getAutenticacaoService().buscarSessao( request.getRemoteAddr(), token );
			AccessTokenResponse response = new AccessTokenResponse();
			response.setAccessToken(token);
			return response;
		
	}
	
		
}
