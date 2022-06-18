import { Category } from './Category';
import { Store } from './Store';
export interface Product {
  id: number
  name: string
  description: string
  price: number
  stock: number
  category: Category
  store : Store 
  image : string
}
