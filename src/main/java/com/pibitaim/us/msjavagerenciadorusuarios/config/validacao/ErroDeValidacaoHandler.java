package com.pibitaim.us.msjavagerenciadorusuarios.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception){
        List<ErroDeFormularioDto> dto = new ArrayList<>();
        List<FieldError> fieldErros = exception.getBindingResult().getFieldErrors();

        fieldErros.forEach(erro -> {
            String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
            dto.add(new ErroDeFormularioDto(erro.getField(), mensagem));
        });

        return dto;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ErroPadrao handle(NoSuchAlgorithmException exception){
        ErroPadrao erroPadrao = new ErroPadrao("Ocorreu um erro inesperado no sistema!", exception.getMessage());
        return erroPadrao;
    }

}
