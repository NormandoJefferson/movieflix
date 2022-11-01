package com.devsuperior.movieflix.services.exceptions;

// Usada para o erro 403 (usuário logado mas o perfil não permite o recurso).
public class ForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String msg) {
		super(msg);
	}
}
