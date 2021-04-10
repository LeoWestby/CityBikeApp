import React, { Component } from 'react';
import './App.css';

class App extends Component {
  state = {
    isLoading: true,
    isError: false,
    stations: [],
    stationsUpdatedAt: new Date(0)
  };

  async componentDidMount() {
    const response = await fetch('http://localhost:8080/api/bikeStatus');
    if (response.status == 200) {
      const body = await response.json();
      const updatedAt = new Date(body.lastUpdated * 1000);
      this.setState({
        stations: body.data.stations,
        stationsUpdatedAt: updatedAt,
        isLoading: false,
        isError: false
      });
    } else {
      this.setState({isLoading: false, isError: true});
    }
  }

  render() {
    const {isLoading, stations, stationsUpdatedAt, isError} = this.state;
    if (isLoading) {
      return <h2>Loading...</h2>
    }

    if (isError) {
      return <h2>Bysykkel er dessverre for øyeblikket utilgjengelig. Besøk oss gjerne igjen litt senere!</h2>
    }

    return <div className="App">
      <h2>Liste over stasjoner</h2>
      <table className="table table-hover">
        <thead>
          <tr>
            <th scope="col">Lokasjon</th>
            <th scope="col">Ledige sykler</th>
            <th scope="col">Ledige låser</th>
          </tr>
        </thead>
        {stations.map(station =>
          <tr key={station.id}>
            <td>{station.station_id}</td>
            <td>{station.num_bikes_available}</td>
            <td>{station.num_docks_available}</td>
          </tr>)
        }
      </table>
    </div>
  }
}

export default App;
