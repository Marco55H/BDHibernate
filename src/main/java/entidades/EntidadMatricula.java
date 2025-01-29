package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "Matricula")
public class EntidadMatricula implements Serializable {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY) //La opción más usada con MySQL
@Column(name="id")
private int id;

@Column(name="idProfesorado")
private int idProfesorado;

@Column(name="idAlumnado")
private int idAlumnado;

@Column(name = "Asignatura")
    private String asignatura;

@Column(name = "Curso")
    private int curso;


    public EntidadMatricula(int idProfesorado, int idAlumnado, String asignatura, int  curso) {
        setIdProfesorado(idProfesorado);
        setIdAlumnado(idAlumnado);
        setAsignatura(asignatura);
        setCurso(curso);
    }

    public EntidadMatricula() {

    }

    public int getIdMatricula() {
        return id;
    }

    public void setIdMatricula(int id) {
        this.id = id;
    }

    public int getIdProfesorado() {
        return idProfesorado;
    }

    public void setIdProfesorado(int idProfesorado) {
        this.idProfesorado = idProfesorado;
    }

    public int getIdAlumnado() {
        return idAlumnado;
    }

    public void setIdAlumnado(int idAlumnado) {
        this.idAlumnado = idAlumnado;
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
    public String toString() 
    {
    	return "ID=>"+this.id+"| IdProfesorado=>"+this.idProfesorado+"| IdAlumnado=>"+this.idAlumnado+ "| Asignatura=>"+this.asignatura+ "| Curso=>"+this.curso;
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EntidadAlumno alumno;
}