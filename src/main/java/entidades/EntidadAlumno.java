package entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "Alumno")
public class EntidadAlumno implements Serializable {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY) //La opción más usada con MySQL
@Column(name="id")
private int id;

@Column(name = "nombre")
    private String nombre;

@Column(name = "apellido")
    private String apellido;

@Column(name = "FechaNacimiento")
	private String FechaNacimiento;

    public EntidadAlumno(String nombre, String apellido, String  FechaNacimiento) {
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(FechaNacimiento);
    }

    public EntidadAlumno() {

    }

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
        return apellido;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
    
    @Override
    public String toString() 
    {
    	return "ID=>"+this.id+"| Nombre=>"+this.nombre+"| Apellido=>"+this.apellido+ "| Fecha nacimiento=>"+this.FechaNacimiento;
    }
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EntidadMatricula matricula;
}
