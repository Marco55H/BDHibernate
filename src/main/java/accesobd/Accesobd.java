package accesobd;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.exception.ConstraintViolationException;

import entidades.EntidadAlumno;
import entidades.EntidadMatricula;
import entidades.EntidadProfesor;

public class Accesobd {
    private SessionFactory sf;
    private Session sesion;
    private Transaction transaction;

   
    public Accesobd() {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            
            sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error al inicializar SessionFactory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo inicializar Hibernate.");
        }
    }

    public void abrir() throws Exception {
        if (sf == null) {
            throw new IllegalStateException("SessionFactory no está inicializada.");
        }

        sesion = sf.openSession();
        transaction = sesion.beginTransaction(); 
    }

    public void cerrar() {
        try {
            if (transaction != null && !transaction.getRollbackOnly()) {
                transaction.commit(); 
            }
        } catch (Exception e) {
        } finally {
            if (sesion != null) {
                sesion.close(); 
            }
        }
    }

    public void borrar(Class<?> clazz, int id) throws Exception {
    Transaction tx = null;

    try {
        // Verificar que la sesión esté abierta
        if (sesion == null || !sesion.isOpen()) {
            abrir();
        }

        // Obtener el objeto a eliminar
        Object objeto = sesion.get(clazz, id);
        if (objeto != null) {
            System.out.println("Eliminando objeto con ID: " + id);

            // Comprobar si ya hay una transacción activa
            if (transaction == null || !transaction.isActive()) {
                // Si no hay transacción activa, crear una nueva
                tx = sesion.beginTransaction();
            } else {
                // Si ya hay una transacción activa, usar la existente
                tx = transaction;
            }

            // Eliminar las dependencias en Matricula (si las hay)
            String hqlDeleteMatriculas = "DELETE FROM EntidadMatricula WHERE idProfesorado = :idProfesor";
            sesion.createQuery(hqlDeleteMatriculas)
                  .setParameter("idProfesor", id)
                  .executeUpdate();

            // Ahora eliminar el profesor
            sesion.delete(objeto);
            tx.commit(); // Confirmar los cambios si la eliminación es exitosa
            System.out.println("Objeto eliminado exitosamente.");
        } else {
            System.out.println("No se encontró el objeto con ID: " + id);
        }
    } catch (org.hibernate.exception.ConstraintViolationException e) {
        // Manejo de violación de restricciones
        System.err.println("Error: No se puede eliminar el objeto con ID " + id +
                " debido a una restricción de clave foránea.");
        
        // Si ocurre un error con la transacción, hacer rollback
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }
    } catch (Exception e) {
        // Error general al intentar eliminar el objeto
        System.err.println("Error al intentar eliminar el objeto: " + e.getMessage());

        // Si hay una transacción activa, hacer rollback
        if (tx != null && tx.isActive()) {
            tx.rollback();
        }

        // Lanza una excepción con un mensaje específico
        throw new Exception("Error al intentar eliminar el objeto con ID " + id + ": " + e.getMessage());
    } finally {
        // Cerrar la sesión
        cerrar();
    }
}

    public List<EntidadAlumno> leerAlumnos(String campo, Object valor) throws Exception {
    List<EntidadAlumno> alumnos = new ArrayList<>();
    
    try {
        // Verificar si la tabla "Alumno" existe
        if (sesion == null || !sesion.isOpen()) {
            abrir();
        }

        // Verificamos si la tabla existe con el nombre correcto
        if (!tablaExiste("Alumno")) { // Usamos "Alumno" en lugar de "EntidadAlumno"
            System.err.println("La tabla 'Alumno' no existe en la base de datos.");
            return alumnos;  // Retorna una lista vacía si no existe la tabla
        }

        String sql;
        Query<EntidadAlumno> query;

        // Si el campo es "id", usamos "="; en caso contrario, usamos "LIKE"
        if ("id".equalsIgnoreCase(campo)) {
            sql = "FROM EntidadAlumno WHERE " + campo + " = :valor";
            query = sesion.createQuery(sql, EntidadAlumno.class);
            query.setParameter("valor", valor); // Búsqueda exacta por ID
        } else {
            sql = "FROM EntidadAlumno WHERE " + campo + " LIKE :valor";
            query = sesion.createQuery(sql, EntidadAlumno.class);
            query.setParameter("valor", "%" + valor + "%"); // Búsqueda parcial con LIKE
        }

        alumnos = query.getResultList();

    } catch (Exception e) {
        System.err.println("Error al intentar obtener los alumnos: " + e.getMessage());
        e.printStackTrace();
        throw e;
    } finally {
        cerrar();
    }

    return alumnos;
}

	public List<EntidadAlumno> leerTodosAlumnos() throws Exception {
    List<EntidadAlumno> alumnos = new ArrayList<>();
    
    try {
        if (sesion == null || !sesion.isOpen()) {
            abrir(); // Verifica que el método 'abrir' funcione correctamente
        }

        // Ahora la consulta solo trae los alumnos, sin relación con el profesor
        String hql = "SELECT a FROM EntidadAlumno a";  // Sin LEFT JOIN FETCH
        System.out.println("Consulta HQL para alumnos: " + hql);

        Query<EntidadAlumno> query = sesion.createQuery(hql, EntidadAlumno.class);
        
        // Ejecuta la consulta y obtiene la lista de alumnos
        alumnos = query.getResultList();

        // Verifica que la lista de alumnos no esté vacía
        System.out.println("Número de alumnos encontrados: " + alumnos.size());

    } catch (Exception e) {
        System.err.println("Error al intentar obtener los alumnos: " + e.getMessage());
        e.printStackTrace();
        throw e;
    } finally {
        if (sesion != null && sesion.isOpen()) {
            cerrar();
        }
    }
    
    return alumnos;
}

	public List<EntidadProfesor> leerProfesores(String campo, Object valor) throws Exception {
    List<EntidadProfesor> profesores = new ArrayList<>();
    
    try {
        // Verificar si la tabla "EntidadProfesor" existe
        if (sesion == null || !sesion.isOpen()) {
            abrir();
        }

        if (!tablaExiste("Profesor")) {
            System.err.println("La tabla 'EntidadProfesor' no existe en la base de datos.");
            return profesores;  // Retorna una lista vacía si no existe la tabla
        }

        String sql;
        Query<EntidadProfesor> query;

        // Si el campo es "id", usamos "="; en caso contrario, usamos "LIKE"
        if ("id".equalsIgnoreCase(campo)) {
            sql = "FROM EntidadProfesor WHERE " + campo + " = :valor";
            query = sesion.createQuery(sql, EntidadProfesor.class);
            query.setParameter("valor", valor); // Búsqueda exacta por ID
        } else {
            sql = "FROM EntidadProfesor WHERE " + campo + " LIKE :valor";
            query = sesion.createQuery(sql, EntidadProfesor.class);
            query.setParameter("valor", "%" + valor + "%"); // Búsqueda parcial con LIKE
        }

        profesores = query.getResultList();

    } catch (Exception e) {
        System.err.println("Error al intentar obtener los profesores: " + e.getMessage());
        e.printStackTrace();
        throw e;
    } finally {
        cerrar();
    }

    return profesores;
}
	
	public List<EntidadProfesor> leerTodosProfesores() throws Exception {
	    List<EntidadProfesor> profesores = new ArrayList<>();
	    
	    try {
	        // Verificar si la sesión está abierta y correctamente inicializada
	        if (sesion == null || !sesion.isOpen()) {
	            System.out.println("Sesión cerrada o nula, abriendo...");
	            abrir();
	        }
	
	        // Verificar si la tabla "Profesor" existe
	        if (!tablaExiste("Profesor")) {  // Cambia "Profesor" al nombre real de la tabla
	            System.err.println("La tabla 'Profesor' no existe en la base de datos.");
	            return profesores;  // Retorna una lista vacía si la tabla no existe
	        }
	
	        // Verificar la consulta HQL antes de ejecutarla
	        String hql = "SELECT p FROM EntidadProfesor p";
	        System.out.println("Consulta HQL: " + hql);
	
	        Query<EntidadProfesor> query = sesion.createQuery(hql, EntidadProfesor.class);
	        profesores = query.getResultList();
	        
	        // Verificar los resultados obtenidos
	        System.out.println("Número de profesores encontrados: " + profesores.size());
	        
	    } catch (Exception e) {
	        System.err.println("Error al intentar obtener los profesores: " + e.getMessage());
	        e.printStackTrace();
	        throw e;
	    } finally {
	        if (sesion != null && sesion.isOpen()) {
	            cerrar();
	        }
	    }
	
	    return profesores;
	}

    public List<EntidadMatricula> leerMatriculas(String campo, Object valor) throws Exception {
        List<EntidadMatricula> matriculas = new ArrayList<>();

        try {
            // Verificar si la tabla "EntidadMatricula" existe
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            if (!tablaExiste("Matricula")) {
                System.err.println("La tabla 'EntidadMatricula' no existe en la base de datos.");
                return matriculas;  // Retorna una lista vacía si no existe la tabla
            }

            String sql;
            Query<EntidadMatricula> query;

            // Si el campo es "id" o alguno de los campos relacionados con EntidadProfesor o EntidadAlumno, usamos "="
            if ("id".equalsIgnoreCase(campo) || "idProfesorado".equalsIgnoreCase(campo) || "idAlumnado".equalsIgnoreCase(campo)) {
                sql = "FROM EntidadMatricula WHERE " + campo + " = :valor";
                query = sesion.createQuery(sql, EntidadMatricula.class);
                query.setParameter("valor", valor); // No usamos LIKE para ID o campos de relación
            } else {
                sql = "FROM EntidadMatricula WHERE " + campo + " LIKE :valor";
                query = sesion.createQuery(sql, EntidadMatricula.class);
                query.setParameter("valor", "%" + valor + "%"); // Agrega comodines para búsqueda parcial
            }

            matriculas = query.getResultList();

        } catch (Exception e) {
            System.err.println("Error al intentar obtener las matrículas: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            cerrar();
        }

        return matriculas;
    }

    public List<EntidadMatricula> leerTodasMatriculas() throws Exception {
    List<EntidadMatricula> matriculas = new ArrayList<>();
    
    try {
        // Verificar si la sesión está abierta
        if (sesion == null || !sesion.isOpen()) {
            abrir();
        }

        // Verificar si la tabla "Matricula" existe
        if (!tablaExiste("Matricula")) {  // Cambia "Matricula" al nombre real de la tabla
            System.err.println("La tabla 'Matricula' no existe en la base de datos.");
            return matriculas;  // Retorna una lista vacía si la tabla no existe
        }

        // Si la tabla existe, realizamos la consulta
        String sql = "SELECT m FROM EntidadMatricula m";
        
        Query<EntidadMatricula> query = sesion.createQuery(sql, EntidadMatricula.class);
        matriculas = query.getResultList();

    } catch (Exception e) {
        System.err.println("Error al intentar obtener las matrículas: " + e.getMessage());
        e.printStackTrace();
        throw e; // Propaga la excepción si hay un error
    } finally {
        cerrar();  // Cierra la sesión
    }

    return matriculas;
}

    public void actualizarAlumno(int id, String nombre, String apellido, String fecha) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            
            EntidadAlumno alumno = sesion.get(EntidadAlumno.class, id);
            if (alumno != null) {
                
            	alumno.setNombre(nombre);
            	alumno.setApellido(apellido);
            	alumno.setFechaNacimiento(fecha);

               
                sesion.update(alumno);

                
                transaction.commit();
                System.out.println("Objeto actualizado exitosamente.\n");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id+"\n");
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e; 
        } finally {
        	cerrar();
        }
    }

    public void actualizarMatricula(int id, int idAlumnado, int idProfesorado, String asignatura, int curso) throws Exception {
        try {
        	
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }


            // Buscar la matrícula por el ID
            EntidadMatricula matricula = sesion.get(EntidadMatricula.class, id);
            if (matricula != null) {
                // Obtener las entidades completas de Profesor y Alumno por sus IDs
                EntidadProfesor profesor = sesion.get(EntidadProfesor.class, idProfesorado);
                EntidadAlumno alumno = sesion.get(EntidadAlumno.class, idAlumnado);

                // Verificar si el alumno existe
                if (alumno == null) {
                    System.out.println("El alumno con ID " + idAlumnado + " no existe.");
                    return; // No continuamos con la inserción
                }
                
                // Verificar si el profesor existe
                if (profesor == null) {
                    System.out.println("El alumno con ID " + idProfesorado + " no existe.");
                    return; // No continuamos con la inserción
                }
                
                // Si las entidades Profesor y Alumno existen, las asignamos a la matrícula
                if (profesor != null && alumno != null) {
                    matricula.setProfesorado(profesor); // Establecer el objeto Profesor
                    matricula.setAlumnado(alumno);      // Establecer el objeto Alumno
                    matricula.setAsignatura(asignatura); // Actualizar asignatura
                    matricula.setCurso(curso);           // Actualizar curso

                    // Actualizar la matrícula en la base de datos
                    sesion.update(matricula);

                    // Commit de la transacción
                    transaction.commit();
                    System.out.println("Objeto actualizado exitosamente.\n");
                } else {
                    System.out.println("No se encontraron las entidades Profesor o Alumno con los IDs proporcionados.\n");
                }
            } else {
                System.out.println("No se encontró el objeto con ID: " + id+"\n");
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e;
        } finally {
            // Cerrar la sesión
            cerrar();
        }
    }

    public void actualizarProfesor(int id, String nombre, String apellido, String fechaNacimiento, int antiguedad) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            
            EntidadProfesor profesor = sesion.get(EntidadProfesor.class, id);
            if (profesor != null) {
                
            	profesor.setNombre(nombre);
            	profesor.setApellido(apellido);
            	profesor.setFechaNacimiento(fechaNacimiento);
            	profesor.setAntiguedad(antiguedad);
               
                sesion.update(profesor);

                
                transaction.commit();
                System.out.println("Objeto actualizado exitosamente.\n");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id+"\n");
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e; 
        } finally {
        	cerrar();
        }
    }

    public void eliminarTabla(String nombreTabla) {
        if (sesion == null || !sesion.isOpen()) {
            throw new IllegalStateException("La sesión no está abierta.");
        }
        
        // Verificar si ya hay una transacción activa
        Transaction transaction = sesion.getTransaction();
        boolean isTransactionActive = transaction != null && transaction.isActive();
        
        try {
            if (!isTransactionActive) {
                transaction = sesion.beginTransaction(); // Inicia una nueva transacción solo si no hay una activa
            }

            String sql = "DROP TABLE IF EXISTS " + nombreTabla;
            sesion.createNativeQuery(sql).executeUpdate();
            
            System.out.println("Tabla " + nombreTabla + " eliminada exitosamente.");
            
        } catch (Exception e) {
            System.out.println("Error");
            if (e.getMessage().toLowerCase().contains("foreign key") || e.getMessage().toLowerCase().contains("constraint")) {
                System.err.println("Error: No se puede eliminar '" + nombreTabla + "' porque tiene referencias en otras tablas.");
            } else {
                System.err.println("Error al eliminar la tabla " + nombreTabla);
            }
        }
    }

    public EntidadAlumno obtenerAlumnoPorId(int id) throws Exception {
        EntidadAlumno alumno = null;

        try {
            // Abrir sesión si no está abierta
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            // Verificar si hay una transacción activa
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction(); // Comenzar una transacción si no está activa
            }

            // Obtener el objeto EntidadAlumno por su ID
            alumno = sesion.get(EntidadAlumno.class, id);

            // Confirmar la transacción si todo fue bien
            transaction.commit();
            
            if (alumno != null) {
                System.out.println("Alumno encontrado \n");
            } else {
                System.out.println("No se encontró un alumno con el ID: " + id+"\n");
            }
        } catch (Exception e) {
            // Manejo de excepciones
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Rollback en caso de error
            }
            System.err.println("Error al intentar obtener el alumno: " + e.getMessage());
            throw e;  // Propagar la excepción
        } finally {
            // Cerrar la sesión de forma segura
            cerrar();
        }

        return alumno;  // Devolver el alumno encontrado (o null si no fue encontrado)
    }

    public EntidadProfesor obtenerProfesorPorId(int id) throws Exception {
        EntidadProfesor profesor = null;

        try {
            // Abrir sesión si no está abierta
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            // Verificar si hay una transacción activa
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction(); // Comenzar una transacción si no está activa
            }

            // Obtener el objeto EntidadProfesor por su ID
            profesor = sesion.get(EntidadProfesor.class, id);

            // Confirmar la transacción si todo fue bien
            transaction.commit();
            
            if (profesor != null) {
                System.out.println("Profesor encontrado: \n");
            } else {
                System.out.println("No se encontró un profesor con el ID: " + id+"\n");
            }
        } catch (Exception e) {
            // Manejo de excepciones
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // Rollback en caso de error
            }
            System.err.println("Error al intentar obtener el profesor: " + e.getMessage());
            throw e;  // Propagar la excepción
        } finally {
            // Cerrar la sesión de forma segura
            cerrar();
        }

        return profesor;  // Devolver el profesor encontrado (o null si no fue encontrado)
    }
 
 // Método que verifica si una tabla existe
    public boolean tablaExiste(String nombreTabla) {
    try {
        String sql = "SELECT COUNT(*) FROM " + nombreTabla;  // Consulta para verificar la existencia de la tabla
        Query query = sesion.createSQLQuery(sql);
        query.getResultList(); // Si la tabla existe, no se lanzará ninguna excepción
        return true;
    } catch (Exception e) {
        System.err.println("La tabla " + nombreTabla + " no existe.");
        return false;
    }
}

    public Object guardar(Object cosa) {
        if (sesion == null) {
            throw new IllegalStateException("La sesión no está abierta.");
        }

        try {
            // Verificar si ya hay una transacción activa
            if (!sesion.getTransaction().isActive()) {
                sesion.beginTransaction();  // Iniciar transacción solo si no está activa
            }

            // Intentamos guardar el objeto
            Object id = sesion.save(cosa);  // Guardamos el objeto y obtenemos su ID generado (si corresponde)

            // Si llegamos aquí, significa que se ha guardado el objeto con éxito
            System.out.println("Objeto guardado exitosamente. Detalles del objeto: \n" + cosa.toString());

            // Confirmar transacción
            sesion.getTransaction().commit();

            return id;  // Devolvemos el ID generado del objeto (si corresponde)
        } catch (ConstraintViolationException e) {
            // Capturamos la excepción de violación de restricción (clave foránea)
            if (sesion.getTransaction().isActive()) {
                sesion.getTransaction().rollback();  // Deshacer los cambios en caso de error
            }
            sesion.clear();  // Limpiar la sesión para evitar problemas futuros
            System.err.println("Error: Violación de restricción de clave foránea.");
            e.printStackTrace();  // Detalles del error
            return null;  // Devolvemos null porque no se pudo guardar debido a la clave foránea inválida
        } catch (Exception e) {
            // Capturamos cualquier otro tipo de excepción
            if (sesion.getTransaction().isActive()) {
                sesion.getTransaction().rollback();  // Deshacer los cambios en caso de error
            }
            sesion.clear();  // Limpiar la sesión
            System.err.println("Error al guardar el objeto: " + e.getMessage());
            e.printStackTrace();  // Detalles del error
            return null;  // Devolvemos null indicando que la operación falló
        }
    }
}