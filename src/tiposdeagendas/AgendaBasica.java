/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiposdeagendas;

import clases.Agenda;
import clases.Turno;
import java.time.LocalDate;
import java.util.HashSet;

/**
 *
 * @author Tony Manjarres
 */
public class AgendaBasica extends Agenda {

    private HashSet<String> usuarios;

    public AgendaBasica(String propietario, String descripcion) {
        super(propietario, descripcion);
        this.usuarios = new HashSet<>();
    }

    @Override
    public boolean reservar(String usuario, Turno turno) {
        if (!this.usuarios.contains(usuario)) {
            this.usuarios.add(usuario);
            return super.reservar(usuario, turno);
        }

        return false;
    }

    @Override
    public Turno nuevoTurnoLibre() {
        LocalDate fechaActual = LocalDate.now();
        Turno tr = new Turno();
        for (Turno turno : this.turnos) {
            if (!turnoOcupado(turno)) {
                LocalDate fecha = turno.getFecha();
                if (fecha.isBefore(fechaActual)) {
                    fechaActual = fecha;
                    tr = turno;
                }
            }
        }

        return tr;
    }

    @Override
    public AgendaBasica clone() {
        AgendaBasica a = (AgendaBasica) super.clone();
        a.usuarios = new HashSet<>(a.usuarios);
        return a;
    }
}
