package co.edu.uniandes.dse.parcial1.entities;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;

import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class EstadioEntity extends BaseEntity {

    private String nombreCiudad;

    private Integer capacidadMaxima;
    
    private Integer precioAlquiler; 

    @PodamExclude
    @OneToMany
    private List<EstadioEntity> conciertos = new ArrayList<>(); 

}
