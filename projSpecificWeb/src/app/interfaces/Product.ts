import { Category } from './Category';
export interface Product {
  id: number
  name: string
  description: string
  price: number
  category: Category
  isActive: boolean;
}
