import React, { useState } from 'react';
import './login.css';

const apiRequest = async (url, method, body) => {
  const headers = {
    'Content-Type': 'application/json',
  };

  const response = await fetch(url, {
    method: method,
    headers: headers,
    body: JSON.stringify(body),
  });

  const data = await response.json();
  return { status: response.status, data };
};

const Login = () => {
  const [firstname, setFirstname] = useState('');
  const [lastname, setLastname] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isRegistering, setIsRegistering] = useState(false);
  const [loggedIn, setLoggedIn] = useState(false);

  const saveToken = (token) => {
    localStorage.setItem('authToken', token);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    const requestBody = {
      email: email,
      password: password,
    };
    const requestMethod = 'POST';
    const requestUrl = 'http://localhost:8080/api/auth/authenticate';

    try {
      const { status, data } = await apiRequest(requestUrl, requestMethod, requestBody);

      if (status === 200) {
        saveToken(data.token);
        setLoggedIn(true);
        alert('Erfolgreich eingeloggt');
      } else {
        console.error('Login failed:', data);
        alert('Ungueltige Anmeldeinformationen');
      }
    } catch (error) {
      console.error('Error during login:', error);
      alert(`Ein Fehler ist aufgetreten:\n\nMethode: ${requestMethod}\nURL: ${requestUrl}\nRequest Body: ${JSON.stringify(requestBody, null, 2)}\n\nFehlermeldung: ${error}`);
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    const requestBody = {
      firstname: firstname,
      lastname: lastname,
      email: email,
      password: password,
    };
    const requestMethod = 'POST';
    const requestUrl = 'http://localhost:8080/api/auth/register';

    try {
      const { status, data } = await apiRequest(requestUrl, requestMethod, requestBody);

      if (status === 200) {
        saveToken(data.token);
        alert('Erfolgreich registriert');
      } else {
        console.error('Registration failed:', data);
        alert('Registrierung fehlgeschlagen');
      }
    } catch (error) {
      console.error('Error during registration:', error);
      alert(`Ein Fehler ist aufgetreten:\n\nMethode: ${requestMethod}\nURL: ${requestUrl}\nRequest Body: ${JSON.stringify(requestBody, null, 2)}\n\nFehlermeldung: ${error}`);
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
