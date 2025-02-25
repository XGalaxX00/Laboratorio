package zegel.edu.pe.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import zegel.edu.pe.dao.ICompeticionesDao;
import zegel.edu.pe.dao.IFaseDao;
import zegel.edu.pe.dao.IInscripcionesDao;
import zegel.edu.pe.dao.IPuntajeDao;
import zegel.edu.pe.dao.ITurnos;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Fase;
import zegel.edu.pe.models.Inscripciones;
import zegel.edu.pe.models.Puntaje;
import zegel.edu.pe.models.Turnos;
import zegel.edu.pe.models.Usuarios;

@Service
public class TurnosServices {

    @Autowired
    private ITurnos turnoRepository;

    @Autowired
    private IInscripcionesDao inscripcionesRepository;

    @Autowired
    private IFaseDao faseRepository;

    @Autowired
    private ICompeticionesDao competicionesRepository;
    
    @Autowired
    private IUsuariosDao usuariosRepository;
    
    @Autowired
    private IPuntajeDao punDao;

    @Transactional
    public void generarTurnosSemifinal(Integer competicionId) {
        System.out.println(">> Generando turnos para competición ID: " + competicionId);

        List<Inscripciones> inscripciones = inscripcionesRepository.findByCompeticiones_Id(competicionId);
        System.out.println(">> Inscripciones encontradas: " + inscripciones.size());

        if (inscripciones.size() < 4) {
            System.out.println(">> No hay suficientes competidores.");
            return;
        }

        List<Usuarios> competidores = inscripciones.stream()
                .map(Inscripciones::getUsuarios)
                .collect(Collectors.toList());

        System.out.println(">> Total de competidores antes de mezclar: " + competidores.size());
        Collections.shuffle(competidores);
        System.out.println(">> Total de competidores después de mezclar: " + competidores.size());

        Fase faseSemifinal = faseRepository.findByNombre("Semifinal")
                .orElseThrow(() -> new RuntimeException("Fase 'Semifinal' no encontrada"));

        Competiciones competicion = competicionesRepository.findById(competicionId)
                .orElseThrow(() -> new RuntimeException("Competición no encontrada"));

        for (int i = 0; i < competidores.size(); i += 2) {
            if (i + 1 < competidores.size()) {
                Turnos turno = new Turnos();
                turno.setCompeticion(competicion);
                turno.setFase(faseSemifinal);
                turno.setCompetidor1(competidores.get(i));
                turno.setCompetidor2(competidores.get(i + 1));

                Turnos turnoGuardado = turnoRepository.save(turno);
                System.out.println(">> Turno guardado: " + turnoGuardado.getId());
            }
        }

        turnoRepository.flush();  // Forzar la escritura en la BD
        System.out.println(">> Turnos guardados en la base de datos.");
    }
    @Transactional
    public void registrarGanadoresSemifinal(Map<Integer, Integer> ganadores) {
        List<Usuarios> ganadoresFinal = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : ganadores.entrySet()) {
            Integer turnoId = entry.getKey();
            Integer ganadorId = entry.getValue();

            Optional<Turnos> turnoOpt = turnoRepository.findById(turnoId);
            Optional<Usuarios> ganadorOpt = usuariosRepository.findById(ganadorId);

            if (turnoOpt.isPresent() && ganadorOpt.isPresent()) {
                Turnos turno = turnoOpt.get();
                Usuarios ganador = ganadorOpt.get();

                System.out.println("Actualizando turno ID: " + turnoId + " con ganador ID: " + ganadorId);

                turno.setGanador(ganador);
                turnoRepository.save(turno);
                
             // Obtener el puntaje del usuario
                Puntaje puntaje = ganador.getPuntaje();
                if (puntaje != null) {
                    puntaje.setPuntaje(puntaje.getPuntaje() + 3);
                    punDao.save(puntaje); // Guardamos directamente en la tabla de puntajes
                } else {
                    throw new IllegalStateException("El usuario no tiene un puntaje asignado.");
                }

                ganadoresFinal.add(ganador);
            } else {
                System.out.println("Error: No se encontró el turno o el ganador.");
            }
        }

        // Si hay exactamente dos ganadores, creamos la final
        if (ganadoresFinal.size() == 2) {
            generarTurnoFinal(ganadoresFinal.get(0), ganadoresFinal.get(1));
        } else {
            System.out.println("Error: No hay suficientes ganadores para la final.");
        }
    }

    @Transactional
    public void generarTurnoFinal(Usuarios competidor1, Usuarios competidor2) {
        System.out.println(">> Generando turno final con " + competidor1.getId() + " vs " + competidor2.getId());

        Fase faseFinal = faseRepository.findByNombre("Final")
                .orElseThrow(() -> new RuntimeException("Fase 'Final' no encontrada"));

        Competiciones competicion = competicionesRepository.findById(23)
                .orElseThrow(() -> new RuntimeException("Competición no encontrada"));

        Turnos turnoFinal = new Turnos();
        turnoFinal.setCompeticion(competicion);
        turnoFinal.setFase(faseFinal);
        turnoFinal.setCompetidor1(competidor1);
        turnoFinal.setCompetidor2(competidor2);

        turnoRepository.save(turnoFinal);
        System.out.println(">> Turno final guardado en la base de datos.");
    }

    
    
    @Transactional
    public void pruebaInsercion() {
        Turnos turno = new Turnos();
        
        turno.setCompeticion(competicionesRepository.findById(23)
            .orElseThrow(() -> new RuntimeException("Competición no encontrada")));
        turno.setFase(faseRepository.findByNombre("Semifinal")
            .orElseThrow(() -> new RuntimeException("Fase 'Semifinal' no encontrada")));
        turno.setCompetidor1(usuariosRepository.findById(42)
            .orElseThrow(() -> new RuntimeException("Usuario 1 no encontrado")));
        turno.setCompetidor2(usuariosRepository.findById(43)
            .orElseThrow(() -> new RuntimeException("Usuario 2 no encontrado")));

        Turnos turnoGuardado = turnoRepository.save(turno);
        System.out.println("Turno guardado con ID: " + turnoGuardado.getId());
    }
    
    public List<Turnos> obtenerTurnosPorCompeticion(Integer competicionId) {
        return turnoRepository.findByCompeticionId(competicionId);
    }

    public List<Turnos> findByCompeticionIdAndFase(Integer competicionId, String faseNombre) {
        return turnoRepository.findByCompeticionIdAndFase(competicionId, faseNombre);
    }
    public List<Turnos> obtenerTodosLosTurnos() {
        return turnoRepository.findAll();
    }

}


