package clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

public abstract class Agenda implements Cloneable {

    protected String propietario;
    protected String descripcion;
    protected HashSet<Turno> turnos;
    protected Map<Turno, String> reservas;

    protected Agenda(String propietario, String descripcion) {
        this.propietario = propietario;
        this.descripcion = descripcion;
        this.turnos = new HashSet<>();
        this.reservas = new HashMap<>();
    }

    //Metodos de Consulta
    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public HashSet<Turno> getTurnos() {
        return new HashSet<>(this.turnos);
    }

    public Map<Turno, String> getReservas() {
        return new HashMap<>(this.reservas);
    }

    //Funcionalidades
    public boolean agregarNuevoTurno(String franja, LocalDate fecha) {
        Turno tr = new Turno(franja, fecha);
        return turnos.add(tr);
    }

    public boolean ajustarDias(int dias) {
        return (dias >= 0 ? atrasarFecha(dias) : adelantarFecha(dias));
    }

    public boolean atrasarFecha(int dias) {
        for (Turno t : turnos) {
            LocalDate fecha = t.getFecha().plusDays(dias);
            t.setFecha(fecha);
        }
        return true;
    }

    public boolean adelantarFecha(int dias) {
        for (Turno t : turnos) {
            LocalDate fecha = t.getFecha().minusDays(dias);
            t.setFecha(fecha);
        }
        return true;
    }

    public LinkedList<Turno> turnosPorDia(LocalDate fecha) {
        LinkedList<Turno> listaTurnos = new LinkedList();
        ArrayList<Turno> copiaConjuntoTurnos = new ArrayList<>(this.turnos);
        for(int i = 0; i < copiaConjuntoTurnos.size(); i++) {
            Turno turno = copiaConjuntoTurnos.get(i);
            LocalDate fechaTurno = turno.getFecha();
            if(fechaTurno.isEqual(fecha)) {
                listaTurnos.add(turno);
            }
        }
        
        return listaTurnos;
    }

    public boolean reservar(String usuario, Turno turno) {
        if (turnos.contains(turno)) {
            if (!turnoOcupado(turno)) {
                reservas.put(turno, usuario);
                return true;
            }
        }

        return false;
    }

    public abstract Turno nuevoTurnoLibre();
    public Turno reservar(String usuario) {
        Turno turno = nuevoTurnoLibre();
        if (turno != null) {
            reservar(usuario, turno);
            return turno;
        }

        return null;
    }

    //Funciones relacionadas a las reservas
    public String usuarioTurno(Turno t) {
        if (!reservas.isEmpty()) {
            return reservas.get(t);
        }

        return null;
    }

    public boolean turnoOcupado(Turno t) {
        return (usuarioTurno(t) != null);
    }

    public boolean cancelarReserva(String usuario, Turno turno) {
        if (reservas.containsKey(turno)) {
            String usuarioTurno = reservas.get(turno);
            if (usuarioTurno.equals(usuario)) {
                reservas.remove(turno);
                return true;
            }
        }

        return false;
    }

    @Override
    public Agenda clone() {
        try {
            Agenda a = (Agenda) super.clone();
            a.turnos = new HashSet<>(a.turnos);
            a.reservas = new HashMap<>();
            return a;
        } catch (CloneNotSupportedException e) {
            System.err.println("ERROR EN LA CLONACION: " + e.getMessage());
        }

        return null;
    }
}
