package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

			System.out.println("=========================================");
			System.out.println("      BIENVENIDO A LA GESTIÓN DE PERSONAS");
			System.out.println("=========================================\n");
			do {
			    System.out.println("Seleccione una opción:");
			    System.out.println("  [1] Insertar Elemento");
			    System.out.println("  [2] Mostrar Elemento");
			    System.out.println("  [3] Editar Elemento");
			    System.out.println("  [4] Borrar Elemento");
			    System.out.println("  [5] Borrar Tabla");
			    System.out.println("  [0] Salir");
			    System.out.println("-----------------------------------------");
			    System.out.print("Ingrese su opción: ");
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
			
			System.out.println("\nSaliendo del sistema... ¡Hasta pronto! 👋");
			
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

	private static void borrarTablaMatricula() throws Exception {
		String asegurar = "";
		
		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();

		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.eliminarTabla("Matricula");
			instancia.cerrar();
		}
		}

	private static void borrarTablaProfesor() throws Exception {
		String asegurar = "";

		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();

		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.eliminarTabla("Profesor");
			instancia.cerrar();
		}
	}

	private static void borrarTablaAlumno() throws Exception {
		String asegurar = "";

		System.out.println("¿Seguro que quiere borrar? (si, no)");
		asegurar=sc.nextLine();

		if(asegurar.toLowerCase().equals("si")) {
			instancia.abrir();
			instancia.eliminarTabla("Alumno");
			instancia.cerrar();
		}
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
	        default:
	            System.out.println("Opción no válida. Por favor, elija una opción correcta.");
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
		String fechaNacimiento;
		int antiguedad;
		boolean fechaValida=false;
	    
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
		
	    if (profesor == null) {
	        System.out.println("No se encontró la matrícula con ID: " + idProfesor);
	        return;
	    }
	    
		//Nombre
		System.out.println("¿Quiere modificar el nombre?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {
	    	
	    	System.out.println("Cuál es el nuevo nombre del Profesor "+idProfesor);
	    	nombre=sc.nextLine();
	    		    	
		}else {
			nombre=profesor.getNombre();
		}
		
		//Apellido
		System.out.println("¿Quiere modificar el apellido?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo apellido del Profesor "+idProfesor);
	    	apellido=sc.nextLine();
	    		    	
		}else {
			apellido=profesor.getNombre ();
		}
		
		//fechanacimiento
		System.out.println("¿Quiere modificar la fecha de nacimiento?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.equalsIgnoreCase("Si")) {

	    	
			System.out.println("Introduce la fecha de nacimiento del profesor (dd/MM/yyyy):");
		    fechaNacimiento = sc.nextLine();
		    
		    while (!fechaValida) {
		        if (validarFecha(fechaNacimiento)) {
		            fechaValida = true;
		        } else {
		            System.out.println("Formato de fecha incorrecto. Usa el formato dd/MM/yyyy.");
		            fechaNacimiento = sc.nextLine();
		        }
		    }
	    		    	
		}else {
			fechaNacimiento=profesor.getFechaNacimiento();
		}
		//Antiguedad
		System.out.println("¿Quiere modificar la antiguedad?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.equalsIgnoreCase("Si")) {

	    	
	    	System.out.println("Cuál es la nueva antiguedad del Profesor "+idProfesor);
	    	antiguedad = sc.nextInt();
	    	sc.nextLine();
	    		    	
		}else {
			antiguedad=profesor.getAntiguedad();
		}
		
		instancia.abrir();
		System.out.println("Inetntando Editar Profesor");
		instancia.actualizarProfesor(idProfesor, nombre, apellido, fechaNacimiento, antiguedad);
		instancia.cerrar();		
	}
	
	private static void editarAlumno() throws ClassNotFoundException, SQLException, Exception {
		int idAlumno;
		String modificar="";
		String nombre ;
		String apellido;
		String fechaNacimiento;
	    boolean fechaValida=false;
	    
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
		
	    if (alumno == null) {
	        System.out.println("No se encontró el alumno con ID: " + idAlumno);
	        return;
	    }
	    
		//Nombre
		System.out.println("¿Quiere modificar el nombre?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {
	    	
	    	System.out.println("Cuál es el nuevo nombre del Alumno "+idAlumno);
	    	nombre=sc.nextLine();
	    		    	
		}else {
			nombre=alumno.getNombre();
		}
		
		//Apellido
		System.out.println("¿Quiere modificar el apellido?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.toLowerCase().equals("Si".toLowerCase())) {

	    	
	    	System.out.println("Cuál es el nuevo apellido del Alumno "+idAlumno);
	    	apellido=sc.nextLine();
	    		    	
		}else {
			apellido=alumno.getNombre ();
		}
		
		//fechanacimiento
		System.out.println("¿Quiere modificar la fecha de nacimiento?(Si, No)");
		modificar=sc.nextLine();

		if(modificar.equalsIgnoreCase("Si")) {

	    	
			System.out.println("Introduce la fecha de nacimiento del alumno (dd/MM/yyyy):");
		    fechaNacimiento = sc.nextLine();
		    
		    while (!fechaValida) {
		        if (validarFecha(fechaNacimiento)) {
		            fechaValida = true;
		        } else {
		            System.out.println("Formato de fecha incorrecto. Usa el formato dd/MM/yyyy.");
		            fechaNacimiento = sc.nextLine();
		        }
		    }
	    		    	
		}else {
			fechaNacimiento=alumno.getFechaNacimiento();
		}
		
		
		instancia.abrir();
		System.out.println("Intentando Editar Alumno");
		instancia.actualizarAlumno(idAlumno, nombre, apellido, fechaNacimiento);
		instancia.cerrar();
		
	}

	private static void editarMatricula() throws ClassNotFoundException, SQLException, Exception {
	    int idMatricula;
	    String modificar = "";
	    int nuevoIdProfesorado = 0;
	    int nuevoIdAlumnado = 0;
	    int curso = 0;
	    String Asignatura = "";

	    List<EntidadMatricula> matriculas;
	    EntidadMatricula matricula = null;

	    System.out.println("\nInserte el ID de la Matricula que quiere modificar");
	    idMatricula = sc.nextInt();
	    sc.nextLine();  // Limpiar el buffer después de leer un número.

	    // Abrir conexión y leer la matrícula
	    instancia.abrir();
	    matriculas = instancia.leerMatriculas("id", idMatricula);
	    instancia.cerrar();

	    // Buscar la matrícula correspondiente.
	    for (EntidadMatricula m : matriculas) {
	        if (m.getIdMatricula() == idMatricula) {
	            matricula = m;
	            break;
	        }
	    }

	    if (matricula == null) {
	        System.out.println("No se encontró la matrícula con ID: " + idMatricula);
	        return;
	    }

	    // Cargar explícitamente la relación con el alumno si es necesario
	    if (matricula.getAlumnado() == null) {
	        System.out.println("No se pudo cargar el alumno asociado a la matrícula.");
	        return;
	    }

	    // Obtener el ID del alumno y del profesor actual.
	    nuevoIdAlumnado = matricula.getAlumnado().getIdAlumno();
	    nuevoIdProfesorado = matricula.getProfesorado().getIdProfesor();

	    // Modificar el idProfesorado si se desea.
	    System.out.println("¿Quiere modificar el idProfesorado? (Si/No)");
	    modificar = sc.nextLine();

	    if (modificar.equalsIgnoreCase("Si")) {
	        System.out.println("Cuál es el nuevo idProfesorado de la Matrícula " + matricula.getIdMatricula());

	        if (sc.hasNextInt()) {
	            nuevoIdProfesorado = sc.nextInt();
	            sc.nextLine();  // Limpiar el buffer

	            // Aquí deberías obtener el profesor desde la base de datos (si usas JPA).
	            EntidadProfesor nuevoProfesor = new EntidadProfesor();
	            nuevoProfesor.setIdProfesor(nuevoIdProfesorado);

	            matricula.setProfesorado(nuevoProfesor);  // Asigna el nuevo profesor.
	        } else {
	            System.out.println("Error: Ingrese un número válido.");
	            sc.nextLine(); // Limpiar el buffer en caso de error.
	        }
	    }

	    // Modificar el idAlumnado si se desea.
	    System.out.println("¿Quiere modificar el idAlumnado? (Si/No)");
	    modificar = sc.nextLine();

	    if (modificar.equalsIgnoreCase("Si")) {
	        System.out.println("Cuál es el nuevo idAlumnado de la Matrícula " + matricula.getIdMatricula());

	        if (sc.hasNextInt()) {
	            nuevoIdAlumnado = sc.nextInt();
	            sc.nextLine();  // Limpiar el buffer

	            // Aquí deberías obtener el alumno desde la base de datos.
	            EntidadAlumno nuevoAlumno = new EntidadAlumno();
	            nuevoAlumno.setIdAlumno(nuevoIdAlumnado);

	            matricula.setAlumnado(nuevoAlumno);  // Asigna el nuevo alumno.

	        } else {
	            System.out.println("Error: Ingrese un número válido.");
	            sc.nextLine(); // Limpiar el buffer en caso de error.
	        }
	    }

	    // Modificar el curso.
	    System.out.println("¿Quiere modificar el curso? (Si/No)");
	    modificar = sc.nextLine();

	    if (modificar.equalsIgnoreCase("Si")) {
	        System.out.println("Cuál es el nuevo curso de la Matrícula " + matricula.getIdMatricula());

	        if (sc.hasNextInt()) {
	            curso = sc.nextInt();
	            sc.nextLine();  // Limpiar el buffer

	        } else {
	            System.out.println("Error: Ingrese un número válido.");
	            sc.nextLine(); // Limpiar el buffer en caso de error.
	        }
	    } else {
	        curso = matricula.getCurso();
	    }

	    // Modificar la asignatura.
	    System.out.println("¿Quiere modificar la Asignatura? (Si/No)");
	    modificar = sc.nextLine();

	    if (modificar.equalsIgnoreCase("Si")) {
	        System.out.println("Cuál es la nueva Asignatura de la Matrícula " + matricula.getIdMatricula());

	        Asignatura = sc.nextLine();

	    } else {
	        Asignatura = matricula.getAsignatura();
	    }

	    // Realizar la actualización en la base de datos.
	    instancia.abrir();
	    System.out.println("Intentando actulizar matricula");
	    instancia.actualizarMatricula(idMatricula, nuevoIdProfesorado, nuevoIdAlumnado, Asignatura, curso);
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
		System.out.println("\nPor que campo quieres buscar \n 1-id \n 2-idProfesorado \n 3-idAlumno \n 4-Asignatura \n 5-Curso \n 6-Ver todas Las Matriculas");
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
			case 6:
				instancia.abrir();
				matriculas = instancia.leerTodasMatriculas();
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

	private static boolean validarFecha(String fecha) {
	    String regex = "^([0-2][0-9]|(3)[0-1])/(0[1-9]|1[0-2])/(\\d{4})$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(fecha);

	    return matcher.matches();
	}
	
	private static List<EntidadProfesor> filtraBusquedaProfesor() throws Exception {
		int opc;
		String campo="";
		Object valor=null;
		List<EntidadProfesor> profesores=null;

		//Debo cambiar la entidad poner en minuscula para hacer lowercase aqui
		System.out.println("\nPor que campo quieres buscar \n 1-id \n 2-nombre \n 3-apellido \n 4-FechaNacimiento \n 5-antiguedad \n 6-Todo los Profesores");
		opc=sc.nextInt();
		sc.nextLine();
		
		switch(opc){
			case 1:
				System.out.println("\nQue valor tiene el campo "+campo);
				campo="id";
	            valor = pasarInt(campo); 
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			case 5:
				System.out.println("\nQue valor tiene el campo "+campo);
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
				System.out.println("\nQue valor tiene el campo "+campo);
				valor=sc.nextLine();
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			case 3:	
				campo="apellido";
				System.out.println("\nQue valor tiene el campo "+campo);
				valor=sc.nextLine();
				instancia.abrir();
				profesores = instancia.leerProfesores(campo, valor);
				instancia.cerrar();
				break;
			case 6:
				instancia.abrir();
				profesores = instancia.leerTodosProfesores();
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
		System.out.println(" \n Por que campo quieres buscar (%LIKE%) \n 1-id \n 2-nombre \n 3-apellido \n 4-FechaNacimiento \n 5-Ver todos Los Alumnos");
		opc=sc.nextInt();
		sc.nextLine();
		

		switch(opc){
		case 1:
			campo="id";
			System.out.println("\nQue valor tiene el campo "+campo);
            valor = pasarInt(campo); 
    		instancia.abrir();
    		alumnos = instancia.leerAlumnos(campo, valor);
    		instancia.cerrar();
		break;
		
		case 4:
			campo = "FechaNacimiento";
			System.out.println("\nQue valor tiene el campo "+campo);
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
			System.out.println("\nQue valor tiene el campo "+campo);
			valor=sc.nextLine();
			instancia.abrir();
			alumnos = instancia.leerAlumnos(campo, valor);
			instancia.cerrar();
		break;
		
		case 3:	
			campo="apellido";
			System.out.println("\nQue valor tiene el campo "+campo);
			valor=sc.nextLine();
			instancia.abrir();
			alumnos = instancia.leerAlumnos(campo, valor);
			instancia.cerrar();
		break;
		case 5:
			instancia.abrir();
			alumnos = instancia.leerTodosAlumnos();
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
	
	private static EntidadMatricula crearMatricula() throws Exception {
	    int idAlumno;
	    int idProfesor;
	    String asignatura;
	    int curso;

	    System.out.println("Id del Alumno correspondiente");
	    idAlumno = sc.nextInt();
	    sc.nextLine();  // Limpiar el buffer
	    System.out.println("Id del Profesor correspondiente");
	    idProfesor = sc.nextInt();
	    sc.nextLine();  // Limpiar el buffer
	    System.out.println("Asignatura de la matrícula");
	    asignatura = sc.nextLine();
	    System.out.println("Curso de la matrícula");
	    curso = sc.nextInt();
	    sc.nextLine();  // Limpiar el buffer
	    
	    EntidadAlumno alumno = instancia.obtenerAlumnoPorId(idAlumno);  // Método que consulta la BD para obtener el alumno
	    if (alumno == null) {
	        System.out.println("No se encontró el alumno con id " + idAlumno);
	        return null;
	    }

	    EntidadProfesor profesor = instancia.obtenerProfesorPorId(idProfesor);  // Método que consulta la BD para obtener el profesor
	    if (profesor == null) {
	        System.out.println("No se encontró el profesor con id " + idProfesor);
	        return null;
	    }

	    // Ahora puedes crear la matrícula
	    EntidadMatricula matricula = new EntidadMatricula(profesor, alumno, asignatura, curso);

	    return matricula;
	}

	
	private static EntidadProfesor crearProfesor() {
		String nombre;
		String apellido;
		String fechaNacimiento="";
		int antiguedad;
		boolean fechaValida = false;
		
		System.out.println("Nombre del profesor : ");
		nombre = sc.nextLine();
		
		System.out.println("Apellido del profesor : ");
		apellido = sc.nextLine();
		
	    System.out.println("Fecha de nacimiento del profesor (dd/MM/yyyy) : ");
	    fechaNacimiento = sc.nextLine();
	    
	    while (!fechaValida) {
	        if (validarFecha(fechaNacimiento)) {
	            fechaValida = true;
	        } else {
	            System.out.println("Formato de fecha incorrecto. Usa el formato dd/MM/yyyy.");
	            fechaNacimiento = sc.nextLine();
	        }
	    }
	    
		System.out.println("Antiguedad del profesor");
		antiguedad = sc.nextInt();
		sc.nextLine();

		EntidadProfesor profesor = new EntidadProfesor(nombre, apellido, fechaNacimiento, antiguedad);

		return profesor;
	}
	
	private static EntidadAlumno crearAlumno() {
		String nombre;
		String apellido;
		String fechaNacimiento;
		boolean fechaValida=false;
		
		System.out.println("Nombre del alumno : ");
		nombre=sc.nextLine();
		System.out.println("Apellido del alumno : ");
		apellido = sc.nextLine();
	    System.out.println("Fecha de nacimiento del alumno (dd/MM/yyyy) : ");
	    fechaNacimiento = sc.nextLine();
	    
	    while (!fechaValida) {
	        if (validarFecha(fechaNacimiento)) {
	            fechaValida = true;
	        } else {
	            System.out.println("Formato de fecha incorrecto. Usa el formato dd/MM/yyyy.");
	            fechaNacimiento = sc.nextLine();
	        }
	    }
		EntidadAlumno alumno = new EntidadAlumno(nombre, apellido, fechaNacimiento);

		return alumno;
	}

	private static int elegirTabla() {
		int eleccion;

		System.out.println("=====================================================");
		System.out.println("      SELECCIÓN DE TABLA CON LA QUE TRABAJAR");
		System.out.println("=====================================================\n");

		do {
		    System.out.println("¿Sobre qué tabla desea trabajar?\n");
		    System.out.println("  [1] Alumno      👦");
		    System.out.println("  [2] Profesor    👨‍🏫");
		    System.out.println("  [3] Matrícula   🗃️");
		    System.out.println("  [0] Salir       🛫");
		    System.out.println("-----------------------------------------");
		    System.out.print("Ingrese su elección: ");
			eleccion = sc.nextInt();
			sc.nextLine();
			
			if(eleccion < 0 || eleccion > 3) {
				System.out.println("No es valida esa opcion ❌");
			}
			
		} while (eleccion < 0 || eleccion > 3);

		return eleccion;
	}

}