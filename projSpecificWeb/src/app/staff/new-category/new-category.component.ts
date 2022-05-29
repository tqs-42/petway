import { Router } from '@angular/router';
import { CategoryService } from './../../services/category.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-category',
  templateUrl: './new-category.component.html',
  styleUrls: ['./new-category.component.css']
})
export class NewCategoryComponent implements OnInit {
  category: {name: string} = {name: ""}

  constructor(private categoryService:CategoryService, private router: Router) { }

  ngOnInit(): void {
  }

  createCategory():void{
    if (this.category.name != "") {
      this.categoryService.createCategory(this.category.name);
      this.router.navigateByUrl('/system/categories')
    }
  }

}
