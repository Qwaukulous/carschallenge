package carschallenge.demo;

import javax.persistence.*;
import java.util.Set;


@Entity
public class CatMake {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String CatMakeName;

    @OneToMany(mappedBy = "catmake", cascade = CascadeType.ALL)
    private Set<Car> car;

    public CatMake() {


    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCatMakeName() {
        return CatMakeName;
    }

    public void setCatMakeName(String catMakeName) {
        CatMakeName = catMakeName;
    }

    public Set<Car> getCar() {
        return car;
    }

    public void setCar(Set<Car> car) {
        this.car = car;
    }
}

