import React, { useEffect, useState } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './common.css';
import { FaCog, FaTrash } from 'react-icons/fa';

function Settings() {
  const [settings, setSettings] = useState([]);
  const [selectedSetting, setSelectedSetting] = useState(null);
  const [formValues, setFormValues] = useState({ minValue: '', maxValue: '', maxRecordsStored: '' });

  useEffect(() => {
    fetch(`/settings/all`)
      .then(response => response.json())
      .then(data => setSettings(data))
      .catch(error => console.error("Error fetching settings:", error));
  }, []);

  const handleIconClick = (setting) => {
    setSelectedSetting(setting);
    setFormValues({
      minValue: setting.minValue,
      maxValue: setting.maxValue,
      maxRecordsStored: setting.maxRecordsStored
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`/settings/set`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ ...selectedSetting, ...formValues })
    })
      .then(response => response.json())
      .then(data => {
        setSettings(settings.map(s => s.id === data.id ? data : s));
        setSelectedSetting(null);
      })
      .catch(error => console.error("Error updating settings:", error));
  };

  const handleDeleteClick = (id) => {
    if (window.confirm(`Do you really want to delete all data from sensor with id ${id}?`)) {
      fetch(`/data/all/${id}`, {
        method: "DELETE",
      })
        .then(() => {
          setSettings(settings.filter(s => s.id !== id));
        })
        .catch(error => console.error("Error deleting setting:", error));
    }
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Sensor Settings</h1>
      <ul className="list-group mb-4">
        {settings.map(setting => (
          <li key={setting.id} className="list-group-item d-flex justify-content-between align-items-center">
            <div>
              <h5>{setting.sensor.type}</h5>
              <p>Max Records Stored: {setting.maxRecordsStored}</p>
            </div>
            <div>
              <FaCog className="mr-3" onClick={() => handleIconClick(setting)} />
              <FaTrash onClick={() => handleDeleteClick(setting.id)} />
            </div>
          </li>
        ))}
      </ul>
      {selectedSetting && (
        <form className="card p-4" onSubmit={handleSubmit}>
          <h2 className="mb-4">Edit Sensor {selectedSetting.sensorId} Settings</h2>
          <div className="form-group">
            <label>Max Records Stored:</label>
            <input type="number" name="maxRecordsStored" className="form-control" value={formValues.maxRecordsStored} onChange={handleInputChange} />
          </div>
          <button type="submit" className="btn btn-primary mr-2">Save</button>
          <button type="button" className="btn btn-secondary" onClick={() => setSelectedSetting(null)}>Cancel</button>
        </form>
      )}
    </div>
  );
}

export default Settings;