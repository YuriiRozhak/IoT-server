import React, { useEffect, useState } from "react";
      import { Line } from "react-chartjs-2";
      import 'chart.js/auto';
      import 'bootstrap/dist/css/bootstrap.min.css';
      import './common.css';


      function getLocalTimeString() {
        const now = new Date();
        return now.getFullYear() + "-" +
          String(now.getMonth() + 1).padStart(2, '0') + "-" +
          String(now.getDate()).padStart(2, '0') + "T" +
          String(now.getHours()).padStart(2, '0') + ":" +
          String(now.getMinutes()).padStart(2, '0') + ":" +
          String(now.getSeconds()).padStart(2, '0') + "." +
          String(now.getMilliseconds()).padStart(3, '0');
      }


      function Dashboard() {
        const [sensorData, setSensorData] = useState([]);
        const [sensors, setSensors] = useState([]);
        const [updatePeriod, setUpdatePeriod] = useState(10000); // Default update period: 5000ms (5 seconds)
        const [fromTime, setFromTime] = useState('');
        const [toTime, setToTime] = useState('now');

        useEffect(() => {
          fetch("http://localhost:8080/sensor/all")
            .then(response => response.json())
            .then(data => setSensors(data))
            .catch(error => console.error("Error fetching sensors:", error));
        }, []);

        useEffect(() => {
          const fetchData = () => {
            if (sensors.length > 0) {
              sensors.forEach(sensor => {
                const to = toTime === 'now' ? getLocalTimeString() : toTime;
                const from = fromTime || new Date(0).toISOString(); // Default to epoch if fromTime is empty
                fetch(`http://localhost:8080/data/all/${sensor.id}/range?from=${from}&to=${to}`)
                  .then(response => response.json())
                  .then(data => setSensorData(prevData => {
                    const newData = prevData.filter(d => d.sensorId !== sensor.id);
                    return [...newData, { sensorId: sensor.id, data }];
                  }))
                  .catch(error => console.error(`Error fetching data for sensor ${sensor.id}:`, error));
              });
            }
          };

          fetchData();
          const interval = setInterval(fetchData, updatePeriod);
          return () => clearInterval(interval);
        }, [sensors, updatePeriod, fromTime, toTime]);

        const getChartData = (sensorId) => {
          const sensor = sensorData.find(s => s.sensorId === sensorId);
          if (!sensor) return { labels: [], datasets: [] };

          const labels = sensor.data.map(entry => new Date(entry.timestamp).toLocaleString());
          const data = sensor.data.map(entry => entry.value);

          return {
            labels,
            datasets: [
              {
                label: `Sensor ${sensorId} Data`,
                data,
                fill: false,
                backgroundColor: 'rgba(75,192,192,0.2)',
                borderColor: 'rgba(75,192,192,1)',
              },
            ],
          };
        };

        const handleUpdatePeriodChange = (event) => {
          setUpdatePeriod(Number(event.target.value));
        };

        const handleFromTimeChange = (event) => {
          setFromTime(event.target.value);
        };

        const handleToTimeChange = (event) => {
          setToTime(event.target.value);
        };

        return (
          <div className="container mt-4">
            <h1 className="mb-4">Sensor Dashboard</h1>
            <div className="form-group">
              <label htmlFor="updatePeriod">Update Period: </label>
              <select id="updatePeriod" className="form-control" value={updatePeriod} onChange={handleUpdatePeriodChange}>
                <option value={1000}>1 second</option>
                <option value={5000}>5 seconds</option>
                <option value={10000}>10 seconds</option>
                <option value={30000}>30 seconds</option>
                <option value={60000}>1 minute</option>
              </select>
            </div>
            <div className="form-group">
              <label htmlFor="fromTime">From: </label>
              <input type="datetime-local" id="fromTime" className="form-control" value={fromTime} onChange={handleFromTimeChange} />
            </div>
            <div className="form-group">
              <label htmlFor="toTime">To: </label>
              <select id="toTime" className="form-control" value={toTime} onChange={handleToTimeChange}>
                <option value="now">Now</option>
                <option value={new Date().toISOString()}>{new Date().toLocaleString()}</option>
              </select>
            </div>
            <div className="row">
              {sensors.map(sensor => (
                <div key={sensor.id} className="col-md-6 mb-4">
                  <div className="card">
                    <div className="card-body">
                      <h5 className="card-title">Sensor {sensor.type}</h5>
                      <Line data={getChartData(sensor.id)} />
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        );
      }

      export default Dashboard;