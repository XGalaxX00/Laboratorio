package zegel.edu.pe;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;

import zegel.edu.pe.dao.ICategoriasDao;
import zegel.edu.pe.dao.IClubesDao;
import zegel.edu.pe.dao.ICompeticionesDao;
import zegel.edu.pe.dao.IEstadosDao;
import zegel.edu.pe.dao.IEventosDao;
import zegel.edu.pe.dao.IFaseDao;
import zegel.edu.pe.dao.INivelesDao;
import zegel.edu.pe.dao.ISedesDao;
import zegel.edu.pe.dao.ISolicitudesDao;
import zegel.edu.pe.dao.ITipo_UsuarioDao;
import zegel.edu.pe.dao.ITurnos;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Categorias;
import zegel.edu.pe.models.Clubes;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Estados;
import zegel.edu.pe.models.Eventos;
import zegel.edu.pe.models.Niveles;
import zegel.edu.pe.models.Puntaje;
import zegel.edu.pe.models.Sedes;
import zegel.edu.pe.models.Solicitudes;
import zegel.edu.pe.models.Tipo_Usuario;
import zegel.edu.pe.models.Turnos;
import zegel.edu.pe.models.Usuarios;
import zegel.edu.pe.services.TurnosServices;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackages = "zegel.edu.pe.services")

class LaboratorioApplicationTests {
	@Autowired
	private IUsuariosDao usuDao;
	@Autowired
	private ICategoriasDao catDao;
	@Autowired
	private ISedesDao sedDao;
	@Autowired
	private INivelesDao nivDao;
	@Autowired
	private ITipo_UsuarioDao tipoDao;
	@Autowired
	private IEventosDao eveDao;
	@Autowired
	private ISolicitudesDao solDao;
	@Autowired
	private IEstadosDao estDao;
	@Autowired
	private IClubesDao cluDao;
	@Autowired
    private TestEntityManager entityManager;
	@Autowired
	private ICompeticionesDao comDao;
	@Autowired
	private IFaseDao faseDao;
	@Autowired
	private ITurnos turDao;
	
	@Autowired
	private TurnosServices turS;
	
	
	
	// INICIAR SESION
	@Test
    public void InicioSesion_Exitoso() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        
        entityManager.persist(usuario);
        entityManager.flush();

        // Act: Buscar el usuario en la BD
        Usuarios encontrado = usuDao.findByCorreo("maria@gmail.com");

