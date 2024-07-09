import React, { useState } from 'react';
import './trips.css';
import editIcon from './edit.svg';
import deleteIcon from './trash.svg';

const initialTravelData = [
  {
    id: 1,
    name: "Reise nach Paris",
    location: "Paris, Frankreich",
    description: "Erlebe die Stadt der Liebe mit ihren berühmten Sehenswürdigkeiten und dem einzigartigen Charme.",
    image: "https://via.placeholder.com/150"
  },
  {
    id: 2,
    name: "Safari in Kenia",
    location: "Nairobi, Kenia",
    description: "Erlebe die wilde Natur und die beeindruckende Tierwelt Afrikas auf einer unvergesslichen Safari.",
    image: "https://via.placeholder.com/150"
  },
  {
    id: 3,
    name: "Strandurlaub auf den Malediven",
    location: "Malediven",
    description: "Entspanne an weißen Sandstränden und genieße das kristallklare Wasser des Indischen Ozeans.",
    image: "https://via.placeholder.com/150"
  }
];

const Trips = () => {
  const [travelData, setTravelData] = useState(initialTravelData);
  const [newTrip, setNewTrip] = useState({
    name: '',
    location: '',
    description: '',
    image: 'https://via.placeholder.com/150'
  });
  const [isEditing, setIsEditing] = useState(false);
  const [currentTrip, setCurrentTrip] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNewTrip({
      ...newTrip,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isEditing) {
      setTravelData(
        travelData.map((trip) =>
          trip.id === currentTrip.id ? { ...newTrip, id: currentTrip.id } : trip
        )
      );
      setIsEditing(false);
      setCurrentTrip(null);
    } else {
      setTravelData([
        ...travelData,
        { ...newTrip, id: travelData.length + 1 }
      ]);
    }
    setNewTrip({
      name: '',
      location: '',
      description: '',
      image: 'https://via.placeholder.com/150'
    });
  };

  const handleEdit = (trip) => {
    setIsEditing(true);
    setCurrentTrip(trip);
    setNewTrip({
      name: trip.name,
      location: trip.location,
      description: trip.description,
      image: trip.image
    });
  };

  const handleDelete = (id) => {
    setTravelData(travelData.filter((trip) => trip.id !== id));
  };

  return (
    <div className="App">
      <div className="cards-container">
        {travelData.map((trip) => (
          <div key={trip.id} className="card">
            <img src={trip.image} alt={trip.name} className="card-image" />
            <h2 className="card-name">{trip.name}</h2>
            <p className="card-location">{trip.location}</p>
            <p className="card-description">{trip.description}</p>
            <button onClick={() => handleEdit(trip)} className="edit-button">
              <img src={editIcon} alt="Bearbeiten" className="icon" />
            </button>
            <button onClick={() => handleDelete(trip.id)} className="delete-button">
              <img src={deleteIcon} alt="Löschen" className="icon" />
            </button>
          </div>
        ))}
      </div>
      <form onSubmit={handleSubmit} className="new-trip-form">
        <h2>{isEditing ? 'Reise bearbeiten' : 'Neue Reise hinzufügen'}</h2>
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
        <button type="submit">{isEditing ? 'Aktualisieren' : 'Hinzufügen'}</button>
      </form>
    </div>
  );
}

export default Trips;
