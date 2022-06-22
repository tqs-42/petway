import { StatusBar } from "expo-status-bar";
import React, { useState } from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
  StyleSheet,
  Text,
  View,
  TextInput,
  Alert,
  TouchableOpacity,
} from "react-native";
import { useFocusEffect } from '@react-navigation/native';

const LoginScreen = ({ navigation }) => {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState(false);
  const [user, setUser] = useState(null)

  const login = () => {
    fetch(`http://localhost:6869/login`, {
      method: "POST",
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        email: email,
        password: password
      })
    })
    .then((response) => {
        if (response.ok) {
          let data = response.json()
          data.then((data) => {
            AsyncStorage.setItem("user", JSON.stringify(data))
            console.log(JSON.stringify(data))
            setUser(data)
            navigation.navigate('Home')
          }
          )
        } else {
          setError(true)
        }
      })
      .catch(() => {
        setError(true)
      })
  }

  return (
    <View style={styles.container}>

      <Text style={styles.title}>EasyDeliver</Text>

      <StatusBar style="auto" />

      <View style={styles.inputView}>
        <TextInput
          style={styles.TextInput}
          placeholder="Email"
          placeholderTextColor="#003f5c"
          value={email}
          onChangeText={(email) => setEmail(email)}
        />
      </View>

      <View style={styles.inputView}>
        <TextInput
          style={styles.TextInput}
          placeholder="Password"
          placeholderTextColor="#003f5c"
          secureTextEntry={true}
          value={password}
          onChangeText={(password) => setPassword(password)}
        />
      </View>

      
        {
        error == true ? 
        <Text>ERROR: Invalid credentials</Text> :
        <Text></Text>
        }

      <TouchableOpacity style={styles.loginBtn} onPress={() => login()}>
        <Text style={styles.loginText}>LOGIN</Text>
      </TouchableOpacity>
    </View>
  );

}

export default LoginScreen;

const styles = StyleSheet.create({

  container: {
    flex: 1,
    backgroundColor: "#008080",
    alignItems: "center",
    justifyContent: "center",
  },

  title: {
    fontSize: 32,
    fontFamily: 'lucida grande',
    fontWeight: "bold",
    color: "#CCCCFF",
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 100,
  },

  inputView: {
    backgroundColor: "#CCCCFF",
    borderRadius: 30,
    width: "70%",
    height: 45,
    marginBottom: 20,

    alignItems: "center",
  },

  TextInput: {
    height: 50,
    flex: 1,
    padding: 10,
    marginLeft: 20,
  },

  forgot_button: {
    height: 30,
    marginBottom: 30,
  },

  loginBtn: {
    width: "80%",
    borderRadius: 25,
    height: 50,
    alignItems: "center",
    justifyContent: "center",
    marginTop: 40,
    backgroundColor: "#89CFF0",
  },
});