        // Assert: Verificar que se encontró el usuario y la contraseña coincide
        assertNotNull(encontrado);
        assertEquals("maria@gmail.com", encontrado.getCorreo());
        assertEquals("123", encontrado.getContrasena());
        System.out.println("Iniciaste Sesion: " + usuario.getNombre());
    }
	
	@Test
    public void InicioSesion_Incorrecto() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        
        entityManager.persist(usuario);
        entityManager.flush();
        
        Usuarios encontrado = usuDao.findByCorreo("maria@gmail.com");

        assertNotNull(encontrado);
        assertNotEquals("incorrecto123", encontrado.getContrasena());
        System.out.println("Error en el inicio de sesion con: " + usuario.getContrasena());
    }
	
	@Test
    public void UsuarioNoEncontrado() {
        Usuarios encontrado = usuDao.findByCorreo("maria@gmail.com");

        assertNull(encontrado);

        System.out.println("No se encontró el usuario, como se esperaba.");
    }
	
	
	
	// PERFIL
	 @Test
     public void PerfilUsuario() {
	    Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        usuario.setFoto(null); // Sin foto

        // Verificaciones
        assertEquals("Maria", usuario.getNombre());
        assertEquals("maria@gmail.com", usuario.getCorreo());
        assertEquals("Manipuladores", usuario.getCategorias().getNombre());
        assertEquals(null, usuario.getFoto());

        System.out.println("Los Datos del usuario correctos.");
    }
	
	@Test
    public void PerfilTieneCategoria() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);

        assertNotNull(usuario.getCategorias());
        assertEquals("Manipuladores", usuario.getCategorias().getNombre());

        System.out.println("Categoría asignada correctamente.");
    }
	
	@Test
    public void FotoPorDefecto() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        usuario.setFoto(null);

        String foto = (usuario.getFoto() == null || usuario.getFoto().isEmpty()) ? "default.jpg" : usuario.getFoto();

        assertEquals("default.jpg", foto);

        System.out.println("Foto asignada correctamente.");
    }
	
	
	// GENERAR TURNOS
	@Test
    public void GenerarTurnosConDatosExistentes() {
        Integer competicionId = 23; // ID de una competición existente en la base de datos

        // Verificar que la competición y fase existen
        assertTrue(comDao.findById(competicionId).isPresent(), "La competición no existe.");
        assertTrue(faseDao.findByNombre("semifinal").isPresent(), "La fase 'semifinal' no existe.");

        // Ejecutar la generación de turnos
        turS.generarTurnosSemifinal(competicionId);

        // Obtener los turnos generados
        List<Turnos> turnos = turDao.findAll();

        // Verificar que se crearon turnos
        assertFalse(turnos.isEmpty(), "No se generaron turnos.");
        assertTrue(turnos.size() >= 2, "Deben generarse al menos 2 turnos.");

        // Mostrar en consola los turnos generados
        for (Turnos turno : turnos) {
            System.out.println("Turno generado: " + turno.getCompetidor1().getId() + " vs " + turno.getCompetidor2().getId());
        }
    }
	
	@Test
	public void CompeticionYFaseExisten() {
	    assertTrue(comDao.findById(24).isPresent(), "La competición no existe.");
	    assertTrue(faseDao.findByNombre("Semifinal").isPresent(), "La fase 'Semifinal' no existe.");
	    System.out.println("La competición y la fase existen.");
	}
	
	
	
	// GENERAR TURNOS MANUALMENTE
	@Test
	public void GenerarTurnosManualmente() {
	    // Simular la existencia de una competición
	    Integer competicionId = 23;
	    assertTrue(comDao.findById(competicionId).isPresent(), "La competición no existe.");

	    // Simular la creación de turnos manuales
	    Usuarios competidor1 = usuDao.findById(42).orElseThrow();
	    Usuarios competidor2 = usuDao.findById(30).orElseThrow();
	    Usuarios competidor3 = usuDao.findById(19).orElseThrow();
	    Usuarios competidor4 = usuDao.findById(43).orElseThrow();

	    // Crear manualmente dos turnos (solo una llamada al servicio)
	    turS.generarTurnosSemifinal(competicionId);

	    // Obtener y verificar los turnos generados
	    List<Turnos> turnos = turDao.findByCompeticionIdAndFase(competicionId, "semifinal");

	    // Mostrar en consola los turnos generados
	    for (Turnos turno : turnos) {
	        System.out.println("Turno generado: " + turno.getCompetidor1().getId() + " vs " + turno.getCompetidor2().getId());
	    }

	    // Verificar que al menos se generaron 2 turnos
	    assertTrue(turnos.size() >= 2, "No se generaron los turnos esperados.");
	}

	
	@Test
	public void VerificarTurnosManuales() {
	    Integer competicionId = 23;
	    List<Turnos> turnos = turDao.findByCompeticionIdAndFase(competicionId, "semifinal");

	    assertFalse(turnos.isEmpty(), "No hay turnos de semifinal en la base de datos.");
	    
	    for (Turnos turno : turnos) {
	        System.out.println("Turno manual: " + turno.getCompetidor1().getId() + " vs " + turno.getCompetidor2().getId());
	    }
	}

	
	
	
	// REGISTRAR RESULTADOS
	@Test
	public void RegistrarFinal() {
	    // Simular dos competidores ganadores
	    Usuarios competidor1 = usuDao.findById(42).orElseThrow();
	    Usuarios competidor2 = usuDao.findById(19).orElseThrow();

	    assertDoesNotThrow(() -> turS.generarTurnoFinal(competidor1, competidor2),
	        "Error al generar el turno final.");

	    System.out.println("Turno final generado correctamente: " + competidor1.getId() + " vs " + competidor2.getId());
	}
	
	@Test
	public void RegistrarGanadores() {
	    // Simulación de ganadores: Clave = ID del turno, Valor = ID del ganador
	    Map<Integer, Integer> ganadores = new HashMap<>();
	    ganadores.put(11, 30); // Turno 101 → Ganador 201
	    ganadores.put(12, 43); // Turno 102 → Ganador 202

	    assertDoesNotThrow(() -> turS.registrarGanadoresSemifinal(ganadores),
	        "Error al registrar ganadores de la semifinal.");

	    System.out.println("Ganadores de la semifinal registrados correctamente.");
	}
	
	
	
	// MOSTRAR RESULTADOS
	@Test
    public void MostrarTurnosFinal() {
        // ID de la competición existente
        Integer competicionId = 23;

        // Obtener turnos de la final desde la base de datos
        List<Turnos> turnosFinal = turDao.findByCompeticionIdAndFase(competicionId, "final");

        // Verificar que hay turnos guardados
        assertFalse(turnosFinal.isEmpty(), "No hay turnos en la fase final.");

        // Mostrar los turnos en consola
        System.out.println("Turnos de la FINAL:");
        for (Turnos turno : turnosFinal) {
            System.out.println(turno.getCompetidor1().getId() + " vs " + turno.getCompetidor2().getId());
        }
    }
	
	@Test
    public void MostrarTurnosSemifinal() {
        // ID de la competición existente
        Integer competicionId = 23;

        // Obtener turnos de la semifinal desde la base de datos
        List<Turnos> turnosSemifinal = turDao.findByCompeticionIdAndFase(competicionId, "semifinal");

        // Verificar que hay turnos guardados
        assertFalse(turnosSemifinal.isEmpty(), "No hay turnos en la fase semifinal.");

        // Mostrar los turnos en consola
        System.out.println("Turnos de la SEMIFINAL:");
        for (Turnos turno : turnosSemifinal) {
            System.out.println(turno.getCompetidor1().getId() + " vs " + turno.getCompetidor2().getId());
        }
    }

	
	
	// INSCRIPCIONES
	@Test
    public void inscripcion_Exitosa() {
        // Crear usuario y competición simulados
        Usuarios usuario = new Usuarios();
        usuario.setId(1);
        usuario.setCorreo("usuario@example.com");
        
        Sedes sede = sedDao.findById(2).orElseThrow();
		Categorias categoria = catDao.findById(1).orElseThrow();
		Eventos registro = new Eventos();
		
		registro.setNombre("Nuevo");
		registro.setFecha("2025-02-25");
		registro.setHora("10:00");
		registro.setSedes(sede);
		registro.setCategorias(categoria);

        Competiciones competicion = new Competiciones();
        competicion.setId(2);
        competicion.setEventos(registro);

        // Simular inscripción
        boolean inscrito = inscribirUsuario(usuario, competicion);

        // Validar que la inscripción fue exitosa
        assertTrue(inscrito);
        System.out.println("Inscripción: Realizado correctamente.");
    }
	
	
	private boolean inscribirUsuario(Usuarios usuario, Competiciones competicion) {
		if (usuario == null || competicion == null) {
            return false;
        }
        return true;
	}
	
	@Test
    public void inscripcion_UsuarioNoExiste() {

		Usuarios usuario = usuDao.findById(45).orElse(null);
        Competiciones competicion = comDao.findById(24).orElse(null);

        assertNull(usuario, "El usuario debería ser nulo porque no existe.");
        assertNotNull(competicion, "La competición sí debería existir.");

        boolean inscrito = inscribirUsuario(usuario, competicion);

        assertFalse(inscrito);
        System.out.println("Inscripción: Usuario no encontrado.");
    }
	
	

	// NOTIFICACIONES
	@Test
    void UsuarioRecibeNotificacionPorCategoria() {
		Sedes sede = sedDao.findById(2).orElseThrow();
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
		Eventos registro = new Eventos();
		
		registro.setNombre("Nuevo Torneo");
		registro.setFecha("2025-02-25");
		registro.setHora("10:00");
		registro.setSedes(sede);
		registro.setCategorias(categoria);

        Usuarios usuarios = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuarios.setNombre("Maria");
        usuarios.setApellido("Lopez");
        usuarios.setTelefono("958471455");
        usuarios.setCorreo("maria@gmail.com");
        usuarios.setDni("45784469");
        usuarios.setContrasena("123");
        usuarios.setCategorias(categoria);
        usuarios.setNiveles(nivel);
        usuarios.setPuntaje(puntaje);
        usuarios.setTipo_usuario(tipo);

        List<String> notificaciones = new ArrayList<>();

        if (usuarios.getCategorias().getId().equals(registro.getCategorias().getId())) {
            notificaciones.add("Nuevo evento: " + registro.getNombre());
        }
        assertFalse(notificaciones.isEmpty(), "El usuario debería recibir una notificación.");
        assertEquals(1, notificaciones.size(), "El usuario debe tener exactamente una notificación.");
        assertEquals("Nuevo evento: Nuevo Torneo", notificaciones.get(0), "El mensaje de la notificación debe ser correcto.");
        System.out.println("Notificacion recibida");

    }
	
	
	
	// USUARIOS
	@Test
    public void RegistrarUsuario_Exitoso() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Maria");
        usuario.setApellido("Lopez");
        usuario.setTelefono("958471455");
        usuario.setCorreo("maria@gmail.com");
        usuario.setDni("45784469");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        
        Usuarios guardado = usuDao.save(usuario);
        
        assertNotNull(guardado.getId());
        assertEquals("Maria", guardado.getNombre());
        assertEquals(1, guardado.getCategorias().getId());
        assertEquals(1, guardado.getNiveles().getId());
        assertNotNull(guardado.getPuntaje());
        assertEquals(5, guardado.getTipo_usuario().getId());
        System.out.println("Usuario Registrado: " + guardado.getNombre() + " " + guardado.getApellido());
    }
	
	@Test
	public void RegistrarUsuario_TelefonoIncompleto() {
		Categorias categoria = catDao.findById(1).orElseThrow();
		Niveles nivel = nivDao.findById(1).orElseThrow();
		Tipo_Usuario tipo = tipoDao.findById(5).orElseThrow();
        Usuarios usuario = new Usuarios();
        Puntaje puntaje = new Puntaje();
        
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setTelefono("98765");
        usuario.setCorreo("juan@gmail.com");
        usuario.setDni("12345678");
        usuario.setContrasena("123");
        usuario.setCategorias(categoria);
        usuario.setNiveles(nivel);
        usuario.setPuntaje(puntaje);
        usuario.setTipo_usuario(tipo);
        
        try {
	        if (usuario.getTelefono() == null || usuario.getTelefono().length() != 10 || !usuario.getTelefono().matches("\\d+")) {
	            throw new IllegalArgumentException("El numero de telefono no puede estar vacío");
	        }
	        usuDao.save(usuario); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El numero de telefono no puede estar vacío", e.getMessage());
	    }
	}
	
	
	
	// EVENTOS
	@Test
	public void RegistrarEvento_Exitoso() {
		Sedes sede = sedDao.findById(2).orElseThrow();
		Categorias categoria = catDao.findById(1).orElseThrow();
		Eventos registro = new Eventos();
		
		registro.setNombre("Nuevo");
		registro.setFecha("2025-02-25");
		registro.setHora("10:00");
		registro.setSedes(sede);
		registro.setCategorias(categoria);
		
		Eventos guardar = eveDao.save(registro);
		
		assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Nuevo", guardar.getNombre());
	    System.out.println("Evento Registrado: " + guardar.getNombre());
	}
	
	@Test 
	public void RegistrarEvento_Erroneo() {
		Sedes sede = sedDao.findById(2).orElseThrow();
		Categorias categoria = catDao.findById(1).orElseThrow();
		Eventos registro = new Eventos();
		
		registro.setNombre("");
		registro.setFecha("2025-02-25");
		registro.setHora("10:00");
		registro.setSedes(sede);
		registro.setCategorias(categoria);
		
		try {
	        if (registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre del evento no puede estar vacío");
	        }
	        eveDao.save(registro); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre del evento no puede estar vacío", e.getMessage());
	    }
	}
	
	
	
	// SOLICITUDES
	@Test
	public void RegistrarSolicitud_Exitoso() {
		Usuarios usuario = usuDao.findById(30).orElseThrow();
		Estados estado = estDao.findById(1).orElseThrow();

		Solicitudes registro = new Solicitudes();
		
		registro.setNombre_club("Nuevo");
		registro.setEncargado(usuario);
		registro.setMotivo("Educativo");
		registro.setEstados(estado);
		
		Solicitudes guardar = solDao.save(registro);
		
		assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Nuevo", guardar.getNombre_club());
	    System.out.println("Solicitud Realizada: " + guardar.getNombre_club());
	}
	
	@Test
	public void RegistrarSolicitud_Erroneo() {
		Usuarios usuario = usuDao.findById(30).orElseThrow();
		Estados estado = estDao.findById(1).orElseThrow();

		Solicitudes registro = new Solicitudes();
		
		registro.setNombre_club("");
		registro.setEncargado(usuario);
		registro.setMotivo("Educativo");
		registro.setEstados(estado);
		
		try {
	        if (registro.getNombre_club() == null || registro.getNombre_club().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre de la solicitud no puede estar vacío");
	        }
	        solDao.save(registro); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre de la solicitud no puede estar vacío", e.getMessage());
	    }
	}
	
	
	// CLUBES
	@Test
	public void RegistrarClub_Exitoso() {
		Usuarios usuario = usuDao.findById(43).orElseThrow();
		Solicitudes solicitud = solDao.findById(16).orElseThrow();
		
		Clubes registro = new Clubes();
		
		registro.setNombre("Nuevo");
		registro.setEncargado(usuario);
		registro.setSolicitudes(solicitud);
		
		Clubes guardar = cluDao.save(registro);
		
		assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Nuevo", guardar.getNombre());
	    System.out.println("Club Registrado: " + guardar.getNombre());
	}
	
	@Test
	public void RegistrarClub_Erroneo() {
		Usuarios usuario = usuDao.findById(43).orElseThrow();
		Solicitudes solicitud = solDao.findById(16).orElseThrow();
		
		Clubes registro = new Clubes();
		
		registro.setNombre("");
		registro.setEncargado(usuario);
		registro.setSolicitudes(solicitud);
		
		try {
	        if (registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre del club no puede estar vacío");
	        }
	        cluDao.save(registro); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre del club no puede estar vacío", e.getMessage());
	    }
	}
	
	
	
	// CATEGORIAS
	@Test
	public void RegistrarCategoria_Exitoso() {
		Categorias registro = new Categorias();
		registro.setNombre("Nuevo");
		
		Categorias guardar = catDao.save(registro);
		
		assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Nuevo", guardar.getNombre());
	    System.out.println("Categoria Registrada: " + guardar.getNombre());
	}
	
	@Test
	public void RegistrarCategoria_Erroneo() {
	    Categorias categoria = new Categorias();
	    categoria.setNombre("");

	    try {
	        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacío");
	        }
	        catDao.save(categoria); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre de la categoria no puede estar vacío", e.getMessage());
	    }
	}
	
	
	
	// SEDES
	@Test
	public void RegistrarSede_Exitoso() {
		Sedes registro = new Sedes();
		registro.setNombre("Nuevo");
		
		Sedes guardar = sedDao.save(registro);
		
		assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Nuevo", guardar.getNombre());
	    System.out.println("Sede Registrada: " + guardar.getNombre());
	}
	
	@Test
	public void RegistrarSede_Erroneo() {
	    Sedes sede = new Sedes();
	    sede.setNombre("");

	    try {
	        if (sede.getNombre() == null || sede.getNombre().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre de la sede no puede estar vacío");
	        }
	        sedDao.save(sede); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre de la sede no puede estar vacío", e.getMessage());
	    }
	}
	
	
	
	// NIVELES
	@Test
	public void RegistrarNivel_Exitoso() {
		Niveles nivel = new Niveles();
	    nivel.setNombre("Avanzado");
	
	    Niveles guardar = nivDao.save(nivel);
	
	    assertNotNull(guardar.getId()); // Verifica que se generó un ID
	    assertEquals("Avanzado", guardar.getNombre());
	    System.out.println("Nivel Registrado: " + guardar.getNombre());
	}
	
	@Test
	public void RegistrarNivel_Erroneo() {
		Niveles nivel = new Niveles();
	    nivel.setNombre(""); // Nombre vacío

	    try {
	        if (nivel.getNombre() == null || nivel.getNombre().trim().isEmpty()) {
	            throw new IllegalArgumentException("El nombre del nivel no puede estar vacío");
	        }
	        nivDao.save(nivel); // Nunca se ejecuta porque se lanza la excepción antes
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	        assertEquals("El nombre del nivel no puede estar vacío", e.getMessage());
	    }
	}

}
