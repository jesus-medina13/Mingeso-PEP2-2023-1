import axios from "axios";
class AcopioService {
    subirArchivo(archivo){
        return axios.post(`gateway-service:8090/acopio`, archivo);
    }
}

export default new AcopioService();