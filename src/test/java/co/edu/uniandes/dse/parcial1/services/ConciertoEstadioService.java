package co.edu.uniandes.dse.parcial1.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.ErrorMessage;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoEstadioService {

	@Autowired
	private ConciertoRepository conciertoRepository;

	@Autowired
	private EstadioRepository estadioRepository;
	
	@Transactional
	public ConciertoEntity replaceEstadio(Long conciertoId, Long estadioId) throws EntityNotFoundException {
		log.info("Inicia proceso de actualizar concierto con id = {0}", conciertoId);
		Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
		if (conciertoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.CONCIERTO_NOT_FOUND);

		Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
		if (estadioEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ESTADIO_NOT_FOUND);

		conciertoEntity.get().setEstadio(estadioEntity.get());
		log.info("Termina proceso de actualizar libro con id = {0}", conciertoId);

		return conciertoEntity.get();
	}


	@Transactional
	public void removeEstadio(Long conciertoId) throws EntityNotFoundException {
		log.info("Inicia proceso de borrar el Estadio del Concierto con id = {0}", conciertoId);
		Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
		if (conciertoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.CONCIERTO_NOT_FOUND);

		Optional<EstadioEntity> estadioEntity = estadioRepository
				.findById(conciertoEntity.get().getEstadio().getId());
		estadioEntity.ifPresent(estadio -> estadio.getConciertos().remove(conciertoEntity.get()));

		conciertoEntity.get().setEstadio(null);
		log.info("Termina proceso de borrar el Estadio del concierto con id = {0}", conciertoId);
	}
}
