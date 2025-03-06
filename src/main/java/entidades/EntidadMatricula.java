package entidades;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Matricula")
public class EntidadMatricula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne  // Relación con EntidadProfesor
    @JoinColumn(name = "idProfesorado")
    private EntidadProfesor profesorado;

    @ManyToOne  // Relación con EntidadAlumno
    @JoinColumn(name = "idAlumnado")
    private EntidadAlumno alumnado;

    @Column(name = "asignatura")
    private String asignatura;

    @Column(name = "curso")
    private int curso;

    // Constructores
    public EntidadMatricula(EntidadProfesor profesorado, EntidadAlumno alumnado, String asignatura, int curso) {
        this.profesorado = profesorado;
        this.alumnado = alumnado;
        this.asignatura = asignatura;
        this.curso = curso;
    }

    public EntidadMatricula() {}

    // Getters y setters
    public int getIdMatricula() {
        return id;
    }

    public void setIdMatricula(int id) {
        this.id = id;
    }

    public EntidadProfesor getProfesorado() {
        return profesorado;
    }

    public void setProfesorado(EntidadProfesor profesorado) {
        this.profesorado = profesorado;
    }

    public EntidadAlumno getAlumnado() {
        return alumnado;
    }

    public void setAlumnado(EntidadAlumno alumnado) {
        this.alumnado = alumnado;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public int getCurso() {
        return curso;
    }

    public void setCurso(int curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "--------------------------------------\n" +
               "📌 ID: " + this.id + "\n" +
               "👨‍🏫 Profesorado: " + profesorado.getNombre() + "\n" +
               "🎓 Alumnado: " + alumnado.getNombre() + "\n" +
               "📖 Asignatura: " + this.asignatura + "\n" +
               "📆 Curso: " + this.curso + "\n" +
               "--------------------------------------";
    }
}
