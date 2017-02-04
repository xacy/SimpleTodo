package endingloop.es.simpletodo.model;

/**
 * Created by xacy on 03/02/2017.
 */

public class Listas {

    private long id;
    private String descripcion;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Listas{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
