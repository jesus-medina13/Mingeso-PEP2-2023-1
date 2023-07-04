import axios from "axios";
class AcopioService {
    subirArchivo(archivo){
        return axios.post(`localhost:8080/acopio`, archivo);
    }
}

export default new AcopioService();