package entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Profesor")
public class EntidadProfesor implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //La opción más usada con MySQL
    @Column(name="id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fechaNacimiento")  // Convención snake_case para BD
    private String fechaNacimiento;      // Cambio a camelCase en Java

    @Column(name = "antiguedad")
    private int antiguedad;

    // Constructores
    public EntidadProfesor(String nombre, String apellido, String fechaNacimiento, int antiguedad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.antiguedad = antiguedad;
    }

    public EntidadProfesor() {}

    // Getters y Setters
    public int getIdProfesor() {
        return id;
    }

    public void setIdProfesor(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFechaNacimiento() {  // Corrección del getter
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    @Override
    public String toString() {
        return "--------------------------------------\n" +
               "📌 ID: " + this.id + "\n" +
               "👤 Nombre: " + this.nombre + " " + this.apellido + "\n" +
               "📅 Fecha de nacimiento: " + this.fechaNacimiento + "\n" +
               "⏳ Antigüedad: " + this.antiguedad + " años\n" +
               "--------------------------------------";
    }

    // Relación con EntidadMatricula
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EntidadMatricula matricula;

    public EntidadMatricula getMatricula() {
        return matricula;
    }

    public void setMatricula(EntidadMatricula matricula) {
        this.matricula = matricula;
    }
}
