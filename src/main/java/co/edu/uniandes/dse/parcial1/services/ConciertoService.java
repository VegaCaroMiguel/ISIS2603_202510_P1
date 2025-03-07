package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

	@Autowired
	ConciertoRepository conciertoRepository;

	@Autowired
	EstadioRepository estadioRepository;
	
	
	@Transactional
	public ConciertoEntity createConcierto(ConciertoEntity conciertoEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación del concierto");
		
		if (conciertoEntity.getEstadio() == null)
			throw new IllegalOperationException("Estadio is not valid");
		
		Optional<EstadioEntity> estadioEntity = estadioRepository.findById(conciertoEntity.getEstadio().getId());
		if (estadioEntity.isEmpty())
			throw new IllegalOperationException("Estadio is not valid");

		if (!validateFecha(conciertoEntity.getFechaConcierto()))
			throw new IllegalOperationException("Fecha is not valid");

        if (!validateCapacidadMaxima(conciertoEntity.getCapacidadMaxima())){
			throw new IllegalOperationException("CapacidadMaxima is not valid"); 
        }

        if (!validatePresupuesto(conciertoEntity.getPresupuesto())){
			throw new IllegalOperationException("Presupuesto is not valid");   
        }

		conciertoEntity.setEstadio(estadioEntity.get());
		log.info("Termina proceso de creación del concierto");
		return conciertoRepository.save(conciertoEntity);
	}

	public boolean validateFecha(LocalDateTime fechaConcierto) {
        LocalDateTime hoy = LocalDateTime.now();
        return fechaConcierto != null && !fechaConcierto.isBefore(hoy);
    }
    
    private boolean validateCapacidadMaxima(Integer capacidad) {
		return capacidad >10; 
	}

    private boolean validatePresupuesto(Double presupuesto) {
		return presupuesto <1000; 
	}
}
