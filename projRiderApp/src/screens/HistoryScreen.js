


import React from 'react';
import { View, Text, Button, StyleSheet, StatusBar } from 'react-native';

const closedDeliveries = [
  {
    id: 1,
    color: "blue",
    text: "Request n2"
  },
  {
    id: 2,
    color: "yellow",
    text: "Request n11"
  },
];

const HistoryScreen = () => {

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Closed Deliveries</Text>
      {closedDeliveries.map((prop, key) => {
        return (
          <Text style={{backgroundColor : prop.color}}>{prop.text}</Text>
        );
      })}
    </View>

  );
};


export default HistoryScreen;

const styles = StyleSheet.create({
  title: {
    fontSize: 32
  },
  container: {
      flex: 1,
      padding: 20,
      paddingTop: 20,
      paddingBottom: 20,
      backgroundColor: '#fff',
      alignItems: 'center',
      justifyContent: 'center',
  }
});
