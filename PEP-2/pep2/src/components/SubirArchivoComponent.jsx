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

const SubirArchivoComponent = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    setFile(selectedFile);
  };

  const handleUpload = () => {
    if (file) {
      const formData = new FormData();
      formData.append('file', file);

      axios.post('/acopio', formData)
        .then(response => {
          console.log('Archivo subido:', response.data);
          // Realizar cualquier acción adicional después de subir el archivo
        })
        .catch(error => {
          console.error('Error al subir el archivo:', error);
          // Manejar el error de manera adecuada
        });
    }
  };

  return (
    <MainContainer>
    <FormContainer>
      <h2>Subir Archivo</h2>
      <FormGroup>
        <Label>Seleccione un archivo CSV:</Label>
        <Input type="file" accept=".csv" onChange={handleFileChange} />
      </FormGroup>
      <Button onClick={handleUpload}>Subir</Button>
      <Link to="/proveedores">
        <Button>Volver</Button>
      </Link>
    </FormContainer>
    </MainContainer>
  );
};

export default SubirArchivoComponent;