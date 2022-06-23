


import React from 'react';
import { Text, StyleSheet, TouchableOpacity, FlatList } from 'react-native';

const Deliveries = ({navigation, deliveries}) => {

  console.log("DELIVERIES", deliveries)

  return (
    deliveries.map((item,key) => (
      <TouchableOpacity style={styles.row}  key={key} onPress={() => navigation.navigate('Delivery', { delivery : item })}>
      <Text>Delivery {item.id}</Text>
      <Text>{item.store_name}</Text>
      <Text>{item.store_address}</Text>
    </TouchableOpacity>
    )
  ))
}

export default Deliveries;

const styles = StyleSheet.create({
  row: {
    backgroundColor: "#4169e1",
    width: "80%",
    height: 100,
    color: "#dddddd",
    textAlign: "center",
    justifyContent: "center",
    marginVertical: 5,
    padding: 5,
    borderRadius: 5
  }
});
