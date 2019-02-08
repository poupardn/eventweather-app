import React, { Component } from 'react';
import Main from './Main';
import axios from 'axios';
import { throws } from 'assert';
import { Header, Table, Menu } from 'semantic-ui-react';
import { properties } from '../properties/properties';

class EventsList extends Component {

    constructor(props) {
        super(props);
        // Don't call this.setState() here!
        this.state = {
            events: null,
            activeItem: "10mi"
        };
        this.handleItemClick = this.handleItemClick.bind(this);
        this.getEvents = this.getEvents.bind(this);
    }

    handleItemClick(e, { name }) {
        e.preventDefault();
        console.log("handled");
        this.setState({ activeItem: name });
        this.getEvents(name);
        this.forceUpdate();
    }

    getEvents(within) {
        console.log(properties.apiUrl);
        axios.get(properties.apiUrl + "/eventweather", {
            params: {
                latitude: this.props.latitude,
                longitude: this.props.longitude,
                within: (within ? within : "10mi")
            }
        })
            .then(res => {
                const events = res.data;
                this.setState({ events: events });
            }).catch(error => {
                console.log(error)
            });
    }

    componentDidMount() {
        console.log("mounted");
        this.getEvents(this.state.activeItem);
    }


    render() {
        const activeItem = this.state.activeItem;

        return (<div>
            <h1>Today's Events Nearby</h1>
            {this.state.events ? (
                <Table celled padded>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell colSpan="8" textAlign="center"><h2>Distance:</h2> <br />
                                <Menu compact>
                                    <Menu.Item name='10mi' active={activeItem === '10mi'} onClick={this.handleItemClick}>
                                        10mi
                                    </Menu.Item>
                                    <Menu.Item name='15mi' active={activeItem === '15mi'} onClick={this.handleItemClick}>
                                        15mi
                                    </Menu.Item>
                                    <Menu.Item name='20mi' active={activeItem === '20mi'} onClick={this.handleItemClick}>
                                        20mi
                                    </Menu.Item>
                                </Menu>
                            </Table.HeaderCell>
                        </Table.Row>
                        <Table.Row>
                            <Table.HeaderCell>Event Name</Table.HeaderCell>
                            <Table.HeaderCell>Link to Description</Table.HeaderCell>
                            <Table.HeaderCell>Address</Table.HeaderCell>
                            <Table.HeaderCell>Start Time</Table.HeaderCell>
                            <Table.HeaderCell>Forecast Summary</Table.HeaderCell>
                            <Table.HeaderCell>Temperature</Table.HeaderCell>
                            <Table.HeaderCell>Feels Like</Table.HeaderCell>
                            <Table.HeaderCell>Rain/Snow Chance</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {this.state.events.map(currentItem => (
                            <Table.Row>
                                <Table.Cell>{currentItem.eventName}</Table.Cell>
                                <Table.Cell><a href={currentItem.url} target="_blank">Click for description</a></Table.Cell>
                                <Table.Cell>{currentItem.address}</Table.Cell>
                                <Table.Cell>{new Date(currentItem.eventStart).toLocaleTimeString()}</Table.Cell>
                                <Table.Cell>{currentItem.summary}</Table.Cell>
                                <Table.Cell>{currentItem.temp}{'\u00b0'} F</Table.Cell>
                                <Table.Cell>{currentItem.feelsLike} {'\u00b0'} F</Table.Cell>
                                <Table.Cell>{currentItem.precChance}%</Table.Cell>
                            </Table.Row>
                        ))}
                    </Table.Body>
                </Table>

            ) : (<div>No events to display</div>)}
        </div>
        );
    }
}

export default EventsList;