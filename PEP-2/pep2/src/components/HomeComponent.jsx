import React, { Component } from "react";
import { Link } from 'react-router-dom';
import styled from "styled-components";
import background from '../images/cows.jpg';

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


export default function Home() {
  return (
    <MainContainer>
      <h1 style={{ titleStyle }}>MILKSTGO</h1>
      <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
        <Link to="/proveedores">
          <button style={buttonStyle}>Proveedores</button>
        </Link>
        <Link to="/subir-archivo">
          <button style={buttonStyle}>Subir Archivo</button>
        </Link>
        <Link to="/planilla">
          <button style={buttonStyle}>Planilla</button>
        </Link>
    </div>
    </MainContainer>
  );
};
