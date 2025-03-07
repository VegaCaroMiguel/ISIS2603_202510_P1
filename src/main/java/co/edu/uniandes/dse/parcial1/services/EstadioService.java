package co.edu.uniandes.dse.parcial1.services;

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
public class EstadioService {

	@Autowired
	ConciertoRepository conciertoRepository;

	@Autowired
	EstadioRepository estadioRepository;
	
	
	@Transactional
	public EstadioEntity createEstadio(EstadioEntity estadioEntity) throws EntityNotFoundException, IllegalOperationException {
		log.info("Inicia proceso de creación de un estadio");

		if (!validateNombreCiudad(estadioEntity.getNombreCiudad()))
			throw new IllegalOperationException("Nombre is not valid");

        if (!validateCapacidadMaxima(estadioEntity.getCapacidadMaxima())){
			throw new IllegalOperationException("CapacidadMaxima is not valid"); 
        }

        if (!validatePrecioAlquiler(estadioEntity.getPrecioAlquiler())){
			throw new IllegalOperationException("PrecioAlquiler is not valid");   
        }

		log.info("Termina proceso de creación del estadio");
		return estadioRepository.save(estadioEntity);
	}

	public boolean validateNombreCiudad(String nombreCiudad) {
        return nombreCiudad.length() >= 3; 
    }
    
    private boolean validateCapacidadMaxima(Integer capacidad) {
		return capacidad >1000; 
	}

    private boolean validatePrecioAlquiler(Integer precioAlquiler) {
		return precioAlquiler <100000; 
	}
}
