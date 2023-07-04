import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeComponent from './components/HomeComponent';
import ProveedorComponent from './components/ProveedorComponent';
import ProveedorCreateComponent from './components/ProveedorCreateComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeComponent/>}/>
          <Route path="/proveedor" element={<ProveedorComponent/>}/>
          <Route path="/proveedor/create" element={<ProveedorCreateComponent/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
