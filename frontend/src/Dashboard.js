import React, { useEffect, useState } from "react";
import { Scatter } from "react-chartjs-2";
import 'chart.js/auto';
import 'chartjs-adapter-date-fns';
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
  const [updatePeriod, setUpdatePeriod] = useState(5000);
  const oneDayBeforeNow = new Date(Date.now() - 86400000).toISOString().slice(0, 16);
  const [fromTime, setFromTime] = useState(oneDayBeforeNow);
  const [toTime, setToTime] = useState('now');
  const [expandedChart, setExpandedChart] = useState(null);

  useEffect(() => {
    fetch(`/sensor/all`)
      .then(response => response.json())
      .then(data => {
        console.log("Fetched sensors:", data);
        setSensors(data);
      })
      .catch(error => console.error("Error fetching sensors:", error));
  }, []);

  useEffect(() => {
    const fetchData = () => {
      if (sensors.length > 0) {
        sensors.forEach(sensor => {
          const to = toTime === 'now' ? getLocalTimeString() : toTime;
          const from = fromTime || new Date(0).toISOString();
          fetch(`/data/all/${sensor.id}/range?from=${from}&to=${to}`)
            .then(response => response.json())
            .then(data => {
              console.log(`Fetched data for sensor ${sensor.id}:`, data);
              setSensorData(prevData => {
                const newData = prevData.filter(d => d.sensorId !== sensor.id);
                return [...newData, { sensorId: sensor.id, data }];
              });
            })
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

    const data = sensor.data.map(entry => ({
      x: new Date(entry.timestamp).getTime(),
      y: entry.value
    }));

    console.log(`Chart data for sensor ${sensorId}:`, data);

    return {
      datasets: [
        {
          label: `Sensor ${sensorId} Data`,
          data,
          backgroundColor: 'rgba(75,192,192,0.2)',
          borderColor: 'rgba(75,192,192,1)',
        },
      ],
    };
  };

  const chartOptions = {
    scales: {
      x: {
        type: 'time',
        time: {
          unit: 'minute',
          tooltipFormat: 'PP pp',
          displayFormats: {
            minute: 'HH:mm',
          },
        },
        min: new Date(fromTime).getTime(),
        max: toTime === 'now' ? Date.now() : new Date(toTime).getTime(),
      },
    },
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

  const handleChartClick = (sensorId) => {
    setExpandedChart(expandedChart === sensorId ? null : sensorId);
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Sensor Dashboard</h1>
      <div className="row">
        <div className="col-md-4">
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
        </div>
        <div className="col-md-4">
          <div className="form-group">
            <label htmlFor="fromTime">From: </label>
            <input type="datetime-local" id="fromTime" className="form-control" value={fromTime} onChange={handleFromTimeChange} />
          </div>
        </div>
        <div className="col-md-4">
          <div className="form-group">
            <label htmlFor="toTime">To: </label>
            <input type="datetime-local" id="toTime" className="form-control" value={toTime} onChange={handleToTimeChange} min={fromTime} />
            <button
              id="toTime"
              className={`form-control ${toTime === 'now' ? 'btn-primary border-success' : 'btn-secondary'}`}
              onClick={() => setToTime('now')}
            >
              Now
            </button>
          </div>
        </div>
      </div>
      <div className="row">
        {sensors.map(sensor => (
          <div key={sensor.id} className={`col-md-${expandedChart === sensor.id ? '12' : '6'} mb-4`}>
            <div className="card" onClick={() => handleChartClick(sensor.id)}>
              <div className="card-body">
                <h5 className="card-title">Sensor {sensor.type}</h5>
                <Scatter data={getChartData(sensor.id)} options={chartOptions} />
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Dashboard;