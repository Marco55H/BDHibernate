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

@Column(name = "FechaNacimiento")
	private String FechaNacimiento;

@Column(name = "antiguedad")
	private int antiguedad;


    public EntidadProfesor(String nombre, String apellido, String  FechaNacimiento, int antiguedad) {
        setNombre(nombre);
        setApellido(apellido);
        setFechaNacimiento(FechaNacimiento);
        setAntiguedad(antiguedad);
    }

    public EntidadProfesor() {

    }

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
    
    public String getFechaNacimiento() {
        return apellido;
    }

    public void setFechaNacimiento(String FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }
    
    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    
    @Override
    public String toString() 
    {
    	return "ID=>"+this.id+"| Nombre=>"+this.nombre+"| Apellido=>"+this.apellido+ "| Fecha nacimiento=>"+this.FechaNacimiento+ "| Antiguedad=>"+this.antiguedad;
    }
    
    
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EntidadMatricula matricula;
}
