package peluCanina.peluCanina.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peluCanina.peluCanina.DTO.DTODuenio;
import peluCanina.peluCanina.DTO.DTOMascota;
import peluCanina.peluCanina.entity.Duenio;
import peluCanina.peluCanina.repository.IDuenioRepository;

@Service
public class DuenioService implements IDuenioService {

    @Autowired
    IDuenioRepository duenioRepo;

    @Autowired
    IMascotaService mascoService;

    @Override
    public void crearDuenio(Duenio duen) {

        duenioRepo.save(duen);
    }

    @Override
    public Duenio traerDuenio(Long id) {

        return duenioRepo.findById(id).orElse(null);
    }

    @Override
    public DTODuenio traerDuenioDTO(Long id) {

        Duenio duen = this.traerDuenio(id);

        DTODuenio dtoDuen = new DTODuenio();

        dtoDuen.setIdDTODuenio(id);
        dtoDuen.setNombre(duen.getNombre());
        dtoDuen.setDireccion(duen.getDireccion());
        dtoDuen.setCelular(duen.getCelular());

        List<DTOMascota> mascosDTO = new ArrayList();

        for (DTOMascota masco : mascoService.listarMascotasDTO()) {

            if (masco.getIdDuenio().equals(id.toString())) {

                mascosDTO.add(masco);

            }

        }

        dtoDuen.setMascosDTO(mascosDTO);
        return dtoDuen;
    }

    @Override
    public List<Duenio> listarDuenios() {

        return duenioRepo.findAll();
    }

    @Override
    public List<DTODuenio> listarDueniosDTO() {

        List<DTODuenio> listarDuenios = new ArrayList();

        for (Duenio duen : this.listarDuenios()) {

            DTODuenio dtoDuen = new DTODuenio();

            dtoDuen.setIdDTODuenio(duen.getId());
            dtoDuen.setNombre(duen.getNombre());
            dtoDuen.setDireccion(duen.getDireccion());
            dtoDuen.setCelular(duen.getCelular());

            List<DTOMascota> mascosDTO = new ArrayList();

            for (DTOMascota masco : mascoService.listarMascotasDTO()) {

                if (masco.getIdDuenio().equals(duen.getId().toString())) {

                    mascosDTO.add(masco);

                }

                dtoDuen.setMascosDTO(mascosDTO);

            }

            listarDuenios.add(dtoDuen);
        }

        return listarDuenios;
    }

    @Override
    public void borrarDuenio(Long id) {
        duenioRepo.deleteById(id);
    }

    @Override
    public void editarDuenio(Duenio duen) {

        Duenio du = this.traerDuenio(duen.getId());

        du.setNombre(duen.getNombre());
        du.setCelular(duen.getCelular());
        du.setDireccion(duen.getDireccion());
        du.setMascotas(duen.getMascotas());

        this.crearDuenio(du);
    }

}
