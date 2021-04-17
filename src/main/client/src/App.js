import React, { Component } from 'react';
import './App.css';

class App extends Component {
  state = {
    isLoading: true,
    isError: false,
    stations: [],
    stationsUpdatedAt: new Date(0)
  };

  async updateBikeList() {
    try {
      const response = await fetch('http://localhost:8080/api/bikeStation/status');
      if (response.status === 200) {
        const body = await response.json();
        this.setState({
          stations: body.stations,
          stationsUpdatedAt: new Date(body.updatedAt),
          isError: false
        });

        //Auto refresh bike list
        setTimeout(() => this.updateBikeList(), body.ttlInSeconds * 1000);
      } else {
        this.setState({isError: true});
      }
    }
    catch {
      this.setState({isError: true});
    }
  }

  async componentDidMount() {
    await this.updateBikeList();
    this.setState({isLoading: false});
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
      <table className="table table-striped table-hover">
        <thead>
          <tr>
            <th scope="col">Lokasjon</th>
            <th scope="col">Ledige sykler</th>
            <th scope="col">Ledige låser</th>
          </tr>
        </thead>
        <tbody>
          {stations.map(station =>
            <tr key={station.id}>
              <td>{station.name}</td>
              <td>{station.availableBikeCount}</td>
              <td>{station.availableLockCount}</td>
            </tr>)
          }
        </tbody>
      </table>
    </div>
  }
}

export default App;
