


import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet, StatusBar } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useFocusEffect } from '@react-navigation/native';
import { set } from 'react-native-reanimated';


const DeliveryScreen = ({ navigation, route }) => {

  const [info, setInfo] = useState(null);
  const [user, setUser] = useState(null);
  const [result, setResult] = useState(null);
  
  const delivery  = route.params.delivery;

  useFocusEffect(
    React.useCallback(() => {

      async function getRiderInfo() {
        let data = await AsyncStorage.getItem("user")
        data = JSON.parse(data)

        setUser(data)
      }

      getRiderInfo()
    }, [])
  );

  useEffect(()=>{
    if (user == null)
      return
      
    getData()
  }, user)

  const getData = () => {
    let result = []
    fetch(
      `http://localhost:6869/deliveries/delivery?id=`+delivery.id,
      {
        headers: { 
                   'Access-Control-Allow-Origin': '*',
                   'Authorization' : 'Bearer ' + user.token,
                  }
      }
    )
      .then(response => response.json())
      .then(data => {
        console.log("deliveries PEDIDOOOOOO", data)
        let array = []
        array.push(data)
        array.map((info) => {
          result.push(<Text style={styles.title}>Delivery No.: {info.delivery.id}</Text>)
          result.push(<Text style={styles.info}>Store: {info.delivery.store.name}</Text>)
          result.push(<Text style={styles.info}>Store address: {info.delivery.store.address}</Text>)
          result.push(<Text style={styles.info}>Status: {info.event.status}</Text>)
          result.push(<Text style={styles.info}>Date: {info.event.timestamp}</Text>)

          if (info.event.status == "PENDING") result.push(<Button style={styles.info} onPress={() => changeStatus("PROCESSING")} title="ACCEPT"/>)
          if (info.event.status == "PROCESSING") result.push(<Button style={styles.info} onPress={() => changeStatus("DELIVERING")} title="DELIVERING"/>)
          if (info.event.status == "DELIVERING") result.push(<Button style={styles.info} onPress={() => changeStatus("DELIVERED")} title="DELIVERED"/>)
          
        })

        setResult(result)


      })
  }

  async function changeStatus(newStatus) {

    console.log("new status", newStatus)
    console.log("TOKEN", user.token)

    const httpOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', 'Authorization' : 'Bearer ' + user.token },
      body: JSON.stringify({ id : delivery.id, status : newStatus, email : user.email })
    };

    fetch("http://localhost:6869/deliveries/deliveryUpdate", httpOptions)
    .then(response => response.json())
    .then(data => {
      getData()
    })

  }

  return (
    <View style={styles.container}>

      {result}
      
    </View>
  );
};


export default DeliveryScreen;

const styles = StyleSheet.create({
  title: {
    fontSize: 32,
    marginBottom: 20,
  },
  info: {
    fontSize: 15,
    alignItems: 'center',
    justifyContent: 'center',
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
