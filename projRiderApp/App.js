import DeliveryScreen from './src/screens/DeliveryScreen';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import DeliveriesScreen from "./src/screens/DeliveriesScreen";
import HistoryScreen from "./src/screens/HistoryScreen";
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import  LoginScreen  from "./src/screens/LoginScreen";
import { Ionicons } from '@expo/vector-icons';

 
const Tab = createBottomTabNavigator();

function Home({ navigation }) {
  return(
    <Tab.Navigator initialRouteName='Deliveries' screenOptions={{ headerShown: false }}  >
      <Tab.Screen name="Deliveries" component={DeliveriesScreen} navigation={navigation} options={{
      tabBarLabel: 'Deliveries',
      tabBarIcon: ({ color, size }) => (
        <Ionicons name="alert" color={color} size={size} />
      ),
      }} 
      />
      <Tab.Screen name="History" component={HistoryScreen} navigation={navigation} options={{
      tabBarLabel: 'History',
      tabBarIcon: ({ color, size }) => (
        <Ionicons name="archive" color={color} size={size} />
      ),
      }} 
      />
    </Tab.Navigator>
  )
}

const Stack = createNativeStackNavigator();

export default function App() {   
  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Login"  >
        <Stack.Screen name="Login" component={LoginScreen} options={{ headerShown: false }}/>
        <Stack.Screen name="Home" component={Home} options={{ headerShown: false }}/>
        <Stack.Screen name="Delivery" component={DeliveryScreen} options={{ headerShown: true, title: 'Delivery Info'}}/>
      </Stack.Navigator>
    </NavigationContainer>
  );  
}  

