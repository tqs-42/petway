import { Category } from './Category';
import { Store } from './Store';
export interface Product {
  id: number
  name: string
  description: string
  image: string
  price: number
  stock: number
  store: Store
  category: Category;
}
