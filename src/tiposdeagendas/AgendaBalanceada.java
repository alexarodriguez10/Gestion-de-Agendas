/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiposdeagendas;

import clases.Agenda;
import clases.Turno;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author Tony Manjarres
 */
public class AgendaBalanceada extends Agenda {

    private HashMap<LocalDate, Integer> mapaBalanceo;

    public AgendaBalanceada(String propietario, String descripcion) {
        super(propietario, descripcion);
        this.mapaBalanceo = new HashMap<>();
    }

    @Override
    public boolean agregarNuevoTurno(String franja, LocalDate fecha) {
        if (!mapaBalanceo.containsKey(fecha)) {
            mapaBalanceo.put(fecha, 1);
        } else {
            int var = mapaBalanceo.get(fecha);
            var = var + 1;
            mapaBalanceo.replace(fecha, var);
        }
        return super.agregarNuevoTurno(franja, fecha);
    }

    @Override
    public boolean reservar(String usuario, Turno turno) {
        LocalDate fecha = turno.getFecha();
        if (mapaBalanceo.containsKey(fecha)) {
            int var = mapaBalanceo.get(fecha);
            var = var - 1;
            mapaBalanceo.replace(fecha, var);
        }
        return super.reservar(usuario, turno);
    }

    @Override
    public boolean cancelarReserva(String usuario, Turno turno) {
        LocalDate fecha = turno.getFecha();
        if (!mapaBalanceo.containsKey(fecha)) {
            mapaBalanceo.put(fecha, 1);
        } else {
            int var = mapaBalanceo.get(fecha);
            var = var + 1;
            mapaBalanceo.replace(fecha, var);
        }
        return super.cancelarReserva(usuario, turno);
    }

    public LocalDate fechaMinima() {
        int min = 100;
        LocalDate auxFecha = null;
        for (LocalDate ld : mapaBalanceo.keySet()) {
            int var = mapaBalanceo.get(ld);
            if (var < min) {
                min = var;
                auxFecha = LocalDate.of(ld.getYear(), ld.getMonth(), ld.getDayOfMonth());
            }
        }

        return auxFecha;
    }

    @Override
    public Turno nuevoTurnoLibre() {
        LocalDate fecha = fechaMinima();
        for(Turno turno : this.turnos) {
            if(fecha.equals(turno.getFecha())) {
                return turno;
            }
        }
        
        return null;
    }

    @Override
    public AgendaBalanceada clone() {
        AgendaBalanceada agendab = (AgendaBalanceada) super.clone();
        agendab.mapaBalanceo = new HashMap<>(agendab.mapaBalanceo);
        return agendab;
    }
}
