import { CategoryService } from './../../services/category.service';
import { Category } from './../../interfaces/Category';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Category[] = [];

  constructor(private categoryService:CategoryService) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories():void{
    this.categoryService.getAll().subscribe(categories => this.categories = categories);
  }

}
