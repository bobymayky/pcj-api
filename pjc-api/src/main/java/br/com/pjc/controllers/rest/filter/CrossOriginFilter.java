package br.com.pjc.controllers.rest.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pjc.controllers.services.AutenticacaoService;
import util.ValidationUtil;

/**
 * <p>Implementação do filtro CORS (Cross-origin resources sharing).</p></p>
 * 
 * 
 */
@Component
public class CrossOriginFilter implements Filter {
	
	/**
	 * Cabeçalhos permitidos.
	 */
	public final String DEFAULT_ALLOWED_HEADERS = "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization";
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	private List<String> endpointsDesprotegidos = new ArrayList<String>();
	
	public AutenticacaoService getAutenticacaoService() {
		return autenticacaoService;
	}
	
	/**
	 * @return Lista de endpoints que não necessitam de autenticação.
	 */
	protected List<String> getEndpointsDesprotegidos() {
		return endpointsDesprotegidos;
	}
	
	/**
	 * @return Cabeçalhos permitidos.
	 */
	public String getCabecalhosPermitidos() {
		return DEFAULT_ALLOWED_HEADERS;
	}

	/**
	 * <p>Verifica se um determinado endpoint não requer autenticação.</p>
	 * 
	 * @param endpoint Endpoint que será verificado. Obrigatório.
	 * @return <b>true</b> caso o endpoint não requeira autenticação ou <b>false</b>, caso contrário.
	 */
	protected boolean isEndpointDesprotegido( String endpoint ){
		for( String endpointDesprotegido : getEndpointsDesprotegidos() ){
			if( endpointDesprotegido.endsWith("*") ){
				if( endpoint.startsWith(endpointDesprotegido.replace("*", "")) ){
					return true;
				}
			} else {
				if( endpoint.equals(endpointDesprotegido) ){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		getEndpointsDesprotegidos().add( "/api/autenticacao/autenticar" );
		getEndpointsDesprotegidos().add( "/api/album/*" );
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE, PATCH");
		response.setHeader("Access-Control-Allow-Headers", getCabecalhosPermitidos() );
		String authorizationHeader = request.getHeader("Authorization");
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			if( !isEndpointDesprotegido(request.getServletPath()) ){
				if(!ValidationUtil.isEmpty(authorizationHeader)){
				  	   String token = authorizationHeader.substring("Bearer".length()).trim();
					   getAutenticacaoService().buscarSessao( request.getRemoteAddr(), token );
					   chain.doFilter(req, resp);
				} else {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				}	
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void destroy() {
	}
	
}