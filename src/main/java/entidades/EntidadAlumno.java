package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Alumno")
public class EntidadAlumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //La opción más usada con MySQL
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "FechaNacimiento")
    private String FechaNacimiento;

    // Constructor con parámetros
    public EntidadAlumno(String nombre, String apellido, String FechaNacimiento) {
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(FechaNacimiento);
    }

    // Constructor vacío
    public EntidadAlumno() {
    }

    // Getters y setters
    public int getIdAlumno() {
        return id;
    }

    public void setIdAlumno(int id) {
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

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    @Override
    public String toString() {
        return "--------------------------------------\n" +
               "📌 ID: " + this.id + "\n" +
               "👤 Nombre: " + this.nombre + " " + this.apellido + "\n" +
               "📅 Fecha de nacimiento: " + this.FechaNacimiento + "\n" +
               "--------------------------------------";
    }

    // Relación de uno a muchos con EntidadMatricula
    @OneToMany(mappedBy = "alumnado", cascade = CascadeType.ALL)
    private List<EntidadMatricula> listaMatriculass;
}
