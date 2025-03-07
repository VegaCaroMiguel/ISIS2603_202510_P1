package co.edu.uniandes.dse.parcial1.services;

	import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
	import java.util.List;

	import jakarta.transaction.Transactional;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
	import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
	import org.springframework.context.annotation.Import;

	import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
	import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
	import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
	import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
	import uk.co.jemos.podam.api.PodamFactory;
	import uk.co.jemos.podam.api.PodamFactoryImpl;

	@DataJpaTest
	@Transactional
	@Import(EstadioService.class)
class EstadioServiceTest {

	@Autowired
	private EstadioService estadioService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EstadioEntity> estadioList = new ArrayList<>();
	private ConciertoEntity conciertoEntity;

	/**
	 * Configuración inicial de la prueba.
	 */
	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

	/**
	 * Limpia las tablas que están implicadas en la prueba.
	 */
	private void clearData() {
		entityManager.getEntityManager().createQuery("delete from ConciertoEntity");
		entityManager.getEntityManager().createQuery("delete from EstadioEntity");
	}

	/**
	 * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
	 */
	private void insertData() {
		
		conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
		entityManager.persist(conciertoEntity);

		for (int i = 0; i < 3; i++) {
			EstadioEntity estadioEntity = factory.manufacturePojo(EstadioEntity.class);
			estadioEntity.getConciertos().add(estadioEntity);
			entityManager.persist(estadioEntity);
			estadioList.add(estadioEntity);
		}
	}

	@Test
	void testCreateEstadio() throws EntityNotFoundException, IllegalOperationException {
		EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
        newEntity.setNombreCiudad("Bogotá");
		newEntity.setCapacidadMaxima(10000);
        newEntity.setPrecioAlquiler(1000000);
		EstadioEntity result = estadioService.createEstadio(newEntity);
		assertNotNull(result);
		EstadioEntity entity = entityManager.find(EstadioEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombreCiudad(), entity.getNombreCiudad());
		assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
		assertEquals(newEntity.getPrecioAlquiler(), entity.getPrecioAlquiler());
	}

	@Test
	void testCreateEstadioWithNoValidPresupuesto() {
		assertThrows(IllegalOperationException.class, () -> {
			EstadioEntity newEntity = factory.manufacturePojo(EstadioEntity.class);
			newEntity.setPrecioAlquiler(null);
			estadioService.createEstadio(newEntity);
		});
	}

}
