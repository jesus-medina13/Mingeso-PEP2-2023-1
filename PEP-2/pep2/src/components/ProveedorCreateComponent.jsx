import React, { useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
import background from '../images/cows.jpg';
import { Link } from 'react-router-dom';

const MainContainer = styled.div`
  width: 100%;
  height: 100vh;
  background-color: #343a40;
  background-image: url(${background});
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
`;

const FormContainer = styled.div`
  max-width: 500px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
`;

const Label = styled.label`
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
`;

const Input = styled.input`
  width: 100%;
  padding: 10px;
  font-size: 16px;
  border: 1px solid #ced4da;
  border-radius: 4px;
`;

const Button = styled.button`
  padding: 10px 20px;
  font-size: 16px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
`;
const buttonStyle = {
    backgroundColor: 'white',
    color: 'black',
    padding: '10px 20px',
    fontSize: '1.2em',
    marginRight: '10px',
  };

const ProveedorCreateComponent = () => {
  const [codigo, setCodigo] = useState('');
  const [nombre, setNombre] = useState('');
  const [categoria, setCategoria] = useState('');
  const [retencion, setRetencion] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();

    const proveedorData = {
      codigo,
      nombre,
      categoria,
      retencion
    };

    axios.post("http://localhost:8080/proveedor", proveedorData)
      .then(response => {
        console.log('Proveedor agregado:', response.data);
        // Realizar cualquier acción adicional después de agregar el proveedor
      })
      .catch(error => {
        console.error('Error al agregar el proveedor:', error);
        // Manejar el error de manera adecuada
      });
  };

  return (
    <MainContainer>
    <FormContainer>
      <h2>Ingresar Proveedor</h2>
      <form onSubmit={handleSubmit}>
        <FormGroup>
          <Label>Código:</Label>
          <Input type="text" value={codigo} onChange={(e) => setCodigo(e.target.value)} />
        </FormGroup>
        <FormGroup>
          <Label>Nombre:</Label>
          <Input type="text" value={nombre} onChange={(e) => setNombre(e.target.value)} />
        </FormGroup>
        <FormGroup>
          <Label>Categoría:</Label>
          <Input type="text" value={categoria} onChange={(e) => setCategoria(e.target.value)} />
        </FormGroup>
        <FormGroup>
          <Label>Retención:</Label>
          <Input type="text" value={retencion} onChange={(e) => setRetencion(e.target.value)} />
        </FormGroup>
        <Button type="submit" onClick={handleSubmit}>Guardar</Button>
      </form>
    </FormContainer>
    </MainContainer>
  );
};

export default ProveedorCreateComponent;