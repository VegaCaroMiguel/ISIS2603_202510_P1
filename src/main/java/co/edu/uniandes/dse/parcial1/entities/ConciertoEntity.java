package co.edu.uniandes.dse.parcial1.entities;

import co.edu.uniandes.dse.parcial1.podam.DateStrategy;
import jakarta.persistence.Entity;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import uk.co.jemos.podam.common.PodamStrategyValue;
import jakarta.persistence.Temporal;

import jakarta.persistence.ManyToOne;

import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Double presupuesto; 
    private Integer capacidadMaxima; 

    @Temporal(TemporalType.DATE)
	@PodamStrategyValue(DateStrategy.class)
	private LocalDateTime fechaConcierto;

    @PodamExclude
    @ManyToOne
    private EstadioEntity estadio;  

}
