import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './common.css';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

function ActuatorsPage() {
  const [actuators, setActuators] = useState([]);
  const [updatePeriod, setUpdatePeriod] = useState(5000); // Default to 5 seconds
  const [isLoading, setIsLoading] = useState(false);
  const [newActuator, setNewActuator] = useState({ name: '', state: false });

  useEffect(() => {
    const fetchActuators = async () => {
     setIsLoading(true);
      try {
        const response = await fetch(`${API_BASE_URL}/actuator/all`);
        const data = await response.json();
        setActuators(data);
      } catch (error) {
        console.error("Error fetching actuators:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchActuators();

    const interval = setInterval(fetchActuators, updatePeriod);
    return () => clearInterval(interval);
  }, [updatePeriod]);

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this actuator?')) {
      try {
        await fetch(`${API_BASE_URL}/actuator/${id}`, { method: 'DELETE' });
        setActuators(actuators.filter(actuator => actuator.id !== id));
      } catch (error) {
        console.error('Error deleting actuator:', error);
      }
    }
  };

  const handleSwitchActuatorState    = async (id) => {
      try {
        await fetch(`${API_BASE_URL}/actuator/switchstate/${id}`, { method: 'POST' });
      } catch (error) {
        console.error('Error deleting actuator:', error);
      }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewActuator({ ...newActuator, [name]: value });
  };

  const handleAddActuator = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`${API_BASE_URL}/actuator/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newActuator)
      });
      const data = await response.json();
      setActuators([...actuators, data]);
      setNewActuator({ name: '', state: false });
    } catch (error) {
      console.error('Error adding actuator:', error);
    }
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Actuators</h1>
      <div className="form-group">
        <label htmlFor="updatePeriod">Update Period:</label>
        <select
          id="updatePeriod"
          className="form-control"
          value={updatePeriod}
          onChange={(e) => setUpdatePeriod(Number(e.target.value))}
        >
          <option value={1000}>1 second</option>
          <option value={3000}>3 seconds</option>
          <option value={5000}>5 seconds</option>
          <option value={10000}>10 seconds</option>
          <option value={30000}>30 seconds</option>
        </select>
      </div>
      {isLoading ? (
        <p>Loading...</p>
      ) : (
        <ul className="list-group mb-4">
          {actuators.map(actuator => (
            <li key={actuator.id} className="list-group-item d-flex justify-content-between align-items-center">
              <div>
                <span
                  style={{
                    display: 'inline-block',
                    width: '10px',
                    height: '10px',
                    borderRadius: '50%',
                    backgroundColor: actuator.state ? 'green' : 'red',
                    marginRight: '10px'
                  }}
                ></span>
                {actuator.name}
              </div>
              <div>
                      {actuator.description}
              </div>
              <div>
                  <button className="btn btn-danger mr-2" onClick={() => handleDelete(actuator.id)}>Delete</button>
                  <button className={`btn ${actuator.state ? 'btn-success' : 'btn-secondary'}`} onClick={() => handleSwitchActuatorState(actuator.id)}>Switch State</button>
              </div>
            </li>
          ))}
        </ul>
      )}
      <form className="card p-4" onSubmit={handleAddActuator}>
        <h2 className="mb-4">Add New Actuator</h2>
        <div className="form-group">
          <label>Name:</label>
          <input
            type="text"
            name="name"
            className="form-control"
            value={newActuator.name}
            onChange={handleInputChange}
          />
        </div>
        <div className="form-group">
          <label>State:</label>
          <select
            name="state"
            className="form-control"
            value={newActuator.state}
            onChange={handleInputChange}
          >
            <option value={true}>On</option>
            <option value={false}>Off</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Add Actuator</button>
      </form>
    </div>
  );
}

export default ActuatorsPage;