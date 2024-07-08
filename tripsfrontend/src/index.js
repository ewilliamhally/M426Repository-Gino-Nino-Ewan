import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Ticket from './ticket'; // Importiere ticket.js

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Ticket />  {/* Inhalt von ticket.js wird hier eingefügt */}
    <App />
  </React.StrictMode>
);
