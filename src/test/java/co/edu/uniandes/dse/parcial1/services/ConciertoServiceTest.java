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
	@Import(ConciertoService.class)
class ConciertoServiceTest {

	@Autowired
	private ConciertoService conciertoService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<ConciertoEntity> conciertoList = new ArrayList<>();
	private EstadioEntity estadioEntity;

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
		
		estadioEntity = factory.manufacturePojo(EstadioEntity.class);
		entityManager.persist(estadioEntity);

		for (int i = 0; i < 3; i++) {
			ConciertoEntity conciertoEntity = factory.manufacturePojo(ConciertoEntity.class);
			conciertoEntity.setEstadio(estadioEntity);
			entityManager.persist(conciertoEntity);
			conciertoList.add(conciertoEntity);
		}
	}

	/**
	 * Prueba para crear un Book
	 */
	@Test
	void testCreateConcierto() throws EntityNotFoundException, IllegalOperationException {
		ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
        newEntity.setFechaConcierto(LocalDateTime.now().plusDays(100));
		newEntity.setCapacidadMaxima(1000);
        newEntity.setPresupuesto(10000.0);
		ConciertoEntity result = conciertoService.createConcierto(newEntity);
		assertNotNull(result);
		ConciertoEntity entity = entityManager.find(ConciertoEntity.class, result.getId());
		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
		assertEquals(newEntity.getPresupuesto(), entity.getPresupuesto());
		assertEquals(newEntity.getCapacidadMaxima(), entity.getCapacidadMaxima());
	}

	@Test
	void testCreateBookWithNoValidPresupuesto() {
		assertThrows(IllegalOperationException.class, () -> {
			ConciertoEntity newEntity = factory.manufacturePojo(ConciertoEntity.class);
			newEntity.setEstadio(estadioEntity);
			newEntity.setPresupuesto(0.0);
			conciertoService.createConcierto(newEntity);
		});
	}

}
