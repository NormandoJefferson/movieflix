package com.devsuperior.movieflix.services.exceptions;

// Erro 401: Usuário não logado.
public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String msg) {
		super(msg);
	}
}
