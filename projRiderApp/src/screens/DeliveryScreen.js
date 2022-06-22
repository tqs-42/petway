


import React from 'react';
import { View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';

const DeliveryScreen = ({ navigation, route }) => {

  console.log("Aqui")

  const { id } = route.params.id;

  return (
    <View style={styles.container}>
        <Text style={styles.title}>Delivery {id}</Text>
    </View>

  );
};


export default DeliveryScreen;

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
