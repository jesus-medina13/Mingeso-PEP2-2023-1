import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HomeComponent from './components/HomeComponent';
import ProveedoresComponent from './components/ProveedoresComponent';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeComponent/>}/>
          <Route path="/proveedores" element={<ProveedoresComponent/>}/>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
