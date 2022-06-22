import { Product } from './Product';
export interface Order {
  id: number
  requestId: number
  status: string  
  eventDate: string  

  // items: { price: number, amount: number, product: Product }[]
}
