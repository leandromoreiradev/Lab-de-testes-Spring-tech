package com.lemoreiradev.listadetarefa.domain.service;

import com.lemoreiradev.listadetarefa.domain.model.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void notificar(Pessoa pessoa) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("APP Spring <equipe.app@email.com>");
        message.setSubject("[CRIAÇÃO] Criação de pessoa");
        message.setText(String.format("Olá você acabou de criar a pessoa %s no banco de dados!", pessoa.getNome()));
        message.setTo("elaineoliveira1992@gmail.com");

        javaMailSender.send(message);

    }
}