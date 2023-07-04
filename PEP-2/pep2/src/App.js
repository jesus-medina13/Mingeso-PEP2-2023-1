import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeComponent from './components/HomeComponent';
import ProveedorComponent from './components/ProveedorComponent';
import ProveedorCreateComponent from './components/ProveedorCreateComponent';
import SubirArchivoComponent from './components/SubirArchivoComponent';
import PlanillaComponent from './components/PlanillaComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeComponent/>}/>
          <Route path="/proveedor" element={<ProveedorComponent/>}/>
          <Route path="/proveedor/create" element={<ProveedorCreateComponent/>}/>
          <Route path="/acopio" element={<SubirArchivoComponent/>}/>
          <Route path="/planilla" element={<PlanillaComponent/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
