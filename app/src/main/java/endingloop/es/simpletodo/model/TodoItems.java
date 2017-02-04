package endingloop.es.simpletodo.model;

/**
 * Created by xacy on 03/02/2017.
 */

public class TodoItems {
    private long id;
    private long todoId;
    private String descripcion;
    private boolean status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TodoItems{" +
                "id=" + id +
                ", todoId=" + todoId +
                ", text='" + descripcion + '\'' +
                ", status=" + status +
                '}';
    }
}
