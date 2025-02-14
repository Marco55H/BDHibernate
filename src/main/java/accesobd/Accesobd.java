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
            System.err.println("Error al confirmar la transacción: " + e.getMessage());
        } finally {
            if (sesion != null) {
                sesion.close(); 
            }
        }
    }
    
    public void borrar(Class<?> clazz, int id) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            Object objeto = sesion.get(clazz, id);
            if (objeto != null) {
                System.out.println("Eliminando objeto con ID: " + id);

                
                if (transaction == null || !transaction.isActive()) {
                    transaction = sesion.beginTransaction();
                }

                
                sesion.delete(objeto);

                
                transaction.commit();
                System.out.println("Objeto eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al intentar eliminar el objeto: " + e.getMessage());
            throw e; 
        } finally {
           
            cerrar();
        }
    }
    
    public List<EntidadAlumno> leerAlumnos(String campo, Object valor) throws Exception {
    	
    	List<EntidadAlumno> alumnos = new ArrayList<>();
    	
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            String sql = "FROM EntidadAlumno WHERE " + campo + " = :valor";
            
            Query<EntidadAlumno> query = sesion.createQuery(sql, EntidadAlumno.class);
            query.setParameter("valor", valor);

            alumnos = query.getResultList();;

        } catch (Exception e) {
            System.err.println("Error al intentar obtener los alumnos: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        } finally {
            
            cerrar();
        }
        
        return alumnos;

    }
    
    public List<EntidadProfesor> leerProfesores(String campo, Object valor) throws Exception {
    	
    	List<EntidadProfesor> profesores = new ArrayList<>();
    	
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            String sql = "FROM EntidadProfesor WHERE " + campo + " = :valor";
            
            Query<EntidadProfesor> query = sesion.createQuery(sql, EntidadProfesor.class);
            query.setParameter("valor", valor);

            profesores = query.getResultList();;
           
        } catch (Exception e) {
            System.err.println("Error al intentar obtener los profesores: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        } finally {
            
            cerrar();
        }
        
        return profesores;

    }
    
    public List<EntidadMatricula> leerMatriculas(String campo, Object valor) throws Exception {
    	
    	List<EntidadMatricula> matriculas = new ArrayList<>();
    	
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            String sql = "FROM EntidadMatricula WHERE " + campo + " = :valor";
            
            Query<EntidadMatricula> query = sesion.createQuery(sql, EntidadMatricula.class);
            query.setParameter("valor", valor);

            matriculas = query.getResultList();;
           
        } catch (Exception e) {
            System.err.println("Error al intentar obtener las matriculas: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        } finally {
            
            cerrar();
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
            	alumno.setFechaNacimiento(apellido);

               
                sesion.update(alumno);

                
                transaction.commit();
                System.out.println("Objeto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e; 
        } finally {
                        cerrar();
        }
    }


    public void actualizarMatricula(int id, int idProfesorado,int idAlumnado, String asignatura, int curso) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            
            EntidadMatricula matricula = sesion.get(EntidadMatricula.class, id);
            if (matricula != null) {
                
            	matricula.setIdProfesorado(idProfesorado);
            	matricula.setIdAlumnado(idAlumnado);
            	matricula.setAsignatura(asignatura);
            	matricula.setCurso(curso);

               
                sesion.update(matricula);

                
                transaction.commit();
                System.out.println("Objeto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e; 
        } finally {
                        cerrar();
        }
    }


    public void actualizarProfesor(int id, int idProfesorado,int idAlumnado, String asignatura, int curso) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            
            EntidadMatricula matricula = sesion.get(EntidadMatricula.class, id);
            if (matricula != null) {
                
            	matricula.setIdProfesorado(idProfesorado);
            	matricula.setIdAlumnado(idAlumnado);
            	matricula.setAsignatura(asignatura);
            	matricula.setCurso(curso);

               
                sesion.update(matricula);

                
                transaction.commit();
                System.out.println("Objeto actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el objeto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al intentar actualizar el objeto: " + e.getMessage());
            throw e; 
        } finally {
                        cerrar();
        }
    }
    
    public void eliminarTabla(String nombreTabla) {
        try {
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            String sql = "DROP TABLE IF EXISTS " + nombreTabla;
            sesion.createNativeQuery(sql).executeUpdate();
            
            transaction.commit();
            System.out.println("Tabla " + nombreTabla + " eliminada exitosamente.");
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().contains("foreign key") || e.getMessage().toLowerCase().contains("constraint")) {
                System.err.println("Error: No se puede eliminar '" + nombreTabla + "' porque tiene referencias en otras tablas.");
            } else {
                System.err.println("Error al eliminar la tabla " + nombreTabla + ": " + e.getMessage());
            }
            
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            cerrar();
        }
    }
    
    public Object guardar(Object cosa) {
        if (sesion == null) {
            throw new IllegalStateException("La sesión no está abierta.");
        }
        return sesion.save(cosa);
    }
}