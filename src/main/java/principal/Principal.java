package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import accesobd.Accesobd;
import entidades.EntidadAlumno;
import entidades.EntidadMatricula;
import entidades.EntidadProfesor;

public class Principal {

	private static Accesobd instancia;
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			int opc;

			instancia = new Accesobd();

			System.out.println("Bienvenido a la gestión de personas");
			System.out.println("Empiece eligiendo una de las siguientes opciones");
			do {
				System.out.println("1- Insertar Elemento");
				System.out.println("2- Mostrar Elemento");
				System.out.println("3- Editar Elemento");
				System.out.println("4- Borrar Elemento");
				System.out.println("5- Borrar Tabla");
				System.out.println("0- Salir");
				opc = sc.nextInt();
				sc.nextLine();

				switch (opc) {
				case 1:
					opcion1();
					break;
				case 2:
					opcion2();
					break;
				case 3:
					opcion3();
					break;
				case 4:
					opcion4();
					break;
				case 5:
					opcion5();
					break;
				}
			} while (opc != 0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (instancia != null) {
				instancia.cerrar();
			}
		}
		sc.close();
	}
	
	private static void opcion5() throws ClassNotFoundException, SQLException, Exception {
		
		System.out.println("Menú de Borrar Tablas");

		int tabla = elegirTabla();
		
		switch (tabla) {
		case 1:
			borrarTablaAlumno();
			break;
		case 2:
			borrarTablaProfesor();
			break;
		case 3:
			borrarTablaMatricula();
			break;
		}
	
	}

	private static void borrarTablaMatricula() {
		// TODO Auto-generated method stub
		
	}

	private static void borrarTablaProfesor() {
		// TODO Auto-generated method stub
		
	}

	private static void borrarTablaAlumno() {
		// TODO Auto-generated method stub
		
	}

	private static void opcion4() throws ClassNotFoundException, SQLException, Exception {
	
		System.out.println("Menú de Borrar");

		int tabla = elegirTabla();
		
		switch (tabla) {
		case 1:
			borrarAlumno();
			break;
		case 2:
			borrarProfesor();
			break;
		case 3:
			borrarMatricula();
			break;
		}
	
	}
	
	private static void borrarAlumno() throws Exception {
		
		String asegurar = "";
		int id;
		
		System.out.println("Borrar Matricula:");
		System.out.println("indique el id del Alumno que quiere borrar");
		id=sc.nextInt();
		sc.nextLine();
		
		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();

		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.borrar(EntidadAlumno.class, id);
			instancia.cerrar();
		}
		
	}
	private static void borrarProfesor() throws Exception {
		
		String asegurar = "";
		int id;
		
		System.out.println("Borrar Profesor:");
		System.out.println("indique el id del profesor que quiere borrar");
		id=sc.nextInt();
		sc.nextLine();

		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();

		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.borrar(EntidadProfesor.class, id);
			instancia.cerrar();
		}
		
	}
	private static void borrarMatricula() throws Exception {
		
		String asegurar = "";
		int id;
		
		System.out.println("Borrar Matricula:");
		System.out.println("indique el id de la matricula que quiere borrar");
		id=sc.nextInt();
		sc.nextLine();

		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();
		
		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.borrar(EntidadMatricula.class, id);
			instancia.cerrar();
		}
		
	}
	
 	private static void opcion3() throws ClassNotFoundException, SQLException, Exception {
		System.out.println("Menú de editar");

		int tabla = elegirTabla();
		
		switch (tabla) {
		case 1:
			editarAlumno();
			break;
		case 2:
			editarProfesor();
			break;
		case 3:
			editarMatricula();
			break;
		}
	}
	
	private static void editarProfesor() throws ClassNotFoundException, SQLException, Exception {
		int idProfesor;
		String modificar="";
		String nombre ;
		String apellido;
		String FechaNacimiento;
		int Antiguedad;
	    
		List<EntidadProfesor> profesores;
		EntidadProfesor profesor = null;
	    
		System.out.println("Inserte el ID del Profesor que quiere modificar");
		idProfesor = sc.nextInt();
		sc.nextLine();
		
		instancia.abrir();
		profesores = instancia.leerProfesores("id", idProfesor);
		instancia.cerrar();
		
		for (EntidadProfesor m : profesores) {
		    if (m.getIdProfesor() == idProfesor) {
		    	profesor = m;
		        break;
		    }
		}
		
		//Nombre
		System.out.println("¿Quiere modificar el nombre?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {
	    	
	    	System.out.println("Cuál es el nuevo nombre del Profesor "+idProfesor);
	    	nombre=sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			nombre=profesor.getNombre();
		}
		
		//Apellido
		System.out.println("¿Quiere modificar el apellido?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo apellido del Profesor "+idProfesor);
	    	apellido=sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			apellido=profesor.getNombre ();
		}
		
		//fechanacimiento
		System.out.println("¿Quiere modificar la fecha de nacimiento?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es la nueva fecha de nacimiento del Profesor "+idProfesor+"(Este formato) 1974-12-11");
	    	FechaNacimiento = sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			FechaNacimiento=profesor.getFechaNacimiento();
		}
		
		
		instancia.abrir();
		instancia.actualizarAlumno(idProfesor, nombre, apellido, FechaNacimiento);
		instancia.cerrar();
		
	}
	
	private static void editarAlumno() throws ClassNotFoundException, SQLException, Exception {
		int idAlumno;
		String modificar="";
		String nombre ;
		String apellido;
		String FechaNacimiento;
	    
		List<EntidadAlumno> alumnos;
		EntidadAlumno alumno = null;
	    
		System.out.println("Inserte el ID del Alumno que quiere modificar");
		idAlumno = sc.nextInt();
		sc.nextLine();
		
		instancia.abrir();
		alumnos = instancia.leerAlumnos("id", idAlumno);
		instancia.cerrar();
		
		for (EntidadAlumno m : alumnos) {
		    if (m.getIdAlumno() == idAlumno) {
		    	alumno = m;
		        break;
		    }
		}
		
		//Nombre
		System.out.println("¿Quiere modificar el nombre?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {
	    	
	    	System.out.println("Cuál es el nuevo nombre del Alumno "+idAlumno);
	    	nombre=sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			nombre=alumno.getNombre();
		}
		
		//Apellido
		System.out.println("¿Quiere modificar el apellido?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo apellido del Alumno "+idAlumno);
	    	apellido=sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			apellido=alumno.getNombre ();
		}
		
		//fechanacimiento
		System.out.println("¿Quiere modificar la fecha de nacimiento?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es la nueva fecha de nacimiento del Alumno "+idAlumno+"(Este formato) 1974-12-11");
	    	FechaNacimiento = sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			FechaNacimiento=alumno.getFechaNacimiento();
		}
		
		
		instancia.abrir();
		instancia.actualizarAlumno(idAlumno, nombre, apellido, FechaNacimiento);
		instancia.cerrar();
		
	}

	private static void editarMatricula() throws ClassNotFoundException, SQLException, Exception {
		int idMatricula;
		String modificar="";
	    int idProfesorado ;
	    int idAlumnado;
	    int curso;
	    String Asignatura="";
	    
		List<EntidadMatricula> matriculas;
		EntidadMatricula matricula = null;
	    
		System.out.println("Inserte el ID de la Matricula que quiere modificar");
		idMatricula = sc.nextInt();
		sc.nextLine();
		
		instancia.abrir();
		matriculas = instancia.leerMatriculas("id", idMatricula);
		instancia.cerrar();
		
		for (EntidadMatricula m : matriculas) {
		    if (m.getIdMatricula() == idMatricula) {
		    	matricula = m;
		        break;
		    }
		}
		
		//Nombre
		System.out.println("¿Quiere modificar el idProfesorado?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {
	    	
	    	System.out.println("Cuál es el nuevo idProfesorado de la Matricula "+idMatricula);
	    	idProfesorado=sc.nextInt();
	    	sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			idProfesorado=matricula.getIdProfesorado();
		}
		
		//Apellido
		System.out.println("¿Quiere modificar el idAlumnado?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo idAlumnado de la Matricula "+idMatricula);
	    	idAlumnado=sc.nextInt();
	    	sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			idAlumnado=matricula.getIdAlumnado();
		}
		
		//fechanacimiento
		System.out.println("¿Quiere modificar el curso?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo curso de la Matricula "+idMatricula);
	    	curso=sc.nextInt();
	    	sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			curso=matricula.getCurso();
		}
		
		
		//antiguedad
		System.out.println("¿Quiere modificar la Asignatura?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es  la nueva Asignatura de la Matricula "+idMatricula);
	    	Asignatura=sc.nextLine();
	    	
			System.out.println("Editado correctamente");
	    	
		}else {
			Asignatura=matricula.getAsignatura();
		}
		
		instancia.abrir();
		instancia.actualizarMatricula(idMatricula, idProfesorado, idAlumnado, Asignatura, curso);
		instancia.cerrar();
		
	}

	private static void opcion2() throws Exception {
		System.out.println("Menú de ver datos");
		int tabla = elegirTabla();

		List<EntidadMatricula> matriculas;
		List<EntidadProfesor> profesores;
		List<EntidadAlumno> alumnos;
		
		switch (tabla) {
		case 1:
			
			alumnos=filtraBusquedaAlumno();
			
            if (!alumnos.isEmpty()) {
            	alumnos.forEach(System.out::println);
            } else {
                System.out.println("No se encontraron los alumnos");
            }
			
			break;
		case 2:
			
			profesores=filtraBusquedaProfesor();
			
            if (!profesores.isEmpty()) {
                profesores.forEach(System.out::println);
            } else {
                System.out.println("No se encontraron los profesores");
            }
			
			break;
		case 3:
			
			matriculas=filtraBusquedaMatricula();
			
            if (!matriculas.isEmpty()) {
            	matriculas.forEach(System.out::println);
            } else {
                System.out.println("No se encontraron las matriculas");
            }
			
			break;
		}
	}
	
	private static List<EntidadMatricula> filtraBusquedaMatricula() throws Exception {
		
		int opc;
		String campo="";
		Object valor=null;
		List<EntidadMatricula> matriculas;
		
		//Debo cambiar la entidad poner en minuscula para hacer lowercase aqui
		System.out.println("Por que campo quieres buscar \n 1-id \n 2-idProfesorado \n 3-idAlumno \n 4-Asignatura \n 5-Curso");
		opc=sc.nextInt();
		sc.nextLine();
		

		switch(opc){
			case 1:
				campo="id";
			valor = pasarInt(campo); 
			instancia.abrir();
			matriculas = instancia.leerMatriculas(campo, valor);
			instancia.cerrar();
				break;
			case 3:	 
				campo="idAlumnado";
			valor = pasarInt(campo); 
			instancia.abrir();
			matriculas = instancia.leerMatriculas(campo, valor);
			instancia.cerrar();
				break;
			case 2:
				campo="idProfesorado";
			valor = pasarInt(campo); 
			instancia.abrir();
			matriculas = instancia.leerMatriculas(campo, valor);
			instancia.cerrar();
				break;
			case 5:
				campo="Curso";
			valor = pasarInt(campo); 
			instancia.abrir();
			matriculas = instancia.leerMatriculas(campo, valor);
			instancia.cerrar();
				break;
			case 4:		
				campo="Asignatura";
				System.out.println("Que valor tiene el campo "+campo);
				valor=sc.nextLine();
				instancia.abrir();
				matriculas = instancia.leerMatriculas(campo, valor);
				instancia.cerrar();
				break;
			default:				
				System.out.println("Campo no valido");	
				matriculas=null;
		}
		

		
		return matriculas;
	}

	private static Object pasarInt(String campo) {
		Object valor;
		System.out.println("Que valor tiene el campo "+campo);
		valor=sc.nextLine();
		valor = Integer.parseInt((String) valor);
		return valor;
	}

	private static List<EntidadProfesor> filtraBusquedaProfesor() throws Exception {
		int opc;
		String campo="";
		Object valor=null;
		List<EntidadProfesor> profesores=null;

		//Debo cambiar la entidad poner en minuscula para hacer lowercase aqui
		System.out.println("Por que campo quieres buscar \n 1-id \n 2-nombre \n 3-apellido \n 4-FechaNacimiento \n 5-antiguedad");
		opc=sc.nextInt();
		sc.nextLine();
		
		System.out.println("Que valor tiene el campo "+campo);

		switch(opc){
			case 1:
				System.out.println("Que valor tiene el campo "+campo);
				campo="id";
	            valor = pasarInt(campo); 
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			case 5:
				System.out.println("Que valor tiene el campo "+campo);
				campo="antiguedad";
		            valor = pasarInt(campo); 
					instancia.abrir();
					profesores = instancia.leerProfesores(campo, valor);
					instancia.cerrar();
				break;
			case 4:
				campo="FechaNacimiento";
				 try {
		                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		                valor = sdf.parse((String) valor);
		            } catch (Exception e) {
		                System.err.println("Error al convertir la fecha. Asegúrate de usar el formato dd/MM/yyyy");
		            }
					instancia.abrir();
					profesores = instancia.leerProfesores(campo, valor);
					instancia.cerrar();
				break;
			case 2:	
				campo="nombre";
				System.out.println("Que valor tiene el campo "+campo);
				valor=sc.nextLine();
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			case 3:	
				campo="apellido";
				System.out.println("Que valor tiene el campo "+campo);
				valor=sc.nextLine();
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			default:
				System.out.println("Campo no valido");									
		}
		
		
		return profesores;
		
	}

	private static List<EntidadAlumno> filtraBusquedaAlumno() throws Exception {
		int opc;
		String campo;
		Object valor=null;
		List<EntidadAlumno> alumnos=null;
		
		//Debo cambiar la entidad poner en minuscula para hacer lowercase aqui
		System.out.println("Por que campo quieres buscar \n 1-id \n 2-nombre \n 3-apellido \n 4-FechaNacimiento");
		opc=sc.nextInt();
		sc.nextLine();
		

		switch(opc){
		case 1:
			campo="id";
			System.out.println("Que valor tiene el campo "+campo);
            valor = pasarInt(campo); 
    		instancia.abrir();
    		alumnos = instancia.leerAlumnos(campo, valor);
    		instancia.cerrar();
		break;
		
		case 4:
			campo = "FechaNacimiento";
			System.out.println("Que valor tiene el campo "+campo);
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    	valor = sdf.parse((String) valor);
		    } catch (Exception e) {
		        System.err.println("Error al convertir la fecha. Asegúrate de usar el formato dd/MM/yyyy");
		    }
			
			instancia.abrir();
			alumnos = instancia.leerAlumnos(campo, valor);
			instancia.cerrar();
			
		            
		break;
		
		case 2:	
			campo="nombre";
			System.out.println("Que valor tiene el campo "+campo);
			valor=sc.nextLine();
			instancia.abrir();
			alumnos = instancia.leerAlumnos(campo, valor);
			instancia.cerrar();
		break;
		
		case 3:	
			campo="apellido";
			System.out.println("Que valor tiene el campo "+campo);
			valor=sc.nextLine();
			instancia.abrir();
			alumnos = instancia.leerAlumnos(campo, valor);
			instancia.cerrar();
		break;
		
		default:
			System.out.println("Campo no valido");									
		}

		return alumnos;
		
	}
	
	private static void opcion1() throws Exception {


		int tabla = elegirTabla();

		switch (tabla) {
		case 1:
			
			EntidadAlumno alumno = crearAlumno();
			
				instancia.abrir();
				instancia.guardar(alumno);
				instancia.cerrar();
			
			break;
			
		case 2:
			
			EntidadProfesor profesor = crearProfesor();
			
				instancia.abrir();
				instancia.guardar(profesor);
				instancia.cerrar();
			
			break;
			
		case 3:

			EntidadMatricula matricula = crearMatricula();
			
				instancia.abrir();
				instancia.guardar(matricula);
				instancia.cerrar();
			
			break;
		}

	}
	
	private static EntidadMatricula crearMatricula() {
		int idAlumno;
		int idProfesor;
		String asignatura;
		int curso;

		System.out.println("Intoduzca el id del Alumno correspondiente");
		idAlumno = sc.nextInt();
		sc.nextLine();
		System.out.println("Intoduzca el id del Profesor correspondiente");
		idProfesor = sc.nextInt();
		sc.nextLine();
		System.out.println("Intoduzca la asignatura de la matricula");
		asignatura = sc.nextLine();
		System.out.println("Introduce el curso de la matricula");
		curso = sc.nextInt();
		sc.nextLine();

		EntidadMatricula matricula = new EntidadMatricula(idAlumno, idProfesor, asignatura, curso);

		return matricula;
	}
	
	private static EntidadProfesor crearProfesor() {
		String nombre;
		String apellido;
		String fechaNacimiento;
		int antiguedad;

		System.out.println("Intoduzca el nombre del profesor");
		nombre = sc.nextLine();
		System.out.println("Intoduzca el apellido del profesor");
		apellido = sc.nextLine();
		System.out.println("Intoduzca la fecha de nacimiento del profesor (dd/mm/AAAA)");
		fechaNacimiento = sc.nextLine();
		System.out.println("Introduce la antiguedad del profesor");
		antiguedad = sc.nextInt();
		sc.nextLine();

		EntidadProfesor profesor = new EntidadProfesor(nombre, apellido, fechaNacimiento, antiguedad);

		return profesor;
	}
	
	private static EntidadAlumno crearAlumno() {
		String nombre;
		String apellido;
		String fechaNacimiento;

		System.out.println("Intoduzca el nombre del alumno");
		nombre=sc.nextLine();
		System.out.println("Intoduzca el apellido del alumno");
		apellido = sc.nextLine();
		System.out.println("Intoduzca la fecha de nacimiento del alumno (dd/mm/AAAA)");
		fechaNacimiento = sc.nextLine();

		EntidadAlumno alumno = new EntidadAlumno(nombre, apellido, fechaNacimiento);

		return alumno;
	}

	private static int elegirTabla() {
		int eleccion;

		System.out.println("¿Sobre que tabla quiere trabajar?");

		do {
			System.out.println("1- Alumno");
			System.out.println("2- Profesor");
			System.out.println("3- Matricula");
			eleccion = sc.nextInt();
			sc.nextLine();
		} while (eleccion < 1 || eleccion > 3);

		return eleccion;
	}

}