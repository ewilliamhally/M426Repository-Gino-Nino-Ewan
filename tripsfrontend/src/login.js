import React, { useState } from 'react';
import './login.css';

const Login = () => {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isRegistering, setIsRegistering] = useState(false);
  const [loggedIn, setLoggedIn] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    
    try {
      const response = await fetch('http://localhost:8080/api/auth/authenticate', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      });

      if (response.ok) {
        const data = await response.json();
        setLoggedIn(true);
        alert('Erfolgreich eingeloggt');
      } else {
        alert('Ungültige Anmeldeinformationen');
      }
    } catch (error) {
      alert('Ein Fehler ist aufgetreten');
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    
    try {
      const response = await fetch('http://localhost:8080/api/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          firstname: firstname,
          lastname: lastname,
          email: email,
          password: password,
        }),
      });

      if (response.ok) {
        alert('Erfolgreich registriert');
      } else {
        alert('Registrierung fehlgeschlagen');
      }
    } catch (error) {
      alert('Ein Fehler ist aufgetreten');
    }
  };

  return (
    <div className="login-container">
      <h1>{isRegistering ? 'Registrieren' : 'Login'}</h1>
      <form onSubmit={isRegistering ? handleRegister : handleLogin} className="login-form">
        {isRegistering && (
          <>
            <div className="form-group">
              <label htmlFor="firstname">Vorname</label>
              <input
                type="text"
                id="firstname"
                placeholder="Vorname"
                value={firstname}
                onChange={(e) => setFirstname(e.target.value)}
                className="login-input"
              />
            </div>
            <div className="form-group">
              <label htmlFor="lastname">Nachname</label>
              <input
                type="text"
                id="lastname"
                placeholder="Nachname"
                value={lastname}
                onChange={(e) => setLastname(e.target.value)}
                className="login-input"
              />
            </div>
          </>
        )}
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="login-input"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="login-input"
          />
        </div>
        <button
          type="submit"
          className={`login-button ${loggedIn ? 'logged-in' : ''}`}
        >
          {loggedIn ? 'Eingeloggt' : (isRegistering ? 'Registrieren' : 'Login')}
        </button>
      </form>
      <button
        type="button"
        onClick={() => setIsRegistering(!isRegistering)}
        className="toggle-button"
      >
        {isRegistering ? 'Zum Login wechseln' : 'Zur Registrierung wechseln'}
      </button>
    </div>
  );
};

export default Login;
