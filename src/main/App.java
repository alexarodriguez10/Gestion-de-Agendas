package main;

import clases.Agenda;
import clases.Turno;
import tiposdeagendas.AgendaBalanceada;
import tiposdeagendas.AgendaBasica;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    public static void main(String[] args) {
        //Crea una agenda básica cuyo propietario sea “Enrique” y como descripción “tutorías”.
        /*Para esa agenda, establece los siguientes turnos:
            o 12 de diciembre de 2018: “09:30 – 10:00” y “10:30 – 11:00”.
            o 13 de diciembre de 2018: “10:30 – 11:00”.*/
        AgendaBasica agendaBasica = new AgendaBasica("Erique", "Tutorías");
        agendaBasica.agregarNuevoTurno("09:30 - 10:00", LocalDate.of(2018, Month.DECEMBER, 12));
        agendaBasica.agregarNuevoTurno("10:30 - 11:00", LocalDate.of(2018, Month.DECEMBER, 12));
        agendaBasica.agregarNuevoTurno("10:30 - 11:00", LocalDate.of(2018, Month.DECEMBER, 13));

        //Crea una agenda balanceada cuyo propietario sea “Enrique” y descripción “Revisión de examen”.
        /*  12 de diciembre de 2018: “12:00 – 12:30” y “13:30 – 14:00”.
            o 13 de diciembre de 2018: “11:00 – 11:30”, “12:30 – 13:00” y “13:00 – 13:30”.*/
        AgendaBalanceada agendaBalanceada = new AgendaBalanceada("Enrique", "Revisión de Examen");
        agendaBalanceada.agregarNuevoTurno("12:00 - 12:30", LocalDate.of(2018, Month.DECEMBER, 12));
        agendaBalanceada.agregarNuevoTurno("13:30 - 14:00", LocalDate.of(2018, Month.DECEMBER, 12));
        agendaBalanceada.agregarNuevoTurno("11:00 - 11:30", LocalDate.of(2018, Month.DECEMBER, 13));
        agendaBalanceada.agregarNuevoTurno("12:30 - 13:00", LocalDate.of(2018, Month.DECEMBER, 13));
        agendaBalanceada.agregarNuevoTurno("13:00 - 13:30", LocalDate.of(2018, Month.DECEMBER, 13));

        //Añade las agendas anteriores agenda una agendas de agendas.
        ArrayList<Agenda> agendas = new ArrayList<>();
        Collections.addAll(agendas, agendaBasica, agendaBalanceada);

        /*Recorre la agendas de agendas y para cada una de ellas:
            o Muestra por la consola su descripción.
            o Muestra por la consola el número de turnos del 13 de diciembre.
            o El usuario “Juan” hace dos reservas automáticas. Muestra por la consola los turnos que se han reservado.
            o Recorre los turnos del 13 de diciembre y muestra por la consola los turnos (toString) que estén ocupados.
            o El usuario “Juan” cancela el primero de los turnos que ha reservado.
            o Por último, si la agenda es balanceada:
                ▪ Muestra el día con mayor disponibilidad.
                ▪ Crea una copia y retrasa una semana los turnos de la copia.
                ▪ Muestra por la consola los turnos de la copia (toString).*/
        for (Agenda agenda : agendas) {
            System.out.println(agenda.toString());
            System.out.print("Turnos para el 13 de Diciembre: ");
            System.out.println(agenda.turnosPorDia(LocalDate.of(2018, Month.DECEMBER, 13)));
            agenda.reservar("Juan");
            agenda.reservar("Juan");
            System.out.println("turnos que han sido reservados: ");
            for (Turno t : agenda.getReservas().keySet()) {
                System.out.println(t.toString());
            }
            System.out.println("Turnos Ocupados del 13 de Dicimebre");
            for (Turno t : agenda.getTurnos()) {
                if (t.getFecha().equals(LocalDate.of(2018, Month.DECEMBER, 13))) {
                    if (agenda.turnoOcupado(t)) {
                        System.out.println(t.toString());
                    }
                }
            }
            ArrayList<Turno> turnos = new ArrayList<>(agenda.getReservas().keySet());
            agenda.cancelarReserva("Juan", turnos.get(0));
            if (agenda instanceof AgendaBalanceada) {
                System.out.print("Dia con mayor disponibilidad: ");
                LocalDate fecha = ((AgendaBalanceada) agenda).fechaMinima();
                System.out.println(fecha.toString());
                AgendaBalanceada copia = (AgendaBalanceada) ((AgendaBalanceada) agenda).clone();
                copia.ajustarDias(7);
                System.out.println("Turnos de la Copia (Atrasados una Semana): ");
                for (Turno t : copia.getTurnos()) {
                    System.out.println(t.toString());
                }
            }
            System.out.println("\n\n---------------------------------\n");
        }
    }
}
