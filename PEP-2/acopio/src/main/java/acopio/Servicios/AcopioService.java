package acopio.Servicios;

import acopio.Entidades.Acopio;
import acopio.Entidades.Acopio2;
import acopio.Repositorios.Acopio2Repository;
import acopio.Repositorios.AcopioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AcopioService {
    @Autowired
    private AcopioRepository acopioRepository;

    @Autowired
    private Acopio2Repository acopio2Repository;

    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear la carpeta uploads.");
        }
    }

    public void guardarArchivo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(fileName != null){
            if(!file.isEmpty()){
                try {
                    init();
                    Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    throw new RuntimeException("No se pudo guardar el archivo. Error: " + e.getMessage());
                }
            }
        }
    }
    public void leerCsv(String fileName) {
        String line = "";
        String csvSplit = ";";
        String root = "uploads/";
        try {
            BufferedReader br = new BufferedReader(new FileReader(root + fileName));
            int firstLine = 1;
            int format = 0;
            while((line = br.readLine()) != null) {
                if(firstLine == 1) {
                    String[] data = line.split(csvSplit);
                    if(data[0].equals("Proveedor") || data[1].equals("% Grasa") || data[2].equals("% Sólido Total")){
                        format = 2;
                    }
                    else if(data[0].equals("Fecha")|| data[1].equals("Turno") || data[2].equals("Proveedor") || data[3].equals("KLS Leche")){
                        format = 1;
                    }
                    else{
                        br.close();
                        throw new RuntimeException("El archivo no tiene el formato correcto.");
                    }
                    firstLine++;
                }
                else if(firstLine != 1 && format == 1){
                    String[] data = line.split(csvSplit);
                    Acopio fileUploadEntity = new Acopio();
                    fileUploadEntity.setFecha(data[0]);
                    fileUploadEntity.setTurno(data[1]);
                    fileUploadEntity.setCodigo(Integer.parseInt(data[2]));
                    fileUploadEntity.setKls_leche(Integer.parseInt(data[3]));
                    acopioRepository.save(fileUploadEntity);
                }
                else if(firstLine != 1 && format == 2){
                    String[] data = line.split(csvSplit);
                    Acopio2 fileUploadEntityType2 = new Acopio2();
                    fileUploadEntityType2.setCodigo(Integer.parseInt(data[0]));
                    fileUploadEntityType2.setGrasa(Float.parseFloat(data[1]));
                    fileUploadEntityType2.setSolido_total(Float.parseFloat(data[2]));
                    acopio2Repository.save(fileUploadEntityType2);
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException("No se encontró el archivo. Error: " + e.getMessage());
        } finally {
            System.out.println("Lectura de archivo finalizada.");
        }
    }
    public List<Acopio> getArchivos() {
        return acopioRepository.findAll();
    }

    public List<Acopio2> getArchivos2() {
        return acopio2Repository.findAll();
    }

    public void crearAcopio(String fecha, String turno, int codigo, int kls_leche) {
        Acopio acopio = new Acopio();
        acopio.setFecha(fecha);
        acopio.setTurno(turno);
        acopio.setCodigo(codigo);
        acopio.setKls_leche(kls_leche);
        acopioRepository.save(acopio);
    }

    public void createAcopio2(int codigo, float grasa, float solido_total) {
        Acopio2 acopio2 = new Acopio2();
        acopio2.setCodigo(codigo);
        acopio2.setGrasa(grasa);
        acopio2.setSolido_total(solido_total);
        acopio2Repository.save(acopio2);
    }
}
