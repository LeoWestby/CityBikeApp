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
    if (response.status === 200) {
      const body = await response.json();
      this.setState({
        stations: body.stations,
        stationsUpdatedAt: new Date(body.updatedAt),
        isLoading: false,
        isError: false
      });
    } else {
      this.setState({isLoading: false, isError: true});
    }
  }


  getFormattedTime = date => date.getHours().toString().padStart(2, "0") + ":" +
                             date.getMinutes().toString().padStart(2, "0") + ":" +
                             date.getSeconds().toString().padStart(2, "0");

  render() {
    const {isLoading, stations, stationsUpdatedAt, isError} = this.state;
    if (isLoading) {
      return <h2>Loading...</h2>
    }

    if (isError) {
      return <h2>Bysykkel er dessverre for øyeblikket utilgjengelig. Besøk oss gjerne igjen litt senere!</h2>
    }

    return <div className="App">
      <h2>Liste over stasjoner (sist oppdatert {this.getFormattedTime(stationsUpdatedAt)})</h2>
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
            <td>{station.name}</td>
            <td>{station.availableBikeCount}</td>
            <td>{station.availableLockCount}</td>
          </tr>)
        }
      </table>
    </div>
  }
}

export default App;
