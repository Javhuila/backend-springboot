package com.project.flutter_backend_desktop.servicio.otp;

import com.project.flutter_backend_desktop.repositorio.RecuperarContrasenaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LimpiadorOtpServicio {
    @Autowired
    private RecuperarContrasenaRepositorio forgotPasswordRepositorio;

    @Scheduled(fixedRate = 60000)
    public void limpaOtp() {
        forgotPasswordRepositorio.findAll().forEach(fp -> {
            if (fp.getTiempoEspera().before(new Date())) {
                forgotPasswordRepositorio.delete(fp);
            }
        });
    }
}
