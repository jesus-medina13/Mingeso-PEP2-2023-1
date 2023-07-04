import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';
import background from '../images/cows.jpg';
import '../App.css';

const MainContainer = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #343a40;
  background-image: url(${background});
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`;

const buttonStyle = {
  backgroundColor: 'white',
  color: 'black',
  padding: '10px 20px',
  fontSize: '1.2em',
  marginRight: '10px',
};

const titleStyle = {
  textAlign: 'center',
  color: 'white',
  fontSize: '3em',
  marginTop: '100px',
};

const TableContainer = styled.div`
  position: relative;
  max-width: 1199.98px;
  margin: 20px auto 0;
  padding: 0 15px;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.3);
    z-index: -1;
  }
`;

const Table = styled.table`
  border-collapse: collapse;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.3);
  border: 1px solid #000;
  color: #fff;
  text-align: center;
  font-family: 'Titillium Web', sans-serif;
`;

const ProveedoresComponent = () => {
  const [proveedores, setProveedores] = useState([]);

  useEffect(() => {
    axios.get("/proveedor")
      .then(response => response.data)
      .then(data => {
        setProveedores(data);
      })
      .catch(error => {
        console.error('Error al obtener los proveedores:', error);
      });
  }, []);

  return (
    <MainContainer>
      <div className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <span className="navbar-brand mb-0">
            <h1>MilkStgo</h1>
          </span>
        </div>
      </div>
      <TableContainer>
        <Table className="table button-text table-dark">
          <thead>
            <tr>
              <th>Código</th>
              <th>Nombre</th>
              <th>Categoría</th>
              <th>Retención</th>
            </tr>
          </thead>
          <tbody>
            {proveedores.map(proveedor => (
              <tr key={proveedor.codigo}>
                <td>{proveedor.codigo}</td>
                <td>{proveedor.nombre}</td>
                <td>{proveedor.categoria}</td>
                <td>{proveedor.retencion}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </TableContainer>
      <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
        <Link to="/proveedor/create">
          <button style={buttonStyle}>Ingresar Proveedor</button>
        </Link>
        <Link to="/">
          <button style={buttonStyle}>Volver</button>
        </Link>
    </div>
    </MainContainer>
  );
};

export default ProveedoresComponent;