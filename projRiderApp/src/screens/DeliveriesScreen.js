


import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';
import { useFocusEffect } from '@react-navigation/native';
import Deliveries from '../components/Deliveries';
import AsyncStorage from '@react-native-async-storage/async-storage';


const DeliveriesScreen = ({ navigation }) => {

  const [deliveries, setDeliveries] = useState(null);
  const [error, setError] = useState(false);

  // fetch data from the API
  useFocusEffect(
    React.useCallback(() => {

      async function getRiderInfo() {
        let data = await AsyncStorage.getItem("user")
        data = JSON.parse(data)
        fetchData(data.token)
      }

      getRiderInfo()

      async function fetchData(token) {

        fetch(
          `http://localhost:6869/deliveries/deliveriesByStatus?status=PENDING`,
          {
            headers: { 
                       'Access-Control-Allow-Origin': '*',
                       'Authorization' : 'Bearer ' + token,
                      }
          }
        )
          .then(response => response.json())
          .then(data => {
            console.log("deliveries", data)
            setDeliveries(data)
          })
          .catch((reason) => {
            setError(true)
          })
      }

    }, [])
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Pendent Deliveries</Text>
      {
        deliveries == null ?

          <Text>No deliveries to accept...</Text>

          :

          <Deliveries deliveries={deliveries}  navigation={navigation}/>

        }
    </View >

  );
};

export default DeliveriesScreen;

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
