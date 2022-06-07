import { Component, OnInit } from '@angular/core';
import { StoresService } from 'src/app/services/Stores/stores.service';
import { Store } from 'src/models/Store';


@Component({
  selector: 'app-stores',
  templateUrl: './stores.component.html',
  styleUrls: ['./stores.component.css']
})
export class StoresComponent implements OnInit {

  stores: Store[] = [];
  stores_length: number | undefined;

  constructor(private storesService: StoresService) { }

  ngOnInit(): void {
    this.getAllStores();
  }

  getAllStores() {
    this.storesService.getAllStores().subscribe(
      data => {
        this.stores = data;
        this.stores_length = this.stores.length;
      }
    )

  }  

}
