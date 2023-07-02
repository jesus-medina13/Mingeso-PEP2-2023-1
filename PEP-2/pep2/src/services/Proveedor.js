import axios from "axios";

class ProveedorService {
    
    guardarProveedor(proveedor){
        return axios.post(`gateway-service:8090/proveedor`, proveedor);
    }
}

export default new ProveedorService();