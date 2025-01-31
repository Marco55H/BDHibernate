package principal;

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
		System.out.println("Intoduzca el apellido del alumno");
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
		nombre = sc.nextLine();
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
		} while (eleccion < 1 || eleccion > 3);

		return eleccion;
	}

}