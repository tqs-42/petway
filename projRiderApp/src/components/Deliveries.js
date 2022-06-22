


import React from 'react';
import { Text, StyleSheet, TouchableOpacity, FlatList } from 'react-native';

const Deliveries = ({ items }) => {

  const renderItem = ({item}) => {
    return (
      <TouchableOpacity style={styles.row} onPress={() => navigation.navigate('Delivery', { id: item.id })}>
        <Text>Delivery {item.id}</Text>
        <Text>Store {item.store.name}</Text>
      </TouchableOpacity>
    )
  };

  return (
    <FlatList
      data={items}
      renderItem={renderItem}
      keyExtractor={item=>item.id}
    />
  )
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
