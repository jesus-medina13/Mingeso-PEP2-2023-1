import axios from "axios";

class ProveedorService {
    
    guardarProveedor(proveedor){
        return axios.post(`localhost:8080/proveedor`, proveedor);
    }
}

export default new ProveedorService();