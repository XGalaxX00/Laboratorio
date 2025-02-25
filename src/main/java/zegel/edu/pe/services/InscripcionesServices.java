package zegel.edu.pe.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import zegel.edu.pe.dao.ICompeticionesDao;
import zegel.edu.pe.dao.IInscripcionesDao;
import zegel.edu.pe.dao.IUsuariosDao;
import zegel.edu.pe.models.Competiciones;
import zegel.edu.pe.models.Inscripciones;
import zegel.edu.pe.models.Usuarios;

@Service
public class InscripcionesServices {

	@Autowired
    private IInscripcionesDao inscripcionesRepo;

    @Autowired
    private IUsuariosDao usuDao;

    @Autowired
    private ICompeticionesDao compeDao;
    
    private static final int MAX_INSCRIPCIONES = 4;

    public Inscripciones inscribirUsuario(Inscripciones ins) {
        return inscripcionesRepo.save(ins);
    }
    
    @Transactional
    public Inscripciones inscribirUsuarioEnEvento(Integer usuarios_id, Integer evento_id) {
        System.out.println("Usuario autenticado: " + usuarios_id);
        System.out.println("Evento recibido: " + evento_id);

        // 🔍 Buscar la competición asociada al evento
        Optional<Competiciones> competicionOpt = compeDao.findByEventoId(evento_id);

        if (competicionOpt.isEmpty()) {
            System.out.println("❌ ERROR: No se encontró una competición asociada al evento con ID " + evento_id);
            throw new IllegalArgumentException("No se encontró una competición asociada a este evento.");
        }

        Competiciones com = competicionOpt.get();
        System.out.println("✅ Competición encontrada: " + com.getId());

        Usuarios usu = usuDao.findById(usuarios_id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // 🔍 Validar si el usuario ya está inscrito
        if (inscripcionesRepo.existsByUsuariosAndCompeticiones(usu, com)) {
            throw new IllegalArgumentException("⚠️ El usuario ya está inscrito en este evento.");
        }

        // 📌 Validar si hay cupos disponibles
        if (inscripcionesRepo.countByCompeticiones(com) >= MAX_INSCRIPCIONES) {
            throw new IllegalStateException("❌ El evento ya alcanzó el máximo de 4 inscripciones.");
        }

        // 📝 Registrar la inscripción
        Inscripciones ins = new Inscripciones();
        ins.setUsuarios(usu);
        ins.setCompeticion(com);

        return inscripcionesRepo.save(ins);
    }

    public List<Inscripciones> obtenerInscripcionesPorEvento(Integer eventos_id) {
        return inscripcionesRepo.findByCompeticiones_Id(eventos_id);
    }
    public List<Competiciones> obtenerCompeticionesPorUsuario(Integer usuarioId) {
        return inscripcionesRepo.findCompeticionesByUsuarioId(usuarioId);
    }
    
    public List<Competiciones> obtenerHistorialCompeticiones(Integer usuarioId) {
        Usuarios usuario = usuDao.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        return inscripcionesRepo.findCompeticionesByUsuarioId(usuario.getId());
    }
}
