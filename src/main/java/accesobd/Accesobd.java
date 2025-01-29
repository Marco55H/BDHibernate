package accesobd;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import entidades.EntidadAlumno;

public class Accesobd {
    private SessionFactory sf;
    private Session sesion;
    private Transaction transaction;

   
    public Accesobd() {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure() //
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
    
    public void leer(Class<?> clazz, int id) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

           
            Object objeto = sesion.get(clazz, id); 
            if (objeto != null) {
                System.out.println("Objeto encontrado con ID: " + id);
                System.out.println(objeto);
            } else {
                System.out.println("No se encontró el objeto con ID: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error al intentar obtener el objeto: " + e.getMessage());
            e.printStackTrace();
            throw e; 
        } finally {
            
            cerrar();
        }
    }
    
    public void actualizar(int id, String nombre, String apellido, String fecha) throws Exception {
        try {
            
            if (sesion == null || !sesion.isOpen()) {
                abrir();
            }

            
            if (transaction == null || !transaction.isActive()) {
                transaction = sesion.beginTransaction();
            }

            
            EntidadAlumno persona = sesion.get(EntidadAlumno.class, id);
            if (persona != null) {
                
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setFechaNacimiento(apellido);

               
                sesion.update(persona);

                
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




    public Object guardar(Object cosa) {
        if (sesion == null) {
            throw new IllegalStateException("La sesión no está abierta.");
        }
        return sesion.save(cosa);
    }
}