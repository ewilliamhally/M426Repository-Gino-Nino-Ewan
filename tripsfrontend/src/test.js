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

const Ticket = () => {
  const [departure, setDeparture] = useState('');
  const [arrival, setArrival] = useState('');
  const [currency, setCurrency] = useState('');
  const [date, setDate] = useState('');
  const [query, setQuery] = useState('');

  const handleSearch = () => {
    const apiKey = '61d98334a653a743d9abecac1bd9377a55014f0831990a2f1285070f2d8440f2';
    const apiUrl = `https://serpapi.com/search?engine=google_flights&departure_id=${departure}&arrival_id=${arrival}&type=2&outbound_date=${date}&api_key=${apiKey}&currency=${currency}`;
    setQuery(apiUrl);
  };

  return (
    <div className="ticket-container">
      <h1>Flight Search</h1>
      <div>
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
      <div>
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
      <div>
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
      <div>
        <label>
          Date:
          <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
        </label>
      </div>
      <button onClick={handleSearch}>Search Flights</button>
      {query && (
        <div className="results">
          <h2>Generated Query:</h2>
          <pre>{query}</pre>
        </div>
      )}
    </div>
  );
};

export default Ticket;
