import React, { useState, useEffect } from 'react';
import './trips.css';
import deleteIcon from './trash.svg';

const Trips = () => {
  const [travelData, setTravelData] = useState([]);
  const [newTrip, setNewTrip] = useState({
    name: '',
    location: '',
    description: '',
    image: ''
  });

  useEffect(() => {
    fetchTrips();
  }, []);

  const fetchTrips = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/trips/allTrips');
      if (response.ok) {
        const data = await response.json();
        setTravelData(data);
      } else {
        console.error('Failed to fetch trips');
      }
    } catch (error) {
      console.error('Error fetching trips:', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewTrip({
      ...newTrip,
      [name]: value
    });
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.onloadend = () => {
      setNewTrip({
        ...newTrip,
        image: reader.result.split(',')[1]
      });
    };
    reader.readAsDataURL(file);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem('authToken');

    try {
      const response = await fetch('http://localhost:8080/api/trips/createTrips', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(newTrip)
      });

      if (response.ok) {
        const createdTrip = await response.json();
        setTravelData([...travelData, createdTrip]);
        setNewTrip({
          name: '',
          location: '',
          description: '',
          image: ''
        });
      } else {
        console.error('Failed to create trip');
      }
    } catch (error) {
      console.error('Error creating trip:', error);
    }
  };

  const handleDelete = async (id) => {
    const token = localStorage.getItem('authToken');

    try {
      const response = await fetch(`http://localhost:8080/api/trips/removeSavedTrips/${id}`, {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (response.ok) {
        setTravelData(travelData.filter((trip) => trip.id !== id));
      } else {
        console.error('Failed to delete trip');
      }
    } catch (error) {
      console.error('Error deleting trip:', error);
    }
  };

  return (
    <div className="App">
      <div className="cards-container">
        {travelData.map((trip) => (
          <div key={trip.id} className="card">
            <img src={`data:image/png;base64,${trip.image}`} alt={trip.name} className="card-image" />
            <h2 className="card-name">{trip.name}</h2>
            <p className="card-location">{trip.location}</p>
            <p className="card-description">{trip.description}</p>
            <button onClick={() => handleDelete(trip.id)} className="delete-button">
              <img src={deleteIcon} alt="Loeschen" className="icon" />
            </button>
          </div>
        ))}
      </div>
      <form onSubmit={handleSubmit} className="new-trip-form">
        <h2>Neue Reise hinzufuegen</h2>
        <input
          type="text"
          name="name"
          placeholder="Name der Reise"
          value={newTrip.name}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="location"
          placeholder="Ort der Reise"
          value={newTrip.location}
          onChange={handleChange}
          required
        />
        <textarea
          name="description"
          placeholder="Beschreibung"
          value={newTrip.description}
          onChange={handleChange}
          required
        />
        <input
          type="file"
          name="image"
          onChange={handleImageChange}
          required
        />
        <button type="submit">Hinzufuegen</button>
      </form>
    </div>
  );
};

export default Trips;
