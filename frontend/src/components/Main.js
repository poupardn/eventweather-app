import React from 'react';
import { geolocated } from 'react-geolocated';
import EventsList from './EventsList';

class Main extends React.Component {
  render() {
    return !this.props.isGeolocationAvailable
      ? <div>Your browser does not support Geolocation</div>
      : !this.props.isGeolocationEnabled
        ? <div><h1>Geolocation is not enabled</h1></div>
        : this.props.coords
          ? <EventsList latitude={this.props.coords.latitude} longitude={this.props.coords.longitude} />
          : <div><h1>Getting the location data&hellip;</h1></div>;
  }
}

export default geolocated({
  positionOptions: {
    enableHighAccuracy: false,
  },
  watchPosition: false,
  userDecisionTimeout: 10000,
})(Main);