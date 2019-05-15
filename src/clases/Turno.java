package clases;

import java.time.LocalDate;
import java.util.Objects;

public class Turno {

    private LocalDate fecha;
    private final String franja;

    
    public Turno(String franja, LocalDate fecha) {
        this.franja = franja;
        this.fecha = fecha;
    }
    
    public Turno() {
        //Constructor vacio
        this("", LocalDate.now());
    }

    //Metodos de Consulta
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getFranja() {
        return franja;
    }

    //Metodos de la Clase Object
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.fecha);
        hash = 59 * hash + Objects.hashCode(this.franja);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Turno other = (Turno) obj;
        if (!Objects.equals(this.franja, other.franja)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Turno{" + "fecha=" + fecha + ", franja=" + franja + '}';
    }
}
