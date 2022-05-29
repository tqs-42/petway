import { Product } from './Product';
export interface Order {
  id: number
  price: number
  states: { datetime: string, state: { id: number, name: string } }[]
  items: { price: number, amount: number, product: Product }[]
}