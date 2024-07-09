import React, { useState } from 'react';
import './ticket.css';

const airports = [
  { id: 'ZRH', name: 'Zurich' },
  { id: 'JFK', name: 'New York JFK' },
  { id: 'LAX', name: 'Los Angeles' },
  { id: 'LHR', name: 'London Heathrow' },
  { id: 'CDG', name: 'Paris Charles de Gaulle' },
  { id: 'FRA', name: 'Frankfurt' },
  { id: 'HND', name: 'Tokyo Haneda' },
  { id: 'DXB', name: 'Dubai' },
  { id: 'SIN', name: 'Singapore Changi' },
  { id: 'SYD', name: 'Sydney' }
];

const currencies = ['USD', 'EUR', 'CHF', 'GBP', 'JPY'];

const airlines = ['Swiss Air', 'Lufthansa', 'Emirates', 'Qatar Airways', 'Singapore Airlines'];

const getRandomPrice = () => {
  return (Math.random() * (1000 - 200) + 200).toFixed(2);
};

const getRandomDuration = () => {
  const hours = Math.floor(Math.random() * 12) + 1;
  const minutes = Math.floor(Math.random() * 60);
  return `${hours}h ${minutes}m`;
};

const getRandomAirline = () => {
  return airlines[Math.floor(Math.random() * airlines.length)];
};

const Ticket = () => {
  const [departure, setDeparture] = useState('');
  const [arrival, setArrival] = useState('');
  const [currency, setCurrency] = useState('');
  const [date, setDate] = useState('');
  const [results, setResults] = useState(null);

  const handleSearch = () => {
    const fakeResponse = {
      departure,
      arrival,
      price: `${getRandomPrice()} ${currency}`,
      duration: getRandomDuration(),
      airline: getRandomAirline(),
      date
    };
    setResults(fakeResponse);
  };

  return (
    <div className="ticket-container">
      <h1 className="title">Flight Search</h1>
      <div className="form-group">
        <label>
          Departure Airport:
          <select value={departure} onChange={(e) => setDeparture(e.target.value)}>
            <option value="">Select Airport</option>
            {airports.map(airport => (
              <option key={airport.id} value={airport.id}>{airport.name}</option>
            ))}
          </select>
        </label>
      </div>
      <div className="form-group">
        <label>
          Arrival Airport:
          <select value={arrival} onChange={(e) => setArrival(e.target.value)}>
            <option value="">Select Airport</option>
            {airports.map(airport => (
              <option key={airport.id} value={airport.id}>{airport.name}</option>
            ))}
          </select>
        </label>
      </div>
      <div className="form-group">
        <label>
          Currency:
          <select value={currency} onChange={(e) => setCurrency(e.target.value)}>
            <option value="">Select Currency</option>
            {currencies.map(cur => (
              <option key={cur} value={cur}>{cur}</option>
            ))}
          </select>
        </label>
      </div>
      <div className="form-group">
        <label>
          Date:
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
        </label>
      </div>
      <button onClick={handleSearch} className="search-button">Search Flights</button>
      {results && (
        <div className="results">
          <h2>Flight Results:</h2>
          <div className="flight">
            <h3>{results.airline}</h3>
            <div className="leg">
              <p><strong>Departure:</strong> {results.departure}</p>
              <p><strong>Arrival:</strong> {results.arrival}</p>
              <p><strong>Price:</strong> {results.price}</p>
              <p><strong>Duration:</strong> {results.duration}</p>
              <p><strong>Date:</strong> {results.date}</p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Ticket;
