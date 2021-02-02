package br.com.pjc.controllers.rest.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import br.com.pjc.controllers.rest.annotations.UsuarioAutenticado;
import br.com.pjc.controllers.services.AutenticacaoService;
import br.com.pjc.model.entities.Usuario;
import util.ValidationUtil;

@Component
public class UsuarioAutenticadoRestResolver implements HandlerMethodArgumentResolver {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	/**
	 * @return Serviço de autenticação.
	 */
	public AutenticacaoService getAutenticacaoService() {
		return autenticacaoService;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(UsuarioAutenticado.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return null;
	}

}