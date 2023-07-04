import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styled from 'styled-components';
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
const TableContainer = styled.div`
  max-width: 1000px;
  margin: 20px auto;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
`;

const TableHeader = styled.th`
  background-color: #f8f9fa;
  padding: 10px;
  text-align: left;
  border: 1px solid #dee2e6;
`;

const TableData = styled.td`
  padding: 10px;
  text-align: left;
  border: 1px solid #dee2e6;
`;

const PlanillaComponent = () => {
  const [planillaData, setPlanillaData] = useState([]);

  useEffect(() => {
    axios.get('/planilla')
      .then(response => {
        setPlanillaData(response.data);
      })
      .catch(error => {
        console.error('Error al obtener los datos de la planilla:', error);
      });
  }, []);

  return (
    <MainContainer>
    <TableContainer>
      <h2>Datos de la Planilla</h2>
      <Table>
        <thead>
          <tr>
            <TableHeader>Quincena</TableHeader>
            <TableHeader>Código proveedor</TableHeader>
            <TableHeader>Nombre proveedor</TableHeader>
            <TableHeader>TOTAL KLS leche</TableHeader>
            <TableHeader>Nro. días que envío leche</TableHeader>
            <TableHeader>Promedio diario KLS leche</TableHeader>
            <TableHeader>%Variación Leche</TableHeader>
            <TableHeader>%Grasa</TableHeader>
            <TableHeader>%Variación Grasa</TableHeader>
            <TableHeader>%Solidos Totales</TableHeader>
            <TableHeader>%Variación ST</TableHeader>
            <TableHeader>Pago por Leche</TableHeader>
            <TableHeader>Pago por Grasa</TableHeader>
            <TableHeader>Pago por Solidos Totales</TableHeader>
            <TableHeader>Bonificación por Frecuencia</TableHeader>
            <TableHeader>Dcto. Variación Leche</TableHeader>
            <TableHeader>Dcto. Variación Grasa</TableHeader>
            <TableHeader>Dcto. Variación ST</TableHeader>
            <TableHeader>Pago TOTAL</TableHeader>
            <TableHeader>Monto Retención</TableHeader>
            <TableHeader>Monto FINAL</TableHeader>
          </tr>
        </thead>
        <tbody>
          {planillaData.map((item, index) => (
            <tr key={index}>
              <TableData>{item.quincena}</TableData>
              <TableData>{item.codigoProveedor}</TableData>
              <TableData>{item.nombreProveedor}</TableData>
              <TableData>{item.totalKlsLeche}</TableData>
              <TableData>{item.numDiasEnvioLeche}</TableData>
              <TableData>{item.promedioDiarioKlsLeche}</TableData>
              <TableData>{item.variacionLeche}</TableData>
              <TableData>{item.grasa}</TableData>
              <TableData>{item.variacionGrasa}</TableData>
              <TableData>{item.solidosTotales}</TableData>
              <TableData>{item.variacionST}</TableData>
              <TableData>{item.pagoLeche}</TableData>
              <TableData>{item.pagoGrasa}</TableData>
              <TableData>{item.pagoSolidosTotales}</TableData>
              <TableData>{item.bonificacionFrecuencia}</TableData>
              <TableData>{item.descuentoVariacionLeche}</TableData>
              <TableData>{item.descuentoVariacionGrasa}</TableData>
              <TableData>{item.descuentoVariacionST}</TableData>
              <TableData>{item.pagoTotal}</TableData>
              <TableData>{item.montoRetencion}</TableData>
              <TableData>{item.montoFinal}</TableData>
            </tr>
          ))}
        </tbody>
      </Table>
    </TableContainer>
    </MainContainer>
  );
};

export default PlanillaComponent;
